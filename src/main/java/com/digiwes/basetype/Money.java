package com.digiwes.basetype;

/**
 * A base / value business entity used to represent money
 */
public class Money {

    /**
     * Currency(Notes:refer to [ISO 4217]) or non-currency terms, such as loyalty points.
     */
    public String units;
    /**
     * A positive floating point number.
     */
    public long amount;
    
	public Money(String units, long amount) {
		super();
		this.units = units;
		this.amount = amount;
	}

}