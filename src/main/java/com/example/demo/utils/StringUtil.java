package com.example.demo.utils;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

public class StringUtil {

    private static final char[] CHAR_LIST = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9' };

    public static List<String> trimSplitByComma(String param) {
        String[] params = param.split(",");
        List<String> result = new ArrayList<String>();
        for (String str : params) {
            if (isNotEmpty(str)) {
                result.add(str.trim());
            }
        }
        return result;
    }

    public static boolean isNotEmpty(String param) {
        return !isNullOrEmpty(param);
    }

    public static boolean isNullOrEmpty(String param) {
        if (param == null || param.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getStackTrace(Throwable th) {
        StringBuffer buffException = new StringBuffer();
        buffException.append(String.format("%s%n", th));
        for (StackTraceElement stackTrace : th.getStackTrace()) {
            if (stackTrace.getClassName().startsWith("com.clustertech.cloud")) {
                buffException.append(String.format("\t%s%n", stackTrace));
            }
        }
        return buffException.toString();
    }

    public static String formatErrorLogger(Throwable th, String methodName, String... params) {
        return String.format("Method={%s(%s)}%n%s", methodName, formateStringByComma(params), getStackTrace(th));
    }

    public static String formateHqlInData(String commaString) {
        String[] paramArray = commaString.trim().split(",");
        return formateStringByComma(paramArray);
    }

    public static String formateHqlInData(Collection<String> params) {
        String[] paramArray = params.toArray(new String[params.size()]);
        return formateStringByComma(paramArray);
    }

    public static String formateHqlInData(String[] params) {
        return formateStringByComma(params);
    }

    public static String formateStringByComma(String... params) {
        if (params == null || params.length <= 0) {
            return "";
        }

        StringBuffer buff = new StringBuffer();
        for (String param : params) {
            String result = param == null ? "null" : param.trim();
            buff.append("'" + result + "',");
        }
        return buff.substring(0, buff.length() - 1);
    }

    public static String converteArrayToString(String[] params) {
        if (params == null || params.length <= 0) {
            return "[]";
        }

        StringBuffer buff = new StringBuffer();
        buff.append("[");
        for (String param : params) {
            String result = param == null ? "null" : param.trim();
            buff.append(result + ", ");
        }
        buff.append("]");
        return buff.delete(buff.length() - 3, buff.length() - 1).toString();
    }

    public static String getRandomChar() {
        return getRandomChar(8);
    }

    public static String getRandomChar(int capacity) {
        StringBuffer sb = new StringBuffer(capacity);
        char c = ' ';
        do {
            c = CHAR_LIST[new SecureRandom().nextInt(CHAR_LIST.length)];
            sb.append(c);
        } while (sb.indexOf(String.valueOf(c)) != -1 && sb.length() < capacity);
        return sb.toString();
    }

    public static String[] converteMapToArray(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return new String[0];
        }

        String[] arr = new String[map.size()];
        int i = 0;
        for (Entry<String, String> entry : map.entrySet()) {
            arr[i] = entry.getKey() + "=" + entry.getValue();
            i++;
        }
        return arr;
    }

    public static String getPastTime(Object interval) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR, -Integer.parseInt(interval.toString()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }

    public static String dateFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static Date dateParse(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }

    public static String substringAfterLast(String str, String separator) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(separator)) {
            return "";
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1 || pos == (str.length() - separator.length())) {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    public static String dataFormater(String data) {
        BigDecimal bd;
        BigDecimal dataFormated;
        bd = new BigDecimal(Double.valueOf(data));
        if (Double.valueOf(data) < 0.01) {
            dataFormated = bd.setScale(4, BigDecimal.ROUND_HALF_UP);
        } else {
            dataFormated = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return dataFormated.toString();
    }
}
