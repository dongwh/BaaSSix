package com.digiwes.product.spec;

import com.digiwes.basetype.TimePeriod;
import com.digiwes.common.enums.ProdSpecEnum;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.util.*;

import static org.junit.Assert.*;

public class ProductSpecCharUseTest {
    private	ProductSpecCharUse pscu =null;
	private ProductSpecCharacteristic prodSpecChar=null;
    private static TimePeriod validFor;
	@Before
	public void createProductSpecCharacteristic(){
		prodSpecChar = new ProductSpecCharacteristic("1","depth", ProdSpecEnum.ProdSpecType.NUMERIC.getName(), validFor, "false",  1,  1, true, "height","");
		ProductSpecCharacteristicValue prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", false);
		prodSpecChar.addValue(prodSpecCharValue);
		ProductSpecCharacteristicValue prodSpecCharValuee = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12.3", false);
		prodSpecChar.addValue(prodSpecCharValuee);
	    pscu = new ProductSpecCharUse(prodSpecChar, false, false, validFor,"depth");
	}
	@BeforeClass
	public static void initVliadFor(){
		validFor = new TimePeriod("2015-06-01 00:00:00","2015-08-01 23:59:59");
	}
	@Test
    public void testAddValue(){
		boolean result=false;
		Set<ProdSpecCharValueUse> exceptProductSpecCharValue=null;
		try{
			pscu.addValue(null,false,validFor);
			fail("add a empty value ,excepted illegalArgumentException for value");
		}   catch (IllegalArgumentException ex){
		}

		ProductSpecCharacteristicValue prodSpecCharValue = new ProductSpecCharacteristicValue("1", "cm", validFor, "12", "", "");
		result= pscu.addValue(prodSpecCharValue, false, validFor);
		assertEquals("add value　：value is not belong of the char",false,result);
		assertEquals("add value　：value is not belong of the char",exceptProductSpecCharValue,pscu.getProdSpecCharValue());

		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", false);
		exceptProductSpecCharValue=new HashSet<ProdSpecCharValueUse>();
		exceptProductSpecCharValue.add(new ProdSpecCharValueUse(prodSpecCharValue, false, validFor)) ;
		result=pscu.addValue(prodSpecCharValue, false, validFor);
		assertEquals("add value",true,result);
		assertEquals("add value",1,pscu.getProdSpecCharValue().size());
		assertEquals("add value",exceptProductSpecCharValue,pscu.getProdSpecCharValue());

		result=pscu.addValue(prodSpecCharValue, false, validFor);
		assertEquals("add a duplicate value",true,result);
		assertEquals("add a duplicate value", 1, pscu.getProdSpecCharValue().size());
		assertEquals("add a duplicate value",exceptProductSpecCharValue,pscu.getProdSpecCharValue());

		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", false);
		result=pscu.addValue(prodSpecCharValue, false, validFor);
		assertEquals("add a duplicate value",true,result);
		assertEquals("add a duplicate value", 1, pscu.getProdSpecCharValue().size());
		assertEquals("add a duplicate value",exceptProductSpecCharValue,pscu.getProdSpecCharValue());
	}
	@Test
	public void testRemoveValue(){
		boolean result=false;
		Set<ProdSpecCharValueUse> exceptProductSpecCharValue=null;
		ProductSpecCharacteristicValue prodSpecCharValue=null;
		try{
			pscu.removeValue(prodSpecCharValue);
			fail("parameter is a empty value ,excepted a illegalArgumenteException for value");
		}   catch (IllegalArgumentException ex){

		}
		prodSpecCharValue = new ProductSpecCharacteristicValue("1", "cm", validFor, "12", "", "");
		result=pscu.removeValue(prodSpecCharValue);
		assertEquals("value is not belong of the char ",false,result);
		assertEquals("value is not belong of the char ", exceptProductSpecCharValue, pscu.getProdSpecCharValue());


 		 prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", false);

		result=pscu.removeValue(prodSpecCharValue);
		assertEquals("the current char not used the value ",true,result);
		assertEquals("the current char not used the value",exceptProductSpecCharValue,pscu.getProdSpecCharValue());

		pscu.addValue(prodSpecCharValue, false, validFor);
		exceptProductSpecCharValue = new HashSet<ProdSpecCharValueUse>();
		result=pscu.removeValue(prodSpecCharValue);
		assertEquals("remove value",true,result);
		assertEquals("remove value",exceptProductSpecCharValue,pscu.getProdSpecCharValue());
	}
	@Test 
	public void testSpecifyDefaultCharacteristicValue(){
		List<ProdSpecCharValueUse> exceptProdSpecCharValueUses = new ArrayList<ProdSpecCharValueUse>();
		ProductSpecCharacteristicValue prodSpecCharValue = null;
		boolean result=false;
		try{
			pscu.specifyDefaultCharacteristicValue(prodSpecCharValue);
			fail("specify a empty value ，excepted illegalArgumentException for charValue");
		}   catch (IllegalArgumentException ex){
		}
		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "15", "", "");
		result=pscu.specifyDefaultCharacteristicValue(prodSpecCharValue);
	    assertEquals("value is not belong of the current char ",false,result);
		assertEquals("value is not belong of the current char ",exceptProdSpecCharValueUses,pscu.retrieveDefaultCharacteristicValueUse());

		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", false);
		result=pscu.specifyDefaultCharacteristicValue(prodSpecCharValue);
		assertEquals("the current char not used the value", false, result);
		assertEquals("the current char not used the value", exceptProdSpecCharValueUses, pscu.retrieveDefaultCharacteristicValueUse());

		pscu.addValue(prodSpecCharValue, false, validFor);
		exceptProdSpecCharValueUses.add(new ProdSpecCharValueUse(prodSpecCharValue, true, validFor));
		result=pscu.specifyDefaultCharacteristicValue(prodSpecCharValue);
		assertEquals("the current char  used the value", true, result);
		assertEquals("the current char  used the value", 1, pscu.retrieveDefaultCharacteristicValueUse().size());
		assertEquals("the current char  used the value", exceptProdSpecCharValueUses, pscu.retrieveDefaultCharacteristicValueUse());

		exceptProdSpecCharValueUses = new ArrayList<ProdSpecCharValueUse>();
 		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12.3", false);
		pscu.addValue(prodSpecCharValue, false, validFor);
		exceptProdSpecCharValueUses.add(new ProdSpecCharValueUse(prodSpecCharValue, true, validFor));
		result=pscu.specifyDefaultCharacteristicValue(prodSpecCharValue);
		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", false);
		exceptProdSpecCharValueUses.add(new ProdSpecCharValueUse(prodSpecCharValue, true, validFor));
		assertEquals("have one defaultValue", true, result);
		assertEquals("have one defaultValue", exceptProdSpecCharValueUses, pscu.retrieveDefaultCharacteristicValueUse());

	}
	@Test
	public void testRetrieveDefaultCharacteristicValueUse(){
		ProductSpecCharacteristicValue prodSpecCharValue = null;
		List<ProdSpecCharValueUse> exceptProdSpecCharValueUses = new ArrayList<ProdSpecCharValueUse>();
		List<ProdSpecCharValueUse> defaultValues=pscu.retrieveDefaultCharacteristicValueUse();
		assertEquals("the current have not used value",exceptProdSpecCharValueUses,defaultValues);

		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12.3", false);
	    pscu.addValue(prodSpecCharValue, true, validFor);
		exceptProdSpecCharValueUses.add(new ProdSpecCharValueUse(prodSpecCharValue,true,validFor)) ;
		defaultValues=pscu.retrieveDefaultCharacteristicValueUse();
		assertEquals("the current have  used value", 1, defaultValues.size());
		assertEquals("the current have  used value",exceptProdSpecCharValueUses,defaultValues);

	}
	@Test
	public void testClearDefaultValue(){
		boolean result=  false;
		List<ProdSpecCharValueUse> exceptProdSpecCharValueUses = new ArrayList<ProdSpecCharValueUse>();
		try{
			result = pscu.clearDefaultValueUse(null);
			fail("parameter is empty value ,excepted a IllegalArgumentExceiption for parameter");
		}   catch (IllegalArgumentException ex){

		}

		ProductSpecCharacteristicValue prodSpecCharValuee = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12.3", false);
		result=pscu.clearDefaultValueUse(prodSpecCharValuee)  ;
		assertEquals("char not used the specify value", false, result);
		assertEquals("char not used the specify value", exceptProdSpecCharValueUses, pscu.retrieveDefaultCharacteristicValueUse());

		pscu.addValue(prodSpecCharValuee, false, validFor);
		result = pscu.clearDefaultValueUse(prodSpecCharValuee);
		assertEquals("char used the specify value ,but not the default value", true, result);
		assertEquals("char not used the specify value", exceptProdSpecCharValueUses, pscu.retrieveDefaultCharacteristicValueUse());

		exceptProdSpecCharValueUses.add(new ProdSpecCharValueUse(prodSpecCharValuee, true, validFor));
		pscu.specifyDefaultCharacteristicValue(prodSpecCharValuee);
		ProductSpecCharacteristicValue prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", false);
		pscu.addValue(prodSpecCharValue, true, validFor);
		result = pscu.clearDefaultValueUse(prodSpecCharValue);
		assertEquals("char used the specify value（have two default value）", true, result);
		assertEquals("char not used the specify value", exceptProdSpecCharValueUses, pscu.retrieveDefaultCharacteristicValueUse());

		result = pscu.clearDefaultValueUse(prodSpecCharValuee);
		exceptProdSpecCharValueUses.remove(new ProdSpecCharValueUse(prodSpecCharValuee, true, validFor)) ;
		assertEquals("char used the specify value（have one default value）", true, result);
		assertEquals("char not used the specify value", exceptProdSpecCharValueUses, pscu.retrieveDefaultCharacteristicValueUse());

	}
}
