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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    public void testListBooksReturnsEmptyWhenNoBooks(){
        when(bookRepository.findAll()).thenReturn(new ArrayList<BookEntity>());
        List<BookDTO> result = underTestBookService.listBooks();
        assertEquals(0, result.size());
    }

    @Test
    public void testListBooksReturnsBooks(){
        List<BookEntity> testBooks = List.of(TestData.testBookEntity());
        when(bookRepository.findAll()).thenReturn(testBooks);
        List<BookDTO> result = underTestBookService.listBooks();

        assertEquals(1,result.size());
    }

    @Test
    public void testBookExistsReturnsFalseWhenNoBookExists(){
        when(bookRepository.existsById(any())).thenReturn(false);
        boolean result = underTestBookService.bookExists(TestData.testBookDTO());
        assertFalse(result);
    }

    @Test
    public void testBookExistsReturnsTrueWhenBookExists(){
        when(bookRepository.existsById(any())).thenReturn(true);
        boolean result = underTestBookService.bookExists(TestData.testBookDTO());
        assertTrue(result);
    }

    @Test
    public void bookIsUpdated(){
        BookDTO bookDTO = TestData.testBookDTO();
        BookEntity bookEntity = TestData.testBookEntity();

        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

        BookDTO result = underTestBookService.updateBook(bookDTO);
        assertEquals(bookDTO,result);
    }


}
