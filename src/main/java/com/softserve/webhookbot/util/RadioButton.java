package com.softserve.webhookbot.util;

import com.softserve.webhookbot.enumeration.Subject;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class RadioButton {
    public static void radioButtonImpl(Subject element, EnumSet<Subject> enumSet) {
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

    public static void removeMandatoryTick(Subject element, EnumSet<Subject> enumSet) {
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

    public static boolean selectedEnough(EnumSet<Subject> enumSet) {
        return enumSet.size() >= (enumSet.contains(Subject.MATH_STANDARD) ? 4 : 3);
    }


    public static boolean notOutOfLimit(EnumSet<Subject> enumSet) {
        return enumSet.size() <= (enumSet.contains(Subject.CREATIVE_COMPETITION) ? 6 : 5);
    }
}

