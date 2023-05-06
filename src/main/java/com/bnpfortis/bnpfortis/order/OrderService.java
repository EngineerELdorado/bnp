package com.bnpfortis.bnpfortis.order;

import com.bnpfortis.bnpfortis.discount.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final DiscountService discountService;
}
