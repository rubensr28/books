package com.example.books.controllers;

import com.example.books.domain.BookDTO;
import com.example.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PutMapping(path = "/{isbn}")
    public ResponseEntity<BookDTO> createBook(@PathVariable String isbn, @RequestBody BookDTO book){
        if (!book.getIsbn().equals(isbn)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        BookDTO createdBook = bookService.create(book);
        return new ResponseEntity<BookDTO>(createdBook, HttpStatus.CREATED);
    }
}
