package com.popa.books.controller;

import com.popa.books.AbstractBooksApplicationTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AutorControllerTest extends AbstractBooksApplicationTest {

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

    @Test
    public void testGetAutori() throws Exception {
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

    @Test
    public void testGetAutoriTree() throws Exception {
        this.mockMvc.perform(get("/autor/tree").
                session(session).
                accept(MediaType.ALL)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testCreateNewAutor() throws Exception {
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

    @Test
    public void testDeleteAutor() throws Exception {
        this.mockMvc.perform(delete("/autor/2").
                session(session).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.ALL)).

                andDo(print()).

                andExpect(status().isOk());
    }
}