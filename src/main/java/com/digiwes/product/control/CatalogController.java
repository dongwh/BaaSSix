package com.ai.digiwes.product.control;

import com.ai.baas.basetype.TimePeriod;
import com.ai.baas.common.catalog.Catalog;
import com.ai.baas.common.util.DateUtils;
import com.ai.baas.product.offering.ProductOffering;
import com.ai.baas.product.offering.catalog.ProductCatalog;
import com.ai.baas.product.offering.price.ProductOfferingPrice;
import com.ai.digiwes.product.control.persistence.CatalogPersistence;
import com.ai.digiwes.product.control.persistence.PersistenceFactory;
import com.ai.digiwes.product.control.persistence.ProductOfferingPersistence;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.*;
import com.ai.digiwes.product.control.persistence.CatalogPersistence;
import com.ai.digiwes.product.control.persistence.impl.CatalogPersistenceSimpleImpl;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaoyp on 2015/7/10.
 */
public class CatalogController {
      private Logger logger=Logger.getLogger(CatalogController.class);

     public  ProductCatalog publishOffering(String catalogId,String offeringId,List<ProductOfferingPrice> productOfferingPrices)throws Exception{

        TimePeriod validFor =new TimePeriod("2015-07-10 00:00:00", "2015-08-10 23:59:59");
        CatalogPersistence catalogPersistence =PersistenceFactory.getCatalogPersistence();
        ProductOfferingPersistence productOfferingPersistence= PersistenceFactory.getProdOfferingPersistence();
       ProductCatalog catalog=catalogPersistence.load(catalogId);
        ProductOffering offering=productOfferingPersistence.load(offeringId);
        catalog.publishOffering(offering, validFor, productOfferingPrices);
        catalogPersistence.save(catalog);
        return catalog;

    }
    /**
     * unPublish offering from productCatalog
     * @param catalogId
     * @param offeringId
     */
    public void unPublishProductOffering(String catalogId, String offeringId){
        List<ProductOffering> productOffering = null;
        try {
            CatalogPersistence catalogPersistence = new CatalogPersistenceSimpleImpl();
            ProductCatalog prodCatalog = catalogPersistence.load(catalogId);
            productOffering = prodCatalog.retrieveProductOffering(new Date());
            if(null != productOffering){
                for(ProductOffering prodOffering : productOffering){
                    if(prodOffering.getId().equals(offeringId)){
                        productOffering.remove(prodOffering);
                    }
                }
            }
        }catch (Exception e){
            logger.error("parameter is null or empty, no check. ");
        }
    }

    /**
     * retrieve productOffering by catalogId (the current time effectively)
     * @param catalogId
     * @return
     */
    public List<ProductOffering> retrieveProductOffering(String catalogId){
        List<ProductOffering> productOffering = null;
        try {
            CatalogPersistence catalogPersistence = new CatalogPersistenceSimpleImpl();
            ProductCatalog prodCatalog = catalogPersistence.load(catalogId);
            productOffering = prodCatalog.retrieveProductOffering(new Date());
        } catch (Exception e){
            logger.error("catalogId is null or empty, no check. ");
        }
        return productOffering;
    }
}
