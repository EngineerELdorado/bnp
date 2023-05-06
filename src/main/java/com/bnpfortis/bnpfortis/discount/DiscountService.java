package com.bnpfortis.bnpfortis.discount;

import com.bnpfortis.bnpfortis.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private BookRepository bookRepository;
}
