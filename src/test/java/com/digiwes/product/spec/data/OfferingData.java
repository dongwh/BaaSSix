package com.ai.baas.product.spec.data;

import com.ai.baas.basetype.TimePeriod;
import com.ai.baas.product.offering.ProductOffering;
import com.ai.baas.product.offering.SimpleProductOffering;
import com.ai.baas.product.offering.price.ProductOfferingPrice;
import com.ai.digiwes.product.control.persistence.PersistenceFactory;
import org.apache.log4j.Logger;

/**
 * Created by Nisx on 2015/7/10.
 */
public class OfferingData {
    private static  Logger logger= Logger.getLogger(OfferingData.class);

    //offerId, OfferName, SpecId
    private final static int OFFER_ID = 0;
    private final static int OFFER_NAME = 1;
    private final static int OFFER_SPEC_ID = 2;
    private static  String[][] offeringData = {
            {"off_1", "11-inch MacBook Air", "spec_1"},
            {"off_2", "13-inch MacBook Air", "spec_2"},
    };
    //offerID, price_id
    private final  static int OFFER_PRICE_OFFER_ID = 0;
    private final  static int OFFER_PRICE_PRICE_ID = 1;
    private static String[][] offeringPrice = {
            {"off_1", "price_1"},
            {"off_1", "price_2"},
            {"off_2", "price_3"},
            {"off_2", "price_4"},
    };
    private static TimePeriod validFor = TimeUtil.creatTimePeriod("2015-06-01 00:00:00", "2015-08-01 00:00:00");
    private static  Boolean isInit = false;
    public static synchronized void init() {
        if (!isInit) {
            try {
                initOffering();
            } catch (Exception e) {
                logger.error("Offering init error.", e);
            }
            isInit = true;
        }
    }
    public static void initOffering() throws Exception {
        for (String[] offeringItem : offeringData) {
            ProductOffering offer = new SimpleProductOffering(offeringItem[OFFER_ID],
                    offeringItem[OFFER_NAME],
                    offeringItem[OFFER_NAME],
                    validFor,
                    SpecData.getSpec(offeringItem[OFFER_SPEC_ID]));
            PersistenceFactory.getProdOfferingPersistence().save(offer);
        }
        //specifyPrice
        specifyPrice();
    }

    private static void specifyPrice() throws Exception {
        //specify price
        for (String[] item : offeringPrice) {
            ProductOffering offer = PersistenceFactory.getProdOfferingPersistence().load(item[OFFER_PRICE_OFFER_ID]);
            ProductOfferingPrice price = PriceData.getPrice(item[OFFER_PRICE_PRICE_ID]);
            if (null != offer && null != price) {
                offer.addPrice(price);
            }
        }
    }
}
