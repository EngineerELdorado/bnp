package com.bnpfortis.bnpfortis.discount;

import com.bnpfortis.bnpfortis.book.Book;
import com.bnpfortis.bnpfortis.book.BookRepository;
import com.bnpfortis.bnpfortis.book.exceptions.EmptyBasketException;
import com.bnpfortis.bnpfortis.order.OrderDiscountResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DiscountServiceTest {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        initDb();
    }


    @Test
    @DisplayName("If the basket is empty, throw an exception")
    void ifNoBooksSelectItShouldThrowAnException() {

        //Given
        List<Long> booksIds = List.of();

        //When

        EmptyBasketException exception = assertThrows(EmptyBasketException.class,
                () -> discountService.calculateDiscount(booksIds));

        //Then
        assertThat(exception.getMessage()).isEqualTo("The basket should contain books");
        ;
    }

    @Test
    @DisplayName("If You buy only one book. there is no discount")
    void itShouldCalculateDiscountAndReturn0() {

        //Given
        List<Long> booksIds = List.of(1L);

        //When

        OrderDiscountResource orderDiscountResource = discountService.calculateDiscount(booksIds);

        //Then
        assertAll("orderDiscountResource",
                () -> assertThat(orderDiscountResource.getOriginalPrice()).isEqualTo(50),
                () -> assertThat(orderDiscountResource.getDiscountPercentage()).isZero(),
                () -> assertThat(orderDiscountResource.getDiscountAmount()).isZero(),
                () -> assertThat(orderDiscountResource.getFinalPrice()).isEqualTo(50)
        );
    }
    @Test
    @DisplayName("Given only identical books give no discount")
    void givenOnlyIdenticalBooks() {

        //Given
        List<Long> booksIds = List.of(1L, 1L);

        //When

        OrderDiscountResource orderDiscountResource = discountService.calculateDiscount(booksIds);

        //Then
        assertAll("orderDiscountResource",
                () -> assertThat(orderDiscountResource.getOriginalPrice()).isEqualTo(100),
                () -> assertThat(orderDiscountResource.getDiscountPercentage()).isZero(),
                () -> assertThat(orderDiscountResource.getDiscountAmount()).isZero(),
                () -> assertThat(orderDiscountResource.getFinalPrice()).isEqualTo(100)
        );
    }

    @Test
    @DisplayName("If You buy two different books from the series, you get a 5% discount on those two books.")
    void itShouldCalculateDiscountAndReturn5() {

        //Given
        List<Long> booksIds = List.of(1L, 2L);


        //When

        OrderDiscountResource orderDiscountResource = discountService.calculateDiscount(booksIds);

        //Then
        assertAll("orderDiscountResource",
                () -> assertThat(orderDiscountResource.getOriginalPrice()).isEqualTo(100),
                () -> assertThat(orderDiscountResource.getDiscountPercentage()).isEqualTo(5),
                () -> assertThat(orderDiscountResource.getDiscountAmount()).isEqualTo(5),
                () -> assertThat(orderDiscountResource.getFinalPrice()).isEqualTo(95)
        );
    }

    @Test
    @DisplayName("If you buy 3 different books, you get a 10% discount")
    void itShouldCalculateDiscountAndReturn10() {

        //Given
        List<Long> booksIds = List.of(1L, 2L, 5L);


        //When

        OrderDiscountResource orderDiscountResource = discountService.calculateDiscount(booksIds);

        //Then
        assertAll("orderDiscountResource",
                () -> assertThat(orderDiscountResource.getOriginalPrice()).isEqualTo(150),
                () -> assertThat(orderDiscountResource.getDiscountPercentage()).isEqualTo(10),
                () -> assertThat(orderDiscountResource.getDiscountAmount()).isEqualTo(15),
                () -> assertThat(orderDiscountResource.getFinalPrice()).isEqualTo(135)
        );
    }

    @Test
    @DisplayName("With 4 different books, you get a 20% discount")
    void itShouldCalculateDiscountAndReturn20() {

        //Given
        List<Long> booksIds = List.of(1L, 2L, 4L, 5L);

        //When

        OrderDiscountResource orderDiscountResource = discountService.calculateDiscount(booksIds);

        //Then
        assertAll("orderDiscountResource",
                () -> assertThat(orderDiscountResource.getOriginalPrice()).isEqualTo(200),
                () -> assertThat(orderDiscountResource.getDiscountPercentage()).isEqualTo(20),
                () -> assertThat(orderDiscountResource.getDiscountAmount()).isEqualTo(40),
                () -> assertThat(orderDiscountResource.getFinalPrice()).isEqualTo(160)
        );
    }

    @Test
    @DisplayName("If you go for the whole hog, and buy all 5, you get a huge 25% discount")
    void itShouldCalculateDiscountAndReturn25() {

        //Given
        List<Long> booksIds = List.of(1L, 2L, 3L, 4L, 5L);

        //When

        OrderDiscountResource orderDiscountResource = discountService.calculateDiscount(booksIds);

        //Then
        assertAll("orderDiscountResource",
                () -> assertThat(orderDiscountResource.getOriginalPrice()).isEqualTo(250),
                () -> assertThat(orderDiscountResource.getDiscountPercentage()).isEqualTo(25),
                () -> assertThat(orderDiscountResource.getDiscountAmount()).isEqualTo(62.5),
                () -> assertThat(orderDiscountResource.getFinalPrice()).isEqualTo(187.5)
        );
    }

    @Test
    @DisplayName("if you buy, say, 4 books, of which 3 are different titles," +
            " you get a 10% discount on the 3 that form part of a set, but the 4th book still costs 50 EUR")
    void itShouldCalculateDiscountAndReturn10For3AndTheRestOriginalAmount() {

        //Given
        List<Long> booksIds = List.of(1L, 2L, 3L, 3L);

        //When

        OrderDiscountResource orderDiscountResource = discountService.calculateDiscount(booksIds);

        //Then
        assertAll("orderDiscountResource",
                () -> assertThat(orderDiscountResource.getOriginalPrice()).isEqualTo(200),
                () -> assertThat(orderDiscountResource.getDiscountPercentage()).isEqualTo(10),
                () -> assertThat(orderDiscountResource.getDiscountAmount()).isEqualTo(15),
                () -> assertThat(orderDiscountResource.getFinalPrice()).isEqualTo(185)
        );
    }


    @Test
    @DisplayName("Final example from the test")
    void finalExample() {

        //Given
        List<Long> booksIds = List.of(1L, 1L, 2L, 2L, 3L, 3L, 4L, 5L);
        //When

        OrderDiscountResource orderDiscountResource = discountService.calculateDiscount(booksIds);

        //Then
        assertAll("orderDiscountResource",
                () -> assertThat(orderDiscountResource.getOriginalPrice()).isEqualTo(400),
                () -> assertThat(orderDiscountResource.getDiscountPercentage()).isEqualTo(20),
                () -> assertThat(orderDiscountResource.getDiscountAmount()).isEqualTo(40),
                () -> assertThat(orderDiscountResource.getFinalPrice()).isEqualTo(320)
        );
    }

    void initDb() {

        List<Book> books = new ArrayList<>();

        double price = 50;
        Book book1 = new Book(1L, "Clean Code", price,
                "Robert Martin", 2008);
        books.add(book1);

        Book book2 = new Book(2L, "The Clean Coder",
                price, "Robert Martin", 2011);
        books.add(book2);

        Book book3 = new Book(3L, "Clean Architecture",
                price, "Robert Martin", 2017);
        books.add(book3);

        Book book4 = new Book(4L, "Test Driven Development by Example",
                price, "Kent Beck", 2003);
        books.add(book4);

        Book book5 = new Book(5L, "Working Effectively With Legacy Code",
                price, "Michael C. Feathers", 2004);
        books.add(book5);

        bookRepository.saveAll(books);
    }
}