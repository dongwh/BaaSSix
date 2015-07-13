package com.ai.baas.product.spec.data;

import com.ai.baas.basetype.TimePeriod;
import com.ai.baas.product.offering.catalog.ProductCatalog;
import com.ai.digiwes.product.control.persistence.PersistenceFactory;
import org.apache.log4j.Logger;

/**
 * Created by Nisx on 2015/7/10.
 */
public class CatalogData {
    private static Logger logger = Logger.getLogger(CatalogData.class);
    private static TimePeriod validFor = TimeUtil.creatTimePeriod("2015-06-01", "2015-08-01");

    private final static int CATALOG_ID = 0;
    private final static int CATALOG_NAME = 0;
    private final static int CATALOG_TYPE = 0;
    private static String[][] catalogData = {
            {"catalog_1", "Mac", "Computer"},
            {"catalog_2", "iPhone", "Mobile"}
    };
    private static  Boolean isInit = false;
    public static synchronized void init() {
        if (!isInit) {
            try {
                initCatalogData();
            } catch (Exception e) {
                logger.error("Catalog init error.", e);
            }
            isInit = true;
        }
    }
    private static void initCatalogData() throws Exception {
        for (String[] item : catalogData) {
            ProductCatalog productCatalog = new ProductCatalog(item[CATALOG_ID],
                    item[CATALOG_NAME],
                    item[CATALOG_TYPE],
                    validFor);
            PersistenceFactory.getCatalogPersistence().save(productCatalog);
        }
    }
    public static ProductCatalog getCatalog(String id) throws Exception {
        if (!isInit) {
            init();
        }
        return PersistenceFactory.getCatalogPersistence().load(id);
    }
}
