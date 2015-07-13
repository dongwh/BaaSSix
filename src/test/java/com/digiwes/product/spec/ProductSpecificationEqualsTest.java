package com.digiwes.product.spec;

import com.digiwes.basetype.TimePeriod;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ProductSpecificationEqualsTest {

	private String productNumber;
    private String name;
    private String brand;
    private String description;
    private static TimePeriod validFor;
    private boolean rtnFlag;
    private String rtnMsg;

	//test prodSpec
	private  ProductSpecification atomicProdSpec;

	private static void setValidFor(){
		validFor = new TimePeriod("2015-06-01", "2015-08-01");
	}

	@Before
	public void setUp(){
		atomicProdSpec = new AtomicProductSpecification("1", "11 Pounds MacBook Air", "apple", "Mac", validFor);
	}

	@Parameters
	public static List data(){
		setValidFor();
		return  Arrays.asList(new Object[][]{
				{true, "两个特征是否完全一致1" ,"1", "11 Pounds MacBook Air", "apple", "Mac", validFor},
				{false, "两个特征是否完全一致2" ,"2", "13 Pounds MacBook Air", "apple", "Mac", validFor}
		});
	}

	public ProductSpecificationEqualsTest(boolean rtnFlag, String rtnMsg, String productNumber, String name, String brand, String description, TimePeriod validFor) {
		super();
		this.productNumber = productNumber;
		this.name = name;
		this.brand = brand;
		this.description = description;
		this.validFor = validFor;
		this.rtnFlag = rtnFlag;
		this.rtnMsg = rtnMsg;



	}

	@Test
	public void testEquals(){
		System.out.println("1111");
		ProductSpecification atomicProdSpecNew = new AtomicProductSpecification(productNumber, name, brand, description, validFor);
		boolean objectEquals = atomicProdSpec.equals(atomicProdSpecNew);
		assertEquals(rtnMsg,objectEquals,rtnFlag);

	}

}
