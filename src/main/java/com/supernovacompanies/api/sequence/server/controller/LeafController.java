package com.supernovacompanies.api.sequence.server.controller;

import com.supernovacompanies.api.sequence.server.exception.LeafServerException;
import com.supernovacompanies.api.sequence.server.exception.NoKeyException;
import com.supernovacompanies.api.sequence.core.common.Result;
import com.supernovacompanies.api.sequence.core.common.Status;
import com.supernovacompanies.api.sequence.server.service.SegmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author michael.he
 */
@RestController
@Slf4j
public class LeafController {
    @Autowired
    private SegmentService segmentService;

    @RequestMapping(value = "/api/segment/get/{key}")
    public String getSegmentId(@PathVariable("key") String key) {
        return get(key, segmentService.getId(key));
    }

    private String get(@PathVariable("key") String key, Result id) {
        Result result;
        if (StringUtils.isBlank(key)) {
            throw new NoKeyException();
        }
        result = id;
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(result.toString());
        }
        return String.valueOf(result.getId());
    }
}
