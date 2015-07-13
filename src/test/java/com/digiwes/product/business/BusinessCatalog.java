package com.digiwes.product.business;


import com.digiwes.product.control.CatalogController;
import com.digiwes.product.control.persistence.CatalogPersistence;
import com.digiwes.product.control.persistence.PersistenceFactory;
import com.digiwes.product.control.persistence.ProductOfferingPersistence;
import com.digiwes.product.offering.ProductOffering;
import com.digiwes.product.offering.catalog.ProductCatalog;
import com.digiwes.product.offering.price.ProductOfferingPrice;
import com.digiwes.product.spec.data.*;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by dongwh on 2015-7-10.
 */
public class BusinessCatalog {
    private  static CatalogPersistence catalogPersistence;
    private static ProductOfferingPersistence productOfferingPersistence;
    private  static CatalogController controller;
	private static Logger logger=Logger.getLogger(BusinessCatalog.class);
    @BeforeClass
    public static  void setUpBeforeClass (){

        catalogPersistence = PersistenceFactory.getCatalogPersistence();
        productOfferingPersistence = PersistenceFactory.getProdOfferingPersistence();
        controller =new CatalogController();
    }
    /**
     * create ProductSpecification
     */
	 @Before
    public  void createProductSpecification(){
          SpecCharData.init();       // create char
          SpecData.init();           //create spec
          OfferingData.init();
          PriceData.init();
          CatalogData.init();
      }
    /**
     * publish offering in ProductCatalog
     */
    @Test
     public void publicCatalog()throws  Exception{
         List<ProductOfferingPrice> offeringPriceList =new ArrayList<ProductOfferingPrice>();
         offeringPriceList.add(PriceData.getPrice("price_1"));
         ProductCatalog catalog = controller.publishOffering("catalog_1","off_1",offeringPriceList);
		 logger.info(catalog.toString());
     }
    /**
     * unPublish offering from productCatalog
     */
    /*public void testUnPublishProductOffering(){
        controller.unPublishProductOffering("catalog_id", "off_1");
    }*/


    /**
     * retrieve offering by productCatalogId (the current time effectively)
     */
    @Test
    public void testRetrieveProductOffering()throws  Exception{
        List<ProductOfferingPrice> offeringPriceList =new ArrayList<ProductOfferingPrice>();
        offeringPriceList.add(PriceData.getPrice("price_1"));
        ProductCatalog catalog = controller.publishOffering("catalog_1","off_1",offeringPriceList);


        String catalogId = "catalog_1";
        List<ProductOffering> prodOffering = controller.retrieveProductOffering(catalogId);
        logger.info(printProductOfferingByCatalogId(prodOffering));
    }

    //print  under the catalog of offering
    private String printProductOfferingByCatalogId(List<ProductOffering> prodOfferingList){
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("productOffering", prodOfferingList);
        return rtnMap.toString();
    }
}


