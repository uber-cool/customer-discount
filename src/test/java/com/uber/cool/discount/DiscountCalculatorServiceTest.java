package com.uber.cool.discount;

import static com.google.common.collect.Maps.newHashMap;
import static com.uber.cool.discount.model.customer.CustomerType.PREMIUM;
import static com.uber.cool.discount.model.customer.CustomerType.REGULAR;
import static java.lang.Long.MAX_VALUE;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import com.uber.cool.discount.model.DiscountSlab;
import com.uber.cool.discount.model.customer.Customer;
import com.uber.cool.discount.model.customer.CustomerType;
import com.uber.cool.discount.model.customer.PremiumCustomer;
import com.uber.cool.discount.model.customer.RegularCustomer;
import com.uber.cool.discount.repository.DiscountRepository;
import com.uber.cool.discount.services.DiscountCalculatorService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DiscountCalculatorServiceTest {

  @MockBean
  DiscountRepository discountRepository;

  @Autowired
  DiscountCalculatorService discountCalculatorService;

  Map<CustomerType, List<DiscountSlab>> discountData = newHashMap();
  private Customer regularCustomer = new RegularCustomer();
  private Customer premiumCustomer = new PremiumCustomer();

  @BeforeEach
  public void setup() {
    DiscountSlab discountSlab1 = new DiscountSlab(0, 5000, 0);
    DiscountSlab discountSlab2 = new DiscountSlab(5000, 10000, 10);
    DiscountSlab discountSlab3 = new DiscountSlab(10000, MAX_VALUE, 20);
    List<DiscountSlab> regularCustomerDiscount = Arrays
        .asList(discountSlab1, discountSlab2, discountSlab3);

    DiscountSlab discountSlab4 = new DiscountSlab(0, 4000, 10);
    DiscountSlab discountSlab5 = new DiscountSlab(4000, 8000, 15);
    DiscountSlab discountSlab6 = new DiscountSlab(8000, 12000, 20);
    DiscountSlab discountSlab7 = new DiscountSlab(12000, MAX_VALUE, 30);

    List<DiscountSlab> premiumCustomerDiscount = Arrays
        .asList(discountSlab4, discountSlab5, discountSlab6, discountSlab7);
    discountData.put(REGULAR, regularCustomerDiscount);
    discountData.put(PREMIUM, premiumCustomerDiscount);
  }

  @Test
  public void testDiscountForRegularCustomer(){
    given(discountRepository.getOrderedDiscountSlabByCustomerType(REGULAR))
        .willReturn(discountData.get(REGULAR));

    long discount = discountCalculatorService.calculateDiscountedPrice(regularCustomer, 5000);
    assertEquals(5000, discount);

    discount = discountCalculatorService.calculateDiscountedPrice(regularCustomer, 10000);
    assertEquals(9500, discount);

    discount = discountCalculatorService.calculateDiscountedPrice(regularCustomer, 15000);
    assertEquals(13500, discount);
  }

  @Test
  public void testDiscounntForPremiumCustomer() {
    given(discountRepository.getOrderedDiscountSlabByCustomerType(PREMIUM))
        .willReturn(discountData.get(PREMIUM));

    long discount = discountCalculatorService.calculateDiscountedPrice(premiumCustomer, 4000);
    assertEquals(3600, discount);

    discount = discountCalculatorService.calculateDiscountedPrice(premiumCustomer, 8000);
    assertEquals(7000, discount);

    discount = discountCalculatorService.calculateDiscountedPrice(premiumCustomer, 12000);
    assertEquals(10200, discount);

    discount = discountCalculatorService.calculateDiscountedPrice(premiumCustomer, 20000);
    assertEquals(15800, discount);
  }
}
