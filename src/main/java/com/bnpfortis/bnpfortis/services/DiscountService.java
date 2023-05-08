package com.bnpfortis.bnpfortis.services;

import com.bnpfortis.bnpfortis.repositories.BookRepository;
import com.bnpfortis.bnpfortis.utils.BasketUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final BasketService basketService;
    private final BookRepository bookRepository;

    public double calculatePurchaseDiscount(int[] basket) {

        basketService.validateBasket(basket);

        final double PRICE_PER_BOOK = 50;
        double totalCost = PRICE_PER_BOOK * basket.length;
        Map<Integer, Double> discountPercentages = bookRepository.getBooksDiscounts();

        if (basket.length == 1) {
            return totalCost;
        }

        int numberOfDistinctBooks = BasketUtil.getNumberOfDistinctBooks(basket);

        if (numberOfDistinctBooks == 1 && basket.length > 1) {
            return totalCost;
        }

        Map<Integer, Integer> bookCountMap = BasketUtil.getBookCounts(basket);
        double discountForDistinctBooks = 0;

        //As long as we have more than one distinct book we need to apply discount again for them
        while (numberOfDistinctBooks > 1) {

            int minCountThatQualifiesForADiscount = BasketUtil.getMinCountPerBook(bookCountMap);
            double discountPercentage = discountPercentages.getOrDefault(numberOfDistinctBooks, 0.0);

            discountForDistinctBooks = discountForDistinctBooks +
                    (numberOfDistinctBooks * minCountThatQualifiesForADiscount * PRICE_PER_BOOK * discountPercentage);

            /*
            Check if the book counts still have values that are greater than the current min count
            If yes then reduce the count (because we will process the discount for it too).
            This allows us to remove distinct books that are already been processed for discount.
             */
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
