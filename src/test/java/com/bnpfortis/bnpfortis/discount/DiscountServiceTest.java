package com.bnpfortis.bnpfortis.discount;

import com.bnpfortis.bnpfortis.order.OrderDiscountResource;
import com.bnpfortis.bnpfortis.order.OrderRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class DiscountServiceTest {

    @Autowired
    private DiscountService discountService;

    @Test
    @DisplayName("Given two distinct books, it should return result of percentage 5")
    void itShouldCalculateDiscountAndReturn5() {

        //Given

        OrderRequestDto orderRequestDto = new OrderRequestDto();
        List<Long> orderItems = List.of(1L, 2L);
        orderRequestDto.setBookIds(orderItems);

        //When

        OrderDiscountResource orderDiscountResource = discountService.calculateDiscount(orderRequestDto);

        //Then
        assertAll("orderDiscountResource",
                () -> assertThat(orderDiscountResource.getDiscountPercentage()).isEqualTo(5),
                () -> assertThat(orderDiscountResource.getDiscountPercentage()).isEqualTo(5),
                () -> assertThat(orderDiscountResource.getDiscountAmount()).isEqualTo(5),
                () -> assertThat(orderDiscountResource.getFinalPrice()).isEqualTo(95)
        );
    }
}