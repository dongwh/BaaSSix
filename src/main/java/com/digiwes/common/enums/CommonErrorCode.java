package com.digiwes.common.enums;

/**
 * Created by zhaoyp on 2015/7/14.
 */
public enum  CommonErrorCode {

    SUCCESS(0,"SUCCESS");

    private  int code;
    private String message;

   CommonErrorCode(int code,String message){
        this.code = code;
        this.message = message;
   }
   public int getCode(){
        return this.code;
   }
   public String getMessage(){
        return this.message;
   }
   public String getMessageByCode(int code){
        for(CommonErrorCode errorCode : CommonErrorCode.values()){
            if (errorCode.getCode() == code ) {
                return errorCode.getMessage();
            }
        }
        return null;
   }
}
