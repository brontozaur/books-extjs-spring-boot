package com.popa.books.servlet.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.popa.books.dao.ExceptionUtil;

public abstract class EventHandler {

	public abstract String handleEvent(HttpServletRequest request) throws ServletException;
	
	private String errorMessage;
	private String errorStackTrace;
	private String errorRootCause;
	
	public void processError(Throwable exc){
		String[] errorDetails = ExceptionUtil.getExceptionCauseAndStackTrace(exc);
		this.errorRootCause = errorDetails[0];
		this.errorStackTrace = errorDetails[1];
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorStackTrace() {
		return errorStackTrace;
	}

	public String getErrorRootCause() {
		return errorRootCause;
	}
}
