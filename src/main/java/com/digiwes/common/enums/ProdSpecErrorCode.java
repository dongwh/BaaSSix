package com.digiwes.common.enums;

import com.digiwes.product.spec.ProdSpecCharValueUse;

/**
 * Created by zhaoyp on 2015/7/14.
 */
public enum ProdSpecErrorCode{
    PROD_SPEC_NAME_IS_NULL(1001,"name is null"),
    PROD_SPEC_NAME_IS_EMPTY(1002,"name is empty"),
    PROD_SPEC_IS_NULL(1004,"productSpecification is null"),
    PROD_SPEC_RELATIONSHIP_TYPE_IS_NULL(1003,"relationshipType is null"),
    PROD_SPEC_EQUALS_TO_CURRENT(1005,"the designated productSpecification equal to current value"),
    PROD_SPEC_HAS_RELATED_TO_CURRENT(1006,"the designated productSpecification equal to current value");


    private  int code;
    private String message;
    private ProdSpecErrorCode(int code,String message){
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
