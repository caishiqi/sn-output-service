package com.supernovacompanies.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "apiResponse", description = "Common Api Response")
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = -8987146499044811408L;

    @ApiModelProperty(value = "general return status", required = true)
    private Integer code;

    @ApiModelProperty(value = "general return information", required = true)
    private String message;

    @ApiModelProperty(value = "general return data", required = true)
    private T data;
}