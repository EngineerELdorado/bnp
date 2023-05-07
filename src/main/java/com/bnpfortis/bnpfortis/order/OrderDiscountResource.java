package com.bnpfortis.bnpfortis.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDiscountResource{

    private double originalPrice;
    private double discountPercentage;
    private double discountAmount;
    private double finalPrice;
}
