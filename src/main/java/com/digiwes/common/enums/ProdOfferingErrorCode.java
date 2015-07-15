package com.digiwes.common.enums;

/**
 * Created by zhaoyp on 2015/7/14.
 */
public enum ProdOfferingErrorCode {

    NAME_IS_NULL(2001,"name is null"),NAME_IS_EMPTY(2002,"name is empty");

    private  int code;
    private String message;
    private ProdOfferingErrorCode (int code,String message){
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
        for(ProdSpecErrorCode errorCode : ProdSpecErrorCode.values()){
            if (errorCode.getCode() == code ) {
                return errorCode.getMessage();
            }
        }
        return null;
    }
}
