package com.supernovacompanies.api.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UrlPathHelper;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiResourceController;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2ControllerWebMvc;
import springfox.documentation.swagger2.web.WebMvcSwaggerTransformationFilter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author jiaqi
 * @date 05/24/2019
 */
@Configuration
@EnableSwagger2
@AutoConfigureAfter({WebMvcConfigurer.class})
public class SwaggerConfig {

    private static final String DEFAULT_PATH = "/v5/{universe}";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(java.util.Date.class, String.class)
                .securitySchemes(security());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("api-service")
                .description("api document")
                .version("v1")
                .build();
    }

    private List<SecurityScheme> security() {
        return newArrayList(
                new ApiKey("Authorization", "Authorization", "header")
        );
    }

    /**
     * SwaggerUI resource
     *
     * @param servletContext
     * @param order
     * @return
     * @throws Exception
     */
    @Bean
    public SimpleUrlHandlerMapping swaggerUrlHandlerMapping(ServletContext servletContext,
                                                            @Value("${swagger.mapping.order:10}") int order) throws Exception {
        SimpleUrlHandlerMapping urlHandlerMapping = new SimpleUrlHandlerMapping();
        Map<String, ResourceHttpRequestHandler> urlMap = new HashMap<>();
        {
            PathResourceResolver pathResourceResolver = new PathResourceResolver();
            pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/webjars/"));
            pathResourceResolver
                    .setUrlPathHelper(new UrlPathHelper());

            ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
            resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/webjars/")));
            resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
            resourceHttpRequestHandler.setServletContext(servletContext);
            resourceHttpRequestHandler.afterPropertiesSet();
            //set new path
            urlMap.put(DEFAULT_PATH + "/webjars/**", resourceHttpRequestHandler);
        }
        {
            PathResourceResolver pathResourceResolver = new PathResourceResolver();
            pathResourceResolver.setAllowedLocations(new ClassPathResource("META-INF/resources/"));
            pathResourceResolver.setUrlPathHelper(new UrlPathHelper());

            ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
            resourceHttpRequestHandler.setLocations(Arrays.asList(new ClassPathResource("META-INF/resources/")));
            resourceHttpRequestHandler.setResourceResolvers(Arrays.asList(pathResourceResolver));
            resourceHttpRequestHandler.setServletContext(servletContext);
            resourceHttpRequestHandler.afterPropertiesSet();
            //Set new path
            urlMap.put(DEFAULT_PATH + "/**", resourceHttpRequestHandler);
        }
        urlHandlerMapping.setUrlMap(urlMap);
        //adjust DispatcherServlet about SimpleUrlHandlerMapping order
        urlHandlerMapping.setOrder(order);
        return urlHandlerMapping;
    }

    /**
     * SwaggerUI API
     */
    @Controller
    @ApiIgnore
    @RequestMapping(DEFAULT_PATH)
    public static class SwaggerResourceController implements InitializingBean {
        private final ApiResourceController apiResourceController;
        private final DocumentationCache documentationCache;
        private final ServiceModelToSwagger2Mapper mapper;
        private final JsonSerializer jsonSerializer;
        private final PluginRegistry<WebMvcSwaggerTransformationFilter, DocumentationType> transformations;
        private static final String HAL_MEDIA_TYPE = "application/hal+json";
        private Swagger2ControllerWebMvc swagger2ControllerWebMvc;

        public SwaggerResourceController(ApiResourceController apiResourceController, DocumentationCache documentationCache, ServiceModelToSwagger2Mapper mapper, JsonSerializer jsonSerializer, PluginRegistry<WebMvcSwaggerTransformationFilter, DocumentationType> transformations) {
            this.apiResourceController = apiResourceController;
            this.documentationCache = documentationCache;
            this.mapper = mapper;
            this.jsonSerializer = jsonSerializer;
            this.transformations = transformations;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            swagger2ControllerWebMvc = new Swagger2ControllerWebMvc(documentationCache, mapper, jsonSerializer, transformations);
        }
        /**
         * Swagger API index URL
         *
         * @return index url
         */
        @RequestMapping
        public ModelAndView index() {
            return new ModelAndView("redirect:" + DEFAULT_PATH + "/doc.html");
        }

        @RequestMapping("/swagger-resources/configuration/security")
        @ResponseBody
        public ResponseEntity<SecurityConfiguration> securityConfiguration() {
            return apiResourceController.securityConfiguration();
        }

        @RequestMapping("/swagger-resources/configuration/ui")
        @ResponseBody
        public ResponseEntity<UiConfiguration> uiConfiguration() {
            return apiResourceController.uiConfiguration();
        }

        @RequestMapping("/swagger-resources")
        @ResponseBody
        public ResponseEntity<List<SwaggerResource>> swaggerResources() {
            return apiResourceController.swaggerResources();
        }

        @RequestMapping(value = "/v2/api-docs", method = RequestMethod.GET, produces = {APPLICATION_JSON_VALUE, HAL_MEDIA_TYPE})
        @ResponseBody
        public ResponseEntity<Json> getDocumentation(
                @RequestParam(value = "group", required = false) String swaggerGroup,
                HttpServletRequest servletRequest) {
            return swagger2ControllerWebMvc.getDocumentation(swaggerGroup, servletRequest);
        }
    }
}
