package com.digiwes.product.control.persistence;

import com.digiwes.product.offering.ProductOffering;

import java.util.List;

/**
 * Created by Nisx on 2015/7/9.
 */
public interface ProductOfferingPersistence {

    public void save(ProductOffering offering) throws Exception;

    public List<ProductOffering> retrieveOffering(String offerName) throws Exception;

    public ProductOffering load(String id) throws Exception;
}