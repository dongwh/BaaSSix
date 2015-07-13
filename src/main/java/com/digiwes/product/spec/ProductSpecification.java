package com.digiwes.product.spec;

import com.digiwes.basetype.Money;
import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.enums.ProdSpecEnum;
import com.digiwes.common.enums.ResultCodeCommonEnum;
import com.digiwes.common.enums.ResultCodeSpecEnum;
import com.digiwes.common.util.NumberUtil;
import com.digiwes.common.util.ValidUtil;
import com.digiwes.product.offering.SimpleProductOffering;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A detailed description of a tangible or intangible object made available externally in the form of a ProductOffering to Customers or other Parties playing a PartyRole. A ProductSpecification may consist of other ProductSpecifications supplied together as a collection. Members of the collection may be offered in their own right. ProductSpecifications may also exist within groupings, such as ProductCategories, ProductLines, and ProductTypes.
 */
public abstract class ProductSpecification {
	private static final Logger log = Logger.getLogger(ProductSpecification.class);

    public List<ProductSpecificationCost> productSpecificationCost;
    public List<ProductSpecificationRelationship> prodSpecRelationship;
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

		assertNotNull("name must not null. ");
		assertNotNull("productNumber must not null. ");

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

		assertNotNull("name must not null. ");
		assertNotNull("productNumber must not null. ");

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
    public String addCharacteristic(ProductSpecCharacteristic specChar, String name, boolean canBeOveridden, boolean isPackage, TimePeriod validFor) {
		//check name
		if(StringUtils.isEmpty(name)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.NAME.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.NAME.getCode();
		}
		//check specChar
		if(ValidUtil.checkObjectIsNull(specChar)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getCode();
		}
		//initialize set of ProductSpecCharUse
    	initProdSpecCharUseSet();
        //the charUse has been used under the specification, can't add the same charUse again
        ProductSpecCharUse prodSpecCharUseNew = new ProductSpecCharUse(specChar, canBeOveridden, isPackage, validFor, name);
		if(this.prodSpecChar.contains(prodSpecCharUseNew)){
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHAR_ALREADY_BE_USED.getCode();
		}
		prodSpecChar.add(prodSpecCharUseNew);
    	return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
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
    public String addCharacteristic(ProductSpecCharacteristic specChar, String name, boolean canBeOveridden, boolean isPackage, TimePeriod validFor, String unique, int minCardinality, int maxCardinality, boolean extensible, String description){
		//check name
		if(StringUtils.isEmpty(name)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.NAME.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.NAME.getCode();
		}
		//check specChar
		if(ValidUtil.checkObjectIsNull(specChar)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getCode();
		}
    	//initialize set of ProductSpecCharUse
    	initProdSpecCharUseSet();
		//the charUse has been used under the specification, can't add the same charUse again
		ProductSpecCharUse prodSpecCharUseNew = new ProductSpecCharUse(specChar, canBeOveridden, isPackage, validFor, name, unique, minCardinality, maxCardinality, extensible, description);
		if(this.prodSpecChar.contains(prodSpecCharUseNew)){
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHAR_ALREADY_BE_USED.getCode();
		}
		prodSpecChar.add(prodSpecCharUseNew);
		return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @return
	 */
    public String removeCharacteristic(ProductSpecCharacteristic specChar, String name){
		//check name
		if(StringUtils.isEmpty(name)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.NAME.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.NAME.getCode();
		}
		//check specChar
		if(ValidUtil.checkObjectIsNull(specChar)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getCode();
		}
    	if (null != this.prodSpecChar){
    		for (ProductSpecCharUse prodSpecUse : prodSpecChar) {
    			if(prodSpecUse.getProdSpecChar().equals(specChar) && prodSpecUse.getName().equals(name)) {
					this.prodSpecChar.remove(prodSpecUse);
					return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
    			}
			}
    	}
    	//an Object which is not exist
    	log.warn("the characteristic in not exist under this spec. ");
		return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param charValue
	 * @param isDefault
	 * @param validFor
	 */
    public String attachCharacteristicValue(ProductSpecCharacteristic specChar, String name, ProductSpecCharacteristicValue charValue, boolean isDefault, TimePeriod validFor) {
		//check name
		if(StringUtils.isEmpty(name)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.NAME.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.NAME.getCode();
		}
		//check specChar
		if(ValidUtil.checkObjectIsNull(specChar)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getCode();
		}
    	//check charValue is null
		if(ValidUtil.checkObjectIsNull(charValue)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC_VALUE.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC_VALUE.getCode();
		}
    	if(null != prodSpecChar && prodSpecChar.size()>0){
    		for (ProductSpecCharUse prodSpecCharUse : this.prodSpecChar) {
    			if(prodSpecCharUse.getProdSpecChar().equals(specChar) && prodSpecCharUse.getName().equals(name)){
					prodSpecCharUse.addValue(charValue, isDefault, validFor);
					break;
				}
			}
    	}
		return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param charValue
	 */
    public String detachCharacteristicValue(ProductSpecCharacteristic specChar,String name, ProductSpecCharacteristicValue charValue){
		//check name
		if(StringUtils.isEmpty(name)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.NAME.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.NAME.getCode();
		}
		//check specChar
		if(ValidUtil.checkObjectIsNull(specChar)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getCode();
		}
		//check charValue is null
		if(ValidUtil.checkObjectIsNull(charValue)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC_VALUE.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC_VALUE.getCode();
		}
    	if(null != prodSpecChar && prodSpecChar.size()>0){
    		for (ProductSpecCharUse prodSpecCharUse : prodSpecChar) {
				if (prodSpecCharUse.getProdSpecChar().equals(specChar) && prodSpecCharUse.getName().equals(name)){
    				prodSpecCharUse.removeValue(charValue);
					return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
    			}
			}
		}
		log.error("under the prodSpec didn't use the characteristic which the value will be used by characteristic");
		return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
	}

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param defaultCharValue
	 */
    public String specifyDefaultCharacteristicValue(ProductSpecCharacteristic specChar, String name, ProductSpecCharacteristicValue defaultCharValue) {
		//check name
		if(StringUtils.isEmpty(name)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.NAME.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.NAME.getCode();
		}
		//check specChar
		if(ValidUtil.checkObjectIsNull(specChar)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getCode();
		}
		//check defaultCharValue is null
		if(ValidUtil.checkObjectIsNull(defaultCharValue)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC_VALUE.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC_VALUE.getCode();
		}
    	if(null != this.prodSpecChar){
    		for (ProductSpecCharUse prodSpecCharUse : prodSpecChar) {
				if (prodSpecCharUse.getProdSpecChar().equals(specChar) && prodSpecCharUse.getName().equals(name)){
    				prodSpecCharUse.specifyDefaultCharacteristicValue(defaultCharValue);
					break;
				}
			}
    	}
		return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
    }

	/**
	 *
	 * @param specChar
	 * @param name
	 * @param defaultCharValue
	 */
	public String clearDefaultCharacteristicValue(ProductSpecCharacteristic specChar, String name, ProductSpecCharacteristicValue defaultCharValue) {
		//check name
		if(StringUtils.isEmpty(name)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.NAME.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.NAME.getCode();
		}
		//check specChar
		if(ValidUtil.checkObjectIsNull(specChar)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getCode();
		}
		//check defaultCharValue is null
		if(ValidUtil.checkObjectIsNull(defaultCharValue)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC_VALUE.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC_VALUE.getCode();
		}
		if (this.prodSpecChar != null) {
			for (ProductSpecCharUse prodSpecCharUse : prodSpecChar) {
				if(prodSpecCharUse.getProdSpecChar().equals(specChar) && prodSpecCharUse.getName().equals(name)){
					prodSpecCharUse.clearDefaultValueUse(defaultCharValue);
					break;
				}
			}
		}
		return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
	}

    /**
     * 
     * @param time
     */
    public List<ProductSpecCharUse> retrieveCharacteristic(Date time) {
    	if(null == time){
    		log.error(ResultCodeCommonEnum.ResultCodeCommon.TIME.getValue());
    		throw new IllegalArgumentException(ResultCodeCommonEnum.ResultCodeCommon.TIME.getValue());
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
    public String setCardinality(ProductSpecCharacteristic specChar, String name, int minCardinality, int maxCardinality) {
		//check specChar
		if(ValidUtil.checkObjectIsNull(specChar)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPEC_CHARACTERISTIC.getCode();
		}
        int rtnNum = NumberUtil.compareTheNumber(minCardinality, maxCardinality);
        if(rtnNum == 1){
        	log.error("minCardinality"+ResultCodeCommonEnum.ResultCodeCommon.SHOULD_BE_LESS_THAN.getValue()+"maxCardinality.");
        	return ResultCodeCommonEnum.ResultCodeCommon.SHOULD_BE_LESS_THAN.getCode();
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
        return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
    }

    /**
     * 
     * @param verType
     * @param curTypeVersion
     * @param description
     * @param revisionDate
     * @param validFor
     */
    private String specifyVersion(String verType, String curTypeVersion, String description, Date revisionDate, TimePeriod validFor) {
    	if(StringUtils.isEmpty(verType)){
    		log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_VERSION_TYPE.getValue());
    		return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_VERSION_TYPE.getCode();
    	}
    	if(StringUtils.isEmpty(curTypeVersion)){
    		log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_CURRENT_VERSION.getValue());
    		return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_CURRENT_VERSION.getCode();
    	}
    	if(ValidUtil.checkObjectIsNull(validFor)){
    		log.error(ResultCodeCommonEnum.ResultCodeCommon.VALID_FOR.getValue());
    		return ResultCodeCommonEnum.ResultCodeCommon.VALID_FOR.getCode();
    	}
    	ProductSpecificationVersion version= new ProductSpecificationVersion(verType,description,curTypeVersion,revisionDate,validFor);
    	if(prodSpecVersion==null ){
			prodSpecVersion = new ArrayList<ProductSpecificationVersion>();
		}
    	if(null != this.getProdSpecVersion()){
			if(this.getProdSpecVersion().contains(version)){
				return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION_VERSION_ALREADY_BE_USED.getCode();
			}
    	}
		prodSpecVersion.add(version);
		return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
    }

    /**
     * 
     * @param version
     * @param description
     * @param revisionDate
     * @param validFor
     * @throws Exception 
     */
    public String specifyVersion(String version, String description, Date revisionDate, TimePeriod validFor) throws Exception {
    	if(version!=null && !"".equals(version)){
    		String []vos = version.split("\\.");
    		if(vos.length!=3){
    			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION_VERSION_SIZE.getValue()+" 3. ");
    			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION_VERSION_SIZE.getCode();
    		}else{
				specifyVersion(ProdSpecEnum.VersionLevel.MAJOR_VERSION.getValue(), vos[0], description, revisionDate, validFor);
				specifyVersion(ProdSpecEnum.VersionLevel.MINOR_VERSION.getValue(), vos[1], description, revisionDate, validFor);
				specifyVersion(ProdSpecEnum.VersionLevel.PATCH_VERSION.getValue(), vos[2], description, revisionDate, validFor);
    		}
    	}
		return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
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
    public String addRelatedProdSpec(ProductSpecification prodSpec, String type, TimePeriod validFor) {
    	if(StringUtils.isEmpty(type)){
    		log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION_RELATIONSHIP_TYPE.getValue());
    		return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION_RELATIONSHIP_TYPE.getCode();
    	}
    	if(ValidUtil.checkObjectIsNull(prodSpec)){
    		log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION.getCode();
    	}
		if(ValidUtil.checkObjectIsNull(validFor)){
			log.error(ResultCodeCommonEnum.ResultCodeCommon.VALID_FOR.getValue());
			return ResultCodeCommonEnum.ResultCodeCommon.VALID_FOR.getCode();
		}
		if(this.equals(prodSpec)){
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION_ALREADY_BE_USED.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION_ALREADY_BE_USED.getCode();
		}

		//checkout time is in period
		ProductSpecificationRelationship prodSpecRealtionship = this.retrieveRelatedProdSpecBySpec(prodSpec);
		if(null != prodSpecRealtionship){
			if(prodSpecRealtionship.getValidFor().isOverlap(validFor)){
				log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION_VALIDFOR.getValue());
				return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION_VALIDFOR.getCode();
			}
		}
    	if(null == this.prodSpecRelationship){
    		this.prodSpecRelationship = new ArrayList<ProductSpecificationRelationship>();
    	}
    	ProductSpecificationRelationship ship =new ProductSpecificationRelationship(this, prodSpec, type, validFor);
    	this.prodSpecRelationship.add(ship);
		return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
    }

	/**
	 *
	 * @param prodSpec
	 * @param oldValidFor
	 * @param validFor
	 * @return
	 */
	public String updateRelatedSpecValidPeriod(ProductSpecification prodSpec, TimePeriod oldValidFor, TimePeriod validFor){

		if (ValidUtil.checkObjectIsNull(prodSpec)) {
			log.error(ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION.getValue());
			return ResultCodeSpecEnum.ResultCodeSpec.PRODUCT_SPECIFICATION.getCode();
		}
		if (ValidUtil.checkObjectIsNull(oldValidFor)){
			log.error("oldValidFor"+ ResultCodeCommonEnum.ResultCodeCommon.VALID_FOR.getValue());
			return ResultCodeCommonEnum.ResultCodeCommon.VALID_FOR.getCode();
		}
		if (ValidUtil.checkObjectIsNull(validFor)){
			log.error(ResultCodeCommonEnum.ResultCodeCommon.VALID_FOR.getValue());
			return ResultCodeCommonEnum.ResultCodeCommon.VALID_FOR.getCode();
		}
		if ( null != prodSpecRelationship) {
			for (ProductSpecificationRelationship productSpecRelationship : prodSpecRelationship) {
				if ( productSpecRelationship.getTargetProdSpec().equals(prodSpec) && productSpecRelationship.getValidFor().equals(oldValidFor)) {
					productSpecRelationship.setValidFor(validFor);
					return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
				}
			}
		}
		return ResultCodeCommonEnum.ResultCodeCommon.SUCCESS.getCode();
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
    	if(null != this.prodSpecRelationship) {
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
