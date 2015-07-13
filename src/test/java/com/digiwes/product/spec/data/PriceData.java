package com.digiwes.product.spec.data;
import com.digiwes.basetype.TimePeriod;
import com.digiwes.product.offering.price.ComponentProductPrice;
import com.digiwes.product.offering.price.ProductOfferingPrice;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nisx on 2015/7/10.
 */
public class PriceData {
    private static final int PRICE_ID = 0;
    private static final int PRICE_NAME = 1;
    private static final int PRICE_PRICE = 2;
    private static String[][] priceData = {
            {"price_1", "price_899", "$899"},
            {"price_2", "price_1099", "$1099"},
            {"price_3", "price_999", "$999"},
            {"price_4", "price_1199", "$1199"},
    };
    private static Map<String,ProductOfferingPrice> priceMap = new HashMap<String,ProductOfferingPrice>();
    private static  Boolean isInit = false;
    public static synchronized void init() {
        if (!isInit) {
            createPriceMap();
            isInit = true;
        }
    }
    public static ProductOfferingPrice getPrice(String id) {
        if (!isInit) {
            init();
        }
        return priceMap.get(id);
    }
    private static void createPriceMap() {
        TimePeriod validFor = TimeUtil.creatTimePeriod("2015-06-01 00:00:00", "2015-08-01 00:00:00");
        for (String[] item : priceData) {
            ProductOfferingPrice price = new ComponentProductPrice(item[PRICE_NAME],validFor, item[PRICE_PRICE]);
            priceMap.put(item[PRICE_ID], price);
        }
    }
}
