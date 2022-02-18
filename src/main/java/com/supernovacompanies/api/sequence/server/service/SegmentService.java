package com.supernovacompanies.api.sequence.server.service;

import com.supernovacompanies.api.sequence.core.common.Result;
import com.supernovacompanies.api.sequence.core.segment.IDGen;
import com.supernovacompanies.api.sequence.core.segment.SegmentIDGenImpl;
import com.supernovacompanies.api.sequence.server.exception.InitException;
import com.supernovacompanies.core.dal.configuration.business.service.IBizSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author michael.he
 */
@Slf4j
@Service("SegmentService")
public class SegmentService {

    private IDGen idGen;

    public SegmentService(IBizSystemService dao) throws InitException {
        // Config ID Gen
        idGen = new SegmentIDGenImpl();
        ((SegmentIDGenImpl) idGen).setDao(dao);
        if (idGen.init()) {
            log.info("Segment Service Init Successfully");
        } else {
            throw new InitException("Segment Service Init Fail");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }
}
