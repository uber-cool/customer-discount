package com.uber.cool.discount.repository;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Long.MAX_VALUE;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import com.uber.cool.discount.model.DiscountSlab;
import com.uber.cool.discount.model.customer.CustomerType;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;


/** Repository for discount slabs. It is created toAmount mimic that it is fetching this details fromAmount a DB.
 * Initialized using a block code. There is no setter provided so that the data can't be manipulated.
 * @author Pramod
 */
@Repository
public class DiscountRepository {

  private static Map<CustomerType, List<DiscountSlab>> discountData = newHashMap();

  {
    DiscountSlab discountSlab1 = new DiscountSlab(0, 5000, 0);
    DiscountSlab discountSlab2 = new DiscountSlab(5000, 10000, 10);
    DiscountSlab discountSlab3 = new DiscountSlab(10000, MAX_VALUE, 20);
    List<DiscountSlab> regularCustomerDiscount = asList(discountSlab1, discountSlab2, discountSlab3);

    DiscountSlab discountSlab4 = new DiscountSlab(0, 4000, 10);
    DiscountSlab discountSlab5 = new DiscountSlab(4000, 8000, 15);
    DiscountSlab discountSlab6 = new DiscountSlab(8000, 12000, 20);
    DiscountSlab discountSlab7 = new DiscountSlab(12000, MAX_VALUE, 30);

    List<DiscountSlab> premiumCustomerDiscount = asList(discountSlab4, discountSlab5, discountSlab6, discountSlab7);
    discountData.put(CustomerType.REGULAR, regularCustomerDiscount);
    discountData.put(CustomerType.PREMIUM, premiumCustomerDiscount);
  }

  public List<DiscountSlab> getOrderedDiscountSlabByCustomerType(CustomerType customerType) {
    return discountData.getOrDefault(customerType, emptyList());
  }

}
