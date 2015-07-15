package com.digiwes.product.spec;
import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.util.ValidUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * A aggregation, migration, substitution, dependency, or exclusivity relationship between/among ProductSpecCharacteristics.
 */
public class ProductSpecCharRelationship {

	private Logger logger= Logger.getLogger(ProductSpecCharRelationship.class);
	private ProductSpecCharacteristic targetProdSpecChar;
    private ProductSpecCharacteristic sourceProdSpecChar;
	public ProductSpecCharacteristic getTargetProdSpecChar() {
		return targetProdSpecChar;
	}
	public ProductSpecCharacteristic getSourceProdSpecChar() {
		return sourceProdSpecChar;
	}
	/**
     * A categorization of the relationship, such as aggregation, migration, substitution, dependency, exclusivity.
     */
    private String charRelationshipType;
    /**
     * The order in which a CharacteristicSpecification appears within another CharacteristicSpecification that defines a grouping of CharacteristicSpecifications.
     * 
     * For example, a grouping may represent the name of an individual. The given name is first, the middle name is second, and the last name is third.
     */
    private int charSpecSeq;
    /**
     * The period for which the relationship is applicable.
     */
    private TimePeriod validFor;

    public String getCharRelationshipType() {
        return this.charRelationshipType;
    }

    public int getCharSpecSeq() {
        return this.charSpecSeq;
    }

    public void setCharSpecSeq(int charSpecSeq) {
        this.charSpecSeq = charSpecSeq;
    }

    public TimePeriod getValidFor() {
        return this.validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }

    /**
     * 
     * @param srourceSpecChar
     * @param targetSpecChar
     * @param relationType
     * @param validFor
     */
    public ProductSpecCharRelationship(ProductSpecCharacteristic srourceSpecChar, ProductSpecCharacteristic targetSpecChar, String relationType, TimePeriod validFor) {
		 assert !ValidUtil.checkObjectIsNull(srourceSpecChar):"sourProdSpecChar should not be null";
		assert !ValidUtil.checkObjectIsNull(targetSpecChar):"targetProdSpecChar should not be null";
		assert !srourceSpecChar.equals(targetSpecChar):"The srcChar is the same as the targetChar.";
		assert !StringUtils.isEmpty(relationType) : "relationType should not be null";

		this.sourceProdSpecChar = srourceSpecChar;
		this.targetProdSpecChar = targetSpecChar;
		this.charRelationshipType = relationType;
		this.validFor = validFor;
    }



    /**
     * 
     * @param srourceSpecChar
     * @param targetSpecChar
     * @param relationType
     * @param validFor
     * @param specSeq
     */
    public ProductSpecCharRelationship(ProductSpecCharacteristic srourceSpecChar,ProductSpecCharacteristic targetSpecChar, String relationType, TimePeriod validFor, int specSeq) {
		this(srourceSpecChar,targetSpecChar,relationType,validFor);
		this.charSpecSeq =specSeq ;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((charRelationshipType == null) ? 0 : charRelationshipType
						.hashCode());
		result = prime
		* result
		+ ((targetProdSpecChar == null) ? 0 : targetProdSpecChar
				.hashCode());
		result = prime * result
				+ ((validFor == null) ? 0 : validFor.hashCode());
		result = prime * result + charSpecSeq;
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
		if (!(obj instanceof ProductSpecCharRelationship)){
			return false;
		}
		ProductSpecCharRelationship other = (ProductSpecCharRelationship) obj;
		if (charRelationshipType == null) {
			if (other.charRelationshipType != null){
				return false;
			}
		} else if (!charRelationshipType.equals(other.charRelationshipType)){
			return false;
		}
		if (targetProdSpecChar == null) {
			if (other.targetProdSpecChar != null){
				return false;
			}
		} else if (!targetProdSpecChar.equals(other.targetProdSpecChar)){
			return false;
		}
		if (charSpecSeq != other.charSpecSeq){
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

    
	@Override
	public String toString(){
		Map<String,Object> targetChar=new HashMap<String,Object>();
		targetChar.put("targetChar",targetProdSpecChar.basicInfoToString() );
		targetChar.put("type",charRelationshipType);
		targetChar.put("validFor",validFor);
		targetChar.put("charSpecSeq", charSpecSeq);
		return targetChar.toString();
	}

}