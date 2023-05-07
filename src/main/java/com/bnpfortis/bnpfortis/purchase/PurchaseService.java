package com.bnpfortis.bnpfortis.purchase;

import com.bnpfortis.bnpfortis.purchase.exceptions.EmptyBasketException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class PurchaseService {

    public double calculatePurchaseDiscount(int[] basket) {

        ifBasketIsEmptyThrowAnException(basket);

        final double PRICE_PER_BOOK = 50;
        double totalCost = PRICE_PER_BOOK * basket.length;
        Map<Integer, Double> discountPercentages = getDiscountPercentages();

        if (basket.length == 1) {
            return totalCost;
        }

        int numberOfDistinctBooks = getNumberOfDistinctBooks(basket);
        if (numberOfDistinctBooks == 1 && basket.length > 1) {
            return totalCost;
        }
        Map<Integer, Integer> bookCountMap = getBookCounts(basket);

        while (numberOfDistinctBooks > 1) {
            int minCount = getMinCount(bookCountMap);
            double discountPercentage = discountPercentages.getOrDefault(numberOfDistinctBooks, 0.0);
            double discountAmount = numberOfDistinctBooks * minCount * PRICE_PER_BOOK * discountPercentage;

            totalCost -= discountAmount;

            for (Map.Entry<Integer, Integer> entry : bookCountMap.entrySet()) {
                int count = entry.getValue();
                if (count >= minCount) {
                    entry.setValue(count - minCount);
                }
            }

            bookCountMap.values().removeIf(count -> count == 0);
            numberOfDistinctBooks = bookCountMap.keySet().size();
        }

        return totalCost;
    }

    private void ifBasketIsEmptyThrowAnException(int[] basket) {
        if (basket.length == 0) {
            throw new EmptyBasketException("The basket is empty");
        }
    }

    private Map<Integer, Double> getDiscountPercentages() {

        Map<Integer, Double> discountPercentagesMap = new HashMap<>();
        discountPercentagesMap.put(0, 0.0);
        discountPercentagesMap.put(1, 0.0);
        discountPercentagesMap.put(2, 0.05);
        discountPercentagesMap.put(3, 0.1);
        discountPercentagesMap.put(4, 0.2);
        discountPercentagesMap.put(5, 0.25);
        return discountPercentagesMap;
    }

    private int getMinCount(Map<Integer, Integer> bookCounts) {
        int minCountThatQualifiesForDiscount = Integer.MAX_VALUE;

        for (int count : bookCounts.values()) {
            if (count > 0 && count < minCountThatQualifiesForDiscount) {
                minCountThatQualifiesForDiscount = count;
            }
        }

        return minCountThatQualifiesForDiscount;
    }

    private int getNumberOfDistinctBooks(int[] basket) {
        Set<Integer> distinctBooks = new HashSet<>();
        for (int book : basket) {
            distinctBooks.add(book);
        }
        return distinctBooks.size();
    }

    private Map<Integer, Integer> getBookCounts(int[] basket) {
        Map<Integer, Integer> bookCounts = new HashMap<>();
        for (int book : basket) {
            bookCounts.put(book, bookCounts.getOrDefault(book, 0) + 1);
        }
        return bookCounts;
    }
}
