package com.example.books.services.impl;

import com.example.books.domain.BookDTO;
import com.example.books.domain.BookEntity;
import com.example.books.repositories.BookRepository;
import com.example.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDTO create(BookDTO book){
        BookEntity bookEntity = bookDTOtoBookEntity(book);
        return bookEntitytoBookDTO(bookRepository.save(bookEntity));
    }

    private BookEntity bookDTOtoBookEntity(BookDTO book){
        return BookEntity.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();

    }

    private BookDTO bookEntitytoBookDTO(BookEntity book){
        return BookDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }


}
