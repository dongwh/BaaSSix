package com.digiwes.common.enums;

/**
 * Created by dongwh on 2015-7-13.
 */
public class ResultCodeSpecEnum {

    public enum ResultCodeSpec{
        PRODUCT_SPECIFICATION("1001","object of ProductSpecification is null. "),
        PRODUCT_SPEC_CHAR_USE("1002","object of ProductSpecCharUse is null. "),
        PRODUCT_SPEC_CHAR_VALUE_USE("1003","object of ProductSpecCharValueUse is null. "),
        PRODUCT_SPEC_CHARACTERISTIC("1004","object of ProductSpecCharacteristic is null. "),
        PRODUCT_SPEC_CHARACTERISTIC_VALUE("1005","object of ProductSpecCharacteristic is null. "),
        NAME("2001","name is null. "),
        PRODUCT_NUMBER("2002","productNumber is null. "),
        PRODUCT_SPEC_CHAR_ALREADY_BE_USED("2003","this productSpecChar has been used. "),
        PRODUCT_VERSION_TYPE("2004","versionType is null. "),
        PRODUCT_CURRENT_VERSION("2005","currentVersion is null. "),
        PRODUCT_SPECIFICATION_VERSION_ALREADY_BE_USED("2006","this productSpecChar has been used. "),
        PRODUCT_SPECIFICATION_VERSION_SIZE("2007","version size is error, version size should be "),
        PRODUCT_SPECIFICATION_RELATIONSHIP_TYPE("2008","type of ProductSpecificaitonRelationship is null. "),
        PRODUCT_SPECIFICATION_ALREADY_BE_USED("2009","this productSpecification has been used. "),
        PRODUCT_SPECIFICATION_VALIDFOR("2010","validFor of productSpecification is error. ");
        private String value;
        private String code;
        ResultCodeSpec(String value,String code){
            this.value = value;
            this.code = code;
        }
        public String getValue(){
            return this.value;
        }
        public String getCode(){
            return this.code;
        }

        public String getValueByCode(String code){
            for(ResultCodeSpec resultCode : ResultCodeSpec.values()){
                if(resultCode.getCode().equals(code)){
                    return resultCode.getValue();
                }
            }
            return null;
        }
    }
}
