package com.popa.books.modules;

import com.popa.books.AbstractBooksApplicationTests;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EdituraTests extends AbstractBooksApplicationTests {

    /**
     * Tests the url: /editura, method: GET, params: page, limit
     *
     * @throws Exception
     */
    @Test
    public void testGetEdituraList() throws Exception {
        this.mockMvc.perform(get("/editura").
                session(session).
                param("page", "1").
                param("limit", "10").
                accept(MediaType.APPLICATION_JSON)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("$.content[*].idEditura").exists()).
                andExpect(jsonPath("$.totalElements", greaterThan(0))).
                andExpect(jsonPath("$.totalPages").exists());
    }
}
