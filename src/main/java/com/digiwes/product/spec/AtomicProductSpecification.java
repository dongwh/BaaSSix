package com.digiwes.product.spec;

import com.digiwes.basetype.TimePeriod;

import java.util.ArrayList;


/**
 * A type of ProductSpecification that does not have any subordinate ProductSpecifications, that is, an AtomicProductSpecification is a leaf-level ProductSpecification.
 */
public class AtomicProductSpecification extends ProductSpecification {

    /**
     * 
     * @param productNumber
     * @param name
     * @param brand
     * @param description
     * @param validFor
     */
    public AtomicProductSpecification(String productNumber, String name, String brand, String description, TimePeriod validFor) {
    	super(name, productNumber, brand, validFor, description);
    }

    /**
     * 
     * @param productNumber
     * @param name
     * @param brand
     */
    public AtomicProductSpecification(String productNumber, String name, String brand) {
    	super(name, productNumber, brand);
    }

	/*@Override
	public void addCharacteristic(ProductSpecCharacteristic specChar,
			boolean canBeOveridden, boolean isPackage, TimePeriod validFor,
			String name, String unique, int minCardinality, int maxCardinality,
			boolean extensible, String description) {
		 
		ProductSpecCharUse prodSpecCharUse = new ProductSpecCharUse(specChar, canBeOveridden, isPackage, validFor, name, unique, minCardinality, maxCardinality, extensible, description);
	    
		if(null==prodSpecChar || "".equals(prodSpecChar)){
			prodSpecChar = new ArrayList<ProductSpecCharUse>();
		}
		
	    prodSpecChar.add(prodSpecCharUse);
		
	}*/

}