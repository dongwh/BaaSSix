package com.ai.baas.product.offering;

import com.ai.baas.basetype.TimePeriod;
import com.ai.baas.common.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BundledProductOffering extends ProductOffering {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BundledProductOffering.class);

    public List<BundledProdOfferOption> bundledProdOfferOption;

    public List<BundledProdOfferOption> getBundledProdOfferOption() {
        return this.bundledProdOfferOption;
    }

    public void setBundledProdOfferOption(List<BundledProdOfferOption> bundledProdOfferOption) {
        this.bundledProdOfferOption = bundledProdOfferOption;
    }

    /**
     * 
     * @param id
     * @param name
     * @param description
     * @param validFor
     */
    public BundledProductOffering(String id, String name, String description, TimePeriod validFor) {
    	super(id, name, description, validFor);
    }

    /**
     * 
     * @param offering
     */
    public boolean addSubOffering(ProductOffering offering) {
        if(null == offering){
            log.error("time should not be null .");
            throw new IllegalArgumentException("time should not be null. ");
        }
        if(offering.equals(this)){
           return false;
        }
        initBundleProdOfferOption();
        for(BundledProdOfferOption option :bundledProdOfferOption){
            if(option.getProductOffering().equals(offering)){
                return  false;
            }
        }

        BundledProdOfferOption bundleOption =  new BundledProdOfferOption(offering, 0, 0);
        this.bundledProdOfferOption.add(bundleOption);
        return  true;
    }

    /**
     * 
     * @param offering
     * @param lowerLimit
     * @param upperLimit
     */
    public boolean addSubOffering(ProductOffering offering, int lowerLimit, int upperLimit) {
        if(null == offering){
            log.error("time should not be null .");
            throw new IllegalArgumentException("time should not be null. ");
        }
        if(offering.equals(this)){
            return false;
        }
        if(NumberUtil.compareTheNumber(lowerLimit, upperLimit) == 1){
            log.error("lowerLimit should not be greater than upperLimit. ");
            throw new IllegalArgumentException("lowerLimit should not be greater than upperLimit. ");
        }
        initBundleProdOfferOption();
         for(BundledProdOfferOption option :bundledProdOfferOption){
             if(option.getProductOffering().equals(offering)){
                return  false;
             }
         }

    	BundledProdOfferOption bundleOption =  new BundledProdOfferOption(offering,lowerLimit,upperLimit);
    	this.bundledProdOfferOption.add(bundleOption);
        return  true;
    }

    public List<ProductOffering> getSubOffering() {
        List<ProductOffering> rtnProdOffering = new ArrayList<ProductOffering>();
        if(null != this.bundledProdOfferOption){
            for(BundledProdOfferOption bundleOption : this.bundledProdOfferOption){
                rtnProdOffering.add(bundleOption.getProductOffering());
            }
        }
        return rtnProdOffering;
    }

    private void initBundleProdOfferOption(){
        if(null == this.bundledProdOfferOption){
            this.bundledProdOfferOption = new ArrayList<BundledProdOfferOption>();
        }
    }


}