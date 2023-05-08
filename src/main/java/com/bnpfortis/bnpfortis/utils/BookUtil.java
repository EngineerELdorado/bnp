package com.bnpfortis.bnpfortis.utils;

import com.bnpfortis.bnpfortis.exceptions.EmptyBasketException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BookUtil {

    private BookUtil() {

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

        for (int count : bookCountMap.values()) {
            if (count > 0 && count < minCountThatQualifiesForDiscount) {
                minCountThatQualifiesForDiscount = count;
            }
        }
        return minCountThatQualifiesForDiscount;
    }
}
