package com.bnpfortis.bnpfortis.services;

import com.bnpfortis.bnpfortis.exceptions.BookNotFoundException;
import com.bnpfortis.bnpfortis.exceptions.EmptyBasketException;
import com.bnpfortis.bnpfortis.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BookRepository bookRepository;

    public void validateBasket(int[] basket) {

        if (basket == null) {
            throw new EmptyBasketException("No basket present");
        }
        if (basket.length == 0) {
            throw new EmptyBasketException("The basket is empty");
        }

        Map<Integer, String> books = bookRepository.getBooks();

        for (int id : basket) {

            if (!books.containsKey(id)) {
                throw new BookNotFoundException("Your basket contain one or more books that are not found");
            }
        }
    }
}
