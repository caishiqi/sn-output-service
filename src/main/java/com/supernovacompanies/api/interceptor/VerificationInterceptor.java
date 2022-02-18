package com.supernovacompanies.api.interceptor;

import com.supernovacompanies.api.util.ApiDocThreadLocalHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author admin
 */
@Component
public class VerificationInterceptor extends HandlerInterceptorAdapter {

    private static final String UNIVERSE = "universe";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map<String, String> pathVariables =
                (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVariables == null || !pathVariables.containsKey(UNIVERSE)) {
            return false;
        }
        String universe = pathVariables.get("universe");
        ApiDocThreadLocalHolder.setUniverse(universe);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) {
        ApiDocThreadLocalHolder.clear();
    }
}

