package com.popa.books;

import com.popa.books.util.BookConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BooksApplication.class)
@WebAppConfiguration
@ActiveProfiles(BookConstants.PROFILE_TEST)
public class BooksApplicationTests {

	@Test
	public void contextLoads() {
		System.err.println("--------test environment-----------");
	}

}
