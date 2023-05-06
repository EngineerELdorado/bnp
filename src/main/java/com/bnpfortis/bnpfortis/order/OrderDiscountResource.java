package com.bnpfortis.bnpfortis.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDiscountResource extends OrderItem {

    private double discountPercentage;
    private double discountAmount;
}
