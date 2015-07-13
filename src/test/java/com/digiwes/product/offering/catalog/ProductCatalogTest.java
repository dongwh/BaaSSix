package com.digiwes.product.offering.catalog;


import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.util.DateUtils;
import com.digiwes.product.offering.ProductOffering;
import com.digiwes.product.offering.SimpleProductOffering;
import com.digiwes.product.offering.price.ComponentProductPrice;
import com.digiwes.product.offering.price.ProductOfferingPrice;
import com.digiwes.product.spec.AtomicProductSpecification;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by zhaoyp on 2015/7/9.
 */
public class ProductCatalogTest {
    private ProductOffering productOffering;
    private ProductCatalog catalog;

    @Before
    public void   setUp() throws ParseException{
        AtomicProductSpecification atomicProdSpec = new AtomicProductSpecification("1", "11 Pounds MacBook Air", "apple", "Mac", new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59")));
        productOffering =new SimpleProductOffering("1","13-inch MacBook Pro","13-inch MacBook Pro", new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59")),atomicProdSpec) ;
        catalog = new ProductCatalog("1","MAC","", new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59")));

    }
    @Test
    public void testPublishOffering() throws ParseException{
        ProductOffering offering = null;
        try{
            catalog.publishOffering(offering,null);
            fail("offering is null excepted IllegalArgument for offering");
        }   catch (IllegalArgumentException ex){

        }

        //normal
       boolean result= catalog.publishOffering(productOffering, new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59")));
       assertEquals("publish Offering",true,result);
       assertEquals("publish Offering",1,catalog.getProdCatalogProdOffer().size());
       assertEquals("publish Offering",productOffering,catalog.getProdCatalogProdOffer().get(0).getProdOffering());

        //the same productOffering can be add repeat
        result= catalog.publishOffering(productOffering, new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59")));
        assertEquals("publish Offering",false,result);
        assertEquals("publish Offering",1,catalog.getProdCatalogProdOffer().size());
        assertEquals("publish Offering",productOffering,catalog.getProdCatalogProdOffer().get(0).getProdOffering());

        //the same productOffering can be add repeat (new)
        ProductOffering productOfferingNew =new SimpleProductOffering("1","13-inch MacBook Pro","13-inch MacBook Pro", new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59")),null) ;
        result= catalog.publishOffering(productOfferingNew, new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59")));
        assertEquals("publish Offering",false,result);
        assertEquals("publish Offering",1,catalog.getProdCatalogProdOffer().size());
        assertEquals("publish Offering",productOffering,catalog.getProdCatalogProdOffer().get(0).getProdOffering());

        //time is null
        ProductOffering productOfferingTwo =new SimpleProductOffering("2","15-inch MacBook Pro","15-inch MacBook Pro", new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59")),null) ;
        result= catalog.publishOffering(productOfferingTwo, null);
        assertEquals("publish Offering",true,result);
        assertEquals("publish Offering",2,catalog.getProdCatalogProdOffer().size());
        assertEquals("publish Offering",productOfferingTwo,catalog.getProdCatalogProdOffer().get(1).getProdOffering());

        //pre time
        //ProductOffering productOfferingTwoTimePre =new SimpleProductOffering("2","15-inch MacBook Pro","15-inch MacBook Pro", new TimePeriod(DateUtils.datetimeFormat.parse("2015-01-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-02-01 23:59:59")),null) ;
        TimePeriod validForPre =  new TimePeriod(DateUtils.datetimeFormat.parse("2015-01-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-02-01 23:59:59"));
        result= catalog.publishOffering(productOffering, validForPre);
        assertEquals("publish Offering",true,result);
        assertEquals("publish Offering",3,catalog.getProdCatalogProdOffer().size());
        ProdCatalogProdOffer prodOfferPre = new ProdCatalogProdOffer(productOffering,  validForPre);
        assertEquals("publish Offering",prodOfferPre,catalog.getProdCatalogProdOffer().get(2));

        //after time
        TimePeriod validForAfter =  new TimePeriod(DateUtils.datetimeFormat.parse("2015-10-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-11-01 23:59:59"));
        result= catalog.publishOffering(productOffering, validForAfter);
        assertEquals("publish Offering",true,result);
        assertEquals("publish Offering",4,catalog.getProdCatalogProdOffer().size());
        ProdCatalogProdOffer prodOfferAfter = new ProdCatalogProdOffer(productOffering,  validForAfter);
        assertEquals("publish Offering",prodOfferAfter,catalog.getProdCatalogProdOffer().get(3));

        //in time
        TimePeriod validForIn =  new TimePeriod(DateUtils.datetimeFormat.parse("2015-03-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-04-01 23:59:59"));
        result= catalog.publishOffering(productOffering, validForIn);
        assertEquals("publish Offering",false,result);
        assertEquals("publish Offering",4,catalog.getProdCatalogProdOffer().size());
        ProdCatalogProdOffer prodOfferIn = new ProdCatalogProdOffer(productOffering,  validForIn);
        assertEquals("publish Offering",false,catalog.getProdCatalogProdOffer().contains(prodOfferIn));
    }

    @Test
    public void testPublishOfferingPrice() throws ParseException{
        ProductOffering offering = null;
        TimePeriod validFor = null;
        List<ProductOfferingPrice> priceList = null;
        try{
            catalog.publishOffering(offering, validFor, priceList);
            fail("offering is null excepted IllegalArgument for offering");
        }   catch (IllegalArgumentException ex){

        }

        //size of priceList is zero
        priceList = new ArrayList<ProductOfferingPrice>();
        boolean result= catalog.publishOffering(productOffering, validFor, priceList);
        assertEquals("publish Offering",true,result);
        assertEquals("publish Offering",1,catalog.getProdCatalogProdOffer().size());
        assertEquals("publish Offering",productOffering,catalog.getProdCatalogProdOffer().get(0).getProdOffering());

        //normal
        ProductOfferingPrice prodOfferingPriceOne = new ComponentProductPrice("priceOne", validFor, "money1");
        ProductOfferingPrice prodOfferingPriceTwo = new ComponentProductPrice("priceTwo", validFor, "money2");
        priceList.add(prodOfferingPriceOne);
        priceList.add(prodOfferingPriceTwo);
        TimePeriod validForIn =  new TimePeriod(DateUtils.datetimeFormat.parse("2015-03-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-04-01 23:59:59"));
        result= catalog.publishOffering(productOffering, validFor, priceList);
        assertEquals("publish Offering",true,result);
        assertEquals("publish Offering",2,catalog.getProdCatalogProdOffer().size());
        assertEquals("publish Offering",productOffering,catalog.getProdCatalogProdOffer().get(1).getProdOffering());
        assertEquals("publish Offering",true,catalog.getProdCatalogProdOffer().get(1).getProductOfferingPrice().equals(priceList));

    }
    @Test
    public  void testUnPublishProductOffering()throws ParseException{
        ProductOffering offering=null;
        try{
            catalog.unPublishOffering(offering);
            fail("offering is null ,excepted a IllegalArgumentException for offering");
        }   catch (IllegalArgumentException ex){

        }
        catalog.unPublishOffering(productOffering);
        assertEquals("productOffering not publish", 0, catalog.retrieveProductOffering(new Date()).size());
        catalog.publishOffering(productOffering, new TimePeriod(DateUtils.datetimeFormat.parse("2015-07-10 23:59:59"), DateUtils.datetimeFormat.parse("2015-08-01 23:59:59")));
        catalog.unPublishOffering(productOffering);
        assertEquals("productOffering not publish", 0, catalog.retrieveProductOffering(new Date()).size());
    }
    @Test
    public void testRetrieveProductOffering ()throws ParseException{
        try{
            catalog.retrieveProductOffering(null);
            fail("time is null ,excepted a IllegalArgumentException for time");
        }   catch (IllegalArgumentException e ){

        }
        List<ProductOffering> offeringList=  catalog.retrieveProductOffering(new Date());
        assertEquals("catalog can't publish Offering", 0, offeringList.size());
        catalog.publishOffering(productOffering, new TimePeriod(DateUtils.datetimeFormat.parse("2015-07-10 23:59:59"), DateUtils.datetimeFormat.parse("2015-08-01 23:59:59"))) ;
        offeringList= catalog.retrieveProductOffering(new Date());
        assertEquals("catalog can't publish Offering (time is after)", 0, offeringList.size());
        TimePeriod validFor =new TimePeriod(DateUtils.datetimeFormat.parse("2015-07-8 23:59:59"), DateUtils.datetimeFormat.parse("2015-08-01 23:59:59"));
        catalog.publishOffering(productOffering, validFor) ;
        offeringList= catalog.retrieveProductOffering(new Date());
        assertEquals("catalog can't publish Offering ", 1, offeringList.size());
        assertEquals("catalog can't publish Offering ", productOffering, offeringList.get(0));
    }
    @Test
    public void testAdvertiseOfferingPrice() throws Exception{
        TimePeriod validFor = new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59"));
        TimePeriod validForPrice = new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-06-01 23:59:59"));

        ProductOfferingPrice price = null;
        //price is null
        try {
            boolean tag = catalog.advertiseOfferingPrice(productOffering, validFor, price);
            assertEquals("advertise offering price", false, tag);
            assertEquals("advertise offering price", 1, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().size());
            fail("price is null, no check. ");
        }catch (Exception e){

        }

        //can't find offering
        price = new ComponentProductPrice("priceOne", validForPrice, "money1");
        catalog.advertiseOfferingPrice(productOffering, validFor, price);
        assertEquals("advertise offering price", 0, catalog.getProdCatalogProdOffer() == null? 0 : catalog.getProdCatalogProdOffer().size());

        //normal
        catalog.publishOffering(productOffering, validFor);
        price = new ComponentProductPrice("priceOne", validForPrice, "money1");
        catalog.advertiseOfferingPrice(productOffering, validFor, price);
        assertEquals("advertise offering price", 1, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().size());
        assertEquals("advertise offering price", price, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().get(0));

        //whether the same price can add repeat
        catalog.advertiseOfferingPrice(productOffering, validFor, price);
        assertEquals("advertise offering price", 2, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().size());
        assertEquals("advertise offering price", price, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().get(1));

        //validFor is not right, this is mean the period of validity in relationship can't find
        TimePeriod validForRelate = new TimePeriod(DateUtils.datetimeFormat.parse("2015-01-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59"));
        ProductOfferingPrice priceTwo = new ComponentProductPrice("priceTwo", validForPrice, "money2");
        boolean tagFlg = catalog.advertiseOfferingPrice(productOffering, validForRelate, priceTwo);
        assertEquals("advertise offering price", 2, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().size());
        assertEquals("advertise offering price", false, tagFlg);
    }

    @Test
    public void testSetOfferingPrice() throws Exception{
        ProductOffering offering = null;
        TimePeriod validFor = new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59"));
        catalog.publishOffering(productOffering, validFor);

        // priceList is null
        List<ProductOfferingPrice> priceList = null;
        catalog.setOfferingPrice(productOffering, validFor, priceList);
        assertEquals("set offering price", 0 , catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice() == null ? 0 : catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().size());

        //normal
        priceList = new ArrayList<ProductOfferingPrice>();
        ProductOfferingPrice prodOfferingPriceOne = new ComponentProductPrice("priceOne", validFor, "money1");
        ProductOfferingPrice prodOfferingPriceTwo = new ComponentProductPrice("priceTwo", validFor, "money2");
        priceList.add(prodOfferingPriceOne);
        priceList.add(prodOfferingPriceTwo);
        catalog.setOfferingPrice(productOffering, validFor, priceList);
        assertEquals("set offering price", 2, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().size());
        assertEquals("set offering price", true, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().equals(priceList));

        //is success to replace all of price
        priceList = new ArrayList<ProductOfferingPrice>();
        ProductOfferingPrice prodOfferingPriceOneR = new ComponentProductPrice("priceOneR", validFor, "money1R");
        ProductOfferingPrice prodOfferingPriceTwoR = new ComponentProductPrice("priceTwoR", validFor, "money2R");
        priceList.add(prodOfferingPriceOneR);
        priceList.add(prodOfferingPriceTwoR);
        catalog.setOfferingPrice(productOffering, validFor, priceList);
        assertEquals("set offering price", 2, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().size());
        assertEquals("set offering price", true, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().equals(priceList));

    }
}
