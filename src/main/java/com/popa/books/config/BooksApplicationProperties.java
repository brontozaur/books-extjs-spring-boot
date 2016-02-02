package com.popa.books.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * This file loads some custom props from application.properties file.
 * Props are accesible after autowiring this class as needed.
 */
@Component
public class BooksApplicationProperties {

    private static final Logger logger = LoggerFactory.getLogger(BooksApplicationProperties.class);

    /**
     * This will be the relative path to the upload folder.
     * Upload folder will be linked to this relative path.
     */
    @Value("${uploads.path}")
    private String uploadsRelativePath;

    @Value("${files.upload.mac.dir}")
    private String macOSUploadsDir;

    @Value("${files.upload.win.dir}")
    private String winUploadsDir;

    @Value("${server.error.include-stacktrace}")
    private String isIncludingStacktrace;

    public boolean isIncludingStacktrace() {
        return "always".equals(isIncludingStacktrace);
    }

    public String getUploadsRelativePath() {
        return uploadsRelativePath;
    }

    // /usr/local/logs
    public String getRootUploadDir() {
        String uploadDir = null;
        if (System.getProperty("os.name").contains("Windows")) {
            uploadDir = winUploadsDir;
        } else if (System.getProperty("os.name").contains("Mac") ||
                System.getProperty("os.name").contains("nux")) {
            uploadDir = macOSUploadsDir;
        } else {
            throw new IllegalArgumentException("Unsupported OS: " + System.getProperty("os.name"));
        }
        File uploadDirAsFile = new File(uploadDir);
        logger.info("Root upload dir: {}", uploadDirAsFile.getAbsolutePath());
        if (!uploadDirAsFile.exists() || !uploadDirAsFile.isDirectory()) {
            if (!uploadDirAsFile.mkdirs()) {
                throw new IllegalArgumentException("Cannot create directory: " + uploadDir);
            }
        }
        return uploadDirAsFile.getAbsolutePath();
    }

    public String getFullUploadDir() {
        String uploadDir = getRootUploadDir() + uploadsRelativePath;
        File uploadDirAsFile = new File(uploadDir);
        if (!uploadDirAsFile.exists() || !uploadDirAsFile.isDirectory()) {
            if (!uploadDirAsFile.mkdirs()) {
                throw new IllegalArgumentException("Cannot create directory: " + uploadDir);
            }
        }
        return uploadDir;
    }


}
