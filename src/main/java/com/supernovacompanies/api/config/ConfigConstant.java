package com.supernovacompanies.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chen
 * @date 10/24/2017
 */
@Component
@Getter
public class ConfigConstant {

    public static final String MULTI_VALUE_SEPARATOR = ",";

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${virus-scan.bucket}")
    private String virusScanBucket;

    @Value("${virus-scan.overtime.millis}")
    private Integer virusScanOvertimeMillis;

    @Value("${notification.sns.arn}")
    private String notificationSnsArn;

    @Value("${s3.bucket}")
    private String s3Bucket;

    @Value("${static.resource.s3.bucket}")
    private String staticResourceS3Bucket;

    @Value("${template.file.s3.bucket}")
    private String templateS3Bucket;

    @Value("${template.file.s3.enable}")
    private Boolean templateS3BucketEnable;

    @Value("${file.transport.token.expire.seconds}")
    private Long redisTokenExpireSeconds;

    @Value("${spring.cluster.name}")
    private String clusterName;

    @Value("${configurationSet.name}")
    private String configurationSetName;

    @Value("${pdf-engine.debug}")
    private boolean engineDebug;

    @Value(value = "${temp.path}")
    private String tempPath;

    @Value(value = "${virus-scan.enabled}")
    private boolean virusScanEnabled;

    @Value("${pdf.file.tempDir}")
    private String pdfTempDir;

    @Value("${callback.retry.delay}")
    private Integer delaySeconds;

    @Value("${callback.retry.times}")
    private Integer retryTimes;

    @Value("${callback.external.host.suffix}")
    private String externalHostSuffix;
}
