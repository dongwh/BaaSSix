package com.digiwes.common.catalog;

import com.digiwes.basetype.TimePeriod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Catalog {

    private  Logger logger= Logger.getLogger(Catalog.class);
    /**
     * A unique identifier for a catalog.
     */
    public String ID;
    /**
     * A word or phrase by which a catalog is known and distinguished from other catalogs.
     */
    public String name;
    /**
     * A categorization of an entry in the catalog such as web or book.
     */
    public String type;
    /**
     * The period of time during which the catalog is applicable.
     */
    public TimePeriod validFor;

    public String getID() {
        return this.ID;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TimePeriod getValidFor() {
        return this.validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }

    /**
     * 
     * @param id
     * @param name
     * @param type
     * @param validFor
     */
    public Catalog(String id, String name, String type, TimePeriod validFor) {
        assert !StringUtils.isEmpty(id):" id can't be null .";
        assert !StringUtils.isEmpty(name):"name can't be null";

    	this.ID = id;
    	this.name = name;
    	this.type = type;
    	this.validFor = validFor;
    	
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Catalog catalog = (Catalog) o;

        return !(ID != null ? !ID.equals(catalog.ID) : catalog.ID != null);

    }

    @Override
    public int hashCode() {
        return ID != null ? ID.hashCode() : 0;
    }

    public Map basicInfoToMap() {
        Map<String,Object> result =new HashMap<String,Object>();
        result.put("ID",ID);
        result.put("name",name);
        result.put("type",type);
        result.put("validFor",validFor);
        return  result;
    }

}