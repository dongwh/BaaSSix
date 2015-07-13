package com.ai.baas.product.spec;

import java.util.*;
import com.ai.baas.basetype.*;

/**
 * A type of ProductSpecification that is formed by aggregating other ProductSpecifications, which may be Composite or Atomic ProductSpecifications.
 */
public class CompositeProductSpecification extends ProductSpecification {

    public List<ProductSpecification> prodSpec;

    /**
     * 
     * @param productNumber
     * @param name
     * @param brand
     */
    public CompositeProductSpecification(String productNumber, String name, String brand) {
    	super(name, productNumber, brand);
    }

    /**
     * 
     * @param productNumber
     * @param name
     * @param brand
     * @param description
     * @param validFor
     */
    public CompositeProductSpecification(String productNumber, String name, String brand, String description, TimePeriod validFor) {
    	super(name, productNumber, brand, validFor, description);
    }

    /**
     * 
     * @param prodSpec
     */
    public void addSubProdSpec(ProductSpecification prodSpec) {
        // TODO - implement CompositeProductSpecification.addSubProdSpec
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param prodSpecId
     */
    public void addSubProdSpec(String prodSpecId) {
        // TODO - implement CompositeProductSpecification.addSubProdSpec
        throw new UnsupportedOperationException();
    }


}