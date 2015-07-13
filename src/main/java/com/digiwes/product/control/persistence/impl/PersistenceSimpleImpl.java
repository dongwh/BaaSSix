package com.digiwes.product.control.persistence.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Nisx on 2015/7/9.
 */
public class PersistenceSimpleImpl {
    private Map<String,Set> dataMap;
    private static Logger logger =Logger.getLogger(PersistenceSimpleImpl.class);
    private static  PersistenceSimpleImpl instance = new PersistenceSimpleImpl();
    private PersistenceSimpleImpl() {
        dataMap = new HashMap<String, Set>();
    }
    public static PersistenceSimpleImpl getInstance() {
        return instance;
    }
    public void save(String dataType, Object data) throws Exception{
        if (StringUtils.isEmpty(dataType)) {
            logger.error("Data type can't be Empty");
            throw new IllegalArgumentException("Data type can't be Empty");
        }
        if (null == data) {
            logger.error("Data can't be null");
            throw new IllegalArgumentException("Data can't be null");
        }
        Set dataSet = dataMap.get(dataType);
        if (null == dataSet) {
            dataSet = new HashSet();
            dataMap.put(dataType, dataSet);
        }
        dataSet.add(data);
    }
    public List loadAll(String dataType, List dataList) {
        Set dataSet = dataMap.get(dataType);
        if (null != dataSet) {
            dataList.addAll(dataSet);
        }
        return dataList;
    }
}