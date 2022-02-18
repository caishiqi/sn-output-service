package com.supernovacompanies.api;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.supernovacompanies.venus.simpleflake.SimpleFlake;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @author chen
 * @date 10/24/2017
 */
@SpringBootApplication
@EntityScan(basePackages = "com.supernovacompanies.api.entity")
@ComponentScan(basePackages = {
        "com.supernovacompanies.api",
        "com.supernovacompanies.venus.config",
        "com.supernovacompanies.venus.configuration",
        "com.supernovacompanies.encryption",
        "com.supernovacompanies.core.dal",
        "com.supernovacompanies.venus.exception"
})
public class SnOutputServiceApplication {

    public static void main(String[] args) {
        SpringApplication
                springApplication = new SpringApplication(SnOutputServiceApplication.class);
        springApplication.run(args);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public SimpleFlake simpleFlake() {
        return SimpleFlake.getInstance();
    }


    @Bean
    public EmailValidator emailValidator() {
        return new EmailValidator();
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSqs) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setWaitTimeOut(20);
        return factory;
    }

    @Bean
    @Profile("!supernova")
    public AmazonS3 amazonS3(){
        return AmazonS3ClientBuilder.defaultClient();
    }
}
