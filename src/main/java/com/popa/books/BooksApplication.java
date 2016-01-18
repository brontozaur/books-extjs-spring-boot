package com.popa.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@SpringBootApplication
public class BooksApplication {

	public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
	}
}
