package com.ai.digiwes.product.control.persistence;

import com.ai.baas.product.offering.catalog.ProductCatalog;

import java.util.List;

/**
 * Created by Nisx on 2015/7/9.
 */
public interface CatalogPersistence {

    public void save(ProductCatalog catalog) throws Exception;

    public List<ProductCatalog> retrieveCatalog(String catalogName) throws Exception;

    public ProductCatalog load(String id) throws Exception;
}
