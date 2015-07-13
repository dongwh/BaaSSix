package com.digiwes.common.enums;

/**
 * Created by dongwh on 2015-7-13.
 */
public class ResultCodeCommonEnum {

    public enum ResultCodeCommon{
        SUCCESS("0","success"),
        VALID_FOR("1","validFor is null. "),
        TIME("2","time is null"),
        SHOULD_BE_LESS_THAN("3"," should be less than ");
        private String value;
        private String code;
        ResultCodeCommon(String value,String code){
            this.value = value;
            this.code = code;
        }
        public String getValue(){
            return this.value;
        }
        public String getCode(){
            return this.code;
        }

        public String getValueByCode (String code){
            for(ResultCodeCommon resultCode : ResultCodeCommon.values()){
                if(resultCode.getCode().equals(code)){
                    return resultCode.getValue();
                }
            }
            return null;
        }
    }
}
