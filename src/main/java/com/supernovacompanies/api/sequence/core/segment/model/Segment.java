package com.supernovacompanies.api.sequence.core.segment.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

public class Segment {
    @Getter @Setter
    private AtomicLong value = new AtomicLong(0);
    @Getter @Setter
    private volatile long max;
    @Getter @Setter
    private volatile int step;
    @Getter @Setter
    private volatile int maxLength;

    @Getter
    private SegmentBuffer buffer;

    public Segment(SegmentBuffer buffer) {
        this.buffer = buffer;
    }

    public long getIdle() {
        return this.getMax() - getValue().get();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Segment(");
        sb.append("value:");
        sb.append(value);
        sb.append(",max:");
        sb.append(max);
        sb.append(",step:");
        sb.append(step);
        sb.append(")");
        return sb.toString();
    }
}
