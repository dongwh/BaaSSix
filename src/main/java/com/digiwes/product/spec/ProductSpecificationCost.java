package com.digiwes.product.spec;

import com.digiwes.basetype.Money;
import com.digiwes.basetype.TimePeriod;

/**
 * A monetary amount assigned to a ProductSpecification that represents the cost to the business to plan, develop, market, and implement the ProductSpecification.
 */
public class ProductSpecificationCost {

    /**
     * The monetary amount that represents the cost.
     */
    private Money costToBusiness;
    /**
     * The period during which the cost is applicable.
     */
    private TimePeriod validFor;

    public Money getCostToBusiness() {
        return this.costToBusiness;
    }

    public void setCostToBusiness(Money costToBusiness) {
        this.costToBusiness = costToBusiness;
    }

    public TimePeriod getValidFor() {
        return this.validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }

    /**
     * 
     * @param cost
     * @param validFor
     */
    public ProductSpecificationCost(Money cost, TimePeriod validFor) {
        this.costToBusiness = cost;
        this.validFor = validFor;
    }

}