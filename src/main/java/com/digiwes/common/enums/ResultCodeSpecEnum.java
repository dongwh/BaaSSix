package com.digiwes.common.enums;

/**
 * Created by dongwh on 2015-7-13.
 */
public class ResultCodeSpecEnum {

    public enum ResultCodeSpec{
        PRODUCT_SPECIFICATION("1001","object of ProductSpecification is null. "),
        PRODUCT_SPEC_CHAR_USE("1002","object of ProductSpecification is null. "),
        PRODUCT_SPEC_CHAR_VALUE_USE("1003","object of ProductSpecification is null. "),
        PRODUCT_SPEC_CHARACTERISTIC("1004","object of ProductSpecification is null. "),
        PRODUCT_SPEC_CHARACTERISTIC_VALUE("1005","object of ProductSpecification is null. "),
        NAME("2001","name is null. "),
        PRODUCT_NUMBER("2002","productNumber is null. ");
        private String value;
        private String name;
        ResultCodeSpec(String value,String name){
            this.value = value;
            this.name = name;
        }
        public String getValue(){
            return this.value;
        }
        public String getName(){
            return this.name;
        }

        public String getValueByName(String name){
            for(ResultCodeSpec resultCode : ResultCodeSpec.values()){
                if(resultCode.getName().equals(name)){
                    return resultCode.getValue();
                }
            }
            return null;
        }
    }
}
