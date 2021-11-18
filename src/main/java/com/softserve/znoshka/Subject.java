/**
 * Copyright 2021
 *
 * All rights reserved.
 *
 * Created on Nov 11, 2021 3:51:25 PM
 */
package com.softserve.znoshka;

/**
 * @author Max Kutsepalov
 *
 */
public enum Subject {
    UKRANIAN,
    UKRANIAN_AND_LITERATURE,
    
    MATH,
    PROFILE_MATH,
    
    ENGLISH,
    SPANISH,
    GERMAN,
    FRENCH,
    
    HISTORY,
    BIOLOGY,
    GEOGRAPHY,
    PHYSICS,
    CHEMISTRY,
    CREATIVE_COMPETITION;
    
    public static String toNormalCase(String enumInstance) {
	String name = enumInstance.replace('_', ' ');
	name = name.toLowerCase();
	name = name.substring(0,1).toUpperCase() + name.substring(1);
	return name;
    }
    
    public static String toUpperSnakeCase(String nameOfEnum) {
	return nameOfEnum.replace(' ', '_').toUpperCase();
    }
}
