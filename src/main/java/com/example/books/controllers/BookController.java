package com.example.books.controllers;

import com.example.books.domain.BookDTO;
import com.example.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping(path = "/{isbn}")
    public ResponseEntity<BookDTO> createBook(@PathVariable String isbn, @RequestBody BookDTO book){
        if(!book.getIsbn().equals(isbn)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        BookDTO createdBook = bookService.create(book);
        return new ResponseEntity<BookDTO>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{isbn}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable String isbn, @RequestBody BookDTO book){

        if(!book.getIsbn().equals(isbn))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else if (!bookService.bookExists(book))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        BookDTO updatedBook = bookService.updateBook(book);
        return new ResponseEntity<BookDTO>(updatedBook, HttpStatus.OK);
    }

    @GetMapping(path = "/{isbn}")
    public ResponseEntity<BookDTO> readBook(@PathVariable String isbn){
        Optional<BookDTO> foundBook = bookService.findById(isbn);

        return foundBook.map(book -> new ResponseEntity<BookDTO>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<BookDTO>(HttpStatus.NOT_FOUND));
    }

    @GetMapping()
    public ResponseEntity<List<BookDTO>> listBooks(){
        return new ResponseEntity<List<BookDTO>>(bookService.listBooks(), HttpStatus.OK);
    }
}
