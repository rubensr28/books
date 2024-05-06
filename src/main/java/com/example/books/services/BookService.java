package com.example.books.services;

import com.example.books.domain.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {

    boolean bookExists(BookDTO book);
    BookDTO create (BookDTO book);
    Optional<BookDTO> findById(String isbn);
    List<BookDTO> listBooks();
    BookDTO updateBook(BookDTO book);
    void deleteBookById(String isbn);
}
