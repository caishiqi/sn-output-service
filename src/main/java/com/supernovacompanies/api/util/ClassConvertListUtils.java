package com.supernovacompanies.api.util;

import com.fasterxml.jackson.databind.JavaType;
import com.supernovacompanies.venus.util.JsonMapperUtils;

import java.util.List;

/**
 * @author jiaqi
 * @date 11/25/2019
 */
public class ClassConvertListUtils {

    public static <T> List<T> convertList(List<?> sourceList, Class<T> type) {
        JsonMapperUtils jsonMapperUtils = JsonMapperUtils.alwaysMapper();
        JavaType javaType = jsonMapperUtils.contructCollectionType(List.class, type);
        return jsonMapperUtils.fromJson(jsonMapperUtils.toJson(sourceList), javaType);
    }

    public static <T> T convertObject(Object obj, Class<T> type) {
        JsonMapperUtils jsonMapperUtils = JsonMapperUtils.alwaysMapper();
        return jsonMapperUtils.fromJson(jsonMapperUtils.toJson(obj), type);
    }
}
