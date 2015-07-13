package com.ai.baas.product.spec;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import com.ai.baas.basetype.TimePeriod;
import com.ai.baas.common.constant.Const;
import com.ai.baas.common.enums.ProdSpecEnum;
import com.ai.baas.common.util.DateUtils;

public class ProductSpecificationTest {
	private static final Logger log = Logger.getLogger(ProductSpecificationTest.class);
	private static ProductSpecCharacteristic psc =null;
	//scene
	private static ProductSpecification prodSpec;
	//test prodSpec
	private  ProductSpecification atomicProdSpec;
	private static TimePeriod validFor;
	String units = "pound";
	long amount = 100;
	//data about prodSpec which is prepared
	private static List<ProductSpecCharacteristic> specCharList = null;
	
	@Before
	public  void setUp(){
		validFor = new TimePeriod("2015-06-01 00:00:00", "2015-08-31 00:00:00");
		atomicProdSpec = new AtomicProductSpecification("1", "11 Pounds MacBook Air", "apple", "Mac", validFor);
	}
	
	/**
	 * the test class about productSpecification when add a characteristic
	 */
	@Test
	public void testAddCharacteristic(){
		ProductSpecCharacteristic specChar = new ProductSpecCharacteristic("1", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");

		//if not add characteristic the size of list
		assertEquals("if not add characteristic the size of list", 0, atomicProdSpec.getProdSpecChar() == null ? 0 : atomicProdSpec.getProdSpecChar().size());

		atomicProdSpec.addCharacteristic(specChar, "nameUse", false, false, validFor);
		assertEquals("add a characteristic success", 1, atomicProdSpec.getProdSpecChar() == null ? 0 : atomicProdSpec.getProdSpecChar().size());
		ProductSpecCharUse prodSpecCharUse = new ProductSpecCharUse(specChar, false, false, validFor, "nameUse");
		assertEquals("characteristic is correct to ProductSpecification", true, atomicProdSpec.getProdSpecChar().contains(prodSpecCharUse));
		
		ProductSpecCharacteristic specCharSame = new ProductSpecCharacteristic("1", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		atomicProdSpec.addCharacteristic(specCharSame, "nameUse", false, false, validFor);
		assertEquals("whether the same characteristic, same nameUse can repeat add", 1, atomicProdSpec.getProdSpecChar() == null ? 0 : atomicProdSpec.getProdSpecChar().size());

		ProductSpecCharacteristic specCharTwo = new ProductSpecCharacteristic("1", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		atomicProdSpec.addCharacteristic(specCharTwo, "nameUseNew", false, false, validFor);
		assertEquals("whether the same characteristic, not same name can add", 2, atomicProdSpec.getProdSpecChar() == null ? 0 : atomicProdSpec.getProdSpecChar().size());
		ProductSpecCharUse prodSpecCharUseNew = new ProductSpecCharUse(specChar, false, false, validFor, "nameUseNew");
		assertEquals("characteristic is correct to ProductSpecification",true,atomicProdSpec.getProdSpecChar().contains(prodSpecCharUseNew));

		try {
			atomicProdSpec.addCharacteristic(specCharTwo, null, false, false, validFor);
			assertEquals("is a null nameUse can be add",2,atomicProdSpec.getProdSpecChar() == null ? 0 : atomicProdSpec.getProdSpecChar().size());
			fail("nameUse is null, no check");
		} catch (Exception e) {
			// check specCharTwo is null
		}

		try {
			atomicProdSpec.addCharacteristic(specCharTwo, "", false, false, validFor);
			assertEquals("is a empty nameUse can be add", 2, atomicProdSpec.getProdSpecChar() == null ? 0 : atomicProdSpec.getProdSpecChar().size());
			fail("nameUse is empty, no check");
		} catch (Exception e) {
			// check specCharTwo is null
		}

		specCharTwo = null;
		try {
			atomicProdSpec.addCharacteristic(specCharTwo, "nameUse", false, false, validFor);
			assertEquals("is a null object about characteristic can be add",2,atomicProdSpec.getProdSpecChar() == null ? 0 : atomicProdSpec.getProdSpecChar().size());
			fail("the object of ProductSpecCharacteristic is null, no check");
		} catch (Exception e) {
			// check specCharTwo is null
		}

	}
	
	/**
	 * the test class about productSpecification when remove a characteristic
	 */
	@Test
	public void testRemoveCharacteristic(){
		ProductSpecCharacteristic specChar = new ProductSpecCharacteristic("1", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		
		atomicProdSpec.removeCharacteristic(specChar, "nameUse");
		assertEquals("if there is a problem not add an object of ProductSpecCharacteristic", 0, (atomicProdSpec.getProdSpecChar() == null ? 0 : atomicProdSpec.getProdSpecChar().size()));
		
		atomicProdSpec.addCharacteristic(specChar, "nameUse", false, false, validFor);
		
		ProductSpecCharacteristic specCharNotExsit = new ProductSpecCharacteristic("2", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		atomicProdSpec.removeCharacteristic(specCharNotExsit, "nameUse");
		assertEquals("is success to delete an object of ProductSpecCharacteristic which is not exist", 1, atomicProdSpec.getProdSpecChar().size());
		ProductSpecCharUse prodSpecCharUse = new ProductSpecCharUse(specCharNotExsit, false, false, validFor, "nameUse");
		assertEquals("does it include", false, atomicProdSpec.getProdSpecChar().contains(prodSpecCharUse));
		
		atomicProdSpec.removeCharacteristic(specChar, "nameUse");
		assertEquals("is success to delete an object of ProductSpecCharacteristic which is exist", 0, atomicProdSpec.getProdSpecChar().size());

		atomicProdSpec.addCharacteristic(specChar, "nameUse", false, false, validFor);
		ProductSpecCharacteristic specCharNew = new ProductSpecCharacteristic("1", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		atomicProdSpec.removeCharacteristic(specCharNew, "nameUse");
		assertEquals("is success to delete an object of ProductSpecCharacteristic which is exist (new)", 0, atomicProdSpec.getProdSpecChar().size());
		ProductSpecCharUse prodSpecCharUseTheSame = new ProductSpecCharUse(specCharNew, false, false, validFor, "nameUse");
		assertEquals("does it include", false, atomicProdSpec.getProdSpecChar().contains(prodSpecCharUseTheSame));

		try {
			atomicProdSpec.removeCharacteristic(specChar, null);
			assertEquals("is a null nameUse can be remove", 0,atomicProdSpec.getProdSpecChar().size());
			fail("nameuse is null, no check");
		} catch (Exception e) {
			// check specCharTwo is null
		}

		try {
			atomicProdSpec.removeCharacteristic(specChar, "");
			assertEquals("is a empty nameUse object about characteristic can be remove", 0,atomicProdSpec.getProdSpecChar().size());
			fail("nameUse is empty, no check");
		} catch (Exception e) {
			// check specCharTwo is null
		}

		specChar = null;
		try {
			atomicProdSpec.removeCharacteristic(specChar, "nameUse");
			assertEquals("is a null object about characteristic can be remove", 0,atomicProdSpec.getProdSpecChar().size());
			fail("the object of ProductSpecCharacteristic is null, no check");
		} catch (Exception e) {
			// check specCharTwo is null
		}
	}
	
	/**
	 * the test class about productSpecification when add value to characteristic
	 */
	@Test
	public void testAttachCharacteristicValue(){
		ProductSpecCharacteristic specChar = new ProductSpecCharacteristic("1", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		ProductSpecCharacteristicValue charValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.TEXT.getValue(), "GBK", validFor, "red", false);
		specChar.addValue(charValue);
		atomicProdSpec.addCharacteristic(specChar, "nameUse", false, false, validFor);
		
		atomicProdSpec.attachCharacteristicValue(specChar, "nameUse", charValue, false, validFor);
		assertEquals("is success to add an value to characteristic", 1, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().size());
		ProdSpecCharValueUse prodSpecCharValueUse = new ProdSpecCharValueUse(charValue, false, validFor);
		assertEquals("if the content is right add to the char", true, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().contains(prodSpecCharValueUse));
		
		atomicProdSpec.attachCharacteristicValue(specChar, "nameUse", charValue, false, validFor);
		assertEquals("whether the same characteristic value can repeat add, the same object", 1, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().size());
		
		ProductSpecCharacteristicValue charValueTheSame = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.TEXT.getValue(), "GBK", validFor, "red", false);
		atomicProdSpec.attachCharacteristicValue(specChar, "nameUse", charValueTheSame, false, validFor);
		assertEquals("whether the same characteristic value can repeat add, new an object(new)", 1, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().size());
		
		specChar = null;
		try {
			atomicProdSpec.attachCharacteristicValue(specChar, "nameUse",charValue, false, validFor);
			assertEquals("if the characteristic is null, if is success to add an value to characteristic", 1, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().size());
			fail("specChar is null, no check");
		}catch (Exception e){

		}

		ProductSpecCharacteristic specCharNotExist = new ProductSpecCharacteristic("2", "size", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		atomicProdSpec.attachCharacteristicValue(specCharNotExist, "nameUse", charValue, false, validFor);
		assertEquals("can't find the characteristic, if is success to add an value", 1, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().size());

		specChar = new ProductSpecCharacteristic("1", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		ProductSpecCharacteristicValue charValueNotExist = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.TEXT.getValue(), "GBK", validFor, "blue", false);
		atomicProdSpec.attachCharacteristicValue(specChar, "nameUse", charValueNotExist, false, validFor);
		assertEquals("is success to add to characteristic if the value is not exist", 1, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().size());
		ProdSpecCharValueUse prodSpecCharValueUseNew = new ProdSpecCharValueUse(charValueNotExist, false, validFor);
		assertEquals("if the content is right add to the char", false, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().contains(prodSpecCharValueUseNew));

		charValue = null;
		try {
			atomicProdSpec.attachCharacteristicValue(specChar, "nameUse", charValue, false, validFor);
			assertEquals("is success to add to characteristic if the value is null", 1, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().size());

			fail("charValue is null, no check. ");
		}catch (Exception e){

		}
	}
	
	/**
	 * the test class to remove an value
	 */
	@Test
	public void testDetachCharacteristicValue(){
		ProductSpecCharacteristic specChar = new ProductSpecCharacteristic("1", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		ProductSpecCharacteristicValue charValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.TEXT.getValue(), "GBK", validFor, "red", false);
		specChar.addValue(charValue);
		atomicProdSpec.addCharacteristic(specChar, "nameUse", false, false, validFor);
		
		atomicProdSpec.detachCharacteristicValue(specChar, "nameUse", charValue);
		assertEquals("not add, delete is any problem", 0, (atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue() == null ? 0 : atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().size()));
		
		atomicProdSpec.attachCharacteristicValue(specChar, "nameUse", charValue, false, validFor);
		ProductSpecCharacteristicValue charValueNotExist = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.TEXT.getValue(), "GBK", validFor, "green", false);
		atomicProdSpec.detachCharacteristicValue(specChar, "nameUse", charValueNotExist);
		assertEquals("is success delete an value which is not exist", 1, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().size());
		ProdSpecCharValueUse prodSpecCharValueUse = new ProdSpecCharValueUse(charValueNotExist, false, validFor);
		assertEquals("if the content is right add to the char", false, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().contains(prodSpecCharValueUse));

		atomicProdSpec.detachCharacteristicValue(specChar, "nameUse", charValue);
		assertEquals("is success delete an value which is exist", 0, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().size());
		
		specChar = null;
		try {
			atomicProdSpec.detachCharacteristicValue(specChar, "nameUse", charValue);
			assertEquals("parameter of charValue is null , if this is right", 0, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().size());
			fail("specChar is null, no check. ");
		}catch (Exception e){

		}
	}
	
	/**
	 * specify an default value to characteristic
	 */
	@Test
	 public void testSpecifyDefaultCharacteristicValue(){
		 ProductSpecCharacteristic specChar = new ProductSpecCharacteristic("1", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		 ProductSpecCharacteristicValue charValueOne = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.TEXT.getValue(), "GBK", validFor, "red", false);
		 specChar.addValue(charValueOne);
		 atomicProdSpec.addCharacteristic(specChar, "nameUse", false, false, validFor);
		 atomicProdSpec.attachCharacteristicValue(specChar, "nameUse", charValueOne, false, validFor);

		 atomicProdSpec.specifyDefaultCharacteristicValue(specChar, "nameUse",charValueOne);
		 assertEquals("is success to add an normal default value", true, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().iterator().next().isIsDefault());

		atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().iterator().next().setIsDefault(false);
		 ProductSpecCharacteristic specCharNotExist = new ProductSpecCharacteristic("2", "size", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		 atomicProdSpec.specifyDefaultCharacteristicValue(specCharNotExist, "nameUse", charValueOne);
		 assertEquals("characteristic is not exist, the success to set the default value", false, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().iterator().next().isIsDefault());

		/* atomicProdSpec.detachCharacteristicValue(specChar, charValueOne, "nameUse");
		 atomicProdSpec.specifyDefaultCharacteristicValue(specChar, charValueOne, "nameUse");
		 assertEquals("characteristic is exist,characteristicValue is not exist", false, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().iterator().next().isIsDefault());*/

		charValueOne.setIsDefault(false);
		atomicProdSpec.specifyDefaultCharacteristicValue(specChar, "nameUse", charValueOne);
		assertEquals("characteristic is exist, characteristicValue is exist but not default value", true, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().iterator().next().isIsDefault());

		atomicProdSpec.specifyDefaultCharacteristicValue(specChar, "nameUse", charValueOne);
		assertEquals("characteristic is exist, characteristicValue is default value can be reset success", true, atomicProdSpec.getProdSpecChar().iterator().next().getProdSpecCharValue().iterator().next().isIsDefault());
	 }


	 /**
	  * query all characteristic by time
	  */
	@Test
	 public void testRetrieveCharacteristic(){
		 boolean rtnFlag = false;
		ProductSpecCharacteristic specChar = new ProductSpecCharacteristic("1", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
		atomicProdSpec.addCharacteristic(specChar, "nameUse", false, false, validFor);

		Date time = null;
		try {
			atomicProdSpec.retrieveCharacteristic(time);
			assertEquals("is correct if time is null", false, rtnFlag);
			fail("no check parameter, time is null");
		} catch (Exception e) {

		}

		String timeStrPre = "2015-01-01 00:00:00";
		Date timePre = DateUtils.str2Date(timeStrPre, DateUtils.date_sdf);
		List<ProductSpecCharUse> prodSpecCharUseList = atomicProdSpec.retrieveCharacteristic(timePre);
		assertEquals("Time parameter to can check the data before time ", 0, prodSpecCharUseList.size());

		String timeStrIn = "2015-07-01 00:00:00";
		Date timeIn = DateUtils.str2Date(timeStrIn, DateUtils.date_sdf);
		prodSpecCharUseList = atomicProdSpec.retrieveCharacteristic(timeIn);
		assertEquals("Time parameter passed in the period of time can check the data", 1, prodSpecCharUseList.size());

		String timeStrAfter = "2015-10-01 00:00:00";
		Date timeAfter = DateUtils.str2Date(timeStrAfter, DateUtils.date_sdf);
		prodSpecCharUseList = atomicProdSpec.retrieveCharacteristic(timeAfter);
		assertEquals("Time parameter to after a period of time can check the data", 0, prodSpecCharUseList.size());
	 }

	/**
	 * add an relationship to spec
	 */
	@Test
	public void testAddRelatedProdSpec(){
		ProductSpecification atomicProdSpecTwo = new AtomicProductSpecification("2", "13 pound MacBook Air", "apple", "Mac", validFor);

		atomicProdSpec.addRelatedProdSpec(atomicProdSpecTwo, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getValue(), validFor);
		assertEquals("is success to add an relationship to spec", 1, atomicProdSpec.getProdSpecRelationship().size());

		atomicProdSpec.addRelatedProdSpec(atomicProdSpecTwo, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getValue(), validFor);
		assertEquals("whether can add an same relationship with the same spec", 1, atomicProdSpec.getProdSpecRelationship().size());

		atomicProdSpec.addRelatedProdSpec(atomicProdSpecTwo, ProdSpecEnum.ProdSpecRelationship.EXCLUSIBITY.getValue(), validFor);
		assertEquals("if you can establish relations with an null spec", 1, atomicProdSpec.getProdSpecRelationship().size());

		try{
			atomicProdSpec.addRelatedProdSpec(atomicProdSpecTwo, "", validFor);
			assertEquals("if type of relationship is null can success to add", 1, atomicProdSpec.getProdSpecRelationship().size());
			fail("type is empty, no check");
		}catch (Exception e){

		}
	}


	public static ProductSpecification getProdSpec() {
		return prodSpec;
	}

	public static void setProdSpec(ProductSpecification prodSpec) {
		ProductSpecificationTest.prodSpec = prodSpec;
	}
}
