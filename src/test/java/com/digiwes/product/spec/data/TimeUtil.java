package com.ai.baas.product.spec.data;

import com.ai.baas.basetype.TimePeriod;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Nisx on 2015/7/10.
 */
public class TimeUtil {
    private static Logger logger= Logger.getLogger(TimeUtil.class);

    public static TimePeriod creatTimePeriod(String startTime, String endTime) {
        TimePeriod validFor = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            validFor = new TimePeriod(sdf.parse(startTime),
                    sdf.parse(endTime));
        } catch (ParseException e) {
            logger.error(e);
        }
        return validFor;
    }
}
