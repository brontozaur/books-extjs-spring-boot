package com.popa.books;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.popa.books.util.BookConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BooksApplication.class)
@WebAppConfiguration
@ActiveProfiles(BookConstants.PROFILE_TEST)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
public class BooksApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockHttpSession session;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysExpect(status().isOk())
                .build();
    }

    @Test
    public void contextLoads() {
        Assert.assertTrue(true);
    }

    /**
     * Tests the url: /autor, method: GET, params: page, limit
     *
     * @throws Exception
     */
    @Test
    public void testGetAutorList() throws Exception {
        this.mockMvc.perform(get("/autor").
                session(session).
                param("page", "1").
                param("limit", "10").
                accept(MediaType.APPLICATION_JSON)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("$.content[*].autorId").exists()).
                andExpect(jsonPath("$.totalElements", greaterThan(0))).
                andExpect(jsonPath("$.totalPages").exists());
    }

    /**
     * Tests the url: /autor/tree, method: GET
     *
     * @throws Exception
     */
    @Test
    public void testGetAutorTree() throws Exception {
        this.mockMvc.perform(get("/autor/tree").
                session(session).
                accept(MediaType.ALL)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
     * Tests the url: /autor/1, method: GET
     *
     * @throws Exception
     */
    @Test
    public void testGetAutor() throws Exception {
        this.mockMvc.perform(get("/autor/1").
                session(session).
                accept(MediaType.APPLICATION_JSON)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("autorId", is(1))).
                andExpect(jsonPath("nume", is("Savatie Bastovoi"))).
                andExpect(jsonPath("dataNasterii", is("2016-02-08")));
    }

    /**
     * Tests the url: /autor, method: POST
     *
     * @throws Exception
     */
    @Test
    public void testCreateAutor() throws Exception {
        UUID autorName = UUID.randomUUID();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("nume", autorName);
        requestBody.put("dataNasterii", new Date());

        this.mockMvc.perform(post("/autor").
                content(OBJECT_MAPPER.writeValueAsString(requestBody)).
                session(session).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON_UTF8)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("nume", is(autorName.toString())));
    }

    /**
     * Tests the url: /autor, method: PUT
     *
     * @throws Exception
     */
    @Test
    public void testUpdateAutor() throws Exception {

        String updatedNume = "Updated nume " + UUID.randomUUID();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("autorId", 1);
        requestBody.put("nume", updatedNume);
        requestBody.put("dataNasterii", new Date());

        this.mockMvc.perform(put("/autor").
                content(OBJECT_MAPPER.writeValueAsString(requestBody)).
                session(session).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON_UTF8)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("nume", is(updatedNume)));
    }

    /**
     * Tests the url: /autor/{id}, method: DELETE
     *
     * @throws Exception
     */
    @Test
    public void deleteAutor() throws Exception {

        this.mockMvc.perform(delete("/autor/1").
                session(session).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.ALL)).

                andDo(print()).

                andExpect(status().isOk());
    }

}
