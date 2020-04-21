package com.uber.cool.discount.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DiscountSlab {
  private long fromAmount;
  private long toAmount;
  private int discountPercentage;
}
