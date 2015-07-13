package com.digiwes.product.offering;

import com.digiwes.basetype.TimePeriod;
import com.digiwes.product.spec.ProductSpecification;
import org.junit.Test;


public class SimpleProductOfferingTest {

	private ProductOffering offering =null;

	public ProductOffering getOffering() {
		return offering;
	}

	public void setOffering(ProductOffering offering) {
		this.offering = offering;
	}

	@Test
    public  void createProductOffering(){
		String id = "0001" ;
		String name = "11 pound MacBook Air 6,288";
		TimePeriod validFor = new TimePeriod("2015-06-01 00:00:00", "2015-08-31 00:00:00");
		String description = "1.6GHz Intel Core i5 processer，Turbo Boost 2.7GHz";
		ProductSpecification prodSpec = null;
        offering = new SimpleProductOffering(id,  name,  description,  validFor,  prodSpec);
    }

}
