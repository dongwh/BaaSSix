package com.digiwes.product.spec;


import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.util.ValidUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ProdSpecCharValueRelationship {

	private Logger logger= Logger.getLogger(ProdSpecCharValueRelationship.class);
	public ProductSpecCharacteristicValue sourceCharValue;
    public ProductSpecCharacteristicValue productSpecCharacteristicValue;
    /**
     * A categorization of the relationship between values, such as aggregation, migration, substitution, dependency, exclusivity.
     */
    private String charValueRelationshipType;
    /**
     * The period for which the relationship is applicable.
     */
    private TimePeriod validFor;
	public ProductSpecCharacteristicValue getSourceCharValue(){
		return  this.sourceCharValue;
	}
	public ProductSpecCharacteristicValue getProductSpecCharacteristicValue(){
		return this.productSpecCharacteristicValue;
	}
    public String getCharValueRelationshipType() {
        return this.charValueRelationshipType;
    }
    public TimePeriod getValidFor() {
        return this.validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }

    /**
     * 
     * @param sourceCharValue
     * @param targetCharValue
     * @param relationType
     * @param validFor
     */
    public ProdSpecCharValueRelationship(ProductSpecCharacteristicValue sourceCharValue, ProductSpecCharacteristicValue targetCharValue, String relationType, TimePeriod validFor) {

		assert !ValidUtil.checkObjectIsNull(sourceCharValue): "srcProdSpecChar should not be null";
		assert !ValidUtil.checkObjectIsNull(targetCharValue): "targetCharValue should not be null";
		assert !sourceCharValue.equals(targetCharValue): "targetCharValue should not be null";
		assert !StringUtils.isEmpty(relationType): "relationType should not be null";

		this.sourceCharValue = sourceCharValue;
        this.productSpecCharacteristicValue = targetCharValue;
		this.charValueRelationshipType=relationType;
		this.validFor=validFor;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((charValueRelationshipType == null) ? 0
						: charValueRelationshipType.hashCode());
		result = prime
				* result
				+ ((productSpecCharacteristicValue == null) ? 0
						: productSpecCharacteristicValue.hashCode());
		result = prime * result
				+ ((validFor == null) ? 0 : validFor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		ProdSpecCharValueRelationship other = (ProdSpecCharValueRelationship) obj;
		if (charValueRelationshipType == null) {
			if (other.charValueRelationshipType != null){
				return false;
			}
		} else if (!charValueRelationshipType
				.equals(other.charValueRelationshipType)){
			return false;
		}
		if (productSpecCharacteristicValue == null) {
			if (other.productSpecCharacteristicValue != null){
				return false;
			}
		} else if (!productSpecCharacteristicValue
				.equals(other.productSpecCharacteristicValue)){
			return false;
		}
		if (validFor == null) {
			if (other.validFor != null){
				return false;
			}
		} else if (!validFor.equals(other.validFor)){
			return false;
		}
		return true;
	}
	public String toString(){
		Map<String,Object> charValue=new HashMap<String,Object>();
		charValue.put("charValue", this.productSpecCharacteristicValue.basicInfoToString());
		charValue.put("charValueRelationshipType", charValueRelationshipType);
		charValue.put("validFor", validFor);
		return charValue.toString();
	}
    
}