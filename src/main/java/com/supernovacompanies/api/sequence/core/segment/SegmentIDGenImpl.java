package com.supernovacompanies.api.sequence.core.segment;

import com.supernovacompanies.api.sequence.core.common.Result;
import com.supernovacompanies.api.sequence.core.common.Status;
import com.supernovacompanies.api.sequence.core.segment.model.Segment;
import com.supernovacompanies.api.sequence.core.segment.model.SegmentBuffer;
import com.supernovacompanies.core.dal.configuration.business.service.IBizSystemService;
import com.supernovacompanies.core.dal.configuration.model.common.dto.CfgBusinessSequenceSettingDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author michael.he
 */
@Slf4j
public class SegmentIDGenImpl implements IDGen {
    /**
     * Exception code when IDCache is not initialized successfully
     */
    private static final String EXCEPTION_ID_IDCACHE_INIT_FALSE = "-1";

    /**
     * Exception code when the key does not exist
     */
    private static final String EXCEPTION_ID_KEY_NOT_EXISTS = "-2";

    /**
     * Exception code when two Segments in SegmentBuffer are not loaded from DB
     */
    private static final String EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL = "-3";

    /**
     * The maximum step size does not exceed 100,0000
     */
    private static final int MAX_STEP = 1000000;

    /**
     * The duration of a segment is 15 minutes
     */
    private static final long SEGMENT_DURATION = 15 * 60 * 1000L;
    private ExecutorService service = new ThreadPoolExecutor(5, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new UpdateThreadFactory());
    private volatile boolean initOK = false;
    private Map<String, SegmentBuffer> cache = new ConcurrentHashMap<>();

    private IBizSystemService dao;

    public static class UpdateThreadFactory implements ThreadFactory {

        private static int threadInitNumber = 0;

        private static synchronized int nextThreadNum() {
            return threadInitNumber++;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Thread-Segment-Update-" + nextThreadNum());
        }
    }

    @Override
    public boolean init() {
        log.info("Init ...");
        //Ensure that the initialization is successful after loading into kv
        updateCacheFromDb();
        initOK = true;
        updateCacheFromDbAtEveryMinute();
        return initOK;
    }

    private void updateCacheFromDbAtEveryMinute() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r);
            t.setName("check-idCache-thread");
            t.setDaemon(true);
            return t;
        });
        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                updateCacheFromDb();
            }
        }, 60, 60, TimeUnit.SECONDS);
    }

    private void updateCacheFromDb() {
        log.info("[begin] update cache from db");
        try {
            List<String> dbTags = dao.getAllBizTags();
            if (dbTags == null || dbTags.isEmpty()) {
                return;
            }
            List<String> cacheTags = new ArrayList<>(cache.keySet());
            Set<String> insertTagsSet = new HashSet<>(dbTags);
            Set<String> removeTagsSet = new HashSet<>(cacheTags);

            //The newly added tags in db are poured into the cache
            for (int i = 0; i < cacheTags.size(); i++) {
                String tmp = cacheTags.get(i);
                if (insertTagsSet.contains(tmp)) {
                    insertTagsSet.remove(tmp);
                }
            }
            for (String tag : insertTagsSet) {
                SegmentBuffer buffer = new SegmentBuffer();
                buffer.setKey(tag);
                Segment segment = buffer.getCurrent();
                segment.setValue(new AtomicLong(0));
                segment.setMax(0);
                segment.setStep(0);
                cache.put(tag, buffer);
                log.info("Add tag {} from db to IdCache, SegmentBuffer {}", tag, buffer);
            }

            //The invalid tags in the cache are deleted from the cache.
            for (int i = 0; i < dbTags.size(); i++) {
                String tmp = dbTags.get(i);
                if (removeTagsSet.contains(tmp)) {
                    removeTagsSet.remove(tmp);
                }
            }
            for (String tag : removeTagsSet) {
                cache.remove(tag);
                log.info("Remove tag {} from IdCache", tag);
            }
        } catch (Exception e) {
            log.warn("update cache from db exception", e);
        }
        log.info("[end] update cache from db");
    }

    @Override
    public Result get(final String key) {
        if (!initOK) {
            return new Result(EXCEPTION_ID_IDCACHE_INIT_FALSE, Status.EXCEPTION);
        }
        if (cache.containsKey(key)) {
            SegmentBuffer buffer = cache.get(key);
            if (!buffer.isInitOk()) {
                synchronized (buffer) {
                    if (!buffer.isInitOk()) {
                        try {
                            updateSegmentFromDb(key, buffer.getCurrent());
                            log.info("Init buffer. Update leaf key {} {} from db", key, buffer.getCurrent());
                            buffer.setInitOk(true);
                        } catch (Exception e) {
                            log.warn("Init buffer {} exception", buffer.getCurrent(), e);
                        }
                    }
                }
            }
            return getIdFromSegmentBuffer(cache.get(key));
        }
        return new Result(EXCEPTION_ID_KEY_NOT_EXISTS, Status.EXCEPTION);
    }

    public void updateSegmentFromDb(String key, Segment segment) {
        SegmentBuffer buffer = segment.getBuffer();
        CfgBusinessSequenceSettingDTO sequenceSetting;

        if (!buffer.isInitOk()) {
            sequenceSetting = dao.updateMaxIdByBizTag(key);
            buffer.setStep(sequenceSetting.getStep());
            buffer.setMinStep(sequenceSetting.getStep());
        } else if (buffer.getUpdateTimestamp() == 0) {
            sequenceSetting = dao.updateMaxIdByBizTag(key);
            buffer.setUpdateTimestamp(System.currentTimeMillis());
            buffer.setMinStep(sequenceSetting.getStep());
        } else {
            long duration = System.currentTimeMillis() - buffer.getUpdateTimestamp();
            int nextStep = buffer.getStep();
            if (duration < SEGMENT_DURATION) {
                if (nextStep * 2 > MAX_STEP) {
                    //do nothing
                } else {
                    nextStep = nextStep * 2;
                }
            } else if (duration < SEGMENT_DURATION * 2) {
                //do nothing with nextStep
            } else {
                nextStep = nextStep / 2 >= buffer.getMinStep() ? nextStep / 2 : nextStep;
            }
            log.info("leafKey[{}], step[{}], duration[{}mins], nextStep[{}]", key, buffer.getStep(), String.format("%.2f", ((double) duration / (1000 * 60))), nextStep);

            sequenceSetting = dao.updateMaxIdByBizTagAndStep(key, nextStep);

            buffer.setUpdateTimestamp(System.currentTimeMillis());
            buffer.setStep(nextStep);
            buffer.setMinStep(sequenceSetting.getStep());
        }
        // must set value before set max
        long value = sequenceSetting.getMaxId() - buffer.getStep();
        segment.getValue().set(value);
        segment.setMax(sequenceSetting.getMaxId());
        segment.setMaxLength(sequenceSetting.getMaxLength());
        segment.setStep(buffer.getStep());
    }

    public Result getIdFromSegmentBuffer(final SegmentBuffer buffer) {
        while (true) {
            buffer.rLock().lock();
            try {
                final Segment segment = buffer.getCurrent();
                if (!buffer.isNextReady() && (segment.getIdle() < 0.9 * segment.getStep()) && buffer.getThreadRunning().compareAndSet(false, true)) {
                    service.execute(new Runnable() {
                        @Override
                        public void run() {
                            Segment next = buffer.getSegments()[buffer.nextPos()];
                            boolean updateOk = false;
                            try {
                                updateSegmentFromDb(buffer.getKey(), next);
                                updateOk = true;
                                log.info("update segment {} from db {}", buffer.getKey(), next);
                            } catch (Exception e) {
                                log.warn(buffer.getKey() + " updateSegmentFromDb exception", e);
                            } finally {
                                if (updateOk) {
                                    buffer.wLock().lock();
                                    buffer.setNextReady(true);
                                    buffer.getThreadRunning().set(false);
                                    buffer.wLock().unlock();
                                } else {
                                    buffer.getThreadRunning().set(false);
                                }
                            }
                        }
                    });
                }
                long value = segment.getValue().getAndIncrement();
                if (value < segment.getMax()) {
                    String seq = buffer.getKey() + StringUtils.leftPad(String.valueOf(value), segment.getMaxLength(), "0");
                    return new Result(seq, Status.SUCCESS);
                }
            } finally {
                buffer.rLock().unlock();
            }
            waitAndSleep(buffer);
            buffer.wLock().lock();
            try {
                final Segment segment = buffer.getCurrent();
                long value = segment.getValue().getAndIncrement();
                if (value < segment.getMax()) {
                    String seq = buffer.getKey() + StringUtils.leftPad(String.valueOf(value), segment.getMaxLength(), "0");
                    return new Result(seq, Status.SUCCESS);
                }
                if (buffer.isNextReady()) {
                    buffer.switchPos();
                    buffer.setNextReady(false);
                } else {
                    log.error("Both two segments in {} are not ready!", buffer);
                    return new Result(EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL, Status.EXCEPTION);
                }
            } finally {
                buffer.wLock().unlock();
            }
        }
    }

    private void waitAndSleep(SegmentBuffer buffer) {
        int roll = 0;
        while (buffer.getThreadRunning().get()) {
            roll += 1;
            if (roll > 10000) {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    log.warn("Thread {} Interrupted", Thread.currentThread().getName());
                }
                break;
            }
        }
    }

    public List<CfgBusinessSequenceSettingDTO> getAllBusinessSequenceSetting() {
        return dao.getAllBusinessSequenceSetting();
    }

    public Map<String, SegmentBuffer> getCache() {
        return cache;
    }

    public IBizSystemService getDao() {
        return dao;
    }

    public void setDao(IBizSystemService dao) {
        this.dao = dao;
    }
}
