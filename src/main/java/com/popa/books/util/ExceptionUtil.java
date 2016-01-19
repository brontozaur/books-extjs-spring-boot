package com.popa.books.util;

import org.apache.commons.lang.exception.ExceptionUtils;

public class ExceptionUtil {

	private ExceptionUtil() {
	}

	public static String getExceptionCause(final Throwable exc) {
		StringBuilder cause = new StringBuilder();
		if (exc != null) {
			String rootCause = ExceptionUtils.getRootCauseMessage(exc);
			String[] rootStacktrace = ExceptionUtils.getRootCauseStackTrace(exc);
			for (int i = 0; i < rootStacktrace.length; i++) {
				if ((rootStacktrace[i] == null)) {
					continue;
				} else if (rootStacktrace[i].indexOf(rootCause) != -1) {
					cause.append(rootStacktrace[i]);
					continue;
				}
			}
		}
		return cause.toString();
	}

	public static String[] getExceptionCauseAndStackTrace(final Throwable exc) {
		StringBuilder cause = new StringBuilder();
		StringBuilder stack = new StringBuilder();
		if (exc != null) {
			String rootCause = ExceptionUtils.getRootCauseMessage(exc);
			String[] rootStacktrace = ExceptionUtils.getRootCauseStackTrace(exc);
			for (int i = 0; i < rootStacktrace.length; i++) {
				if ((rootStacktrace[i] == null)) {
					continue;
				} else if (rootStacktrace[i].indexOf(rootCause) != -1) {
					cause.append(rootStacktrace[i]);
					continue;
				}
				stack.append("\t").append(rootStacktrace[i]).append("\n");
			}
			if (cause.length() == 0) {
				final String tmp = stack.toString();
				if (tmp.indexOf('\n') != -1) {
					cause.append(tmp.substring(0, tmp.indexOf('\n')).trim());
				}
			}
		}
		if (stack.length() == 0) {
			stack.append("\t").append("Nu se cunosc detaliile erorii...");
		}
		return new String[] { cause.toString(), stack.toString() };
	}

}
