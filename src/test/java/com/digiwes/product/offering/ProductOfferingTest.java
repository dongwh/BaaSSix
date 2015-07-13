package com.digiwes.product.offering;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.util.DateUtils;
import com.digiwes.product.offering.ProductOffering;
import com.digiwes.product.offering.price.ComponentProductPrice;
import com.digiwes.product.offering.price.ProductOfferingPrice;
import com.digiwes.product.spec.AtomicProductSpecification;
import com.digiwes.product.spec.ProductSpecification;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ProductOfferingTest {

    private ProductOffering productOffering ;
    private  BundledProductOffering bundledProductOffering;
    private ProductSpecification productSpecification;
    private ProductOfferingPrice price =null;
    @Before
    public void setUp(){
        productSpecification= new AtomicProductSpecification("23213","13-inch MacBook Pro","Mac");
        productOffering=new SimpleProductOffering("1","13-inch MacBook Pro","13-inch MacBook Pro",new TimePeriod(DateUtils.str2Date("2015-01-01 00:00:00", DateUtils.datetimeFormat),DateUtils.str2Date("2015-06-01 23:59:59", DateUtils.datetimeFormat)),productSpecification);
        bundledProductOffering =new BundledProductOffering("1","13-inch MacBook Pro","13-inch MacBook Pro",new TimePeriod(DateUtils.str2Date("2015-01-01 00:00:00", DateUtils.datetimeFormat),DateUtils.str2Date("2015-06-01 23:59:59", DateUtils.datetimeFormat)));
    }
    @Test
    public void testProductOffering(){
         try{
             productOffering=new SimpleProductOffering("1","13-inch MacBook Pro","13-inch MacBook Pro",new TimePeriod(DateUtils.str2Date("2015-01-01 00:00:00", DateUtils.datetimeFormat),DateUtils.str2Date("2015-06-01 23:59:59", DateUtils.datetimeFormat)),null);
            fail("productSpec is null ,excepted IllegalArgumentException for spec");
         } catch (IllegalArgumentException ex){

         }
        try{
            productOffering=new SimpleProductOffering(null,"13-inch MacBook Pro","13-inch MacBook Pro",new TimePeriod(DateUtils.str2Date("2015-01-01 00:00:00", DateUtils.datetimeFormat),DateUtils.str2Date("2015-06-01 23:59:59", DateUtils.datetimeFormat)),null);
            fail("productSpec is null ,excepted IllegalArgumentException for spec");
        }   catch (IllegalArgumentException ex){

        }
        try{
            productOffering=new SimpleProductOffering("1",null,"13-inch MacBook Pro",new TimePeriod(DateUtils.str2Date("2015-01-01 00:00:00", DateUtils.datetimeFormat),DateUtils.str2Date("2015-06-01 23:59:59", DateUtils.datetimeFormat)),null);
            fail("productSpec is null ,excepted IllegalArgumentException for spec");
        }   catch (IllegalArgumentException ex){

        }
        productOffering=new SimpleProductOffering("1","13-inch MacBook Pro","13-inch MacBook Pro",new TimePeriod(DateUtils.str2Date("2015-01-01 00:00:00", DateUtils.datetimeFormat),DateUtils.str2Date("2015-06-01 23:59:59", DateUtils.datetimeFormat)),productSpecification);


    }
    @Test
    public void testAddPrice(){
        ProductOfferingPrice price =null;
        try{
            productOffering.addPrice(price);
            fail("price is null excepted IllegalArgumentException for price");
        }   catch (IllegalArgumentException ex ){

        }
        price = new ComponentProductPrice("",new TimePeriod(DateUtils.str2Date("2015-01-01 00:00:00", DateUtils.datetimeFormat),DateUtils.str2Date("2015-06-01 23:59:59", DateUtils.datetimeFormat)),"32.3")  ;
        productOffering.addPrice(price);
        assertEquals("add a normal price", 1, productOffering.getProductOfferingPrice().size());
        assertEquals("add a normal price",true,productOffering.getProductOfferingPrice().contains(price));

        productOffering.addPrice(price);
        assertEquals("add a duplicate price", 2, productOffering.getProductOfferingPrice().size());
        assertEquals("add a duplicate price", true,productOffering.getProductOfferingPrice().contains(price));
    }
    @Test
    public void testRetrievePrice(){
        try{
            productOffering.retrievePrice(null);
            fail("time is null ,excepted IllegalArgumentException for time");
        }  catch (IllegalArgumentException ex){

        }
        List<ProductOfferingPrice> prodcutOfferingPriceList= productOffering.retrievePrice(DateUtils.str2Date("2015-01-01 00:00:00", DateUtils.datetimeFormat));
        assertEquals("offering havn't any price ", 0, prodcutOfferingPriceList.size());
        price = new ComponentProductPrice("",new TimePeriod(DateUtils.str2Date("2015-01-03 00:00:00", DateUtils.datetimeFormat),DateUtils.str2Date("2015-06-01 23:59:59", DateUtils.datetimeFormat)),"32.3")  ;
        productOffering.addPrice(price);
        prodcutOfferingPriceList = productOffering.retrievePrice(DateUtils.str2Date("2015-03-01 00:00:00", DateUtils.datetimeFormat));
        assertEquals("offering havn one  price ", 1, prodcutOfferingPriceList.size());
        assertEquals("offering havn one  price ", true, prodcutOfferingPriceList.contains(price));
    }
    @Test
    public void testSetPrice(){
        List<ProductOfferingPrice> priceList=null;
        try{
            productOffering.setPrice(null);
            fail("price is null ,excepted IllegalArgumentException for price");
        }  catch (IllegalArgumentException ex){

        }
        priceList = new ArrayList<ProductOfferingPrice>();
        price = new ComponentProductPrice("",new TimePeriod(DateUtils.str2Date("2015-01-03 00:00:00", DateUtils.datetimeFormat),DateUtils.str2Date("2015-06-01 23:59:59", DateUtils.datetimeFormat)),"32.3")  ;
        priceList.add(price);
        productOffering.setPrice(priceList);
        assertEquals("setPrice have no price", priceList, productOffering.getProductOfferingPrice());
        assertEquals("setPrice have no price", priceList.size(), productOffering.getProductOfferingPrice().size());
        price = new ComponentProductPrice("",new TimePeriod(DateUtils.str2Date("2015-01-03 00:00:00", DateUtils.datetimeFormat),DateUtils.str2Date("2015-06-01 23:59:59", DateUtils.datetimeFormat)),"332")  ;

        priceList = new ArrayList<ProductOfferingPrice>();
        priceList.add(price);
        price = new ComponentProductPrice("",new TimePeriod(DateUtils.str2Date("2015-01-03 00:00:00", DateUtils.datetimeFormat),DateUtils.str2Date("2015-09-01 23:59:59", DateUtils.datetimeFormat)),"332")  ;
        priceList.add(price);
        productOffering.setPrice(priceList);
        assertEquals("setPrice have no price", priceList, productOffering.getProductOfferingPrice());
        assertEquals("setPrice have no price",priceList.size(), productOffering.getProductOfferingPrice().size());
    }
    @Test
    public void testAddSubOffering(){
        ProductOffering offering=null;
        boolean result =false;
       try{
           bundledProductOffering.addSubOffering(null);
           fail("offering is null ,excepted illegalArgumentException for Offering ");
       }   catch (IllegalArgumentException ex){

       }
        bundledProductOffering.addSubOffering(productOffering);
        assertEquals("add sub String", 1, bundledProductOffering.getBundledProdOfferOption().size());
        assertEquals("add sub String", productOffering, bundledProductOffering.getBundledProdOfferOption().get(0).getProductOffering());

        result = bundledProductOffering.addSubOffering(bundledProductOffering);
        assertEquals("add duplicate sub String", false, result);

        assertEquals("add sub String", 1, bundledProductOffering.getBundledProdOfferOption().size());
        assertEquals("add sub String", productOffering, bundledProductOffering.getBundledProdOfferOption().get(0).getProductOffering());

        result= bundledProductOffering.addSubOffering(productOffering, 0, 0);
        assertEquals("add duplicate sub String", false, result);
        assertEquals("add duplicate sub String", 1, bundledProductOffering.getBundledProdOfferOption().size());
        assertEquals("add duplicate sub String", productOffering, bundledProductOffering.getBundledProdOfferOption().get(0).getProductOffering());


    }

}
