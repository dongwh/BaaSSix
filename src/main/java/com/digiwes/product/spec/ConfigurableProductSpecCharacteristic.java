package com.ai.baas.product.spec;

import com.ai.baas.basetype.*;

/**
 * A type of ProductSpecCharacteristic, such as color, whose may be assigned during the ordering or some other process.
 */
public class ConfigurableProductSpecCharacteristic extends ProductSpecCharacteristic {

    /**
     * 
     * @param id
     * @param name
     * @param valueType
     * @param validFor
     * @param unique
     * @param minCardinality
     * @param maxCardinality
     * @param extensible
     * @param description
     * @param derivationFormula
     */
    public ConfigurableProductSpecCharacteristic(String id, String name, String valueType, TimePeriod validFor, String unique, int minCardinality, int maxCardinality, boolean extensible, String description, String derivationFormula) {
    	super(id, name, valueType, validFor, unique, minCardinality, maxCardinality, extensible, description, derivationFormula);
    }
    public ConfigurableProductSpecCharacteristic(String id, String name, String valueType, TimePeriod validFor, String unique, int minCardinality, int maxCardinality) {
        super(id, name, valueType, validFor, unique, minCardinality, maxCardinality);
    }
}