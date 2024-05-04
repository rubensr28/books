package com.example.books.controllers;

import com.example.books.domain.BookDTO;
import com.example.books.services.BookService;
import com.example.books.util.TestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Test
    public void testBookIsCreated() throws Exception {
        BookDTO book = TestData.testBookDTO();
        String uri = MessageFormat.format("/books/{0}",book.getIsbn());
        String bookJson = (new ObjectMapper()).writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    public void testBookIdDiffersInPathAndBody() throws Exception {
        BookDTO book = TestData.testBookDTO();
        String bookJson = (new ObjectMapper()).writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/987654321")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testReadBookReturns404WhenNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/987654321"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testReadBookReturns200WhenFound() throws Exception {
        BookDTO book = TestData.testBookDTO();
        bookService.create(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/"+ book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testReadBookReturnsCorrectBookWhenFound() throws Exception {
        BookDTO book = TestData.testBookDTO();
        bookService.create(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/books/"+ book.getIsbn()))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

}
