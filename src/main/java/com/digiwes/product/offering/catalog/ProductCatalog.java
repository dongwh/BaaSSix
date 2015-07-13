package com.digiwes.product.offering.catalog;


import java.lang.reflect.Array;
import java.util.*;

import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.catalog.Catalog;
import com.digiwes.product.offering.ProductOffering;
import com.digiwes.product.offering.price.ProductOfferingPrice;
import org.apache.log4j.Logger;

/**
 * A list of ProductOfferings for sale, with prices and illustrations, for example in book form or on the web. ProductCatalogs can be used by Customers during a self-care ordering process and may be used across one or more DistributionChannels.
 *  
 * A list of ProductOfferings for sale, with prices and illustrations, for example in book form or on the web. ProductCatalogs can be used by Customers during a self-care ordering process and may be used across one or more DistributionChannels.
 * ?
 */
public class ProductCatalog extends Catalog {
   private Logger logger =Logger.getLogger(ProductCatalog.class);
   public List<ProdCatalogProdOffer> prodCatalogProdOffer;

   public List<ProdCatalogProdOffer> getProdCatalogProdOffer() {
		return prodCatalogProdOffer;
   }

	/**
     * 
     * @param id
     * @param name
     * @param type
     * @param validFor
     */
    public ProductCatalog(String id, String name, String type, TimePeriod validFor) {
        super(id, name, type, validFor);
    }

    /**
     * 
     * @param offering
     * @param validFor
     */
    public boolean publishOffering(ProductOffering offering, TimePeriod validFor) {
        if( null == offering ){
           logger.error("offer can't be null");
           throw  new IllegalArgumentException("offering can't be null");
        }
        if(null != validFor && validFor.getEndDateTime().getTime() <= (new Date()).getTime()){
            logger.error("can't release the failure offering");
            throw  new IllegalArgumentException("can't release the failure offering");
        }
        if( null == prodCatalogProdOffer ){
            prodCatalogProdOffer = new ArrayList<ProdCatalogProdOffer>();
        }else{
            List<ProductOffering> validOfferingList = this.retrieveProductOffering(new Date());
            for(ProductOffering validOffering : validOfferingList){
                if(validOffering.equals(offering) &&  null == validOffering.getValidFor() ){
                   return false;
                } else if(validOffering.equals(offering) && validOffering.getValidFor().isOverlap(validFor) ){
                    return false;
                }
            }
        }
    	ProdCatalogProdOffer prodOffer = new ProdCatalogProdOffer( offering,  validFor);
    	this.prodCatalogProdOffer.add(prodOffer);
        return true;
    }

    /**
     * 
     * @param offering
     * @param validFor
     * @param price
     */
    public boolean publishOffering(ProductOffering offering, TimePeriod validFor, List<ProductOfferingPrice> price) {
        if( null == offering ){
            logger.error("offer can't be null");
            throw  new IllegalArgumentException("offering can't be null");
        }
        if(null != validFor && validFor.getEndDateTime().getTime() <= (new Date()).getTime()){
            logger.error("can't release the failure offering");
            throw  new IllegalArgumentException("can't release the failure offering");
        }
        if( null == prodCatalogProdOffer ){
            prodCatalogProdOffer = new ArrayList<ProdCatalogProdOffer>();
        }else{
            ProdCatalogProdOffer validCatalogOffering= retrieveProductCatalogProductOffering(offering, new Date());
            if( null == validCatalogOffering){
                 return false;
            }
        }
        ProdCatalogProdOffer prodOffer = new ProdCatalogProdOffer( offering,  validFor,price);
        this.prodCatalogProdOffer.add(prodOffer);
        return true;
    }


    /**
     * 
     * @param offering
     */
    public void unPublishOffering(ProductOffering offering) {
        if( null == offering){
            logger.error("offering can't be null");
            throw new IllegalArgumentException("offering can't be null ");
        }
        ProdCatalogProdOffer validCatalogOffering= retrieveProductCatalogProductOffering(offering,new Date());
        if( null == validCatalogOffering){
           return;
        }
        validCatalogOffering.setValidFor(new TimePeriod(validCatalogOffering.getValidFor().getStartDateTime(), new Date()));

    }

    /**
     * 
     * @param offering
     * @param time
     */
    public List<ProductOfferingPrice> retrieveOfferingPrice(ProductOffering offering, Date time) {
       if( null ==offering ) {
           logger.error("offering should not be null");
           throw  new IllegalArgumentException("offering should not be null");
       }
       if( null ==time ){
           logger.error("time should not be null");
           throw  new IllegalArgumentException("time should not be null");
       }
        List<ProductOfferingPrice> validPrices=new ArrayList<ProductOfferingPrice>();
       if( null != this.prodCatalogProdOffer && prodCatalogProdOffer.size()>0 )   {
           for (ProdCatalogProdOffer catalogOffering :prodCatalogProdOffer){
               if(catalogOffering.getProdOffering().equals(offering) && catalogOffering.getValidFor().isInTimePeriod(time) ){
                  List<ProductOfferingPrice> price= catalogOffering.getProductOfferingPrice();
                   for(ProductOfferingPrice offeringPrice :price){
                           if(offeringPrice.getValidFor() != null && offering.getValidFor().isInTimePeriod(time) ){
                               validPrices.add(offeringPrice);
                           }
                   }
                 break;
               }
           }
       }
       return validPrices;
    }

    /**
     * 
     * @param offering
     * @param timePeriod
     * @param price
     */
    public boolean advertiseOfferingPrice(ProductOffering offering, TimePeriod timePeriod, ProductOfferingPrice price) {
        if( null != prodCatalogProdOffer && prodCatalogProdOffer.size() >0 ) {
            for(ProdCatalogProdOffer catalogOffering : prodCatalogProdOffer){
                if( timePeriod == catalogOffering.getValidFor() && catalogOffering.getProdOffering().equals(offering)){
                   return catalogOffering.advertiseOfferingPrice(price);
                }
            }
        }
        return false;
    }
    public List<ProductOffering> retrieveProductOffering(Date time){
        if( null == time ){
           logger.error("time should not be null");
           throw   new IllegalArgumentException("time should not be null");
        }
        List<ProductOffering> offerings=new ArrayList<ProductOffering>();
        if( null != this.prodCatalogProdOffer )  {
            for(ProdCatalogProdOffer catalogOffering : prodCatalogProdOffer){
                if( null == catalogOffering.getValidFor() || catalogOffering.getValidFor().isInTimePeriod(time)){
                    offerings.add(catalogOffering.getProdOffering());
                }
            }
        }
        return offerings;
    }
    private ProdCatalogProdOffer retrieveProductCatalogProductOffering(ProductOffering offering,Date time){
        if( null != this.prodCatalogProdOffer )  {
            for(ProdCatalogProdOffer catalogOffering : prodCatalogProdOffer){
                if( catalogOffering.getProdOffering().equals(offering) && (null == catalogOffering.getValidFor() || catalogOffering.getValidFor().isInTimePeriod(time))){
                    return catalogOffering;
                }
            }
        }
        return null;
    }


    /**
     * 
     * @param offering
     * @param timePeriod
     * @param price
     */
    public boolean setOfferingPrice(ProductOffering offering, TimePeriod timePeriod, List<ProductOfferingPrice> price) {
        if(null == offering){
            logger.error("offering should not be null");
            throw  new IllegalArgumentException("offering should not be null");
        }
        if( null != prodCatalogProdOffer && prodCatalogProdOffer.size() >0 ) {
            for(ProdCatalogProdOffer catalogOffering : prodCatalogProdOffer){
                if( timePeriod == catalogOffering.getValidFor() && catalogOffering.getProdOffering().equals(offering)){
                    catalogOffering.setOfferingPrice(price);
                    return true;
                }
            }
        }
        return false;
    }

    public String toString() {
        Map<String,Object> result =basicInfoToMap();
        result.put("prodCatalogProdOffering",prodCatalogProdOffer);
        return result.toString();
    }

}