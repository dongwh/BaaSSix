package com.ai.baas.product.spec;

import java.util.*;

import com.ai.util.ConvertMap2Json;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ai.baas.basetype.*;

public class ProductSpecCharUse {
	private static final Logger logger = Logger.getLogger(ProductSpecCharUse.class);

	private ProductSpecCharacteristic prodSpecChar;
    private Set<ProdSpecCharValueUse> prodSpecCharValue;
    /**
     * A word, term, or phrase by which the CharacteristicSpecification is known and distinguished from other CharacteristicSpecifications.
     */
    private String name;
    /**
     * A narrative that explains the CharacteristicSpecification.
     */
    private String description;
    /**
     * An indicator that specifies if a value is unique for the specification.
     * 
     * Possible values are: "unique while value is in effect" and "unique whether value is in effect or not"
     */
    private String unique;
    /**
     * An indicator that specifies if the associated CharacteristicSpecification is a composite.
     */
    private boolean isPackage;
    /**
     * An indicator that specifies that the CharacteristicSpecValues associated with the CharacteristicSpec cannot be changed when instantiating a ServiceCharacteristicValue. For example, a bandwidth of 64 MB cannot be changed.
     */
    private boolean canBeOveridden;
    /**
     * The minimum number of instances a CharacteristicValue can take on. For example, zero to five phone numbers in a group calling plan, where zero is the value for the minCardinality.
     */
    private int minCardinality;
    /**
     * The maximum number of instances a CharacteristicValue can take on. For example, zero to five phone numbers in a group calling plan, where five is the value for the maxCardinality.
     */
    private int maxCardinality;
    /**
     * An indicator that specifies that the values for the characteristic can be extended by adding new values when instantiating a characteristic for a Service.
     */
    private boolean extensible;

    public ProductSpecCharacteristic getProdSpecChar() {
        return this.prodSpecChar;
    }

    public void setProdSpecChar(ProductSpecCharacteristic prodSpecChar) {
        this.prodSpecChar = prodSpecChar;
    }

    public Set<ProdSpecCharValueUse> getProdSpecCharValue() {
        return this.prodSpecCharValue;
    }

    public void setProdSpecCharValue(Set<ProdSpecCharValueUse> prodSpecCharValue) {
        this.prodSpecCharValue = prodSpecCharValue;
    }

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

    public String getUnique() {
        return this.unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public boolean isIsPackage() {
        return this.isPackage;
    }

    public void setIsPackage(boolean isPackage) {
        this.isPackage = isPackage;
    }

    public boolean isCanBeOveridden() {
        return this.canBeOveridden;
    }

    public void setCanBeOveridden(boolean canBeOveridden) {
        this.canBeOveridden = canBeOveridden;
    }

    public int getMinCardinality() {
        return this.minCardinality;
    }

    public void setMinCardinality(int minCardinality) {
        this.minCardinality = minCardinality;
    }

    public int getMaxCardinality() {
        return this.maxCardinality;
    }

    public void setMaxCardinality(int maxCardinality) {
        this.maxCardinality = maxCardinality;
    }

    public boolean isExtensible() {
        return this.extensible;
    }

    public void setExtensible(boolean extensible) {
        this.extensible = extensible;
    }

	/**
	 *
	 * @param specChar
	 * @param canBeOveridden
	 * @param isPackage
	 * @param validFor
	 * @param name
	 */
    public ProductSpecCharUse(ProductSpecCharacteristic specChar, boolean canBeOveridden, boolean isPackage, TimePeriod validFor, String name) {
		 init(specChar,canBeOveridden,isPackage,validFor,name);
    }
    /**
     * 
     * @param specChar
     * @param canBeOveridden
     * @param isPackage
     * @param validFor
     * @param name
     * @param unique
     * @param minCardinality
     * @param maxCardinality
     * @param extensible
     * @param description
     */
    public ProductSpecCharUse(ProductSpecCharacteristic specChar, boolean canBeOveridden, boolean isPackage, TimePeriod validFor, String name, String unique, int minCardinality, int maxCardinality, boolean extensible, String description) {
		init(specChar,canBeOveridden,isPackage,validFor,name);
        this.unique = unique;
        this.minCardinality = minCardinality;
        this.maxCardinality = maxCardinality;
        this.extensible = extensible;
        this.description = description;
    }
	private void init(ProductSpecCharacteristic specChar, boolean canBeOveridden, boolean isPackage, TimePeriod validFor, String name){
		if (null == specChar) {
			logger.error("specChar should not be null");
			throw new IllegalArgumentException("specChar should not be null");
		}
		if(StringUtils.isEmpty(name)){
			logger.error("name should not be null");
			throw new IllegalArgumentException("name should not be null");
		}
		this.name = name;
		this.prodSpecChar =specChar;
		this.canBeOveridden=canBeOveridden;
		this.isPackage = isPackage;
	}

    /**
     * 
     * @param specCharId
     * @param canBeOveridden
     * @param isPackage
     * @param validFor
     * @param name
     * @param unique
     * @param minCardinality
     * @param maxCardinality
     * @param extensible
     * @param description
     */
    public ProductSpecCharUse(String specCharId, boolean canBeOveridden, boolean isPackage, TimePeriod validFor, String name, String unique, int minCardinality, int maxCardinality, boolean extensible, String description) {

		 this.canBeOveridden = canBeOveridden;
    	 this.isPackage =isPackage;
    	 this.name=name;
    	 this.unique =unique;
    	 this.minCardinality=minCardinality;
    	 this.maxCardinality =maxCardinality;
    	 this.extensible =extensible;
    	 this.description =description;
    }

    /**
     * 
     * @param minCardinality
     * @param maxCardinality
     */
    public void specifyCardinality(int minCardinality, int maxCardinality) {
		if(minCardinality <= maxCardinality){
			this.minCardinality = minCardinality;
			this.maxCardinality = maxCardinality;
		}else{
			logger.error("minCardinality is less than maxCardinality");
			throw new IllegalArgumentException();
		}
    }

    /**
     * 
     * @param charValue
     * @param isDefault
     * @param validFor
     */
    public boolean addValue(ProductSpecCharacteristicValue charValue, boolean isDefault, TimePeriod validFor) {
		if( null == charValue){
			logger.error("charValue should not be null");
			throw new IllegalArgumentException("charValue should not be null.") ;
		}
		if( null == this.prodSpecChar.getProdSpecCharValue() || !this.prodSpecChar.getProdSpecCharValue().contains(charValue)){
			return false;
		}
		ProdSpecCharValueUse charValueUse = new ProdSpecCharValueUse(charValue, isDefault, validFor);
		if (null == this.prodSpecCharValue) {
			this.prodSpecCharValue = new HashSet<ProdSpecCharValueUse>();
		}
		this.prodSpecCharValue.add(charValueUse);
		return true;
    }


    /**
     * 
     * @param charValue
     */
    public boolean removeValue(ProductSpecCharacteristicValue charValue) {
		if ( null == charValue) {
			logger.error("charValue should not be null.");
			throw new IllegalArgumentException("charValue should not be null");
		}
		if( null == this.prodSpecChar.getProdSpecCharValue() || !this.prodSpecChar.getProdSpecCharValue().contains(charValue)){
			return false;
		}
    	if ( null != prodSpecCharValue  &&  prodSpecCharValue.size()>0 ){
    		for(ProdSpecCharValueUse charValueUse :prodSpecCharValue){
        		if(charValueUse.getProdSpecCharValue().equals(charValue)){
        			prodSpecCharValue.remove(charValueUse);
        		}
        	}	
    	} else {
			logger.warn("the current ProSpecChar have not value");
    	}
		return true;
    }


    /**
     * 
     * @param defaultValue
     */
    public boolean specifyDefaultCharacteristicValue(ProductSpecCharacteristicValue defaultValue) {
		if ( null == defaultValue) {
			logger.error("the specify defaultValue should not be null");
			throw  new IllegalArgumentException("the specify defaultValue should not be null");
		}
        if (this.prodSpecCharValue!=null) {
			if(!this.prodSpecChar.getProdSpecCharValue().contains(defaultValue)){
				logger.warn("the specify value is not belong of the current char");
				return false;
			}
        	for(ProdSpecCharValueUse pscvu:prodSpecCharValue){
        		if(pscvu.getProdSpecCharValue().equals(defaultValue)){
        			if(!pscvu.isIsDefault()){
        				pscvu.setIsDefault(true);
						break;
        			}else{
						logger.warn("The current value has been set to the default value");
					}
        		}
        	}
        }else{
			logger.warn("the current ProSpecChar have not value");
        	return false;
        }
		return true;
    }
	public boolean clearDefaultValueUse(ProductSpecCharacteristicValue defaultValue){
		if ( null == defaultValue){
			logger.error("the specify defaultValue should not be null");
			throw  new IllegalArgumentException("the specify defaultValue should not be null");
		}
		if( null == prodSpecChar || !this.prodSpecChar.getProdSpecCharValue().contains(defaultValue)){
			logger.warn("the specify value is not belong of the current char");
			return false;
		}
		if( null != this.prodSpecCharValue && this.prodSpecCharValue.size()>0) {

		   for (ProdSpecCharValueUse charValueUse :prodSpecCharValue ) {
			      if(charValueUse.getProdSpecCharValue().equals(defaultValue)){
					    if(charValueUse.isIsDefault()){
							charValueUse.setIsDefault(false);
							break;
						}
				  }
		   }
			return true;
		}
		return false;
	}
	public List<ProdSpecCharValueUse> retrieveDefaultCharacteristicValueUse() {
		List<ProdSpecCharValueUse> defaultValueUse = new ArrayList<ProdSpecCharValueUse>();
		if (null != prodSpecCharValue) {
			for (ProdSpecCharValueUse valueUse : prodSpecCharValue) {
				if (valueUse.isIsDefault()) defaultValueUse.add(valueUse);
			}
		}
		return defaultValueUse;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProductSpecCharUse that = (ProductSpecCharUse) o;

		if (prodSpecChar != null ? !prodSpecChar.equals(that.prodSpecChar) : that.prodSpecChar != null) return false;
		return !(name != null ? !name.equals(that.name) : that.name != null);

	}

	@Override
	public int hashCode() {
		int result = prodSpecChar != null ? prodSpecChar.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	public String toString(){
		Map<String,Object> result=new HashMap<String, Object>();
		result.put("name",name);
		result.put("unique",unique);
		result.put("isPackage",isPackage);
		result.put("canBeOveridden",canBeOveridden);
		result.put("minCardinality",minCardinality);
		result.put("maxCardinality",maxCardinality);
		result.put("extensible",extensible);
		result.put("description",description);
		result.put("prodSpecChar",prodSpecChar.basicInfoToString());
		result.put("prodSpecCharValue",prodSpecCharValue);
		return result.toString();
	}
}