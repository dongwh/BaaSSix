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
		try{
			pscu.addValue(null,false,validFor);
			fail("add a empty value ,excepted illegalArgumentException for value");
		}   catch (IllegalArgumentException ex){
		}

		ProductSpecCharacteristicValue prodSpecCharValue = new ProductSpecCharacteristicValue("1", "cm", validFor, "12", "", "");
		result= pscu.addValue(prodSpecCharValue, false, validFor);
		assertEquals("add value　：value is not belong of the char",false,result);
		assertEquals("add value　：value is not belong of the char",null,pscu.getProdSpecCharValue());

		 prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", false);

		result=pscu.addValue(prodSpecCharValue, false, validFor);
		assertEquals("add value",true,result);
		assertEquals("add value",1,pscu.getProdSpecCharValue().size());
		assertEquals("add value",prodSpecCharValue,pscu.getProdSpecCharValue().iterator().next().getProdSpecCharValue());

		result=pscu.addValue(prodSpecCharValue, false, validFor);
		assertEquals("add a duplicate value",true,result);
		assertEquals("add a duplicate value", 1, pscu.getProdSpecCharValue().size());
		assertEquals("add a duplicate value",prodSpecCharValue,pscu.getProdSpecCharValue().iterator().next().getProdSpecCharValue());

		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", false);
		result=pscu.addValue(prodSpecCharValue, false, validFor);
		assertEquals("add a duplicate value",true,result);
		assertEquals("add a duplicate value", 1, pscu.getProdSpecCharValue().size());
		assertEquals("add a duplicate value",prodSpecCharValue,pscu.getProdSpecCharValue().iterator().next().getProdSpecCharValue());

	}
	@Test
	public void testRemoveValue(){
		boolean result=false;
		ProductSpecCharacteristicValue prodSpecCharValue=null;
		try{
			pscu.removeValue(prodSpecCharValue);
			fail("parameter is a empty value ,excepted a illegalArgumenteException for value");
		}   catch (IllegalArgumentException ex){

		}
		prodSpecCharValue = new ProductSpecCharacteristicValue("1", "cm", validFor, "12", "", "");
		result=pscu.removeValue(prodSpecCharValue);
		assertEquals("value is not belong of the char ",false,result);
		assertEquals("value is not belong of the char ",null,pscu.getProdSpecCharValue());


		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", "", "");
		result=pscu.removeValue(prodSpecCharValue);
		assertEquals("the current char not used the value ",false,result);
		assertEquals("the current char not used the value",null,pscu.getProdSpecCharValue());

		pscu.addValue(prodSpecCharValue, false, validFor);
		result=pscu.removeValue(prodSpecCharValue);
		assertEquals("remove value",true,result);
		assertEquals("remove value",0,pscu.getProdSpecCharValue().size());
	}
	@Test 
	public void testSpecifyDefaultCharacteristicValue(){

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

		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", false);
		result=pscu.specifyDefaultCharacteristicValue(prodSpecCharValue);
		assertEquals("the current char not used the value", false, result);
		assertEquals("the current char not used the value", null, pscu.getProdSpecCharValue());

		pscu.addValue(prodSpecCharValue, false, validFor);
		result=pscu.specifyDefaultCharacteristicValue(prodSpecCharValue);
		assertEquals("the current char  used the value", true, result);
		assertEquals("the current char  used the value", 1, pscu.retrieveDefaultCharacteristicValueUse().size());
		assertEquals("the current char  used the value", true, pscu.getProdSpecCharValue().iterator().next().isIsDefault());


		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12.3", false);
		pscu.addValue(prodSpecCharValue, false, validFor);
		result=pscu.specifyDefaultCharacteristicValue(prodSpecCharValue);
		assertEquals("have one defaultValue", true, result);
		assertEquals("have one defaultValue", 2, pscu.retrieveDefaultCharacteristicValueUse().size());
		Iterator<ProdSpecCharValueUse>  useChar=pscu.getProdSpecCharValue().iterator();
		useChar.next();
		assertEquals("have one defaultValue", true, useChar.next().isIsDefault());
	}
	@Test
	public void testRetrieveDefaultCharacteristicValueUse(){
		ProductSpecCharacteristicValue prodSpecCharValue = null;

		List<ProdSpecCharValueUse> defaultValues=pscu.retrieveDefaultCharacteristicValueUse();
		assertEquals("the current have not used value",0,defaultValues.size());

		prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12.3", false);
	    pscu.addValue(prodSpecCharValue,true,validFor);
		defaultValues=pscu.retrieveDefaultCharacteristicValueUse();
		assertEquals("the current have  used value", 1, defaultValues.size());
		assertEquals("the current have  used value",prodSpecCharValue,defaultValues.get(0).getProdSpecCharValue());

	}
	@Test
	public void testClearDefaultValue(){
		boolean result=  false;
		try{
			result = pscu.clearDefaultValueUse(null);
			fail("parameter is empty value ,excepted a IllegalArgumentExceiption for parameter");
		}   catch (IllegalArgumentException ex){

		}

		ProductSpecCharacteristicValue prodSpecCharValuee = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12.3", false);
		result=pscu.clearDefaultValueUse(prodSpecCharValuee)  ;
		assertEquals("char not used the specify value", false, result);
		assertEquals("char not used the specify value", 0, pscu.retrieveDefaultCharacteristicValueUse().size());

		pscu.addValue(prodSpecCharValuee, false, validFor);
		result = pscu.clearDefaultValueUse(prodSpecCharValuee);
		assertEquals("char used the specify value ,but not the default value", true, result);
		assertEquals("char not used the specify value", 0, pscu.retrieveDefaultCharacteristicValueUse().size());

		pscu.specifyDefaultCharacteristicValue(prodSpecCharValuee);
		ProductSpecCharacteristicValue prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "cm", validFor, "12", false);
		pscu.addValue(prodSpecCharValue, true, validFor);
		result = pscu.clearDefaultValueUse(prodSpecCharValue);
		assertEquals("char used the specify value（have two default value）", true, result);
		assertEquals("char not used the specify value", 1, pscu.retrieveDefaultCharacteristicValueUse().size());

		result = pscu.clearDefaultValueUse(prodSpecCharValuee);
		assertEquals("char used the specify value（have one default value）", true, result);
		assertEquals("char not used the specify value", 0, pscu.retrieveDefaultCharacteristicValueUse().size());

	}
}
