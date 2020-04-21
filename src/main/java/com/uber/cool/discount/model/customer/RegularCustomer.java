package com.uber.cool.discount.model.customer;

public class RegularCustomer extends Customer {

  public CustomerType getType() {
    return CustomerType.REGULAR;
  }
}
