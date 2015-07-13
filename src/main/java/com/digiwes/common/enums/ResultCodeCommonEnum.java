package com.digiwes.common.enums;

/**
 * Created by dongwh on 2015-7-13.
 */
public class ResultCodeCommonEnum {

    public enum ResultCodeCommon{
        SUCCESS("0","success"),
        VALID_FOR("1","validFor is null. ");
        private String value;
        private String name;
        ResultCodeCommon(String value,String name){
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
            for(ResultCodeCommon resultCode : ResultCodeCommon.values()){
                if(resultCode.getName().equals(name)){
                    return resultCode.getValue();
                }
            }
            return null;
        }
    }
}
