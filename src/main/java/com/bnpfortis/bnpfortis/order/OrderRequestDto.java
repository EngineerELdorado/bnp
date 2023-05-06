package com.bnpfortis.bnpfortis.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    private List<Long> bookIds;
}
