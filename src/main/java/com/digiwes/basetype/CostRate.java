package com.ai.baas.basetype;

/**
 * A base / value business entity used to represent a cost per time unit. e.g. $10/hr, 33 pence/min
 */
public class CostRate {

    public Money numerator;
    public Duration denominator;

}