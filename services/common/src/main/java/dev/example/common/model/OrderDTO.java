package dev.example.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Order DTO object */
@Setter
@Getter
@ToString
public class OrderDTO {
  private Long orderId;
  private Long productId;
  private String productName;
  private Double price;
  private Status status;
}
