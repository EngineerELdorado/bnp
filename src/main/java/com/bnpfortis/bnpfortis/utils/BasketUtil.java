package com.bnpfortis.bnpfortis.utils;

import com.bnpfortis.bnpfortis.exceptions.EmptyBasketException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BasketUtil {

    private BasketUtil() {
    }

    public static int getNumberOfDistinctBooks(int[] basket) {
        if (basket == null) {
            return 0;
        }
        Set<Integer> distinctBooks = new HashSet<>();
        for (int book : basket) {
            distinctBooks.add(book);
        }
        return distinctBooks.size();
    }

    public static Map<Integer, Integer> getBookCounts(int[] basket) {
        if (basket == null) {
            return Map.of();
        }
        Map<Integer, Integer> bookCounts = new HashMap<>();
        for (int book : basket) {
            bookCounts.put(book, bookCounts.getOrDefault(book, 0) + 1);
        }
        return bookCounts;
    }

    public static int getMinCountPerBook(Map<Integer, Integer> bookCountMap) {

        if (bookCountMap == null) {
            throw new EmptyBasketException("No data present");
        }
        int minCountThatQualifiesForDiscount = Integer.MAX_VALUE;

        /*
         * LOGIC EXPLANATION:
         * 1. For each distinct book from the map we get its value (its count)
         * 2. Check if the count is greater than 0. Meaning if there is a copy remaining on the basket for this book.
         * 3. We see if this count is less than what was saved previously as the min count that qualifies for discount.
         * 4. If Yes then we update the min count that qualifies for discount.
         * 5. We return the min count that qualifies for discount.
         */
        for (int count : bookCountMap.values()) {
            if (count > 0 && count < minCountThatQualifiesForDiscount) {
                minCountThatQualifiesForDiscount = count;
            }
        }
        return minCountThatQualifiesForDiscount;
    }
}
