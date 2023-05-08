package com.bnpfortis.bnpfortis.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class BookService {

    public int getNumberOfDistinctBooks(int[] basket) {

        if (basket == null) {
            return 0;
        }
        Set<Integer> distinctBooks = new HashSet<>();
        for (int book : basket) {
            distinctBooks.add(book);
        }
        return distinctBooks.size();
    }

    public Map<Integer, Integer> getBookCounts(int[] basket) {

        if (basket == null) {
            return Map.of();
        }
        Map<Integer, Integer> bookCounts = new HashMap<>();
        for (int book : basket) {
            bookCounts.put(book, bookCounts.getOrDefault(book, 0) + 1);
        }
        return bookCounts;
    }
}
