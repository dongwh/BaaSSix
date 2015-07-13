package com.ai.util;

import com.ai.baas.basetype.TimePeriod;
import com.ai.baas.common.enums.ProdSpecEnum;
import com.ai.baas.product.spec.ProductSpecCharacteristic;
import com.ai.baas.product.spec.ProductSpecCharacteristicValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhaoyp on 2015/7/9.
 */
public class ConvertMap2Json {
    private static final String QUOTE = "\"";

    /**
     * e.g.
     * {
     * 		"eventName": "name"
     * }
     *
     * @param body
     * @param tabCount
     * @param addComma
     * @return
     */
    public static String buildJsonBody(Map<String, Object> body, int tabCount, boolean addComma)
    {
        StringBuilder sbJsonBody = new StringBuilder();
        sbJsonBody.append("{\n");
        Set<String> keySet = body.keySet();
        int count = 0;
        int size = keySet.size();
        for (String key : keySet)
        {
            count++;
            sbJsonBody.append(buildJsonField(key, body.get(key), tabCount + 1, count != size));
        }
        sbJsonBody.append(getTab(tabCount+1));
        sbJsonBody.append("\t}");
        return sbJsonBody.toString();
    }

    /**
     * e.g.
     * "eventName": "aaa"
     *
     * @param key
     * @param value
     * @param tabCount
     * @param addComma
     * @return
     */
    private static String buildJsonField(String key, Object value, int tabCount, boolean addComma)
    {
        StringBuilder sbJsonField = new StringBuilder();
        sbJsonField.append(getTab(tabCount));
        sbJsonField.append("\t").append(QUOTE).append(key).append(QUOTE).append(": ");
        sbJsonField.append(buildJsonValue(value, tabCount, addComma));
        return sbJsonField.toString();
    }

    /**
     * e.g.
     * "string"
     * {
     * 		"key": "value"
     * }
     *
     * @param value
     * @param tabCount
     * @param addComma
     * @return
     */
    private static String buildJsonValue(Object value, int tabCount, boolean addComma)
    {
        StringBuilder sbJsonValue = new StringBuilder();
        if( null == value){
            sbJsonValue.append(QUOTE).append("null").append(QUOTE);
        }   else  if (value instanceof String) {
            sbJsonValue.append(QUOTE).append(value).append(QUOTE);
        }
        else if (value instanceof Integer || value instanceof Long || value instanceof Double)
        {
            sbJsonValue.append(value);
        }
        else if (value instanceof java.util.Date)
        {
            sbJsonValue.append(QUOTE).append(formatDate((java.util.Date) value)).append(QUOTE);
        }
        else if (value.getClass().isArray() || value instanceof java.util.Collection ||value instanceof  Set) {
            sbJsonValue.append(buildJsonArray(value, tabCount, addComma));
        }
        else if (value instanceof Map)
        {
            sbJsonValue.append(buildJsonBody((Map) value, tabCount, addComma));
        } else if(value instanceof  ProductSpecCharacteristic){
            sbJsonValue.append(QUOTE).append(((ProductSpecCharacteristic) value).toString()).append(QUOTE);;
        } else if(value instanceof ProductSpecCharacteristicValue){
            sbJsonValue.append(QUOTE).append(((ProductSpecCharacteristicValue) value).toString()).append(QUOTE);

        }
        sbJsonValue.append(buildJsonTail(addComma));
        return sbJsonValue.toString();
    }

    /**
     * [
     * 		"value"
     * ]
     * [
     * 		{
     * 			"key": "value"
     * 		}
     * ]
     *
     * @param value
     * @param tabCount
     * @param addComma
     * @return
     */
    private static String buildJsonArray(Object value, int tabCount, boolean addComma)
    {
        StringBuilder sbJsonArray = new StringBuilder();
        sbJsonArray.append("[\n");
        sbJsonArray.append(getTab(tabCount));
        Object[] objArray = null;
        if (value.getClass().isArray())
        {
            objArray = (Object[]) value;
        }
        else if (value instanceof java.util.Collection)
        {
            objArray = ((java.util.Collection) value).toArray();
        }else if(value instanceof Set){
            objArray = ((Set) value).toArray();
        }
        int size = objArray.length;
        int count = 0;
        for (Object obj : objArray)
        {
            sbJsonArray.append(getTab(tabCount));
            sbJsonArray.append(buildJsonValue(obj, tabCount, true));
            sbJsonArray.append(getTab(tabCount));

        }
        sbJsonArray.append(getTab(tabCount));
        sbJsonArray.append("]");
        return sbJsonArray.toString();
    }

    /**
     *
     * @param count
     * @return
     */
    private static String getTab(int count)
    {
        StringBuilder sbTab = new StringBuilder();
        while (count-- > 0)
        {
            sbTab.append("\t");
        }
        return sbTab.toString();
    }

    /**
     *
     * @param addComma
     * @return
     */
    private static String buildJsonTail(boolean addComma)
    {
        return addComma ? ",\n" : "\n";
    }

    /**
     *
     * @param date
     * @return
     */
    private static String formatDate(java.util.Date date)
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
    public static void main(String[] args)throws ParseException{
        Map<String,Object> map=new HashMap<String, Object>();
        TimePeriod validFor =new TimePeriod(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-07-8 23:59:59"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-08-01 23:59:59"));
        ProductSpecCharacteristic specChar = new ProductSpecCharacteristic("1", "color", "1", validFor, "unique", 1, 3, false, "description", "derivationFormula");
        ProductSpecCharacteristicValue charValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.TEXT.getValue(), "GBK", validFor, "red", false);
        specChar.addValue(charValue);
        charValue = new ProductSpecCharacteristicValue(ProdSpecEnum.ProdSpecType.TEXT.getValue(), "GBK", validFor, "blu", false);
        specChar.addValue(charValue);
        System.out.println(specChar.toString()) ;

    }
}
