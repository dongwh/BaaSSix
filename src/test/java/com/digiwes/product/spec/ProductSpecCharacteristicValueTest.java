package com.digiwes.product.spec;

import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.enums.ProdSpecEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ProductSpecCharacteristicValueTest {

	private ProductSpecCharacteristicValue memoryCharValue;
	private ProductSpecCharacteristicValue charValue;
	private static TimePeriod validFor;

	@BeforeClass
	public static void setUpBeforeClass(){
		validFor = new TimePeriod("2015-02-03 12:00:00","2015-07-21 23:59:59");
	}
	@Before
	public void initValue(){
		memoryCharValue=new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(),"GHz",validFor,"2.7",false);
		charValue=new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(),"GHz",validFor,"2.9",false);

	}

	@Test
	public void testAddRelatedCharacteristic(){
		boolean result =false;
		List<ProdSpecCharValueRelationship> exceptProdSpecCharValueRelationships = new ArrayList<ProdSpecCharValueRelationship>();
		try{
			result=memoryCharValue.addRelatedCharValue(null,ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getName(), validFor);
			assertEquals("add a related charValue,charValue is null",false, result);
		}   catch (IllegalArgumentException ex){

		}
		try{
			result=memoryCharValue.addRelatedCharValue(charValue,null, validFor);
			assertEquals("add a related charValue,charValue is null",false, result);
		}   catch (IllegalArgumentException ex){

		}
		result=memoryCharValue.addRelatedCharValue(memoryCharValue, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(), validFor);
		assertEquals("add a related charValue,the specify value is same with currentCharValue", false, result);
		assertEquals("add a related charValue,the specify value is same with currentCharValue", null, memoryCharValue.getProdSpecCharValueRelationship());

		result=memoryCharValue.addRelatedCharValue(charValue,ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(), validFor);
		ProdSpecCharValueRelationship specCharValueRelationShip = new ProdSpecCharValueRelationship(memoryCharValue,charValue, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(), validFor);
		exceptProdSpecCharValueRelationships.add(specCharValueRelationShip);
		assertEquals("add a related charValue", true, result);
		assertEquals("add a related charValue", 1, memoryCharValue.getProdSpecCharValueRelationship().size());
		assertEquals("add a related charValue",exceptProdSpecCharValueRelationships, memoryCharValue.getProdSpecCharValueRelationship());

		result=memoryCharValue.addRelatedCharValue(charValue, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(), validFor);
		assertEquals("add a related charValue,have created a exclusive relationship", false, result);
		assertEquals("add a related charValue,have created a exclusive relationship", 1, memoryCharValue.getProdSpecCharValueRelationship().size());
		assertEquals("add a related charValue,have created a exclusive relationship",exceptProdSpecCharValueRelationships, memoryCharValue.getProdSpecCharValueRelationship());

		result=memoryCharValue.addRelatedCharValue(charValue, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(), new TimePeriod("2015-01-01 00:00:00", "2015-01-29 23:59:59"));
		specCharValueRelationShip = new ProdSpecCharValueRelationship(memoryCharValue,charValue, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(), new TimePeriod("2015-01-01 00:00:00", "2015-01-29 23:59:59"));
		exceptProdSpecCharValueRelationships.add(specCharValueRelationShip);
		Assert.assertEquals("add Related charValuehave create a aggregation relationship (time before )", true, result);
		Assert.assertEquals("add Related charValue.", 2, memoryCharValue.getProdSpecCharValueRelationship().size());
		Assert.assertEquals("add Related charValue.", exceptProdSpecCharValueRelationships, memoryCharValue.getProdSpecCharValueRelationship());

		result=memoryCharValue.addRelatedCharValue(charValue, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(), new TimePeriod("2015-01-01 00:00:00", "2015-06-29 23:59:59"));
		assertEquals("add Related charValuehave create a aggregation relationship (time in period  )", false, result);
		assertEquals("add Related charValue.", 2, memoryCharValue.getProdSpecCharValueRelationship().size());
		assertEquals("add Related charValue.", exceptProdSpecCharValueRelationships, memoryCharValue.getProdSpecCharValueRelationship());

		specCharValueRelationShip = new ProdSpecCharValueRelationship(memoryCharValue,charValue, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(),  new TimePeriod("2015-09-01 23:59:59", "2015-10-29 23:59:59"));
		exceptProdSpecCharValueRelationships.add(specCharValueRelationShip);
		result=memoryCharValue.addRelatedCharValue(charValue, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(), new TimePeriod("2015-09-01 23:59:59", "2015-10-29 23:59:59"));
		Assert.assertEquals("add Related charValuehave create a aggregation relationship (time after  )", true, result);
		Assert.assertEquals("add Related charValue.", 3, memoryCharValue.getProdSpecCharValueRelationship().size());
		Assert.assertEquals("add Related charValue.", exceptProdSpecCharValueRelationships, memoryCharValue.getProdSpecCharValueRelationship());

		result=memoryCharValue.addRelatedCharValue(charValue, ProdSpecEnum.ProdSpecRelationship.DEPENDENCY.getName(), validFor);
		assertEquals("add a related charValue,have created a exclusive relationship",false, result);
		assertEquals("add a related charValue,have created a exclusive relationship",charValue, memoryCharValue.getProdSpecCharValueRelationship().iterator().next().getProductSpecCharacteristicValue());
		assertEquals("add a related charValue,have created a exclusive relationship", exceptProdSpecCharValueRelationships, memoryCharValue.getProdSpecCharValueRelationship());
	}
	@Test
	public void testReteriveRelatedCharValue(){
		List<ProductSpecCharacteristicValue> charValues = null;
		List<ProductSpecCharacteristicValue> exceptCharValues=new ArrayList<ProductSpecCharacteristicValue>();
		try{
			charValues=memoryCharValue.retieveRelatedCharValue(null,null);
			fail("reterive the relation charValues,excepted illegalArgumentException for parameter");
		}catch (IllegalArgumentException ex){
		}

		charValues=memoryCharValue.retieveRelatedCharValue(ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(), new Date());
		assertEquals("Reterive the related CharValue,The current charValue and the specified charValue have no  created an exclusivity relationship",0,charValues.size());

		exceptCharValues.add(charValue);
		memoryCharValue.addRelatedCharValue(charValue, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(), validFor);
		charValues=memoryCharValue.retieveRelatedCharValue(ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getName(), new Date());
		assertEquals("Reterive the related CharValue,The current charValue and the specified charValue have  created an exclusivity relationship", 1, charValues.size());
		assertEquals("Reterive the related CharValue,The current charValue and the specified charValue have  created an exclusivity relationship",exceptCharValues,charValues);

		charValues=memoryCharValue.retieveRelatedCharValue(ProdSpecEnum.ProdSpecRelationship.DEPENDENCY.getName(), new Date());
		assertEquals("Reterive the related CharValue,The current charValue and the specified charValue have created an exclusivity relationship",0,charValues.size());
	}
	
	
}
