package com.softserve.bot.util;
import com.softserve.bot.model.Subject;

import java.util.EnumSet;
import java.util.Set;


public final class EnumSetUtil {

    private EnumSetUtil() {

    }

    static <T extends Enum<T>> int code(EnumSet<T> set) {
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
    public static void addTick(Subject element, Set<Subject> enumSet) {
        switch (element) {
            case UKRAINIAN:
                enumSet.remove(Subject.LITERATURE);
                enumSet.add(element);
                break;
            case LITERATURE:
                enumSet.remove(Subject.UKRAINIAN);
                enumSet.add(element);
                break;
            case MATH_PROFILE:
                enumSet.remove(Subject.MATH_STANDARD);
                enumSet.add(element);
                break;
            case MATH_STANDARD:
                enumSet.remove(Subject.MATH_PROFILE);
                enumSet.add(element);
                break;
            case FRENCH:
                enumSet.remove(Subject.ENGLISH);
                enumSet.remove(Subject.GERMANY);
                enumSet.remove(Subject.SPANISH);
                enumSet.add(element);
                break;
            case ENGLISH:
                enumSet.remove(Subject.FRENCH);
                enumSet.remove(Subject.GERMANY);
                enumSet.remove(Subject.SPANISH);
                enumSet.add(element);
                break;
            case GERMANY:
                enumSet.remove(Subject.FRENCH);
                enumSet.remove(Subject.ENGLISH);
                enumSet.remove(Subject.SPANISH);
                enumSet.add(element);
                break;
            case SPANISH:
                enumSet.remove(Subject.GERMANY);
                enumSet.remove(Subject.FRENCH);
                enumSet.remove(Subject.ENGLISH);
                enumSet.add(element);
                break;
            default:
                enumSet.add(element);
        }
    }

    public static void removeTick(Subject element, Set<Subject> enumSet) {
        switch (element) {
            case LITERATURE:
                enumSet.remove(element);
                enumSet.add(Subject.UKRAINIAN);
                break;
            case UKRAINIAN:
                enumSet.remove(element);
                enumSet.add(Subject.LITERATURE);
                break;
            case MATH_PROFILE:
                enumSet.remove(element);
                enumSet.add(Subject.MATH_STANDARD);
                break;
            case MATH_STANDARD:
                enumSet.remove(element);
                enumSet.add(Subject.MATH_PROFILE);
                break;
            default:
                enumSet.remove(element);
        }
    }

    public static boolean selectedEnough(Set<Subject> enumSet) {
        return enumSet.size() >= (enumSet.contains(Subject.MATH_STANDARD) ? 4 : 3);
    }


    public static boolean notOutOfLimit(Set<Subject> enumSet) {
        return enumSet.size() <= (enumSet.contains(Subject.CREATIVE_COMPETITION) ? 6 : 5);
    }

    public static boolean isEnumSet(String line){
        try{
            Integer.parseInt(line);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
