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

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilServiceTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private UtilService utilService;

    @Test
    @DisplayName("Test validation whn basket is null")
    void whenValidatingItShouldThrowAnExceptionIfBasketIsNull() {

        //Given //When //Then
        assertThatThrownBy(() -> utilService.validateBasket(null))
                .isInstanceOf(EmptyBasketException.class)
                .hasMessage("No basket present");
        verify(bookRepository, never()).getBooks();
    }

    @Test
    @DisplayName("Test validation whn basket is empty")
    void whenValidatingItShouldThrowAnExceptionIfBasketIsEmpty() {

        //Given //
        int[] booksIds = {};
        //When
        assertThatThrownBy(() -> utilService.validateBasket(booksIds))
                .isInstanceOf(EmptyBasketException.class)
                .hasMessage("The basket is empty");
        verify(bookRepository, never()).getBooks();
    }

    @Test
    @DisplayName("Test validation whn basket is empty")
    void whenValidatingItShouldThrowAnExceptionIfBasketContainsAnUnknownBook() {

        //Given //
        int[] booksIds = {1, 0};
        //When
        given(bookRepository.getBooks()).willReturn(TestData.getBooks());
        assertThatThrownBy(() -> utilService.validateBasket(booksIds))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Your basket contain one or more books that are not found");
        verify(bookRepository, times(2)).getBooks();
    }

    @Test
    @DisplayName("Happy path test on validating a basket")
    void whenValidatingReturnSuccessfulIfOnHappyPath() {

        int[] booksIds = {1, 2, 3, 4};
        given(bookRepository.getBooks()).willReturn(TestData.getBooks());
        assertThatNoException().isThrownBy(() -> utilService.validateBasket(booksIds));
    }

    @Test
    void whenGettingMinCountThrownExceptionIfDataIsNull(){
        assertThatThrownBy(()->utilService.getMinCountPerBook(null))
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
        int minCountPerBook = utilService.getMinCountPerBook(bookCountMap);
        //Then
        assertThat(minCountPerBook).isEqualTo(2);
    }
}