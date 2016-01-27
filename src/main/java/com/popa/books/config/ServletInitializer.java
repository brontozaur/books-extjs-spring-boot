package com.popa.books.config;

import com.popa.books.BooksApplication;
import com.popa.books.util.BookConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(ServletInitializer.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.profiles(addDefaultProfile()).sources(BooksApplication.class);
	}

	/**
	 * If no profile has been set, the DEV profile is sent automatically.
	 */
	private String addDefaultProfile() {
		String profile = System.getProperty("spring.profiles.active");
		if (profile != null) {
			logger.info("Running with Spring profile(s) {}", profile);
			return profile;
		}

		logger.warn("No Spring profile configured, running with default configuration");
		return BookConstants.PROFILE_DEV;
	}

}
