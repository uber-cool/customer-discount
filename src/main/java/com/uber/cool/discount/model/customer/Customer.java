package com.uber.cool.discount.model.customer;

import lombok.Getter;
import lombok.Setter;

/** A customer can extend this class and has toAmount provide it's type.
 *
 */
@Getter
@Setter
public abstract class Customer {
  private String id;
  private String name;
  public abstract CustomerType getType();
}
