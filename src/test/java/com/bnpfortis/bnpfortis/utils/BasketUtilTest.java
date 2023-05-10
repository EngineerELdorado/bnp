package com.bnpfortis.bnpfortis.utils;

import com.bnpfortis.bnpfortis.exceptions.EmptyBasketException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BasketUtilTest {

    @Test
    @DisplayName("Happy path test on getting min count per book")
    void itShouldGetMinCountPerBookOnHappyPath() {
        //Given
        Map<Integer, Integer> bookCountMap = new HashMap<>();
        bookCountMap.put(1, 3);
        bookCountMap.put(2, 4);
        bookCountMap.put(3, 2);

        //When
        int minCountPerBook = BasketUtil.getMinCountPerBook(bookCountMap);

        //Then
        assertThat(minCountPerBook).isEqualTo(2);
    }
    @Test
    @DisplayName("Should return number of distinct books")
    void itShouldReturnNumberOfDistinctBooks() {
        //Given
        int[] booksIds = {1, 1, 2, 2, 3, 3};

        //When
        int numberOfDistinctBooks = BasketUtil.getNumberOfDistinctBooks(booksIds);

        //Then
        assertThat(numberOfDistinctBooks).isEqualTo(3);
    }

    @Test
    @DisplayName("If basket is null then return 0")
    void itShouldReturnZeroIfBasketIsNull() {
        //Given//When
        int numberOfDistinctBooks = BasketUtil.getNumberOfDistinctBooks(null);

        //Then
        assertThat(numberOfDistinctBooks).isZero();
    }

    @Test
    @DisplayName("If basket is empty then return 0")
    void itShouldReturnZeroIfBasketIsEmpty() {
        //Given
        int[] booksIds = {};

        // When
        int numberOfDistinctBooks = BasketUtil.getNumberOfDistinctBooks(booksIds);

        //Then
        assertThat(numberOfDistinctBooks).isZero();
    }

    @Test
    @DisplayName("If basket not null then return counts of each book")
    void itShouldReturnBookCountsIfBasketIsNotNull() {
        //Given
        int[] booksIds = {1, 1, 2, 2, 3, 3};

        //When
        Map<Integer, Integer> bookCounts = BasketUtil.getBookCounts(booksIds);

        //Then
        assertThat(bookCounts)
                .containsEntry(1, 2)
                .containsEntry(2, 2)
                .containsEntry(3, 2);
    }

    @Test
    @DisplayName("If basket is empty then return an empty count map")
    void itShouldReturnEmptyMapIfBasketIsNull() {
        //Given//When
        Map<Integer, Integer> bookCountsMap = BasketUtil.getBookCounts(null);

        //Then
        assertThat(bookCountsMap).isEmpty();
    }

    @Test
    @DisplayName("If there is no data at all, throw an exception")
    void whenGettingMinCountItShouldThrownExceptionIfDataIsNull() {
        assertThatThrownBy(() -> BasketUtil.getMinCountPerBook(null))
                .isInstanceOf(EmptyBasketException.class)
                .hasMessage("No data present");
    }
}