package no.lundesgaard.sudokufeud.api.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ISO8601DateTimeFormatUtil {
    public static DateFormat iso8601DateTimeFormat(Level level) {
        return new SimpleDateFormat(level.format);
    }

    public enum Level {
        YYYY("yyyy"),
        YYYY_MM("yyyy-MM"),
        YYYY_MM_DD("yyyy-MM-dd"),
        YYYY_MM_DD_T_HH_MM_TZD("yyyy-MM-dd'T'HH:mmXXX"),
        YYYY_MM_DD_T_HH_MM_SS_TZD("yyyy-MM-dd'T'HH:mm:ssXXX"),
        YYYY_MM_DD_T_HH_MM_SS_S_TZD("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        private String format;

        private Level(String format) {
            this.format = format;
        }
    }
}
