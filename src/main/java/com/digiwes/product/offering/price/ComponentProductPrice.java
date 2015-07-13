package com.digiwes.product.offering.price;

import com.digiwes.basetype.TimePeriod;

/**
 * Created by zhaoyp on 2015/7/9.
 */
public class ComponentProductPrice extends ProductOfferingPrice{
    private  String money;

    public ComponentProductPrice(String name, TimePeriod validFor,String money){
          super(name,validFor);
        this.money=money;
    }
}
