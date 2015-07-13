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
        PRODUCT_NUMBER("2002","productNumber is null. ");
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
