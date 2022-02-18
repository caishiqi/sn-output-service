package com.supernovacompanies.api.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

/**
 * @author: nolan
 * @date: 8/3/21
 */
@Slf4j
public class FreeMarkerUtils {

    public static String createContentByFreeMarker(String templateContent, Object params)
            throws TemplateException, IOException {
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setClassicCompatible(true);
        cfg.setNumberFormat("#.##########");
        Template template = new Template(null, templateContent, cfg);
        templateContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
        return templateContent;
    }
}
