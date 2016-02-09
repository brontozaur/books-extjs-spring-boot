package com.popa.books.controller;

import com.popa.books.AbstractBooksApplicationTest;
import com.popa.books.model.Autor;
import com.popa.books.model.Editura;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest extends AbstractBooksApplicationTest {

    private final String BASE64_IMAGE_DATA = "R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7"; //transparent pixel
    private final String BASE64_IMAGE = BookController.BASE64_PREFIX + BASE64_IMAGE_DATA;


    @Test
    public void testGetBook() throws Exception {
        this.mockMvc.perform(get("/book/1").
                session(session).
                accept(MediaType.APPLICATION_JSON)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("bookId", is(1))).
                andExpect(jsonPath("title", is("A iubi inseamna a ierta"))).
                andExpect(jsonPath("originalTitle", is("A iubi inseamna a ierta"))).
                andExpect(jsonPath("anAparitie", is(1996))).
                andExpect(jsonPath("nrPagini", is(245))).
                andExpect(jsonPath("width", is(100))).
                andExpect(jsonPath("height", is(130))).
                andExpect(jsonPath("isbn", is("978-323-43222"))).
                andExpect(jsonPath("citita", is(true))).
                andExpect(jsonPath("serie", is("SB"))).
                andExpect(jsonPath("author.nume", is("Savatie Bastovoi"))).
                andExpect(jsonPath("author.autorId", is(1))).
                andExpect(jsonPath("author.dataNasterii", is("2016-02-08"))).
                andExpect(jsonPath("editura.numeEditura", is("Cathisma"))).
                andExpect(jsonPath("editura.idEditura", is(1))).
                andExpect(jsonPath("categorie.numeCategorie", is("Carte ortodoxa"))).
                andExpect(jsonPath("categorie.idCategorie", is(1)));
    }

    @Test
    public void testGetBooksByAutor() throws Exception {
        this.mockMvc.perform(get("/book").
                session(session).
                param("page", "1").
                param("limit", "10").
                param("filterValue", "1"). // looking for books with idAutor = 1
                param("searchType", BookController.SEARCH_TYPE_AUTORI_TREE).
                accept(MediaType.APPLICATION_JSON)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("$.content[*].bookId").exists()).
                andExpect(jsonPath("$.content[0].author.autorId", is(1))).
                andExpect(jsonPath("$.totalElements", greaterThan(0))).
                andExpect(jsonPath("$.totalPages").exists());
    }

    @Test
    public void testGetBooksByEditura() throws Exception {
        this.mockMvc.perform(get("/book").
                session(session).
                param("page", "1").
                param("limit", "10").
                param("filterValue", "1"). // looking for books with idEditura = 1
                param("searchType", BookController.SEARCH_TYPE_EDITURI_TREE).
                accept(MediaType.APPLICATION_JSON)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("$.content[*].bookId").exists()).
                andExpect(jsonPath("$.content[0].editura.idEditura", is(1))).
                andExpect(jsonPath("$.totalElements", greaterThan(0))).
                andExpect(jsonPath("$.totalPages").exists());
    }

    @Test
    public void testGetBooksByFirstLetter() throws Exception {
        this.mockMvc.perform(get("/book").
                session(session).
                param("page", "1").
                param("limit", "10").
                param("filterValue", "A"). // looking for books with title starting with A
                param("searchType", BookController.SEARCH_TYPE_BOOKS_TREE).
                accept(MediaType.APPLICATION_JSON)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("$.content[*].bookId").exists()).
                andExpect(jsonPath("$.content[0].title", startsWith("A"))).
                andExpect(jsonPath("$.totalElements", greaterThan(0))).
                andExpect(jsonPath("$.totalPages").exists());
    }

    @Test
    public void testGetBooksWithNoFilter() throws Exception {
        this.mockMvc.perform(get("/book").
                session(session).
                param("page", "1").
                param("limit", "10").
                param("filterValue", ""). // looking for books with no filters except page and limit
                param("searchType", BookController.SEARCH_TYPE_GRID).
                accept(MediaType.APPLICATION_JSON)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("$.content[*].bookId").exists()).
                andExpect(jsonPath("$.totalElements", greaterThan(0))).
                andExpect(jsonPath("$.totalPages").exists());
    }

    @Test
    public void testGetBooksTree() throws Exception {
        this.mockMvc.perform(get("/book/tree").
                session(session).
                accept(MediaType.ALL)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testCreateNewBook() throws Exception {
        // structure should match BookDTO structure, as this kind of object is the request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "Nebunul");
        requestBody.put("originalTitle", "Nebunul");
        requestBody.put("anAparitie", 2001);
        requestBody.put("nrPagini", 120);
        requestBody.put("width", 110);
        requestBody.put("height", 120);
        requestBody.put("isbn", "978-233-4382");
        requestBody.put("citita", true);
        requestBody.put("serie", "SB");
        requestBody.put("idAutor", 1);
        requestBody.put("idEditura", 1);
        requestBody.put("idCategorie", 1);
        requestBody.put("frontCoverData", BASE64_IMAGE);

        this.mockMvc.perform(post("/book").
                content(OBJECT_MAPPER.writeValueAsString(requestBody)).
                session(session).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON_UTF8)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("bookId", greaterThan(1))).
                andExpect(jsonPath("title", is("Nebunul"))).
                andExpect(jsonPath("originalTitle", is("Nebunul"))).
                andExpect(jsonPath("anAparitie", is(2001))).
                andExpect(jsonPath("nrPagini", is(120))).
                andExpect(jsonPath("width", is(110))).
                andExpect(jsonPath("height", is(120))).
                andExpect(jsonPath("isbn", is("978-233-4382"))).
                andExpect(jsonPath("citita", is(true))).
                andExpect(jsonPath("serie", is("SB"))).
                andExpect(jsonPath("bookCover.bookCoverId", greaterThan(0))).
                andExpect(jsonPath("bookCover.front", is(BASE64_IMAGE_DATA))).
                andExpect(jsonPath("bookCover.back", isEmptyOrNullString())).
                andExpect(jsonPath("author.nume", is("Savatie Bastovoi"))).
                andExpect(jsonPath("author.autorId", is(1))).
                andExpect(jsonPath("author.dataNasterii", is("2016-02-08"))).
                andExpect(jsonPath("editura.numeEditura", is("Cathisma"))).
                andExpect(jsonPath("editura.idEditura", is(1))).
                andExpect(jsonPath("categorie.numeCategorie", is("Carte ortodoxa"))).
                andExpect(jsonPath("categorie.idCategorie", is(1)));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("bookId", 1);
        requestBody.put("title", "Cronici incomode");
        requestBody.put("originalTitle", "Cronici incomode");
        requestBody.put("anAparitie", 2003);
        requestBody.put("nrPagini", 220);
        requestBody.put("width", 130);
        requestBody.put("height", 110);
        requestBody.put("isbn", "978-543-2123");
        requestBody.put("citita", false);
        requestBody.put("serie", "SB");
        requestBody.put("idCategorie", 1);
        requestBody.put("frontCoverData", BASE64_IMAGE);
        requestBody.put("backCoverData", BASE64_IMAGE);

        this.mockMvc.perform(put("/book/1").
                content(OBJECT_MAPPER.writeValueAsString(requestBody)).
                session(session).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON_UTF8)).

                andDo(print()).

                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("bookId", is(1))).
                andExpect(jsonPath("title", is("Cronici incomode"))).
                andExpect(jsonPath("originalTitle", is("Cronici incomode"))).
                andExpect(jsonPath("anAparitie", is(2003))).
                andExpect(jsonPath("nrPagini", is(220))).
                andExpect(jsonPath("width", is(130))).
                andExpect(jsonPath("height", is(110))).
                andExpect(jsonPath("isbn", is("978-543-2123"))).
                andExpect(jsonPath("citita", is(false))).
                andExpect(jsonPath("author", is((Autor) null))).
                andExpect(jsonPath("editura", is((Editura) null))).
                andExpect(jsonPath("categorie.numeCategorie", is("Carte ortodoxa"))).
                andExpect(jsonPath("bookCover.bookCoverId", greaterThan(0))).
                andExpect(jsonPath("bookCover.front", is(BASE64_IMAGE_DATA))).
                andExpect(jsonPath("bookCover.back", is(BASE64_IMAGE_DATA)));
    }

    @Test
    public void testDeleteBook() throws Exception {
        this.mockMvc.perform(delete("/book/1").
                session(session).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.ALL)).

                andDo(print()).

                andExpect(status().isOk());
    }
}