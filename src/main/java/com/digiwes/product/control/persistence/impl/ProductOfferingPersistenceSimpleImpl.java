package com.ai.digiwes.product.control.persistence.impl;

import com.ai.baas.product.offering.ProductOffering;
import com.ai.digiwes.product.control.persistence.ProductOfferingPersistence;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nisx on 2015/7/9
 */
public class ProductOfferingPersistenceSimpleImpl implements ProductOfferingPersistence {
    private static  final  String DATA_TYPE = "PROD_OFFERING";
    private static Logger logger =Logger.getLogger(ProductOfferingPersistenceSimpleImpl.class);
    private List<ProductOffering> getAllOffering() {
        List<ProductOffering> productOfferingListList = new ArrayList<ProductOffering>();
        PersistenceSimpleImpl.getInstance().loadAll(DATA_TYPE,productOfferingListList);
        return productOfferingListList;
    }

    public void save(ProductOffering offering) throws Exception {
        PersistenceSimpleImpl.getInstance().save(DATA_TYPE, offering);
    }

    public List<ProductOffering> retrieveOffering(String offerName) throws Exception {
        if (StringUtils.isEmpty(offerName)) {
            logger.error("parameter [offerName] can't be empty");
            throw new IllegalArgumentException("parameter [offerName] can't be empty");
        }
        List<ProductOffering> resultOfferingList = new ArrayList<ProductOffering>();
        List<ProductOffering> offeringList = getAllOffering();
        for (ProductOffering offering : offeringList) {
            if ( offering.getName().contains(offerName)) {
                resultOfferingList.add(offering);
            }
        }
        return resultOfferingList;
    }

    public ProductOffering load(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            logger.error("parameter [id] can't be empty");
            throw new IllegalArgumentException("parameter [id] can't be empty");
        }
        List<ProductOffering> offeringList = getAllOffering();
        for (ProductOffering offering : offeringList) {
            if ( offering.getId().equals(id)) {
                return offering;
            }
        }
        return null;
    }
}
