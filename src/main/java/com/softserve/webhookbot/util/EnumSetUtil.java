package com.softserve.webhookbot.util;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class EnumSetUtil {
    public static <T extends Enum<T>> int code(EnumSet<T> set) {
        int res = 0;
        for (var e : set) {
            res += 1 << e.ordinal();
        }
        return res;
    }

    public static <T extends Enum<T>> EnumSet<T> decode(int code, Class<T> tClass) {
        EnumSet<T> result = EnumSet.allOf(tClass);
        for (var e : result) {
            if (((1 << e.ordinal()) & code) == 0) {
                result.remove(e);
            }
        }
        return result;
    }
}
