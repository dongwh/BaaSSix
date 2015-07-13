package com.digiwes.product.offering.price;

import com.digiwes.basetype.TimePeriod;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


/**
 * An amount, usually of money, that is asked for or allowed when a ProductOffering is bought, rented, or leased. The price is valid for a defined period of time and may not represent the actual price paid by a customer.
 */
public abstract class ProductOfferingPrice {
     private Logger logger= Logger.getLogger(ProductOfferingPrice.class);
    /**
     * A short descriptive name such as "affinity discount" .
     */
    private String name;
    /**
     * A narrative that explains in detail the semantics of this component.
     */
    private String description;
    /**
     * The period for which the price is valid.
     */
    private TimePeriod validFor;

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

    /**
     * 
     * @param name
     * @param validFor
     */
    public ProductOfferingPrice(String name, TimePeriod validFor) {
        if( null == name ){
          logger.error("name can't be null");
          throw  new IllegalArgumentException("name can't be null");
        }
        this.name = name;
        this.validFor = validFor;
    }
    public ProductOfferingPrice(String name,TimePeriod validFor, String description) {
        if( null == name ){
            logger.error("name can't be null");
            throw  new IllegalArgumentException("name can't be null");
        }
        this.name = name;
        this.validFor = validFor;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ! (o instanceof  ProductOfferingPrice) ) return false;

        ProductOfferingPrice that = (ProductOfferingPrice) o;

        if (!name.equals(that.name)) return false;
        if (!description.equals(that.description)) return false;
        return validFor.equals(that.validFor);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + validFor.hashCode();
        return result;
    }
    public String toString(){
        Map<String,Object> result =new HashMap<String, Object>();
        result.put("name",name);
        result.put("description",description);
        result.put("validFor",validFor);
        return  result.toString();
    }
}