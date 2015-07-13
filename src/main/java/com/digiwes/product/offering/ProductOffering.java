package com.digiwes.product.offering;

import java.util.*;

import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.enums.ProdSpecEnum;
import com.digiwes.product.offering.catalog.ProdCatalogProdOffer;
import com.digiwes.product.offering.price.ProductOfferingPrice;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * The presentation of one or more ProductSpecifications to the marketplace for sale, rental, or lease for a ProductOfferingPrice. A ProductOffering may target one or more MarketSegments, be included in one or more ProductCatalog, presented in support of one or more ProductStrategies, and made available in one or more Places. ProductOffering may represent a simple offering of a single ProductSpecification or could represent a bundling of one or more other ProductOffering.
 */
public abstract class ProductOffering {
    private static final Logger log = Logger.getLogger(ProductOffering.class);

    private List<ProductOfferingPrice> productOfferingPrice;
    private List<ProductOfferingRelationship> prodOfferingRelationship;
    private List<ProdCatalogProdOffer> prodCatalogProdOffer;
    
    public List<ProductOfferingPrice> getProductOfferingPrice() {
		return productOfferingPrice;
	}

	public void setProductOfferingPrice(
			List<ProductOfferingPrice> productOfferingPrice) {
		this.productOfferingPrice = productOfferingPrice;
	}

	public List<ProductOfferingRelationship> getProdOfferingRelationship() {
		return prodOfferingRelationship;
	}

	public void setProdOfferingRelationship(
			List<ProductOfferingRelationship> prodOfferingRelationship) {
		this.prodOfferingRelationship = prodOfferingRelationship;
	}

	public List<ProdCatalogProdOffer> getProdCatalogProdOffer() {
		return prodCatalogProdOffer;
	}

	public void setProdCatalogProdOffer(
			List<ProdCatalogProdOffer> prodCatalogProdOffer) {
		this.prodCatalogProdOffer = prodCatalogProdOffer;
	}

	/**
     * A unique identifier for the ProductOffering.
     */
    private String id;
    /**
     * A word, term, or phrase by which the ProductOffeirng is known and distinguished from other ProductOfferings.
     */
    private String name;
    /**
     * A narrative that explains what the offering is.
     */
    private String description;
    /**
     * The period during which the offering is applicable.
     */
    private TimePeriod validFor;
    /**
     * The condition in which the offering exists, such as planned, obsolete, active
     */
    private String status;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TimePeriod getValidFor() {
        return this.validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }

    public String getStatus() {
        return this.status;
    }

    /**
     * 
     * @param id
     * @param name
     * @param description
     * @param validFor
     */
    public ProductOffering(String id, String name, String description, TimePeriod validFor) {
        if(StringUtils.isEmpty(id)){
            log.error("id is null or empty, please check the parameter");
            throw new IllegalArgumentException("id is null or empty, please check the parameter");
        }
        if(StringUtils.isEmpty(name)){
            log.error("name is null or empty, please check the parameter");
            throw new IllegalArgumentException("name is null or empty, please check the parameter");
        }
    	this.id = id;
    	this.name = name;
    	this.description = description;
    	this.validFor = validFor;
        this.status = ProdSpecEnum.ProdOfferingStatus.PORD_OFFERING_STATUS_ACTIVE.getValue();
    }

    private ProductOfferingRelationship retrieveRelatedOfferingByOffering(ProductOffering targetOffering){
        if(null != this.prodOfferingRelationship){
            for(ProductOfferingRelationship offeringRelate : prodOfferingRelationship){
                if(offeringRelate.getTargetOffering().equals(targetOffering)){
                    return offeringRelate;
                }
            }
        }
        return null;
    }
    /**
     * 
     * @param offering
     * @param relationType
     * @param validFor
     */
    public boolean addRelatedOffering(ProductOffering offering, String relationType, TimePeriod validFor) {
        if(StringUtils.isEmpty(relationType)){
            log.error("the parameter relationType is null. ");
            throw new IllegalArgumentException("relationType should not be null. ");
        }
        if(null == offering){
            log.error("the object of ProductOffering is null. ");
            throw new IllegalArgumentException("ProductOffering should not be null .");
        }
        if(this.equals(offering)){
            log.error("can't establish relationship with itself. ");
            throw new IllegalArgumentException("can't establish relationship with itself. ");
        }
        //checkout time is in period
        ProductOfferingRelationship offeringRealtionship = this.retrieveRelatedOfferingByOffering(offering);
        if(null != offeringRealtionship){
            if(offeringRealtionship.getValidFor().isOverlap(validFor)){
                log.error("time is in period");
                return false;
            }
        }
    	ProductOfferingRelationship relatedOffering =  new ProductOfferingRelationship(this,offering,relationType,validFor);
    	if(null == this.prodOfferingRelationship){
    		this.prodOfferingRelationship = new ArrayList<ProductOfferingRelationship>();
    	}
    	this.prodOfferingRelationship.add(relatedOffering);
        return true;
    }

    /**
     * 
     * @param offering
     */
    public boolean updateRelatedOffering(ProductOffering offering, TimePeriod oldValidFor, TimePeriod validFor) {
        if(null == offering){
            log.error("offering should not be null .");
            throw new IllegalArgumentException("offering should not be null. ");
        }
        if (null == oldValidFor ){
            log.error("oldValidFor should not be null .");
            throw new IllegalArgumentException("oldValidFor should not be null. ");
        }
        if (null == validFor ){
            log.error("validFor should not be null .");
            throw new IllegalArgumentException("validFor should not be null. ");
        }
    	if(this.prodOfferingRelationship != null ){
            for(ProductOfferingRelationship offeringRelationship : this.prodOfferingRelationship){
                if(offeringRelationship.getTypeRelationship().equals(offering) && offeringRelationship.getValidFor().equals(oldValidFor)){
                    offeringRelationship.setValidFor(validFor);
                    return true;
                }
            }
    	}
        return false;
    }

    /**
     * 
     * @param relationType
     */
    public List<ProductOffering> retrieveRelatedOffering(String relationType) {
    	List<ProductOffering> prodOffering= new ArrayList<ProductOffering>();
    	if(StringUtils.isEmpty(relationType)){
            log.error("relationType should not be null .");
            throw new IllegalArgumentException("relationType should not be null. ");
    	}
    	if( null != this.prodOfferingRelationship){
            for(ProductOfferingRelationship offeringRelat : prodOfferingRelationship){
                if(relationType.equals(offeringRelat.getTypeRelationship())){
                    prodOffering.add(offeringRelat.getTargetOffering());
                }
            }
    	}
        return prodOffering;
    }

    /**
     * 
     * @param relationType
     * @param time
     */
    public List<ProductOffering> retrieveRelatedOffering(String relationType, Date time) {
        List<ProductOffering> prodOffering= new ArrayList<ProductOffering>();
        if(StringUtils.isEmpty(relationType)){
            log.error("relationType should not be null .");
            throw new IllegalArgumentException("relationType should not be null. ");
        }
        if( null != this.prodOfferingRelationship){
            if(null == time){
                for(ProductOfferingRelationship offeringRelat : prodOfferingRelationship){
                    if(relationType.equals(offeringRelat.getTypeRelationship())){
                        prodOffering.add(offeringRelat.getTargetOffering());
                    }
                }
            }else{
                for(ProductOfferingRelationship offeringRelat : prodOfferingRelationship){
                    if(relationType.equals(offeringRelat.getTypeRelationship()) && offeringRelat.getValidFor().isInTimePeriod(time)){
                        prodOffering.add(offeringRelat.getTargetOffering());
                    }
                }
            }
        }
        return prodOffering;
    }

    /**
     * 
     * @param price
     */
    public void addPrice(ProductOfferingPrice price) {
        if(null == price){
            log.error("price should not be null .");
            throw new IllegalArgumentException("price should not be null. ");
        }
        if(null == this.productOfferingPrice ){
            this.productOfferingPrice = new ArrayList<ProductOfferingPrice>();
        }
        this.productOfferingPrice.add(price);
    }

    /**
     *
     * @param priceList
     */
    public void setPrice(List<ProductOfferingPrice> priceList) {
        if(null == priceList || priceList.size()<=0){
            log.error("priceList should not be null .");
            throw new IllegalArgumentException("priceList should not be null. ");
        }
        if(null == this.productOfferingPrice){
            this.productOfferingPrice = new ArrayList<ProductOfferingPrice>();
            this.productOfferingPrice = priceList;
        }else{
            this.productOfferingPrice.clear();
            for(ProductOfferingPrice offeringPrice : priceList){
                this.productOfferingPrice.add(offeringPrice);
            }
        }
    }

    /**
     * 
     * @param time
     */
    public List<ProductOfferingPrice> retrievePrice(Date time) {
        if(null == time){
            log.error("time should not be null .");
            throw new IllegalArgumentException("time should not be null. ");
        }
        List<ProductOfferingPrice> rtnProdOfferingPrice = new ArrayList<ProductOfferingPrice>();
        if(null != this.productOfferingPrice){
            for(ProductOfferingPrice offeringPrice : this.productOfferingPrice ){
                if(offeringPrice.getValidFor().isInTimePeriod(time)){
                    rtnProdOfferingPrice.add(offeringPrice);
                }
            }
        }
    	return rtnProdOfferingPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductOffering that = (ProductOffering) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        Map<String, Object> rtnMap = getBasicMap();
        rtnMap.put("productOfferingPrice", this.productOfferingPrice);
        return rtnMap.toString();
    }
    public Map<String, Object> getBasicMap(){
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("id",this.id);
        rtnMap.put("name",this.name);
        rtnMap.put("description",this.description);
        rtnMap.put("validFor",this.validFor);
        rtnMap.put("status",this.status);
        return rtnMap;
    }
}