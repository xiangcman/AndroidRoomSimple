package com.single.room.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiangcheng on 19/1/4.
 */

public class FormatUtils {
    public static String formatCode(String code) {
        return "学号:" + code;
    }

    public static String formatName(String name) {
        return "名字:" + name;
    }

    public static String formatBirthday(Date date) {
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            return "生日:" + simpleDateFormat.format(date);
        }
        return "";
    }
}
