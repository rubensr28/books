package com.example.books.services;

import com.example.books.domain.BookDTO;
import com.example.books.domain.BookEntity;
import com.example.books.repositories.BookRepository;
import com.example.books.services.impl.BookServiceImpl;
import com.example.books.util.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl underTestBookService;

    @Test
    public void testBookIsSaved(){
        BookDTO bookDTO = TestData.testBookDTO();
        BookEntity bookEntity = TestData.testBookEntity();

        //Mocking return of bookRepository
        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

        BookDTO result = underTestBookService.create(bookDTO);
        assertEquals(bookDTO,result);
    }

    @Test
    public void testFindByIdReturnsEmptyWhenNotFound(){
        String isbn = "9876543";
        when(bookRepository.findById(eq(isbn))).thenReturn(Optional.empty());
        Optional<BookDTO> result = underTestBookService.findById(isbn);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testBookFindById(){
        BookDTO bookDTO = TestData.testBookDTO();
        BookEntity bookEntity = TestData.testBookEntity();

        when(bookRepository.findById(eq(bookDTO.getIsbn()))).thenReturn(Optional.of(bookEntity));

        Optional<BookDTO> result = underTestBookService.findById(bookDTO.getIsbn());
        assertEquals(Optional.of(bookDTO), result);
    }
}
