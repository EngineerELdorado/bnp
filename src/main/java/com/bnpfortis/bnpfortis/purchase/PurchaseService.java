package com.bnpfortis.bnpfortis.purchase;

import com.bnpfortis.bnpfortis.book.Book;
import com.bnpfortis.bnpfortis.book.BookRepository;
import com.bnpfortis.bnpfortis.purchase.exceptions.EmptyBasketException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final BookRepository bookRepository;

    public double calculateDiscount(List<Long> booksIds) {

        if (booksIds.isEmpty()) {
            throw new EmptyBasketException("The basket should contain books");
        }

        Set<Long> discountableBooks = new HashSet<>();
        List<Long> nonDiscountableBooks = new ArrayList<>();

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

            discountAmount = (originalPriceOfDiscountableBooks * discountPercentage) / 100;
        }

        return (originalPriceOfDiscountableBooks + originalPriceOfNonDiscountableBooks) - discountAmount;
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
