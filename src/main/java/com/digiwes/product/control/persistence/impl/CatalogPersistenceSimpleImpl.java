package com.digiwes.product.control.persistence.impl;

import com.digiwes.product.control.persistence.CatalogPersistence;
import com.digiwes.product.offering.catalog.ProductCatalog;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nisx on 2015/7/9.
 */
public class CatalogPersistenceSimpleImpl implements CatalogPersistence {
    private static Logger logger =Logger.getLogger(CatalogPersistenceSimpleImpl.class);
    private static  final  String DATA_TYPE = "PROD_CATALOG";

    public void save(ProductCatalog catalog) throws Exception {
       PersistenceSimpleImpl.getInstance().save(DATA_TYPE, catalog);
    }
    private List<ProductCatalog> getAllCatalog() {
        List<ProductCatalog> productCatalogList = new ArrayList<ProductCatalog>();
        PersistenceSimpleImpl.getInstance().loadAll(DATA_TYPE,productCatalogList);
        return productCatalogList;
    }

    public List<ProductCatalog> retrieveCatalog(String catalogName) throws Exception {
        if (StringUtils.isEmpty(catalogName)) {
            logger.error("parameter [catalogName] can't be empty");
            throw new IllegalArgumentException("parameter [catalogName] can't be empty");
        }
        List<ProductCatalog> productCatalogList = getAllCatalog();
        List<ProductCatalog> resultList = new ArrayList<ProductCatalog>();
        for (ProductCatalog catalog : productCatalogList) {
            if (catalog.getName().contains(catalogName)) {
                resultList.add(catalog);
            }
        }
        return resultList;
    }

    public ProductCatalog load(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            logger.error("parameter [id] can't be empty");
            throw new IllegalArgumentException("parameter [id] can't be empty");
        }
        List<ProductCatalog> productCatalogList = getAllCatalog();
        for (ProductCatalog catalog : productCatalogList) {
            if ( catalog.getID().equals(id)) {
                return catalog;
            }
        }
        return null;
    }
}
