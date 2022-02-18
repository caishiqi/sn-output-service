package com.supernovacompanies.api.config;

import com.supernovacompanies.venus.resttemplate.RestTemplateUtils;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    @Primary
    public RestTemplate getRestTemplate() {
        return RestTemplateUtils.build(new DefaultResponseErrorHandler());
    }

    @Bean
    public RestTemplate getExternalRestTemplate() {
        return RestTemplateUtils.build(new DefaultResponseErrorHandler());
    }

    @Bean("coreRestTemplate")
    public RestTemplate coreRestTemplate() {
        RestTemplate venusRestTemplate = RestTemplateUtils.build(new DefaultResponseErrorHandler());
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        venusRestTemplate.setRequestFactory(requestFactory);
        return venusRestTemplate;
    }


}
