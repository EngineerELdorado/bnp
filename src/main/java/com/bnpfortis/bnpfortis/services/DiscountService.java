package com.bnpfortis.bnpfortis.services;

import com.bnpfortis.bnpfortis.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final BookService bookService;
    private final UtilService utilService;
    private final BookRepository bookRepository;

    public double calculatePurchaseDiscount(int[] basket) {

        utilService.validateBasket(basket);

        final double PRICE_PER_BOOK = 50;
        double totalCost = PRICE_PER_BOOK * basket.length;
        Map<Integer, Double> discountPercentages = bookRepository.getBooksDiscounts();

        if (basket.length == 1) {
            return totalCost;
        }

        int numberOfDistinctBooks = bookService.getNumberOfDistinctBooks(basket);
        if (numberOfDistinctBooks == 1 && basket.length > 1) {
            return totalCost;
        }
        Map<Integer, Integer> bookCountMap = bookService.getBookCounts(basket);
        double discountForDistinctBooks = 0;
        while (numberOfDistinctBooks > 1) {

            int minCountThatQualifiesForADiscount = utilService.getMinCountPerBook(bookCountMap);
            double discountPercentage = discountPercentages.getOrDefault(numberOfDistinctBooks, 0.0);

            discountForDistinctBooks = discountForDistinctBooks +
                    (numberOfDistinctBooks * minCountThatQualifiesForADiscount *
                            PRICE_PER_BOOK * discountPercentage);

            for (Map.Entry<Integer, Integer> entry : bookCountMap.entrySet()) {
                int count = entry.getValue();
                if (count >= minCountThatQualifiesForADiscount) {
                    entry.setValue(count - minCountThatQualifiesForADiscount);
                }
            }

            bookCountMap.values().removeIf(count -> count == 0);
            numberOfDistinctBooks = bookCountMap.keySet().size();
        }

        return totalCost - discountForDistinctBooks;
    }
}
