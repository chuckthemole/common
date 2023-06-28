package com.rumpus.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rumpus.common.AbstractCommon;

// TODO expand this helper 
public class DateTimeHelpers extends AbstractCommon {

    /**
     * 
     * @return Date object if able to parse, else return null
     */
    public static Date dd_MM_yyyy(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date dateObject = null;
        try {
            dateObject = sdf.parse(date);
        } catch (ParseException exception) {
            LOG.info(exception.toString());
        }
        return dateObject;
    }
}
