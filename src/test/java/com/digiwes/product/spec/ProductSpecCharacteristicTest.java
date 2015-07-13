package com.ai.baas.product.spec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ai.baas.basetype.TimePeriod;
import com.ai.baas.common.enums.ProdSpecEnum;
import com.ai.baas.common.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductSpecCharacteristicTest {
	private static final Logger logger = Logger.getLogger(ProductSpecCharacteristicTest.class);
	private ProductSpecCharacteristic prodSpecChar;
    private ProductSpecCharacteristic configSpecChar;

    private static TimePeriod validFor;
	@BeforeClass
	public static void initVliadFor(){
		validFor = new TimePeriod("2015-06-01  00:00:00","2015-08-01  23:59:59");
	}
	@Before
	public void createProductSpecCharacteristic(){
        prodSpecChar = new ProductSpecCharacteristic("1", "height", ProdSpecEnum.ProdSpecType.NUMERIC.getName(), validFor, "false",  1,  1, true, "height","");
        configSpecChar=new ConfigurableProductSpecCharacteristic("2", "Memory", ProdSpecEnum.ProdSpecType.NUMERIC.getName(),validFor, "unique",1,1);
        ProductSpecCharacteristicValue value=new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(),"GHz",validFor,"2.7",false);
        configSpecChar.addValue(value);
        value=new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(),"GHz",new TimePeriod("2015-05-03 12:00:00","2015-07-21 23:59:59"),"2.9",true);
        configSpecChar.addValue(value);
    }

	@Test
	public void  testAddValue(){
        boolean result = false;
        ProductSpecCharacteristicValue prodSpecCharValue=null;
        try{
            result=prodSpecChar.addValue(prodSpecCharValue);
            fail("add a empty value， expected IllegalArgumentException for value");
        }catch(IllegalArgumentException ex){

        }
        try{
            prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.TEXT.getName(), "GHz", validFor, "8", "", "");
            result=prodSpecChar.addValue(prodSpecCharValue);
            fail("The value type of Character and CharacterValue value is different,expected IllegalArgumentException for valueType");
        }catch(IllegalArgumentException ex){
        }
        prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "GHz", validFor, "8",true);
        result=prodSpecChar.addValue(prodSpecCharValue);
        assertEquals("add a normal value",true,result);
        assertEquals("add a normal value",true,prodSpecChar.getProdSpecCharValue().contains(prodSpecCharValue));
        assertEquals("add a normal value", 1, prodSpecChar.getProdSpecCharValue().size());

        result= prodSpecChar.addValue(prodSpecCharValue);
        assertEquals("Add a duplicate value",true,result);
        assertEquals("Add a duplicate value",1,prodSpecChar.getProdSpecCharValue().size());


        prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "GHz", validFor, "8",true);
        result= prodSpecChar.addValue(prodSpecCharValue);
        assertEquals("Add a duplicate value ,Values are the same as before. ",true,result);
        assertEquals("Add a duplicate value ,Values are the same as before. ", 1, prodSpecChar.getProdSpecCharValue().size());

	} 
	@Test
	public void  testRemoveValue(){
        boolean result = false;
        ProductSpecCharacteristicValue prodSpecCharValue = null;
        try{
            prodSpecChar.removeValue(prodSpecCharValue);
            fail("add a empty value， expected IllegalArgumentException for value");
        }   catch (IllegalArgumentException ex){

        }
        prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "GHz", validFor, "8",true);
        result=prodSpecChar.removeValue(prodSpecCharValue);
        assertEquals("the current char have no value",true,result);
        assertEquals("the current char have no value",null,prodSpecChar.getProdSpecCharValue());

        prodSpecChar.addValue(prodSpecCharValue);
        result=prodSpecChar.removeValue(prodSpecCharValue);
        assertEquals("value is belong of Char", true, result);
        assertEquals("value is belong of Char", false, prodSpecChar.getProdSpecCharValue().contains(prodSpecCharValue));

        prodSpecCharValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "mi", validFor, "10",true);
        result=prodSpecChar.removeValue(prodSpecCharValue);
        assertEquals("value not  belong of Char", true, result);
        assertEquals("value not belong of Char",false,prodSpecChar.getProdSpecCharValue().contains(prodSpecCharValue));

	}
    @Test
    public void testRetrieveValue() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date time=null;
        List<ProductSpecCharacteristicValue> prodSpecCharValues=null;
        try {
                       prodSpecCharValues=prodSpecChar.retrieveValue(time);
            fail("query the value of Charistist:time is null, expected IllegalArgumentException for time");

        } catch (IllegalArgumentException ex) {
        }

        time=new Date();
        prodSpecCharValues=prodSpecChar.retrieveValue(time);
        assertEquals("query the value of CharististValue:No value of the Characteristic",0, prodSpecCharValues.size());

        prodSpecCharValues=configSpecChar.retrieveValue(time);
        assertEquals("query the value of Charistist:(Time points in two time periods.)",2,prodSpecCharValues.size());

        prodSpecCharValues=configSpecChar.retrieveValue(format.parse("2015-07-22 12:00:00"));
        ProductSpecCharacteristicValue value=new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(),"GHz",new TimePeriod("2015-05-03 12:00:00","2015-07-21 23:59:59"),"2.9",true);
        assertEquals("query the value of Charistist:(Time points in on time periods.)",1,prodSpecCharValues.size());
        assertEquals("query the value of Charistist:(Time points in on time periods.)",true,prodSpecCharValues.contains(value));

        prodSpecCharValues=configSpecChar.retrieveValue(format.parse("2015-01-03 12:00:00"));
        assertEquals("query the value of Charistist:(Time points not in on time periods.)",0,prodSpecCharValues.size());
    }

	@Test
	public void  testAddRelatedCharacteristic(){
        ProductSpecCharacteristic targetChar = null;
        boolean result = false;
        try {
            prodSpecChar.addRelatedCharacteristic(targetChar, ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getName(), validFor);
            fail("add a empty targetProdSpec ,expected IllegalArgumentException for targetProdSpec");
        } catch (IllegalArgumentException ex){

        }
        try {
            targetChar = new ProductSpecCharacteristic("2", "Size and weight", ProdSpecEnum.ProdSpecType.NUMERIC.getName(), validFor, "true",  1,  1, true, "compistchar","");
             result=prodSpecChar.addRelatedCharacteristic(targetChar, null, validFor);
             fail("add a empty type ,expected IllegalArgumentException for targetProdSpec");
        } catch (IllegalArgumentException ex){

        }
        result=prodSpecChar.addRelatedCharacteristic(prodSpecChar,ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getName(), validFor);
        assertEquals("add Related SpecChar:The srcChar is the same as the targetChar.",false, result);
        assertEquals("add Related SpecChar:The srcChar is the same as the targetChar.",null, prodSpecChar.getProdSpecCharRelationship());

        result=prodSpecChar.addRelatedCharacteristic(targetChar, ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getValue(), validFor);
        assertEquals("add Related SpecChar",true, result);
        assertEquals("add Related SpecChar.",1, prodSpecChar.getProdSpecCharRelationship().size());
        assertEquals("add Related SpecChar.",targetChar,prodSpecChar.getProdSpecCharRelationship().iterator().next().getTargetProdSpecChar());

        result=prodSpecChar.addRelatedCharacteristic(targetChar, ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getValue(), validFor);
        assertEquals("add duplicat Relationship",false, result);
        assertEquals("add duplicat Relationship.",1, prodSpecChar.getProdSpecCharRelationship().size());

        result=prodSpecChar.addRelatedCharacteristic(targetChar, ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getValue(), new TimePeriod("2015-01-01 00:00:00","2015-01-29 23:59:59"));
        assertEquals("add Related SpecChar：have create a aggregation relationship (time before )",true, result);
        assertEquals("add Related SpecChar.",2, prodSpecChar.getProdSpecCharRelationship().size());

        result=prodSpecChar.addRelatedCharacteristic(targetChar, ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getValue(), new TimePeriod("2015-01-01 00:00:00","2015-06-29 23:59:59"));
        assertEquals("add Related SpecChar：have create a aggregation relationship (time in period  )",false, result);
        assertEquals("add Related SpecChar.",2, prodSpecChar.getProdSpecCharRelationship().size());

        result=prodSpecChar.addRelatedCharacteristic(targetChar, ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getValue(), new TimePeriod("2015-09-01 23:59:59","2015-10-29 23:59:59"));
        assertEquals("add Related SpecChar：have create a aggregation relationship (time after  )",true, result);
        assertEquals("add Related SpecChar.",3, prodSpecChar.getProdSpecCharRelationship().size());

        result=prodSpecChar.addRelatedCharacteristic(targetChar, ProdSpecEnum.ProdSpecRelationship.DEPENDENCY.getValue(), validFor);
        assertEquals("add Related SpecChar:have create other relationship", false, result);
        assertEquals("add Related SpecChar：have create other relationship",3, prodSpecChar.getProdSpecCharRelationship().size());
        assertEquals("add Related SpecChar：have create other relationship", targetChar, prodSpecChar.getProdSpecCharRelationship().iterator().next().getTargetProdSpecChar());
        assertEquals("add Related SpecChar：have create other relationship",ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getValue(),prodSpecChar.getProdSpecCharRelationship().iterator().next().getCharRelationshipType());

    }

	@Test 
	public void testSpecifyDefaultValue(){
        boolean result=false;
        ProductSpecCharacteristicValue prodSpecCharValue=null;
        try{
            prodSpecChar.specifyDefaultValue(prodSpecCharValue);
            fail("add empty value ,expected IllegalArgumentException for value");
        }   catch (IllegalArgumentException ex){

        }
        prodSpecCharValue = new ProductSpecCharacteristicValue("1", "GHz", validFor, "8",false);
		result = prodSpecChar.specifyDefaultValue(prodSpecCharValue);
		assertEquals("no value of the current char", false, result);
        assertEquals("no value of the current char", 0, prodSpecChar.retrieveDefaultValue().size());

        prodSpecCharValue =  new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "GHz", validFor, "9",false);
        result=prodSpecChar.specifyDefaultValue(prodSpecCharValue);
        assertEquals("value not belong of the char",false,result);
        assertEquals("value not belong of the char", false, prodSpecChar.retrieveDefaultValue().contains(prodSpecCharValue));

        prodSpecChar.addValue(prodSpecCharValue);
        result=prodSpecChar.specifyDefaultValue(prodSpecCharValue);
		assertEquals("Char does not exist default values", true, prodSpecChar.retrieveDefaultValue().contains(prodSpecCharValue));
        assertEquals("Char does not exist default values", 1, prodSpecChar.retrieveDefaultValue().size());

        prodSpecCharValue =  new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "GHz", validFor, "10",false);
        prodSpecChar.addValue(prodSpecCharValue);
        result= prodSpecChar.specifyDefaultValue(prodSpecCharValue);
        assertEquals("the current is not the defaultValue( has one default values)", true, result);
        assertEquals("the current is not the defaultValue( has one default values)", true, prodSpecChar.retrieveDefaultValue().contains(prodSpecCharValue));
        assertEquals("the current is not the defaultValue( has one default values)",2, prodSpecChar.retrieveDefaultValue().size());
        prodSpecCharValue =  new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "GHz", validFor, "10",false);

        result= prodSpecChar.specifyDefaultValue(prodSpecCharValue);
        assertEquals("the current value is  the default Value", true, result);
        assertEquals("the current value is  the default Value", true, prodSpecChar.retrieveDefaultValue().contains(prodSpecCharValue));
        assertEquals("the current value is  the default Value",2, prodSpecChar.retrieveDefaultValue().size());
    }
    @Test
    public void testRetrieveDefaultValue(){

        List<ProductSpecCharacteristicValue> defaultCharValue=prodSpecChar.retrieveDefaultValue();
        assertEquals("retriveve the default value ：Char does not exist  values", 0, defaultCharValue.size());

        ProductSpecCharacteristicValue value  =  new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "GHz", validFor, "10",false);
        prodSpecChar.addValue(value);
        defaultCharValue=prodSpecChar.retrieveDefaultValue();
        assertEquals("retriveve the default value ：Char does not exist  default values", 0, defaultCharValue.size());
        assertEquals("retriveve the default value ：Char does not exist  default values",false,defaultCharValue.contains(value));

        defaultCharValue=configSpecChar.retrieveDefaultValue();
        assertEquals("retriveve the default value ：Default values exist of  Char ",1,defaultCharValue.size());

    }
    @Test
    public void testClearDefaultValue(){
        boolean result = false;
        ProductSpecCharacteristicValue value =null;
        try{
            result=prodSpecChar.clearDefaultValue(value);
            fail("clear the default Value of Char ,but the value is null,expected IllegalArgumentException for value");
        }catch(IllegalArgumentException ex){
        }

        value  =  new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(), "GHz", validFor, "10",false);
        result=prodSpecChar.clearDefaultValue(value);
        assertEquals("No value of the Characteristic",false,result);
        assertEquals("No value of the Characteristic",null,prodSpecChar.getProdSpecCharValue());
        assertEquals("No value of the Characteristic", false, prodSpecChar.retrieveDefaultValue().contains(value));

        result = configSpecChar.clearDefaultValue(value);
        assertEquals("clear default value:  Values do not belong to this char",false,result);
        assertEquals("clear default value:  Values do not belong to this char",false,configSpecChar.getProdSpecCharValue().contains(value));
        assertEquals("clear default value:  Values do not belong to this char", false, configSpecChar.retrieveDefaultValue().contains(value));

        prodSpecChar.addValue(value);
        result=prodSpecChar.clearDefaultValue(value);
        assertEquals("No Default value of the Characteristic", true, result);
        assertEquals("No Default value of the Characteristic", false, prodSpecChar.retrieveDefaultValue().contains(value));

        value=new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(),"GHz",validFor,"2.7",false);
        result=configSpecChar.clearDefaultValue(value);
        assertEquals("Value is not the default value",true,result);
        assertEquals("Value is not the default value",false,configSpecChar.retrieveDefaultValue().contains(value));

        value=new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.NUMERIC.getName(),"GHz",new TimePeriod("2015-05-03 12:00:00","2015-07-21 23:59:59"),"2.9",true);
        configSpecChar.addValue(value);
        result=configSpecChar.clearDefaultValue(value);

        assertEquals("clear default value: Value is  the default value",true,result);
        assertEquals("clear default value: Value is  the default value",false,configSpecChar.retrieveDefaultValue().contains(value));
    }
     @Test
    public void testUpdateRelatedCharValidPeriod()  {
        boolean result = false;
        ProductSpecCharacteristic targetSpecChar = null;
        TimePeriod newValidFor =new TimePeriod("2015-01-01 00:00:00","2015-03-03 23:59:59");

        try{
            result=prodSpecChar.updateRelatedCharValidPeriod(targetSpecChar,validFor,newValidFor );
            fail("update validPeriod of the relationship:this prodSpecChar is null,excepted IllegalArgumentException for prodSpecChar");
        }catch(IllegalArgumentException ex){
        }

        targetSpecChar = new ProductSpecCharacteristic("2", "Size and weight", ProdSpecEnum.ProdSpecType.NUMERIC.getName(), validFor, "true",  1,  1, true, "compistchar","");
        result= prodSpecChar.updateRelatedCharValidPeriod(targetSpecChar,validFor,newValidFor) ;
        assertEquals("update validPeriod of the relationship: not exists the relationship",false,result);

        prodSpecChar.addRelatedCharacteristic(targetSpecChar, ProdSpecEnum.ProdSpecRelationship.AGGREGATION.getName(), validFor);
        result= prodSpecChar.updateRelatedCharValidPeriod(targetSpecChar,validFor,newValidFor);
        assertEquals("update validPeriod of the relationship",true,result);
        assertEquals("update validPeriod of the relationship",newValidFor,prodSpecChar.getProdSpecCharRelationship().iterator().next().getValidFor());

    }
}
