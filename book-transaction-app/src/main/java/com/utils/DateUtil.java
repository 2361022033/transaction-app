package com.utils;

import cn.hutool.core.util.ObjectUtil;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@UtilityClass
public class DateUtil {

    public String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
    }

    public String dateToString(LocalDateTime localDateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        return localDateTime.format(format);
    }

    public LocalDateTime loacalDateTimeToDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Long toTimeStamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


    public static Date timeStamp2Date(Long timeStamp) {
        if (ObjectUtil.isNull(timeStamp)) {
            return null;
        }
        Date date = new Date(timeStamp);
        return date;
    }

    public Long date2Long(Date date) {
        return Optional.ofNullable(date).map(Date::getTime).orElse(null);
    }

}
