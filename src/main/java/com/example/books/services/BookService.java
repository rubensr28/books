package com.example.books.services;

import com.example.books.domain.BookDTO;

import java.util.Optional;

public interface BookService {

    BookDTO create (BookDTO book);
    Optional<BookDTO> findById(String isbn);
}
