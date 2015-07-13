package com.digiwes.product.control.persistence;


import com.digiwes.product.control.persistence.impl.CatalogPersistenceSimpleImpl;
import com.digiwes.product.control.persistence.impl.ProductOfferingPersistenceSimpleImpl;

/**
 * Created by Nisx on 2015/7/9.
 */
public class PersistenceFactory {

    public static CatalogPersistence getCatalogPersistence() {
        return new CatalogPersistenceSimpleImpl();
    }

    public static ProductOfferingPersistence getProdOfferingPersistence() {
        return new ProductOfferingPersistenceSimpleImpl();
    }
}
