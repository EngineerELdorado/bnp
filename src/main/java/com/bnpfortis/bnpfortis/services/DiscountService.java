package com.bnpfortis.bnpfortis.services;

import com.bnpfortis.bnpfortis.exceptions.BookNotFoundException;
import com.bnpfortis.bnpfortis.exceptions.EmptyBasketException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class DiscountService {

    public double calculatePurchaseDiscount(int[] basket) {

        validateBasket(basket);

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
        double discountForDistinctBooks = 0;
        while (numberOfDistinctBooks > 1) {

            int minCountThatQualifiesForADiscount = getMinCountThatQualifiesForADiscount(bookCountMap);
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

    private void validateBasket(int[] basket) {
        if (basket.length == 0) {
            throw new EmptyBasketException("The basket is empty");
        }
        for (int id : basket) {
            if (!getBooksLocalDB().containsKey(id)) {
                throw new BookNotFoundException("Your basket contain one or more books that are not found");
            }
        }
    }

    private Map<Integer, Double> getDiscountPercentages() {

        Map<Integer, Double> discountPercentagesMap = new HashMap<>();

        discountPercentagesMap.put(2, 0.05);
        discountPercentagesMap.put(3, 0.1);
        discountPercentagesMap.put(4, 0.2);
        discountPercentagesMap.put(5, 0.25);
        return discountPercentagesMap;
    }

    private int getMinCountThatQualifiesForADiscount(Map<Integer, Integer> bookCountMap) {
        int minCountThatQualifiesForDiscount = Integer.MAX_VALUE;

        for (int count : bookCountMap.values()) {
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

    private Map<Integer, String> getBooksLocalDB() {

        Map<Integer, String> books = new HashMap<>();
        books.put(1, "Clean Code (Robert Martin, 2008)");
        books.put(2, "The Clean Coder (Robert Martin, 2011)");
        books.put(3, "Clean Architecture (Robert Martin, 2017)");
        books.put(4, "Test Driven Development by Example (Kent Beck, 2003)");
        books.put(5, "Working Effectively With Legacy Code (Michael C. Feathers, 2004)");
        return books;
    }
}
