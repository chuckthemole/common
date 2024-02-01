package com.rumpus.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rumpus.common.ICommon;

// TODO expand this helper 
public class DateTimeHelpers implements ICommon {

    private static final com.rumpus.common.Logger.ICommonLogger LOG = com.rumpus.common.Logger.CommonLogger.createLogger(DateTimeHelpers.class);

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
            com.rumpus.common.Builder.LogBuilder.logBuilderFromStringArgsNoSpaces(DateTimeHelpers.class, exception.toString());
        }
        return dateObject;
    }
}
