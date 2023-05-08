package com.bnpfortis.bnpfortis.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }

    @Test
    void itShouldReturnNumberOfDistinctBooksIfBasketIsNull() {

        //Given
        int[] booksIds = {1, 1, 2, 2, 3, 3};
        //When

        int numberOfDistinctBooks = bookService.getNumberOfDistinctBooks(booksIds);
        //Then
        assertThat(numberOfDistinctBooks).isEqualTo(3);
    }

    @Test
    void itShouldReturnZeroIfBasketIsNull() {

        //Given//When
        int numberOfDistinctBooks = bookService.getNumberOfDistinctBooks(null);

        //Then
        assertThat(numberOfDistinctBooks).isZero();
    }

    @Test
    void itShouldReturnZeroIfBasketIsEmpty() {

        //Given
        int[] booksIds = {};

        // When
        int numberOfDistinctBooks = bookService.getNumberOfDistinctBooks(booksIds);

        //Then
        assertThat(numberOfDistinctBooks).isZero();
    }

    @Test
    void itShouldReturnBookCountsIfBasketIsNotNull() {

        //Given
        int[] booksIds = {1, 1, 2, 2, 3, 3};

        //When
        Map<Integer, Integer> bookCounts = bookService.getBookCounts(booksIds);

        //Then
        assertThat(bookCounts)
                .containsEntry(1, 2)
                .containsEntry(2, 2)
                .containsEntry(3, 2);
    }

    @Test
    void itShouldReturnEmptyMapIfBasketIsNull() {

        //Given//When
        Map<Integer, Integer> bookCounts = bookService.getBookCounts(null);

        //Then
        assertThat(bookCounts).isEmpty();
    }
}