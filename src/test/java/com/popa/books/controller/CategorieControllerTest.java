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

public class CategorieControllerTest extends AbstractBooksApplicationTest {

    @Test
    public void testGetCategorie() throws Exception {
        this.mockMvc.perform(get("/categorie/1").
                session(session).
                accept(MediaType.APPLICATION_JSON)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("idCategorie", is(1))).
                andExpect(jsonPath("numeCategorie", is("Carte ortodoxa")));
    }

    @Test
    public void testGetCategorii() throws Exception {
        this.mockMvc.perform(get("/categorie").
                session(session).
                param("page", "1").
                param("limit", "10").
                accept(MediaType.APPLICATION_JSON)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("$.content[*].idCategorie").exists()).
                andExpect(jsonPath("$.totalElements", greaterThan(0))).
                andExpect(jsonPath("$.totalPages").exists());
    }

    @Test
    public void testCreateNewCategorie() throws Exception {
        UUID edituraName = UUID.randomUUID();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("numeCategorie", edituraName);

        this.mockMvc.perform(post("/categorie").
                content(OBJECT_MAPPER.writeValueAsString(requestBody)).
                session(session).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON_UTF8)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("numeCategorie", is(edituraName.toString())));
    }

    @Test
    public void testUpdateCategorie() throws Exception {
        String updatedNume = "Updated nume " + UUID.randomUUID();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idCategorie", 1);
        requestBody.put("numeCategorie", updatedNume);

        this.mockMvc.perform(put("/categorie").
                content(OBJECT_MAPPER.writeValueAsString(requestBody)).
                session(session).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON_UTF8)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("numeCategorie", is(updatedNume)));
    }

    @Test
    public void testDeleteCategorie() throws Exception {
        this.mockMvc.perform(delete("/categorie/2").
                session(session).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.ALL)).

                andDo(print()).

                andExpect(status().isOk());
    }
}