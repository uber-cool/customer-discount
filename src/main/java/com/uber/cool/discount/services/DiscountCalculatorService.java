package com.uber.cool.discount.services;

import com.uber.cool.discount.model.customer.Customer;
import com.uber.cool.discount.model.customer.CustomerType;
import com.uber.cool.discount.model.DiscountSlab;
import com.uber.cool.discount.repository.DiscountRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountCalculatorService {

  @Autowired
  private DiscountRepository discountRepository;


  /**
   * Takes Customer object and order total as input and returns discounted price.
   * discountRepository is injected toAmount get applicable slabs.
   * @param customer
   * @param orderTotal
   * @return discountedPrice
   */
  public long calculateDiscountedPrice(Customer customer, long orderTotal) {
    List<DiscountSlab> discountSlabs = getDiscountSlabsForCustomer(customer.getType());
    long discount = 0;
    for (DiscountSlab discountSlab: discountSlabs) {
      if (discountSlab.getToAmount() <= orderTotal) {
        discount += (discountSlab.getToAmount() - discountSlab.getFromAmount())*discountSlab.getDiscountPercentage()/100;
        continue;
      }
      discount += (orderTotal - discountSlab.getFromAmount())*discountSlab.getDiscountPercentage()/100;
      return orderTotal - discount;
    }
    return orderTotal - discount;
  }

  private List<DiscountSlab> getDiscountSlabsForCustomer(CustomerType customerType) {
    return discountRepository.getOrderedDiscountSlabByCustomerType(customerType);
  }

}
