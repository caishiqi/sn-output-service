package com.supernovacompanies.api.config;

import com.supernova.cactus.client.api.CactusClient;
import com.supernova.cactus.client.config.CactusClientConfig;
import com.supernova.cactus.client.error.CactusClientException;
import com.supernovacompanies.api.constants.PropertyValueKeyConstants;
import com.supernovacompanies.api.config.properties.GlobalUrlsProperties;
import com.supernovacompanies.venus.configuration.service.DevelopmentConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author chen
 * @date 08/04/2018
 */
@Configuration
@Slf4j
public class CactusConfig {

    @Value("${spring.application.name}")
    private String callerApp;

    @Autowired
    private DevelopmentConfigService developmentConfigService;

    @Bean
    public CactusClient cactusClient() {
        CactusClient cactusClient = CactusClient.getClientInstance();
        try {
            CactusClientConfig cactusClientConfig = new CactusClientConfig();
            cactusClientConfig.cactusServer = getGlobalUrlProperties().getCactusUrl();
            cactusClientConfig.callerApp = callerApp;
            cactusClientConfig.restTemplate = new RestTemplate();
            cactusClient.init(cactusClientConfig);

        } catch (CactusClientException e) {
            log.error("[cactusClientInit] error: ", e);
        }
        return cactusClient;
    }

    private GlobalUrlsProperties getGlobalUrlProperties() {
        GlobalUrlsProperties globalUrlsProperties = developmentConfigService.getConfig(
                PropertyValueKeyConstants.CATEGORY_CODE_GENERAL, PropertyValueKeyConstants.KEY_GLOBAL_URLS,
                GlobalUrlsProperties.class);
        return globalUrlsProperties;
    }

}
