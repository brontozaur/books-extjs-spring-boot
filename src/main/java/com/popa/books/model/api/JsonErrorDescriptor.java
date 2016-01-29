package com.popa.books.model.api;

import java.util.Map;

/**
 * Utility class to hold error details for error response.
 */
public class JsonErrorDescriptor {

    public Integer status;
    public String error;
    public String message;
    public String timeStamp;
    public String trace;

    public JsonErrorDescriptor(int status, Map<String, Object> errorAttributes) {
        this.status = status;
        this.error = (String) errorAttributes.get("error");
        this.message = (String) errorAttributes.get("message");
        this.timeStamp = errorAttributes.get("timestamp").toString();
        this.trace = (String) errorAttributes.get("trace");
    }
}
