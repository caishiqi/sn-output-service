package com.supernovacompanies.api.util;


import com.supernovacompanies.api.exception.ServiceErrorEnum;
import com.supernovacompanies.api.exception.ServiceException;
import com.supernovacompanies.venus.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author chen
 * @date 10/24/2017
 */
@Slf4j
public final class MailFileUtils {

    private static final String BASE_PATH = "templates";
    public static final String TYPE_THEME = "theme";
    public static final String TYPE_SUBJECT = "subject";
    public static final String TYPE_CONTENT = "content";

    private MailFileUtils() {
        throw new IllegalStateException("api doc class");
    }

    public static String getFile(String type, String templateName) {
        String emailTitlePath = BASE_PATH + File.separator + type + File.separator + templateName;
        try {
            return FileUtil.readFileFromClassPath(emailTitlePath);
        } catch (Exception e) {
            log.error("[getFile] file not exist type:{}, name:{}, Exception:", type, templateName, e);
            throw new ServiceException(ServiceErrorEnum.TEMPLATE_FILE_NOTE_EXIST, e);
        }
    }


    public static String toString(String path) {
        InputStream inputStream = FileUtils.class.getResourceAsStream(path);
        String content = null;
        try {
            content = IOUtils.toString(inputStream, Charset.defaultCharset());
        } catch (IOException e) {
            log.error("[toString] read file to string failure:", e);
        }
        return content;
    }
}
