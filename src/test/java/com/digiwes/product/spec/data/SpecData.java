package com.digiwes.product.spec.data;

import com.digiwes.basetype.TimePeriod;
import com.digiwes.product.spec.AtomicProductSpecification;
import com.digiwes.product.spec.ProductSpecCharacteristic;
import com.digiwes.product.spec.ProductSpecCharacteristicValue;
import com.digiwes.product.spec.ProductSpecification;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Nisx on 2015/7/10.
 */
public class SpecData {
    //SpecId, productNumber, name, brand, description
    private static final int S_SPEC_ID_INDEX = 0;
    private static final int S_SPEC_NUMBER_INDEX = 1;
    private static final int S_SPEC_NAME_INDEX = 2;
    private static final int S_SPEC_BRAND_INDEX = 3;
    private static final int S_SPEC_DES_INDEX = 4;
    private static  String[][] simpleSpecData = {
            {"spec_1","1", "11 Ó¢´ç MacBook Air", "apple", "Mac"},
            {"spec_2","2", "13 Ó¢´ç MacBook Air", "apple", "Mac"},
    };
    //useId,specId, charId, charName,
    private static final int USECHAR_USE_ID = 0;
    private static final int USECHAR_SPEC_ID = 1;
    private static final int USECHAR_CHAR_ID = 2;
    private static final int USECHAR_CHAR_NAME = 3;
    private static String[][] specUseChar = {
            {"uc1","spec_1","1", "CPU"},
            {"uc2","spec_1","2", "Size and weight"},
            {"uc3","spec_1","3","high"},
            {"uc4","spec_1","4", "width"}
    };

    //useId, valueId, defaultFlag
    private static final int USEVALUE_USE_ID = 0;
    private static final int USEVALUE_VALUE_ID = 1;
    private static final int USEVALUE_DEFAULT_FLAG = 2;
    private static String[][] useValue = {
            {"uc1","11","true"},
            {"uc1","12","false"},
            {"uc3", "31", "tue"},
            {"uc4", "41", "tue"},
    };


    private static Map<String, ProductSpecification> specMap = new HashMap<String, ProductSpecification>();
    private static Map<String, Map<String, Object>> valueMap;
    private static TimePeriod validFor;
    private static  Boolean isInit = false;
    private static Logger logger= Logger.getLogger(SpecData.class);

    public static synchronized void init() {
        validFor =  TimeUtil.creatTimePeriod("2015-06-01 00:00:00", "2015-08-01 00:00:00");
        if (!isInit) {
            try {
                createProdSpec();
            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
            }
            isInit = true;
        }
    }

    private static Map<String, List<Map>> getValueUseMap() {
        Map<String, List<Map>> valueUseMap = new HashMap<String, List<Map>>();
        for (String[] useValueItem : useValue) {
            List<Map> useValueList = valueUseMap.get(useValueItem[USEVALUE_USE_ID]);
            if (null == useValueList) {
                useValueList = new ArrayList<Map>();
            }
            Map useValueData = new HashMap();
            useValueData.put("VALUE", SpecCharData.getValue(useValueItem[USEVALUE_VALUE_ID]));
            useValueData.put("DEFAULT", useValueItem[USEVALUE_DEFAULT_FLAG]);
            useValueList.add(useValueData);
        }
        return valueUseMap;
    }

    private static void  createProdSpec() throws Exception {

        for (String[] specItem : simpleSpecData) {
            ProductSpecification prodSpec = new AtomicProductSpecification(
                    specItem[S_SPEC_NUMBER_INDEX],
                    specItem[S_SPEC_NAME_INDEX],
                    specItem[S_SPEC_BRAND_INDEX],
                    specItem[S_SPEC_DES_INDEX],
                    validFor);

            //set version
            prodSpec.specifyVersion("1.0.0", "create a version", new Date(), validFor);

            specMap.put(specItem[S_SPEC_ID_INDEX], prodSpec);
        }
        //attach char and value
        attachCharAndValue();
    }

    private static void attachCharAndValue() {
        //make valueUse
        Map<String, List<Map>> valueUseMap = getValueUseMap();
        //attach Char
        for (String[] charUseItem : specUseChar ) {
            ProductSpecification spec = specMap.get(charUseItem[USECHAR_SPEC_ID]);
            ProductSpecCharacteristic selectedSpecChar = SpecCharData.getChar(charUseItem[USECHAR_CHAR_ID]);
            if (null != spec && null != selectedSpecChar) {
                spec.addCharacteristic(selectedSpecChar, charUseItem[USECHAR_CHAR_NAME],
                        false, true, validFor,
                        "", 1, 3, false,
                        "this is a description about size and weight used by CharUse");

                //attach value
                List<Map> useCharValueList = valueUseMap.get(charUseItem[USECHAR_USE_ID]);
                if (null != useCharValueList) {
                    for (Map item : useCharValueList) {
                        spec.attachCharacteristicValue(selectedSpecChar,
                                charUseItem[USECHAR_CHAR_NAME],
                                (ProductSpecCharacteristicValue)item.get("VALUE"),
                                Boolean.valueOf(item.get("DEFAULT").toString()),
                                validFor);
                    }
                }
            }
        }
    }

    public static ProductSpecification getSpec(String id) {
        if (!isInit) {
            init();
        }
        return specMap.get(id);
    }

}
