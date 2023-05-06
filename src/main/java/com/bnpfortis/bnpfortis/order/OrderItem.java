package com.bnpfortis.bnpfortis.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItem {

    private long bookId;
    private int quantity;
}
