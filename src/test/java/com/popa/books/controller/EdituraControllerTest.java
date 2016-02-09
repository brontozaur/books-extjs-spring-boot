package com.popa.books.controller;

import com.popa.books.AbstractBooksApplicationTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EdituraControllerTest extends AbstractBooksApplicationTest {

    @Test
    public void testGetEditura() throws Exception {
        this.mockMvc.perform(get("/editura/1").
                session(session).
                accept(MediaType.APPLICATION_JSON)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("idEditura", is(1))).
                andExpect(jsonPath("numeEditura", is("Cathisma")));
    }

    @Test
    public void testGetEdituri() throws Exception {
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

    @Test
    public void testGetEdituriTree() throws Exception {
        this.mockMvc.perform(get("/editura/tree").
                session(session).
                accept(MediaType.ALL)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testCreateNewEditura() throws Exception {
        UUID edituraName = UUID.randomUUID();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("numeEditura", edituraName);

        this.mockMvc.perform(post("/editura").
                content(OBJECT_MAPPER.writeValueAsString(requestBody)).
                session(session).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON_UTF8)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("numeEditura", is(edituraName.toString())));
    }

    @Test
    public void testUpdateEditura() throws Exception {
        String updatedNume = "Updated nume " + UUID.randomUUID();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idEditura", 1);
        requestBody.put("numeEditura", updatedNume);

        this.mockMvc.perform(put("/editura").
                content(OBJECT_MAPPER.writeValueAsString(requestBody)).
                session(session).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON_UTF8)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("numeEditura", is(updatedNume)));
    }

    @Test
    public void testDeleteEditura() throws Exception {
        this.mockMvc.perform(delete("/editura/2").
                session(session).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.ALL)).

                andDo(print()).

                andExpect(status().isOk());
    }
}