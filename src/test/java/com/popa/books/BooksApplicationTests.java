package com.popa.books;

import com.popa.books.util.BookConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BooksApplication.class)
@WebAppConfiguration
@ActiveProfiles(BookConstants.PROFILE_TEST)
public class BooksApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private MockHttpSession session;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void contextLoads() {
		Assert.assertTrue(true);
	}

	@Test
	public void testAutorUrls() throws Exception {
        this.mockMvc.perform(get("/autor").
                session(session).
                param("page", "1").
                param("limit", "10").
                accept(MediaType.APPLICATION_JSON)).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("$.autorList[*].autorId").exists()). //will fail i guess if there are no authors
                andExpect(jsonPath("$.totalCount").exists()).

                andDo(log()).
                andDo(print());
	}

}
