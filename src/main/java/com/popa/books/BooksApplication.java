package com.popa.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
//@ComponentScan("com.popa.books.repository", "com.popa.books")
@EnableJpaRepositories ("com.popa.books.repository")
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
