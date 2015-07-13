package com.ai.baas.product.offering.catalog;

import com.ai.baas.product.offering.*;
import java.util.*;

import com.ai.baas.product.offering.price.*;
import com.ai.baas.basetype.*;
import com.ai.util.ConvertMap2Json;
import org.apache.log4j.Logger;

/**
 * The appearance of a ProductOffering in a ProductCatalog.
 */
public class ProdCatalogProdOffer {

    private Logger logger= Logger.getLogger(ProdCatalogProdOffer.class);
    public ProductOffering prodOffering;
    public List<ProductOfferingPrice> productOfferingPrice;
    /**
     * The period during which the ProductOffering appears in the ProductCatalog.
     */
    private TimePeriod validFor;

    public TimePeriod getValidFor() {
        return this.validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }
    public  ProductOffering getProdOffering(){
        return this.prodOffering;
    }
    public   List<ProductOfferingPrice> getProductOfferingPrice(){
        return this.productOfferingPrice;
    }

    /**
     *
     * @param offering
     * @param validFor
     */
    public ProdCatalogProdOffer(ProductOffering offering, TimePeriod validFor) {
        if( null == offering){
            logger.error("offering can't be null");
            throw new IllegalArgumentException("offering Can't be null");
        }
        this.prodOffering =  offering;
        this.validFor = validFor;
    }

    /**
     *
     * @param offering
     * @param validFor
     * @param price
     */
    public ProdCatalogProdOffer(ProductOffering offering, TimePeriod validFor, List<ProductOfferingPrice> price) {
        if( null == offering){
            logger.error("offering can't be null");
            throw new IllegalArgumentException("offering Can't be null");
        }
        this.prodOffering =  offering;
        this.validFor = validFor;
        this.productOfferingPrice=price;
    }

    /**
     *
     * @param price
     */
    public boolean advertiseOfferingPrice(ProductOfferingPrice price) {
        if( null == price ){
            logger.error("advertiseOfferingPrice method ,price should not be null");
            throw new IllegalArgumentException("advertiseOfferingPrice method ,price should not be null");
        }
        if ( null == this.productOfferingPrice ){
            productOfferingPrice=new ArrayList<ProductOfferingPrice>();
        }        productOfferingPrice.add(price);
        return  true;
    }

    /**
     *
     * @param price
     */
    public void setOfferingPrice(List<ProductOfferingPrice> price) {
        productOfferingPrice=price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdCatalogProdOffer that = (ProdCatalogProdOffer) o;

        if (!prodOffering.equals(that.prodOffering)) return false;
        return validFor.equals(that.validFor);

    }

    @Override
    public int hashCode() {
        int result = prodOffering.hashCode();
        result = 31 * result + validFor.hashCode();
        return result;
    }

    public String toString() {
        Map<String,Object> result=new HashMap<String, Object>();
        result.put("ProductOffering",prodOffering);
        result.put("ProductOfferingPrice",productOfferingPrice);
        result.put("validFor",validFor);
        return result.toString();
    }

}