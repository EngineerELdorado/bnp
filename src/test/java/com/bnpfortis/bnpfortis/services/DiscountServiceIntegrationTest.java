package com.bnpfortis.bnpfortis.services;

import com.bnpfortis.bnpfortis.exceptions.BookNotFoundException;
import com.bnpfortis.bnpfortis.exceptions.EmptyBasketException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class DiscountServiceIntegrationTest {

    @Autowired
    private DiscountService discountService;

    /**
     * My own added test cases. Meaning these cases were not mentionned in the assignment
     */

    @Test
    @DisplayName("If the basket is empty, throw an exception and send appropriate error message")
    void givenBasketIsEmptyThenThrowTheAppropriateExceptionAndAppropriateMessage() {
        //Given
        int[] booksIds = {};

        //When //Then
        assertThatThrownBy(() -> discountService.calculatePurchaseDiscount(booksIds))
                .isInstanceOfAny(EmptyBasketException.class)
                .hasMessage("The basket is empty");
    }

    @Test
    @DisplayName("If the basket is null, throw an exception and send appropriate error message")
    void givenBasketIsNullThenTrowTheAppropriateExceptionAndAppropriateMessage() {
        //Given //When //Then
        assertThatThrownBy(() -> discountService.calculatePurchaseDiscount(null))
                .isInstanceOfAny(EmptyBasketException.class)
                .hasMessage("No basket present");
    }

    @Test
    @DisplayName("If basket contains any book that is not found then throw an exception")
    void givenBasketContainsUnknownBookThenThrowAppropriateExceptionWithAppropriateMessage() {
        //Given
        int[] booksIds = {6, 7};

        //When //Then
        assertThatThrownBy(() -> discountService.calculatePurchaseDiscount(booksIds))
                .isInstanceOfAny(BookNotFoundException.class)
                .hasMessage("Your basket contain one or more books that are not found");
    }

    @Test
    @DisplayName("If You buy only one book. there is no discount")
    void givenBasketHasOnlyOneBookThenApplyNoDiscount() {
        //Given
        int[] booksIds = {1};

        //When
        double result = discountService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(50);
    }

    @Test
    @DisplayName("Given only identical books give no discount")
    void givenBasketHasOnlyIdenticalBooksTheApplyNoDiscount() {
        //Given
        int[] booksIds = {1, 1};

        //When
        double result = discountService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(100);
    }

    @Test
    @DisplayName("Test for the scenario where the buyer has two copies of each book")
    void givenBasketHasTwoCopiesOfEachBookThenApplyAppropriateDiscount() {
        //Given
        int[] booksIds = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5};

        //When
        double result = discountService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(375);
    }

    /**
     * Test Cases that were mentioned in the assignment
     */

    @Test
    @DisplayName("If You buy two different books from the series, you get a 5% discount on those two books.")
    void givenBasketHasTwoDistinctBooksThenApplyDiscountOf5Percent() {
        //Given
        int[] booksIds = {1, 2};

        //When
        double result = discountService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(95);
    }

    @Test
    @DisplayName("If you buy 3 different books, you get a 10% discount")
    void givenBasketHasThreeDistinctBooksThenApplyDiscountOf10Percent() {
        //Given
        int[] booksIds = {1, 2, 5};

        //When
        double result = discountService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(135);
    }

    @Test
    @DisplayName("With 4 different books, you get a 20% discount")
    void givenBasketHasFourDistinctBooksThenApplyDiscountOf20Percent() {
        //Given
        int[] booksIds = {1, 2, 4, 5};

        //When
        double result = discountService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(160);
    }

    @Test
    @DisplayName("If you go for the whole hog, and buy all 5, you get a huge 25% discount")
    void givenBasketHasFiveDistinctBooksThenApplyDiscountOf25Percent() {
        //Given
        int[] booksIds = {1, 2, 3, 4, 5};

        //When
        double result = discountService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(187.5);
    }

    @Test
    @DisplayName("if you buy, say, 4 books, of which 3 are different titles," +
            " you get a 10% discount on the 3 that form part of a set, but the 4th book still costs 50 EUR")
    void givenBaskHasFourBooksOfWhich3AreDistinctThenApplyAppropriateDiscount() {
        //Given
        int[] booksIds = {1, 2, 3, 3};

        //When
        double result = discountService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(185);
    }

    @Test
    @DisplayName("Final example from the assignment")
    void testFinalExample() {
        //Given
        int[] booksIds = {1, 1, 2, 2, 3, 3, 4, 5};

        //When
        double result = discountService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(322.5);
    }
}