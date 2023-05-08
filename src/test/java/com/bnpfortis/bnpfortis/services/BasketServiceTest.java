package com.bnpfortis.bnpfortis.services;

import com.bnpfortis.bnpfortis.data.TestData;
import com.bnpfortis.bnpfortis.exceptions.BookNotFoundException;
import com.bnpfortis.bnpfortis.exceptions.EmptyBasketException;
import com.bnpfortis.bnpfortis.repositories.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {


    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BasketService basketService;

    @Test
    @DisplayName("Happy path test on validating a basket")
    void whenValidatingReturnSuccessfulIfOnHappyPath() {

        //Given
        int[] booksIds = {1, 2, 3, 4};
        given(bookRepository.getBooks()).willReturn(TestData.getBooks());

        //When //Then
        assertThatNoException().isThrownBy(() -> basketService.validateBasket(booksIds));
    }

    @Test
    @DisplayName("Test validation whn basket is empty")
    void whenValidatingItShouldThrowAnExceptionIfBasketIsEmpty() {

        //Given
        int[] booksIds = {};

        //When
        assertThatThrownBy(() -> basketService.validateBasket(booksIds))
                .isInstanceOf(EmptyBasketException.class)
                .hasMessage("The basket is empty");

        //Then
        then(bookRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Test validation when basket has unknown books")
    void whenValidatingItShouldThrowAnExceptionIfBasketContainsAnUnknownBook() {

        //Given
        int[] booksIds = {1, 0};

        //When
        given(bookRepository.getBooks()).willReturn(TestData.getBooks());
        assertThatThrownBy(() -> basketService.validateBasket(booksIds))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Your basket contain one or more books that are not found");

        //Then
        then(bookRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Test validation whn basket is null")
    void whenValidatingItShouldThrowAnExceptionIfBasketIsNull() {

        //When //Then
        assertThatThrownBy(() -> basketService.validateBasket(null))
                .isInstanceOf(EmptyBasketException.class)
                .hasMessage("No basket present");

        //Then
        then(bookRepository).shouldHaveNoInteractions();
    }
}