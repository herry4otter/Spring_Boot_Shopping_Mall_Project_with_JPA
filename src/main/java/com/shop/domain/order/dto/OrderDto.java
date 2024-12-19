package com.shop.domain.order.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.xml.stream.XMLInputFactory;

@Getter
@Setter
public class OrderDto {

    @NotNull(message = "상품 아이디는 필수 입니다.")
    private Long itemId;

    @Min(value = 1, message = "상품의 최소 수량 개수는 1개입니다.")
    @Max(value = 999, message = "상품의 최대 수량 개수는 999개 입니다.")
    private int count;
}
