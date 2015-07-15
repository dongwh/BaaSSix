package com.digiwes.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by zhaoyp on 2015/7/13.
 */
public class ValidUtil {

    public static boolean checkObjectIsNull(Object param){
          if( null == param ){
              return true;
          }
        return false;
    }

}
