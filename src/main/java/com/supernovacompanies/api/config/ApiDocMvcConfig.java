package com.supernovacompanies.api.config;

import com.supernovacompanies.api.interceptor.VerificationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ApiDocMvcConfig implements WebMvcConfigurer {

    private static final String V_5 = "/v5/**";

    @Autowired
    private VerificationInterceptor verificationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(verificationInterceptor).addPathPatterns(V_5);
    }
}
