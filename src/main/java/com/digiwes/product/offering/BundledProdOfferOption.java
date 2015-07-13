package com.digiwes.product.offering;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A set of numbers that specifies the lower and upper limits for a ProductOffering that can be procured as part of the related BundledProductOffering.
 * 
 * Values can range from 0 to unbounded.
 */
public class BundledProdOfferOption {

    private Logger logger = Logger.getLogger(BundledProdOfferOption.class);
    private  ProductOffering productOffering;
    private  BundledProductOffering bundledProductOffering;
    
    public   ProductOffering getProductOffering() {
		return productOffering;
	}

	public void setProductOffering(ProductOffering productOffering) {
		this.productOffering = productOffering;
	}

	public BundledProductOffering getBundledProductOffering() {
		return bundledProductOffering;
	}

	public void setBundledProductOffering(
			BundledProductOffering bundledProductOffering) {
		this.bundledProductOffering = bundledProductOffering;
	}

	/**
     * The lower limit of related ProductOfferings that can be procured as part of the BundledProductOffering.
     * 
     * Values can range from 0 to unbounded.
     */
    private int numberRelOfferLowerLimit;
    /**
     * The upper limit of related ProductOfferings that can be procured as part of the BundledProductOffering.
     * 
     * Values can range from 0 to unbounded.
     */
    private int numberRelOfferUpperLimit;

    public int getNumberRelOfferLowerLimit() {
        return this.numberRelOfferLowerLimit;
    }

    public void setNumberRelOfferLowerLimit(int numberRelOfferLowerLimit) {
        this.numberRelOfferLowerLimit = numberRelOfferLowerLimit;
    }

    public int getNumberRelOfferUpperLimit() {
        return this.numberRelOfferUpperLimit;
    }

    public void setNumberRelOfferUpperLimit(int numberRelOfferUpperLimit) {
        this.numberRelOfferUpperLimit = numberRelOfferUpperLimit;
    }

    /**
     * 
     * @param offering
     * @param lowerLimit
     * @param upperLimit
     */
    public BundledProdOfferOption(ProductOffering offering, int lowerLimit, int upperLimit) {
        if( null == offering ){
            logger.error("offering should not be null");
            throw new IllegalArgumentException("offering should not be null");
        }
        if(lowerLimit <  upperLimit){
            logger.error("upperLimit is less than lowerLimit");
            throw new IllegalArgumentException("upperLimit is less than lowerLimit");
        }
    	this.productOffering = offering;
    	this.numberRelOfferLowerLimit = lowerLimit;
    	this.numberRelOfferUpperLimit = upperLimit;
    	
    }
    public String toString(){
        Map<String,Object> result =new HashMap<String,Object>();
        result.put("lowerLimit",numberRelOfferLowerLimit);
        result.put("upperLimit",numberRelOfferUpperLimit);
        result.put("productOffering",productOffering.getBasicMap().toString());
        return result.toString();
    }

}