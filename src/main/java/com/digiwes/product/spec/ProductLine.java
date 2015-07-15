package com.digiwes.product.spec;

import com.digiwes.common.enums.ProdSpecEnum;

/**
 * A method used to group related ProductSpecifications marketed by the same company that differ only in size or style.
 */
public class ProductLine extends ProductSpecificationType {

    /**
     * 
     * @param type
     * @param description
     */
    public ProductLine(String type, String description) {
    	super(type, description);
    }


}