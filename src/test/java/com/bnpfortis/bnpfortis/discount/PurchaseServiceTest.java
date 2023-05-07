package com.bnpfortis.bnpfortis.discount;

import com.bnpfortis.bnpfortis.purchase.PurchaseService;
import com.bnpfortis.bnpfortis.purchase.exceptions.EmptyBasketException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class PurchaseServiceTest {

    @Autowired
    private PurchaseService purchaseService;

    @Test
    @DisplayName("If the basket is empty, throw an exception")
    void testEmptyBasket() {

        //Given
        int[] booksIds = {};

        //When //Then
        assertThatThrownBy(() -> purchaseService.calculatePurchaseDiscount(booksIds))
                .isInstanceOfAny(EmptyBasketException.class)
                .hasMessage("The basket is empty");
    }

    @Test
    @DisplayName("If You buy only one book. there is no discount")
    void testBasketWithOnlyOneBook() {

        //Given
        int[] booksIds = {1};

        //When
        double result = purchaseService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(50);
    }

    @Test
    @DisplayName("Given only identical books give no discount")
    void testTwoIdenticalBooks() {

        //Given
        int[] booksIds = {1, 1, 2, 2};

        //When
        double result = purchaseService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(190);
    }

    @Test
    @DisplayName("If You buy two different books from the series, you get a 5% discount on those two books.")
    void testTwoDifferentBooks() {

        //Given
        int[] booksIds = {1, 2};

        //When
        double result = purchaseService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(95);
    }

    @Test
    @DisplayName("If you buy 3 different books, you get a 10% discount")
    void itShouldCalculateDiscountAndReturn10() {

        //Given
        int[] booksIds = {1, 2, 5};

        //When
        double result = purchaseService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(135);
    }

    @Test
    @DisplayName("With 4 different books, you get a 20% discount")
    void itShouldCalculateDiscountAndReturn20() {

        //Given
        int[] booksIds = {1, 2, 4, 5};

        //When
        double result = purchaseService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(160);
    }

    @Test
    @DisplayName("If you go for the whole hog, and buy all 5, you get a huge 25% discount")
    void itShouldCalculateDiscountAndReturn25() {

        //Given
        int[] booksIds = {1, 2, 3, 4, 5};

        //When
        double result = purchaseService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(187.5);
    }

    @Test
    @DisplayName("if you buy, say, 4 books, of which 3 are different titles," +
            " you get a 10% discount on the 3 that form part of a set, but the 4th book still costs 50 EUR")
    void itShouldCalculateDiscountAndReturn10For3AndTheRestOriginalAmount() {

        //Given
        int[] booksIds = {1, 2, 3, 3};

        //When
        double result = purchaseService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(185);
    }

    @Test
    @DisplayName("Test for the scenario where the buyer has two copies of each book")
    void testTwoCopiesOfEachBook() {

        //Given
        int[] booksIds = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5};

        //When
        double result = purchaseService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(375);
    }

    @Test
    @DisplayName("Final example from the test")
    void finalExample() {

        //Given
        int[] booksIds = {1, 1, 2, 2, 3, 3, 4, 5};

        //When
        double result = purchaseService.calculatePurchaseDiscount(booksIds);

        //Then
        assertThat(result).isEqualTo(320);
    }
}