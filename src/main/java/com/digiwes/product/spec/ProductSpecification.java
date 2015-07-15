package com.digiwes.product.spec;

import com.digiwes.basetype.Money;
import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.enums.CommonErrorCode;
import com.digiwes.common.enums.ProdSpecEnum;
import com.digiwes.common.enums.ProdSpecErrorCode;
import com.digiwes.common.util.NumberUtil;
import com.digiwes.common.util.ValidUtil;
import com.digiwes.product.offering.SimpleProductOffering;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * A detailed description of a tangible or intangible object made available externally in the form of a ProductOffering to Customers or other Parties playing a PartyRole. A ProductSpecification may consist of other ProductSpecifications supplied together as a collection. Members of the collection may be offered in their own right. ProductSpecifications may also exist within groupings, such as ProductCategories, ProductLines, and ProductTypes.
 */
public abstract class ProductSpecification {
	private static final Logger log = Logger.getLogger(ProductSpecification.class);

    public List<ProductSpecificationCost> productSpecificationCost;
    public List<ProductSpecificationRelationship> prodSpecRelationship = new ArrayList<ProductSpecificationRelationship>();
    public List<ProductSpecificationVersion> prodSpecVersion;
    public Set<ProductSpecCharUse> prodSpecChar;
    public List<CompositeProductSpecification> compositeProdSpec;
    public List<SimpleProductOffering> simpleProdOffering;
    
    public List<ProductSpecificationCost> getProductSpecificationCost() {
		return productSpecificationCost;
	}

	public List<ProductSpecificationVersion> getProdSpecVersion() {
		return prodSpecVersion;
	}

	public Set<ProductSpecCharUse> getProdSpecChar() {
		return prodSpecChar;
	}
	

	public List<ProductSpecificationRelationship> getProdSpecRelationship() {
		return prodSpecRelationship;
	}

	public void setProdSpecRelationship(
			List<ProductSpecificationRelationship> prodSpecRelationship) {
		this.prodSpecRelationship = prodSpecRelationship;
	}


	/**
     * The name of the product specification.
     */
    private String name;
    /**
     * The manufacturer or trademark of the specification.
     */
    private String brand;
    /**
     * A narrative that explains in detail what the product spec is.
     */
    private String description;
    /**
     * An identification number assigned to uniquely identify the specification.
     */
    private String productNumber;
    /**
     * The period for which the product specification is valid.
     */
    private TimePeriod validFor;
    /**
     * The condition of the product specification, such as active, inactive, planned.
     */
    private String lifecycleStatus;

    public String getName() {
        return this.name;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductNumber() {
        return this.productNumber;
    }

    public TimePeriod getValidFor() {
        return this.validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }

    public String getLifecycleStatus() {
        return this.lifecycleStatus;
    }

	/**
	 *
	 * @param name
	 * @param productNumber
	 * @param brand
	 */
    public ProductSpecification(String name, String productNumber, String brand) {
		//check name is null
		if(StringUtils.isEmpty(name)){
			log.error("parameter is error ：name is null. ");
			throw new IllegalArgumentException("name should not be null .");
		}
		if(StringUtils.isEmpty(productNumber)){
			log.error("parameter is error ：productNumber is null. ");
			throw new IllegalArgumentException("productNumber should not be null .");
		}
        this.name = name;
        this.productNumber = productNumber;
        this.brand = brand;
        this.lifecycleStatus = ProdSpecEnum.ProdSpecStatus.STATUS_ACTIVE.getValue();
    }

	/**
	 *
	 * @param name
	 * @param productNumber
	 * @param brand
	 * @param validFor
	 * @param description
	 */
    public ProductSpecification(String name, String productNumber, String brand, TimePeriod validFor, String description) {
		//check name is null
		if(StringUtils.isEmpty(name)){
			log.error("parameter is error ：name is null. ");
			throw new IllegalArgumentException("name can't be null .");
		}
		if(StringUtils.isEmpty(productNumber)){
			log.error("parameter is error ：productNumber is null. ");
			throw new IllegalArgumentException("productNumber can't be null .");
		}
        this.name = name;
        this.productNumber = productNumber;
        this.brand = brand;
        this.validFor = validFor;
        this.description = description;
        this.lifecycleStatus = ProdSpecEnum.ProdSpecStatus.STATUS_ACTIVE.getValue();
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param canBeOveridden
	 * @param isPackage
	 * @param validFor
	 * @return
	 */
    public boolean addCharacteristic(ProductSpecCharacteristic specChar, String name, boolean canBeOveridden, boolean isPackage, TimePeriod validFor) {
		//check name and specChar
		checkNameAndSpecChar(name, specChar);
    	//initialize set of ProductSpecCharUse
    	initProdSpecCharUseSet();
        //the charUse has been used under the specification, can't add the same charUse again
        ProductSpecCharUse prodSpecCharUseNew = new ProductSpecCharUse(specChar, canBeOveridden, isPackage, validFor, name);
		if(this.prodSpecChar.contains(prodSpecCharUseNew)){
			return false;
		}
		prodSpecChar.add(prodSpecCharUseNew);
    	return true;
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param canBeOveridden
	 * @param isPackage
	 * @param validFor
	 * @param unique
	 * @param minCardinality
	 * @param maxCardinality
	 * @param extensible
	 * @param description
	 * @return
	 */
    public boolean addCharacteristic(ProductSpecCharacteristic specChar, String name, boolean canBeOveridden, boolean isPackage, TimePeriod validFor, String unique, int minCardinality, int maxCardinality, boolean extensible, String description){
		//check name and specChar
		checkNameAndSpecChar(name, specChar);
    	//initialize set of ProductSpecCharUse
    	initProdSpecCharUseSet();
		//the charUse has been used under the specification, can't add the same charUse again
		ProductSpecCharUse prodSpecCharUseNew = new ProductSpecCharUse(specChar, canBeOveridden, isPackage, validFor, name, unique, minCardinality, maxCardinality, extensible, description);
		if(this.prodSpecChar.contains(prodSpecCharUseNew)){
			return false;
		}
		prodSpecChar.add(prodSpecCharUseNew);
    	return true;
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @return
	 */
    public void removeCharacteristic(ProductSpecCharacteristic specChar, String name){
		//check name and specChar
		checkNameAndSpecChar(name, specChar);
    	if (null != this.prodSpecChar){
    		for (ProductSpecCharUse prodSpecUse : prodSpecChar) {
    			if(prodSpecUse.getProdSpecChar().equals(specChar) && prodSpecUse.getName().equals(name)){
    				this.prodSpecChar.remove(prodSpecUse);
					return;
    			}
			}
    	}
    	//an Object which is not exist
    	log.warn("the characteristic in not exist under this spec. ");
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param charValue
	 * @param isDefault
	 * @param validFor
	 */
    public void attachCharacteristicValue(ProductSpecCharacteristic specChar, String name, ProductSpecCharacteristicValue charValue, boolean isDefault, TimePeriod validFor) {
		//check name and specChar
		checkNameAndSpecChar(name, specChar);
    	//judge charValue is null
    	checkProdSpecCharValue(charValue);
    	if(null != prodSpecChar && prodSpecChar.size()>0){
    		for (ProductSpecCharUse prodSpecCharUse : this.prodSpecChar) {
    			if(prodSpecCharUse.getProdSpecChar().equals(specChar) && prodSpecCharUse.getName().equals(name)){
					prodSpecCharUse.addValue(charValue, isDefault, validFor);
					break;
				}
			}
    	}
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param charValue
	 */
    public void detachCharacteristicValue(ProductSpecCharacteristic specChar,String name, ProductSpecCharacteristicValue charValue){
		//check name and specChar
		checkNameAndSpecChar(name, specChar);
    	//judge charValue is null
    	checkProdSpecCharValue(charValue);
    	boolean charIsExist = false;
    	if(null != prodSpecChar && prodSpecChar.size()>0){
    		for (ProductSpecCharUse prodSpecCharUse : prodSpecChar) {
    			if(prodSpecCharUse.getProdSpecChar().equals(specChar) && prodSpecCharUse.getName().equals(name)){
    				charIsExist = true;
    				prodSpecCharUse.removeValue(charValue);
					break;
    			}
			}
    		if(!charIsExist){
    			log.error("under the prodSpec didn't use the characteristic which the value will be used by characteristic");
    		}
    	}
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param defaultCharValue
	 */
    public void specifyDefaultCharacteristicValue(ProductSpecCharacteristic specChar, String name, ProductSpecCharacteristicValue defaultCharValue) {
		//check name and specChar
		checkNameAndSpecChar(name, specChar);
    	//judge charValue is null
    	checkProdSpecCharValue(defaultCharValue);
    	if(null != this.prodSpecChar){
    		for (ProductSpecCharUse prodSpecCharUse : prodSpecChar) {
    			if(prodSpecCharUse.getProdSpecChar().equals(specChar) && prodSpecCharUse.getName().equals(name)){
    				prodSpecCharUse.specifyDefaultCharacteristicValue(defaultCharValue);
					break;
				}
			}
    	}
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param defaultCharValue
	 */
	public void clearDefaultCharacteristicValue(ProductSpecCharacteristic specChar, String name, ProductSpecCharacteristicValue defaultCharValue) {
		//check name and specChar
		checkNameAndSpecChar(name, specChar);
		//judge charValue is null
		checkProdSpecCharValue(defaultCharValue);
		if (this.prodSpecChar != null) {
			for (ProductSpecCharUse prodSpecCharUse : prodSpecChar) {
				if(prodSpecCharUse.getProdSpecChar().equals(specChar) && prodSpecCharUse.getName().equals(name)){
					prodSpecCharUse.clearDefaultValueUse(defaultCharValue);
					break;
				}
			}
		}
	}

    /**
     * 
     * @param time
     */
    public List<ProductSpecCharUse> retrieveCharacteristic(Date time) {
    	if(null == time){
    		log.error("parameter is error ：time is null. ");
    		throw new IllegalArgumentException("specChar should not be null .");
    	}
		List<ProductSpecCharUse> prodSpecCharUseByDate = new ArrayList<ProductSpecCharUse>();
    	if(null != this.prodSpecChar){
			for (ProductSpecCharUse productSpecCharUse : this.prodSpecChar) {
				TimePeriod validForTime = productSpecCharUse.getProdSpecChar().getValidFor();
				if(null != validForTime && validForTime.isInTimePeriod(time)){
					prodSpecCharUseByDate.add(productSpecCharUse);
				}
			}
		}
    	return prodSpecCharUseByDate;
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param time
	 * @return
	 */
    public List<ProdSpecCharValueUse> retrieveCharacteristicValue(ProductSpecCharacteristic specChar, String name, Date time) {
    	Set<ProductSpecCharUse> prodSpecCharUseList = this.prodSpecChar;
    	List<ProdSpecCharValueUse> prodSpecCharValueUseByDate = new ArrayList<ProdSpecCharValueUse>();
    	if(null != prodSpecCharUseList && prodSpecCharUseList.size()>0){
    		for (ProductSpecCharUse productSpecCharUse : prodSpecCharUseList) {
    			if(productSpecCharUse.getProdSpecChar().equals(specChar) && productSpecCharUse.getName().equals(name)){
    				if(productSpecCharUse.getProdSpecChar().getValidFor().isInTimePeriod(time)){
    					for (ProdSpecCharValueUse prodSpecCharValueUse : productSpecCharUse.getProdSpecCharValue()) {
							if(prodSpecCharValueUse.getValidFor().isInTimePeriod(time)){
								prodSpecCharValueUseByDate.add(prodSpecCharValueUse);
							}
						}
    				}else{
    					//the characteristics of the prodSpec isn't in the time period 
    					return prodSpecCharValueUseByDate;
    				}
    			}
    		}
    	}
    	return prodSpecCharValueUseByDate;
    }

    /**
     * this operation is  convert ProductSpecCharacteristic type to ProductSpecCharUse type
     * @param prodSpecCharN
     * @return
     */
	//this operation is invalid
    private ProductSpecCharUse retrieveProdSpecCharUse(ProductSpecCharacteristic prodSpecCharN){

    	if(null!=prodSpecChar && prodSpecChar.size()>0){
    		for (ProductSpecCharUse prodSpecCharUse : prodSpecChar) {
    			if(prodSpecCharUse.getProdSpecChar().equals(prodSpecCharN)){
    				return prodSpecCharUse;
    			}
    		}
    	}
    	return null;
    }
    /**
     * 
     * @return
     */
    public Set<ProductSpecCharUse> retrieveRootCharacteristic() {
		//TODO
    	Set<ProductSpecCharUse> resultProdSpecCharUseList = new HashSet<ProductSpecCharUse>();
    	resultProdSpecCharUseList = this.prodSpecChar;
    	if(null!=prodSpecChar && prodSpecChar.size()>0){
    		for (ProductSpecCharUse prodSpecCharUse : prodSpecChar) {
    			List<ProductSpecCharacteristic> relatedChars = prodSpecCharUse.getProdSpecChar().retrieveRelatedCharacteristic(ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getValue(), new Date());
    			if(null == relatedChars || relatedChars.size() == 0){
    				for (ProductSpecCharacteristic prodSpecChar : relatedChars) {
    					ProductSpecCharUse prodSpecCharSub = retrieveProdSpecCharUse(prodSpecChar);
    					if(null != prodSpecCharSub){
    						resultProdSpecCharUseList.remove(prodSpecCharSub);
    					}
					}
    				resultProdSpecCharUseList.add(prodSpecCharUse);
    			}
			}
    	}
    	return resultProdSpecCharUseList;
    }

    /**
     * 
     * @param specChar
     * @param time
     */
    public Set<ProductSpecCharUse> getLeafCharacteristic(ProductSpecCharacteristic specChar, Date time) {
		//TODO
    	Set<ProductSpecCharUse> prodSpecCharUseList = this.prodSpecChar;
    	Set<ProductSpecCharUse> resultProdSpecCharUseSet = new HashSet<ProductSpecCharUse>();
    	if(null!=prodSpecCharUseList && prodSpecCharUseList.size()>0){
    		/*for (int i = 0; i < prodSpecCharUseList.size(); i++) {
    			if(prodSpecCharUseList.get(i).getProdSpecChar().getID().equals(specChar.getID())){
    				ProductSpecCharacteristic[] relatedChars = prodSpecCharUseList.get(i).getProdSpecChar().queryRelatedCharacteristic(ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getValue());
        			if(null != relatedChars && relatedChars.length > 0){
        				resultProdSpecCharUseList.add(prodSpecCharUseList.get(i));
        			}
    			}
			}*/
    	}
    	return resultProdSpecCharUseSet;
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param minCardinality
	 * @param maxCardinality
	 * @return
	 */
    public boolean setCardinality(ProductSpecCharacteristic specChar, String name, int minCardinality, int maxCardinality) {
		if(null == prodSpecChar){
			log.error("parameter is error ：the Object of ProductSpecCharacteristic is null. ");
			throw new IllegalArgumentException("specChar should not be null .");
		}
        int rtnNum = NumberUtil.compareTheNumber(minCardinality, maxCardinality);
        if(rtnNum == 1){
        	log.error("minCardinality should be less than or equal to  maxCardinality.");
        	throw new IllegalArgumentException("the parameter of verType is error.");
        }
        if(null != prodSpecChar && prodSpecChar.size()>0){
        	for (ProductSpecCharUse prodSpecCharUse : prodSpecChar) {
        		if(prodSpecCharUse.getProdSpecChar().equals(specChar) && prodSpecCharUse.getName().equals(name)){
        			prodSpecCharUse.setMinCardinality(minCardinality);
        			prodSpecCharUse.setMaxCardinality(maxCardinality);
					break;
        		}
			}
        }
        return true;
    }

    /**
     * 
     * @param verType
     * @param curTypeVersion
     * @param description
     * @param revisionDate
     * @param validFor
     */
    private void specifyVersion(String verType, String curTypeVersion, String description, Date revisionDate, TimePeriod validFor) {
    	if(StringUtils.isEmpty(verType)){
    		log.error("the parameter of verType is null. ");
    		throw new IllegalArgumentException("the parameter of verType is error.");
    	}
    	if(StringUtils.isEmpty(curTypeVersion)){
    		log.error("the parameter of curTypeVersion is null. ");
    		throw new IllegalArgumentException("the parameter of curTypeVersion is error.");
    	}
    	if(null == validFor){
    		log.error("the parameter of validFor is null. ");
    		throw new IllegalArgumentException("the parameter of validFor is error.");
    	}
    	ProductSpecificationVersion versi= new ProductSpecificationVersion(verType,description,curTypeVersion,revisionDate,validFor);
    	if(prodSpecVersion==null ){
			prodSpecVersion = new ArrayList<ProductSpecificationVersion>();
		}
    	if(null != this.getProdSpecVersion()){
			if(this.getProdSpecVersion().contains(versi)){
				return;
			}
    	}
		prodSpecVersion.add(versi);
    }

    /**
     * 
     * @param version
     * @param description
     * @param revisionDate
     * @param validFor
     * @throws Exception 
     */
    public void specifyVersion(String version, String description, Date revisionDate, TimePeriod validFor) throws Exception {
    	if(version!=null && !"".equals(version)){
    		String []vos = version.split("\\.");
    		if(vos.length!=3){
    			log.error("the format of version is not correct");
    			throw new IllegalArgumentException("the parameter of version is error.");
    		}else{
				specifyVersion(ProdSpecEnum.VersionLevel.MAJOR_VERSION.getValue(), vos[0], description, revisionDate, validFor);
				specifyVersion(ProdSpecEnum.VersionLevel.MINOR_VERSION.getValue(), vos[1], description, revisionDate, validFor);
				specifyVersion(ProdSpecEnum.VersionLevel.PATCH_VERSION.getValue(), vos[2], description, revisionDate, validFor);
    		}
    	}
    }
    
    public List<ProductSpecificationVersion> retrieveCurrentVersion() {
    	//TODO 
    	return null;
    }

    /**
     * 
     * @param majorVersion
     * @param description
     * @param revisionDate
     */
    public String upgradeMajorVersion(String majorVersion, String description, Date revisionDate) {
    	//TODO 
    	return "";
    }

    /**
     * 
     * @param minorVersion
     * @param description
     * @param revisionDate
     */
    public String upgradeMinorVersion(String minorVersion, String description, Date revisionDate) {
    	//TODO 
    	return "";
    }

    /**
     * 
     * @param patchVersion
     * @param description
     * @param revisionDate
     */
    public String upgradePatchVersion(String patchVersion, String description, Date revisionDate) {
    	//TODO 
    	return null;
    }

    /**
     * 
     * @param cost
     * @param validFor
     */
    public void addCost(Money cost, TimePeriod validFor) {
    	ProductSpecificationCost prodSpecCost = new ProductSpecificationCost(cost, validFor);
        this.getProductSpecificationCost().add(prodSpecCost);
    }

    /**
     * 
     * @param cost
     * @param validFor
     */
    public void updateCostPeriod(ProductSpecificationCost cost, TimePeriod validFor) {
    	//TODO
    }

    /**
     * 
     * @param time
     */
    public List<ProductSpecificationCost> retrieveCost(Date time) {
    	//TODO 
        return null;
    }

	private ProductSpecificationRelationship retrieveRelatedProdSpecBySpec(ProductSpecification targetProdSpec){
		if(null != this.prodSpecRelationship){
			for(ProductSpecificationRelationship prodSpecShip : prodSpecRelationship){
				if(prodSpecShip.getTargetProdSpec().equals(targetProdSpec)){
					return prodSpecShip;
				}
			}
		}
		return null;
	}

    /**
     *
     * @param prodSpec
     * @param type
     * @param validFor
     */
    public int addRelatedProdSpec(ProductSpecification prodSpec, String type, TimePeriod validFor) {
    	if (StringUtils.isEmpty(type)) {
			return ProdSpecErrorCode.RELATIONSHIP_TYPE_IS_NULL.getCode();
    	}
    	if(!ValidUtil.checkObjectIsNull(prodSpec)){
			return ProdSpecErrorCode.PROD_SPEC_IS_NULL.getCode();
    	}
		if(this.equals(prodSpec)){
			return ProdSpecErrorCode.PROD_SPEC_EQUALS_TO_CURRENT.getCode();
		}
		//checkout time is in period
		ProductSpecificationRelationship prodSpecRealtionship = this.retrieveRelatedProdSpecBySpec(prodSpec);
		if(null != prodSpecRealtionship){
			if(prodSpecRealtionship.getValidFor().isOverlap(validFor)){
				return ProdSpecErrorCode.PROD_SPEC_HAS_RELATED_TO_CURRENT.getCode();
			}
		}
    	ProductSpecificationRelationship ship =new ProductSpecificationRelationship(this, prodSpec, type, validFor);
    	this.prodSpecRelationship.add(ship);
		return CommonErrorCode.SUCCESS.getCode();
    }

	/**
	 *
	 * @param prodSpec
	 * @param oldValidFor
	 * @param validFor
	 * @return
	 */
	public boolean updateRelatedSpecValidPeriod(ProductSpecification prodSpec, TimePeriod oldValidFor, TimePeriod validFor){

		if (null == prodSpec ) {
			log.error("prodSpecChar should not be null. ");
			throw new IllegalArgumentException("prodSpecChar should not be null. ");
		}
		if (null == oldValidFor ){
			log.error("oldValidFor should not be null .");
			throw new IllegalArgumentException("oldValidFor should not be null. ");
		}
		if (null == validFor ){
			log.error("validFor should not be null .");
			throw new IllegalArgumentException("validFor should not be null. ");
		}
		if ( null != prodSpecRelationship) {
			for (ProductSpecificationRelationship productSpecRelationship : prodSpecRelationship) {
				if ( productSpecRelationship.getTargetProdSpec().equals(prodSpec) && productSpecRelationship.getValidFor().equals(oldValidFor)) {
					productSpecRelationship.setValidFor(validFor);
					return true;
				}
			}
		}
		return false;
	}

    /**
     * 
     * @param type
	 */
    public List<ProductSpecification> retrieveRelatedProdSpec(String type) {
    	if(StringUtils.isEmpty(type)){
    		log.error("the parameter type is null, the value of the parameter type="+type);
    		throw new IllegalArgumentException("type should not be null .");
    	}
    	List<ProductSpecification> rtnResultSpec = new ArrayList<ProductSpecification>();
    	if(null != this.prodSpecRelationship){
    		for (ProductSpecificationRelationship prodSpecRelate : this.prodSpecRelationship) {
    			if(prodSpecRelate.getType().equals(type)){
					rtnResultSpec.add(prodSpecRelate.getTargetProdSpec());
    			}
			}
    	}
    	return rtnResultSpec;
    }

    /**
     * 
     * @param type
     * @param time
     */
    public List<ProductSpecification> retrieveRelatedProdSpec(String type, Date time) {
    	List<ProductSpecification> rtnResultSpec = new ArrayList<ProductSpecification>();
    	if(null != this.prodSpecRelationship){
    		if(null == time){
    			for (ProductSpecificationRelationship prodSpecRelate : this.prodSpecRelationship) {
	    			if(prodSpecRelate.getType().equals(type)){
						rtnResultSpec.add(prodSpecRelate.getTargetProdSpec());
	    			}
    			}
    		}else{
    			for (ProductSpecificationRelationship prodSpecRelate : this.prodSpecRelationship) {
	    			if(prodSpecRelate.getValidFor().isInTimePeriod(time) && prodSpecRelate.getType().equals(type)){
						rtnResultSpec.add(prodSpecRelate.getTargetProdSpec());
	    			}
    			}
    		}
    	}
    	return rtnResultSpec;
    }
    
    /**
     * initialize set of ProductSpecCharUse
     */
    private void initProdSpecCharUseSet(){
    	if (null == prodSpecChar) {
			prodSpecChar = new HashSet<ProductSpecCharUse>();
		}
    }

	/**
	 * check parameter is null
	 */
    private void checkNameAndSpecChar(String name, ProductSpecCharacteristic prodSpecChar){
		//name is not null
		if(StringUtils.isEmpty(name)){
			log.error("parameter is error ：name is null. ");
			throw new IllegalArgumentException("name should not be null .");
		}
		if(null == prodSpecChar){
			log.error("parameter is error ：the Object of ProductSpecCharacteristic is null. ");
			throw new IllegalArgumentException("specChar should not be null .");
		}
	}

    /**
     * check parameter is null
     */
    public void checkProdSpecCharValue(ProductSpecCharacteristicValue charValue){
    	if(null == charValue){
    		log.error("parameter is error ：the Object of ProductSpecCharacteristicValue is null. ");
    		throw new IllegalArgumentException("charValue should not be null .");
    	}
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProductSpecification that = (ProductSpecification) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;
		return !(productNumber != null ? !productNumber.equals(that.productNumber) : that.productNumber != null);

	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (brand != null ? brand.hashCode() : 0);
		result = 31 * result + (productNumber != null ? productNumber.hashCode() : 0);
		return result;
	}

	public String toString(){
		Map<String, Object> rtnMap = getBasicInfo();
		rtnMap.put("prodSpecRelationship",prodSpecRelationship);
		rtnMap.put("prodSpecChar",prodSpecChar);
		return rtnMap.toString();
	}

	public String getBasicInfoToString(){
		return getBasicInfo().toString();
	}
	private Map<String, Object> getBasicInfo(){
		Map<String, Object> basicInfoMap = new HashMap<String, Object>();
		this.lifecycleStatus = ProdSpecEnum.ProdSpecStatus.STATUS_ACTIVE.getValue();
		basicInfoMap.put("name",this.name);
		basicInfoMap.put("productNumber",this.productNumber);
		basicInfoMap.put("brand",this.brand);
		basicInfoMap.put("validFor",this.validFor);
		basicInfoMap.put("description",this.description);
		basicInfoMap.put("lifecycleStatus",this.lifecycleStatus);
		return basicInfoMap;
	}
}
