package com.digiwes.product.spec;

import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.util.ValidUtil;
import org.apache.log4j.Logger;
import java.util.Map;
import java.util.HashMap;

import java.util.HashMap;

/**
 * A use of the ProdSpecCharacteristicValue by an ProductSpecification to which additional properties (attributes) apply or override the properties of similar properties contained in ProdSpecCharacteristicValue.
 */
public class ProdSpecCharValueUse {
	private static Logger logger = Logger.getLogger(ProductSpecCharUse.class);
	public ProductSpecCharacteristicValue prodSpecCharValue;
    public ProductSpecCharacteristicValue getProdSpecCharValue() {
		return prodSpecCharValue;
	}
	/**
     * Indicates if the value is the default value for a characteristic.
     */
    private boolean isDefault;
    /**
     * The period of time for which the use of the CharacteristicSpecificationValue is applicable.
     */
    private TimePeriod validFor;

    public boolean isIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public TimePeriod getValidFor() {
        return this.validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }

    /**
     * 
     * @param charVal
     * @param isDefault
     * @param validFor
     */
    public ProdSpecCharValueUse(ProductSpecCharacteristicValue charVal, boolean isDefault, TimePeriod validFor) {
        assert  !ValidUtil.checkObjectIsNull(charVal) :"charValue should not be null";

		this.prodSpecCharValue = charVal;
        this.isDefault = isDefault;
        this.validFor = validFor;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((prodSpecCharValue == null) ? 0 : prodSpecCharValue
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdSpecCharValueUse other = (ProdSpecCharValueUse) obj;
		if (prodSpecCharValue == null) {
			if (other.prodSpecCharValue != null)
				return false;
		} else if (!prodSpecCharValue.equals(other.prodSpecCharValue))
			return false;
		return true;
	}
	public String toString(){
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("isDefault",isDefault);
		result.put("validFor",validFor);
		result.put("prodSpecCharValue",prodSpecCharValue.basicInfoToString());
		return result.toString();
	}


    
}