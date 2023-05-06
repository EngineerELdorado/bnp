package com.bnpfortis.bnpfortis.discount;

import com.bnpfortis.bnpfortis.book.Book;
import com.bnpfortis.bnpfortis.book.BookRepository;
import com.bnpfortis.bnpfortis.order.OrderDiscountResource;
import com.bnpfortis.bnpfortis.order.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final BookRepository bookRepository;
    private final Set<Long> discountableBooks = new HashSet<>();
    private final List<Long> nonDiscountableBooks = new ArrayList<>();

    public OrderDiscountResource calculateDiscount(OrderRequestDto orderRequestDto) {

        OrderDiscountResource orderDiscountResource = new OrderDiscountResource();

        for (Long bookId : orderRequestDto.getBookIds()) {

            if (discountableBooks.add(bookId)) {
                nonDiscountableBooks.add(bookId);
            }
        }

        if (!discountableBooks.isEmpty()) {
            double originalPrice = bookRepository.findAllById(discountableBooks).stream()
                    .mapToDouble(Book::getPrice)
                    .sum();
            int discountPercentage = getPercentage();
            double discountAmount = (originalPrice * discountPercentage) / 100;
            double finalPrice = originalPrice-discountAmount;

            orderDiscountResource.setOriginalPrice(originalPrice);
            orderDiscountResource.setDiscountPercentage(discountPercentage);
            orderDiscountResource.setDiscountAmount(discountAmount);
            orderDiscountResource.setFinalPrice(finalPrice);
        }

        return orderDiscountResource;
    }

    private int getPercentage() {

        return switch (discountableBooks.size()) {
            case 2 -> 5;
            case 3 -> 10;
            case 4 -> 20;
            case 5 -> 25;
            default -> 0;
        };
    }
}
