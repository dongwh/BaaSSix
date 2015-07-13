package com.ai.baas.basetype;

import com.ai.baas.common.util.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A base / value business entity used to represent a period of time, between two time points
 */
public class TimePeriod {

    /**
     * An instant of time, starting at the TimePeriod
     * 
     * Notes:
     * If null, then represents to the beginning of time
     */
    public Date startDateTime;
    /**
     * An instant of time, ending at the TimePeriod:
     * 
     * Notes:
     * If null, then represents to the end of time
     */
    public Date endDateTime;

    public Date getStartDateTime() {
        return this.startDateTime;
    }


    public Date getEndDateTime() {
        return this.endDateTime;
    }

    public TimePeriod(String startDateTime, String endDateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if( null == startDateTime || null == endDateTime){
            throw  new IllegalArgumentException("starDateTime or endDateTime can not be  null ");
        }
        try {
            if (StringUtils.isNotEmpty(startDateTime)) {
                this.startDateTime = format.parse(startDateTime);
            }
            if (StringUtils.isNotEmpty(endDateTime)) {
                this.endDateTime = format.parse(endDateTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public TimePeriod(Date startDateTime, Date endDateTime) {
        if( null == startDateTime || null == endDateTime){
            throw  new IllegalArgumentException("starDateTime or endDateTime can not be  null ");
        }
        if ( null != startDateTime ) {
                this.startDateTime =startDateTime;
        }
        if (null != endDateTime ) {
                this.endDateTime = endDateTime;
        }
    }
    public int compait(Date time) throws Exception{
    	DateUtils du = new DateUtils();
    	if(du.dateDiff('s', du.parseCalendar(time), du.parseCalendar(startDateTime))<0){
    		return -1;
    	}
    	if(du.dateDiff('s', du.parseCalendar(time), du.parseCalendar(endDateTime))>0){
    		return 1;
    	}
    	if(du.dateDiff('s', du.parseCalendar(time), du.parseCalendar(startDateTime))>=0 &&
    			du.dateDiff('s', du.parseCalendar(time), du.parseCalendar(endDateTime))<=0){
    		return 0;
    	}
    	return 0;
    }
    
    /**
     * @param time
     * @return
     */
    public boolean isInTimePeriod(Date time){
        if (this.startDateTime != null && this.endDateTime != null) {
            if(time.compareTo(this.startDateTime) == 1 && time.compareTo(this.endDateTime) == -1){
            	return true;
            }else{
                return false;
            }
        }
        return false;
    }
    public  boolean isOverlap(TimePeriod validFor){
        if( null == startDateTime && null== endDateTime){
          return true;
        }
        if( null == validFor){
            return true;
        }
        if (validFor.getStartDateTime().compareTo(endDateTime)<=0 && validFor.endDateTime.compareTo(startDateTime)>=0) return true;
        else return false;
    }
    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Map<String,String> vaildFor=new HashMap<String,String>();
        vaildFor.put("startDateTime", this.startDateTime == null ? "" : format.format(this.startDateTime));
        vaildFor.put("endDateTime", this.endDateTime == null ? "" : format.format(this.endDateTime));
        return  vaildFor.toString();
    }

}