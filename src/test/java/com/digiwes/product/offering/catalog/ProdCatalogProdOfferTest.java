package com.digiwes.product.offering.catalog;


import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.util.DateUtils;
import com.digiwes.product.offering.ProductOffering;
import com.digiwes.product.offering.SimpleProductOffering;
import com.digiwes.product.offering.price.ComponentProductPrice;
import com.digiwes.product.offering.price.ProductOfferingPrice;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by dongwh on 2015-7-10.
 */
public class ProdCatalogProdOfferTest {
    private ProductOffering productOffering;
    private ProductCatalog catalog;
    private TimePeriod validFor ;
    @Before
    public void setUp() throws Exception{
        validFor = new TimePeriod(DateUtils.datetimeFormat.parse("2015-02-10 23:59:59"),DateUtils.datetimeFormat.parse("2015-08-01 23:59:59"));
        productOffering =new SimpleProductOffering("1","13-inch MacBook Pro","13-inch MacBook Pro",validFor ,null) ;
        catalog = new ProductCatalog("1","MAC","", validFor);
    }

    @Test
    public void testAdvertiseOfferingPrice(){
        catalog.publishOffering(productOffering, validFor);

        //price is null
       ProductOfferingPrice price = null;
        boolean rtnFlag ;
        try {
            rtnFlag = catalog.getProdCatalogProdOffer().get(0).advertiseOfferingPrice(price);
            assertEquals("advertise offering price", false, rtnFlag);
            assertEquals("advertise offering price", 0, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice()==null?0:catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().size());
            fail("price is null, no check");
        } catch (Exception e){

        }

        //normal
        price = new ComponentProductPrice("priceOne", validFor, "moneyOne");
        rtnFlag = catalog.getProdCatalogProdOffer().get(0).advertiseOfferingPrice(price);
        assertEquals("advertise offering price", true, rtnFlag);
        assertEquals("advertise offering price", 1, catalog.getProdCatalogProdOffer().get(0).getProductOfferingPrice().size());
    }

}
