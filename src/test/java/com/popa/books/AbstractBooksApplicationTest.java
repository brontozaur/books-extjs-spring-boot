package com.popa.books;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.popa.books.util.BookConstants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BooksApplication.class)
@WebAppConfiguration
@ActiveProfiles(BookConstants.PROFILE_TEST)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
public abstract class AbstractBooksApplicationTest {

    protected MockMvc mockMvc;

    protected static final Logger logger = LoggerFactory.getLogger(AbstractBooksApplicationTest.class);

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    protected MockHttpSession session;

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Before
    public void setUp() throws Exception {
        logger.info("\n\n\t------------------Starting a test from " + getClass().getName() + " ----------------------\n");
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysExpect(status().isOk())
                .build();
    }

    @After
    public void finish(){
        logger.info("\n\n\t------------------Finished a test from " + getClass().getName() + " ----------------------\n");
    }

    @Test
    public void contextLoads() {
        Assert.assertTrue(true);
    }
}
