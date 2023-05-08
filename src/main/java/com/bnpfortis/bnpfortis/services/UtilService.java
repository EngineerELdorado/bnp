package com.bnpfortis.bnpfortis.services;

import com.bnpfortis.bnpfortis.exceptions.BookNotFoundException;
import com.bnpfortis.bnpfortis.exceptions.EmptyBasketException;
import com.bnpfortis.bnpfortis.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UtilService {

    private final BookRepository bookRepository;

    public void validateBasket(int[] basket) {

        if (basket == null) {
            throw new EmptyBasketException("No basket present");
        }
        if (basket.length == 0) {
            throw new EmptyBasketException("The basket is empty");
        }
        for (int id : basket) {

            Map<Integer, String> books = bookRepository.getBooks();
            if (!books.containsKey(id)) {
                throw new BookNotFoundException("Your basket contain one or more books that are not found");
            }
        }
    }

    public int getMinCountPerBook(Map<Integer, Integer> bookCountMap) {

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
