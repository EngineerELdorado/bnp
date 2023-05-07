package com.bnpfortis.bnpfortis.discount;

import com.bnpfortis.bnpfortis.book.Book;
import com.bnpfortis.bnpfortis.book.BookRepository;
import com.bnpfortis.bnpfortis.book.exceptions.EmptyBasketException;
import com.bnpfortis.bnpfortis.order.OrderDiscountResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private static final double TWO_BOOKS_DISCOUNT = 0.05;
    private static final double THREE_BOOKS_DISCOUNT = 0.1;
    private static final double FOUR_BOOKS_DISCOUNT = 0.2;
    private static final double FIVE_BOOKS_DISCOUNT = 0.25;

    private final BookRepository bookRepository;

    public OrderDiscountResource calculateDiscount(List<Long> booksIds) {

        if (booksIds.isEmpty()) {
            throw new EmptyBasketException("The basket should contain books");
        }

        Set<Long> discountableBooks = new HashSet<>();
        List<Long> nonDiscountableBooks = new ArrayList<>();

        OrderDiscountResource orderDiscountResource = new OrderDiscountResource();

        for (Long bookId : booksIds) {

            if (!discountableBooks.add(bookId)) {
                nonDiscountableBooks.add(bookId);
            }
        }

        double originalPriceOfDiscountableBooks = getPriceSum(discountableBooks);
        double originalPriceOfNonDiscountableBooks = getPriceSum(nonDiscountableBooks);
        int discountPercentage = getPercentage(discountableBooks);
        double discountAmount = 0;

        if (!discountableBooks.isEmpty()) {

            orderDiscountResource.setOriginalPrice(originalPriceOfDiscountableBooks +
                    originalPriceOfNonDiscountableBooks);
            discountAmount = (originalPriceOfDiscountableBooks * discountPercentage) / 100;
        }

        double finalPrice = (originalPriceOfDiscountableBooks + originalPriceOfNonDiscountableBooks) - discountAmount;
        orderDiscountResource.setDiscountPercentage(discountPercentage);
        orderDiscountResource.setDiscountAmount(discountAmount);
        orderDiscountResource.setFinalPrice(finalPrice);
        return orderDiscountResource;
    }

    private double getPriceSum(Collection<Long> bookIds) {

        return bookRepository.findAllById(bookIds).stream()
                .mapToDouble(Book::getPrice)
                .sum();
    }

    private int getPercentage(Collection<Long> discountableBooks) {

        return switch (discountableBooks.size()) {
            case 2 -> 5;
            case 3 -> 10;
            case 4 -> 20;
            case 5 -> 25;
            default -> 0;
        };
    }
}
