package com.bnpfortis.bnpfortis.utils;

import com.bnpfortis.bnpfortis.exceptions.EmptyBasketException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BookUtilTest {

    @Test
    void itShouldReturnNumberOfDistinctBooksIfBasketIsNull() {

        //Given
        int[] booksIds = {1, 1, 2, 2, 3, 3};
        //When

        int numberOfDistinctBooks = BookUtil.getNumberOfDistinctBooks(booksIds);
        //Then
        assertThat(numberOfDistinctBooks).isEqualTo(3);
    }

    @Test
    void itShouldReturnZeroIfBasketIsNull() {

        //Given//When
        int numberOfDistinctBooks = BookUtil.getNumberOfDistinctBooks(null);

        //Then
        assertThat(numberOfDistinctBooks).isZero();
    }

    @Test
    void itShouldReturnZeroIfBasketIsEmpty() {

        //Given
        int[] booksIds = {};

        // When
        int numberOfDistinctBooks = BookUtil.getNumberOfDistinctBooks(booksIds);

        //Then
        assertThat(numberOfDistinctBooks).isZero();
    }

    @Test
    void itShouldReturnBookCountsIfBasketIsNotNull() {

        //Given
        int[] booksIds = {1, 1, 2, 2, 3, 3};

        //When
        Map<Integer, Integer> bookCounts = BookUtil.getBookCounts(booksIds);

        //Then
        assertThat(bookCounts)
                .containsEntry(1, 2)
                .containsEntry(2, 2)
                .containsEntry(3, 2);
    }

    @Test
    void itShouldReturnEmptyMapIfBasketIsNull() {

        //Given//When
        Map<Integer, Integer> bookCounts = BookUtil.getBookCounts(null);

        //Then
        assertThat(bookCounts).isEmpty();
    }

    @Test
    void whenGettingMinCountThrownExceptionIfDataIsNull() {
        assertThatThrownBy(() -> BookUtil.getMinCountPerBook(null))
                .isInstanceOf(EmptyBasketException.class)
                .hasMessage("No data present");
    }

    @Test
    @DisplayName("Happy path test on getting min count per book")
    void itShouldGetMinCountPerBookOnHappyPath() {

        //Given
        Map<Integer, Integer> bookCountMap = new HashMap<>();
        bookCountMap.put(1, 3);
        bookCountMap.put(2, 4);
        bookCountMap.put(3, 2);

        //When
        int minCountPerBook = BookUtil.getMinCountPerBook(bookCountMap);
        //Then
        assertThat(minCountPerBook).isEqualTo(2);
    }
}