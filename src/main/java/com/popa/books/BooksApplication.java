package com.popa.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class BooksApplication {

	public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
	}

    @RequestMapping("/books")
    String index() {
        return "/index";
    }
}
