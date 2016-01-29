package com.popa.books.model.api;

import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Utility class to hold error details for error response.
 */
public class JsonErrorDescriptor {

    public Integer status;
    public String statusDecoded;
    public String error;
    public String message;
    public String timeStamp;
    public String trace;
    public String url;
    public String method;
    public String serverInfo;
    public String realPath;
    public String userAgent;
    public String protocol;
    public String accept;
    public String parameters;


    public JsonErrorDescriptor(int status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.error = (String) errorAttributes.get("error");
        this.message = (String) errorAttributes.get("message");
        this.timeStamp = errorAttributes.get("timestamp").toString();
        this.trace = (String) errorAttributes.get("trace");
        HttpStatus httpStatus = HttpStatus.valueOf(status);
        if (httpStatus != null) {
            this.statusDecoded = httpStatus.getReasonPhrase();
        } else {
            this.statusDecoded = "Unknown HTTP error code; " + status;
        }
        this.url = (String) errorAttributes.get("url");
        this.method = (String) errorAttributes.get("method");
        this.serverInfo = (String) errorAttributes.get("serverInfo");
        this.realPath = (String) errorAttributes.get("realPath");
        this.userAgent = (String) errorAttributes.get("userAgent");
        this.protocol = (String) errorAttributes.get("protocol");
        this.accept = (String) errorAttributes.get("accept");
        this.parameters = (String) errorAttributes.get("parameters");
    }
}
