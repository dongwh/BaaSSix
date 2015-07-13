package com.ai.baas.product.spec;

import com.ai.baas.basetype.TimePeriod;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * A migration, substitution, dependency, or exclusivity relationship between/among ProductSpecifications.
 */
public class ProductSpecificationRelationship {
	private static final Logger log = Logger.getLogger(ProductSpecificationRelationship.class);

    ProductSpecification targetProdSpec;
    ProductSpecification sourceSpec;
    /**
     * A categorization of the relationship, such as migration, substitution, dependency, exclusivity.
     */
    private String type;
    /**
     * The period for which the relationship is applicable.
     */
    private TimePeriod validFor;

    /**
     * 
     * @param sourceSpec
     * @param targetSpec
     * @param type
     * @param validFor
     */
    public ProductSpecificationRelationship(ProductSpecification sourceSpec, ProductSpecification targetSpec, String type, TimePeriod validFor) {
		if(null == sourceSpec){
			log.error("parameter of sourceSpec is null");
			throw new IllegalArgumentException("parameter of sourceSpec can't be null");
		}
		if(null == targetSpec){
			log.error("parameter of targetSpec is null");
			throw new IllegalArgumentException("parameter of targetSpec can't be null");
		}
		if(sourceSpec.equals(targetSpec)){
			log.error("sourceSpec and targetSpec are the same object ");
			throw new IllegalArgumentException("can't establish relationship with itself. ");
		}
        this.sourceSpec = sourceSpec;
        this.targetProdSpec = targetSpec;
    	this.type = type;
    	this.validFor = validFor;
    }

    /**
     * 
     * @param sourceSpec
     * @param targetSpecId
     * @param type
     * @param validFor
     */
    public ProductSpecificationRelationship(ProductSpecification sourceSpec, String targetSpecId, String type, TimePeriod validFor) {
        // TODO
    }

    public ProductSpecification getTargetProdSpec() {
		return targetProdSpec;
	}

	public void setTargetProdSpec(ProductSpecification targetProdSpec) {
		this.targetProdSpec = targetProdSpec;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TimePeriod getValidFor() {
		return validFor;
	}

	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}

	/**
     * 
     * @param sourceSpecId
     * @param targetSpec
     * @param type
     * @param validFor
     */
    public ProductSpecificationRelationship(String sourceSpecId, ProductSpecification targetSpec, String type, TimePeriod validFor) {
        // TODO - implement ProductSpecificationRelationship.ProductSpecificationRelationship
        throw new UnsupportedOperationException();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((targetProdSpec == null) ? 0 : targetProdSpec.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((validFor == null) ? 0 : validFor.hashCode());
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
		ProductSpecificationRelationship other = (ProductSpecificationRelationship) obj;
		if (targetProdSpec == null) {
			if (other.targetProdSpec != null){
				return false;
			}
		} else if (!targetProdSpec.equals(other.targetProdSpec)){
			return false;
		}
		if (type == null) {
			if (other.type != null){
				return false;
			}
		} else if (!type.equals(other.type)){
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
		targetChar.put("targetChar",targetProdSpec.getBasicInfoToString() );
		targetChar.put("type",type);
		targetChar.put("validFor",validFor);
		return targetChar.toString();
	}

}