package com.example.books.util;

import com.example.books.domain.BookDTO;
import com.example.books.domain.BookEntity;

public class TestData {

    public static BookDTO testBookDTO(){
        return BookDTO.builder()
                .isbn("0123456789")
                .title("Moby-Dick or The Whale")
                .author("Herman Melville").build();
    }

    public static BookDTO testUpdateBookDTO(){
        return BookDTO.builder()
                .isbn("0123456789")
                .title("Moby-Dick")
                .author("Herman Melville").build();
    }

    public static BookEntity testBookEntity(){
        return BookEntity.builder()
                .isbn("0123456789")
                .title("Moby-Dick or The Whale")
                .author("Herman Melville").build();
    }


}
