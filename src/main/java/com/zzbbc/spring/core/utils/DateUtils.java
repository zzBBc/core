package com.zzbbc.spring.core.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateUtils {
    private static final Logger LOGGER = LogManager.getLogger(DateUtils.class);

    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIMESTAMP_DATE_FORMAT = "yyyy-MM-dd";

    public static String formatTimeStampDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIMESTAMP_DATE_FORMAT);

        return CommonUtils.getOrDefault(date, simpleDateFormat::format, "");
    }

    public static Date toDate(String time) {
        LocalDateTime localDateTime = null;
        try {
            localDateTime = toLocalDateTimeInternal(time);
        } catch (DateTimeParseException e) {
        }

        if (Objects.isNull(localDateTime)) {
            try {
                LocalDate localDate =
                        LocalDate.parse(time, DateTimeFormatter.ofPattern(DATE_FORMAT));

                return toDate(localDate);
            } catch (DateTimeParseException e) {
                LOGGER.error("Invalid date format!", e);

                return null;
            }
        }

        return toDate(localDateTime);
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(String time) {
        try {
            return toLocalDateTimeInternal(time);
        } catch (DateTimeParseException e) {
            LOGGER.error("Invalid date format!", e);
            return null;
        }
    }

    private static LocalDateTime toLocalDateTimeInternal(String time)
            throws DateTimeParseException {
        LocalDateTime localDateTime = null;

        try {
            localDateTime = LocalDateTime.parse(time);
        } catch (DateTimeParseException e) {
            localDateTime =
                    LocalDateTime.parse(time, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        } catch (NullPointerException e) {
            return null;
        }

        return localDateTime;
    }
}
