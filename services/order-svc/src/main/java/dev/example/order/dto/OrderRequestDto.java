package dev.example.order.dto;

import lombok.Data;

@Data
public class OrderRequestDto {
    private Long productId;
    private String productName;
    private Double price;
}
