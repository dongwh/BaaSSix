package com.ai.baas.product.offering;

import com.ai.baas.basetype.TimePeriod;
import com.ai.baas.product.spec.ProductSpecification;
import com.ai.util.ConvertMap2Json;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * A type of ProductOffering that does not have any subordinate ProductOfferings, that is, an SimpleProductOffering is a leaf-level ProductOffering.
 */
public class SimpleProductOffering extends ProductOffering {

	private Logger logger=Logger.getLogger(SimpleProductOffering.class);
	 private ProductSpecification productSpecification;
	
	
    public ProductSpecification getProductSpecification() {
		return productSpecification;
	}


	public void setProductSpecification(ProductSpecification productSpecification) {
		this.productSpecification = productSpecification;
	}


	/**
     * 
     * @param id
     * @param name
     * @param description
     * @param validFor
     * @param prodSpec
     */
    public SimpleProductOffering(String id, String name, String description, TimePeriod validFor, ProductSpecification prodSpec) {

    	super(id, name, description, validFor);
		if( null ==prodSpec ){
			logger.error("prodSpec can't be  null");
			throw  new IllegalArgumentException("prodSpec can't be  null");
		}
    	this.productSpecification = prodSpec;
    }

	public String toString(){
		Map<String, Object> rtnMap = getBasicMap();
		rtnMap.put("productSpecification", productSpecification);
		return rtnMap.toString();
	}

}