package com.popa.books.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * This file loads some custom props from application.properties file.
 * Props are accesible after autowiring this class as needed.
 */
@Component
public class BooksApplicationProperties {

    /**
     * This will be the relative path to the upload folder.
     * Upload folder will be linked to this relative path.
     */
    @Value("${covers.upload.path}")
    private String coversUploadPath;

    @Value("${covers.upload.mac.dir}")
    private String macOSCoversDir;

    @Value("${covers.upload.win.dir}")
    private String winCoversDir;

    @Value("${covers.format}")
    private String coversFormat;

    public String getCoversFormat() {
        return coversFormat;
    }

    public String getCoversUploadPath() {
        return coversUploadPath;
    }

    // /usr/local/logs
    public String getRootUploadDir() {
        String uploadDir = null;
        if (System.getProperty("os.name").contains("Windows")) {
            uploadDir = winCoversDir;
        } else if (System.getProperty("os.name").contains("Mac") ||
                System.getProperty("os.name").contains("nux")) {
            uploadDir = macOSCoversDir;
        } else {
            throw new IllegalArgumentException("Unsupported OS: " + System.getProperty("os.name"));
        }
        File uploadDirAsFile = new File(uploadDir);
        if (!uploadDirAsFile.exists() || !uploadDirAsFile.isDirectory()) {
            if (!uploadDirAsFile.mkdirs()) {
                throw new IllegalArgumentException("Cannot create directory: " + uploadDir);
            }
        }
        return uploadDir;
    }

    // /usr/local/logs/cover
    public String getFullUploadDir() {
        String uploadDir = getRootUploadDir() + coversUploadPath;
        File uploadDirAsFile = new File(uploadDir);
        if (!uploadDirAsFile.exists() || !uploadDirAsFile.isDirectory()) {
            if (!uploadDirAsFile.mkdirs()) {
                throw new IllegalArgumentException("Cannot create directory: " + uploadDir);
            }
        }
        return uploadDir;
    }


}
