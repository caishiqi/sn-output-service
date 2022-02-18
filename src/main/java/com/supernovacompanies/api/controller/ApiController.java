package com.supernovacompanies.api.controller;

import com.supernovacompanies.api.entity.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "open api")
@Slf4j
public class ApiController {

    @GetMapping("/v5/{universe}/startApplication")
    @ApiOperation(value = "test api")
    public ApiResponse<String> startApplication() {
        log.info("[startApplication] api start");
        return ApiResponse.<String>builder().code(200).message("success").data("this is test api").build();
    }
}
