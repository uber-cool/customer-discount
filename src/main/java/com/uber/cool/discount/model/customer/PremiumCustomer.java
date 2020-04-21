package com.uber.cool.discount.model.customer;

public class PremiumCustomer extends Customer {

  public CustomerType getType() {
    return CustomerType.PREMIUM;
  }
}
