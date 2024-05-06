package com.example.books.services.impl;

import com.example.books.domain.BookDTO;
import com.example.books.domain.BookEntity;
import com.example.books.repositories.BookRepository;
import com.example.books.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean bookExists(BookDTO book) {
        return bookRepository.existsById((book.getIsbn()));
    }

    @Override
    public BookDTO create(BookDTO book){
        BookEntity bookEntity = bookDTOtoBookEntity(book);
        return bookEntitytoBookDTO(bookRepository.save(bookEntity));
    }

    @Override
    public Optional<BookDTO> findById(String isbn) {
        Optional<BookEntity> foundBook = bookRepository.findById(isbn);
        return foundBook.map(book -> bookEntitytoBookDTO(book));
    }

    @Override
    public List<BookDTO> listBooks() {
        List<BookEntity> foundBooks = bookRepository.findAll();
        return foundBooks.stream().map(book -> bookEntitytoBookDTO(book)).collect(Collectors.toList());
    }

    @Override
    public BookDTO updateBook(BookDTO book) {
        BookEntity bookEntity = bookDTOtoBookEntity(book);
        deleteBookById(book.getIsbn());
        return bookEntitytoBookDTO(bookRepository.save(bookEntity));
    }

    @Override
    public void deleteBookById(String isbn) {
        try {
            bookRepository.deleteById(isbn);
        } catch (EmptyResultDataAccessException e) {
            log.debug("Attempted to delete non-existing book", e);
        }
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
