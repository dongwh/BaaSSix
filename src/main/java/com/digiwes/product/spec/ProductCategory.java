package com.digiwes.product.spec;

/**
 * A method used to group a set of ProductSpecifications that are classified together because of common characteristics.
 */
public class ProductCategory extends ProductSpecificationType {

    /**
     * 
     * @param type
     * @param description
     */
    public ProductCategory(String type, String description) {
    	super(type, description);
    }

}