/*  
 *  Copyright (C) 2010  Johan Degraeve
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/gpl.txt>.
 *    
 *  Please contact Johan Degraeve at johan.degraeve@johandegraeve.net if you need
 *  additional information or have any questions.
 */
package net.johandegraeve.helpdiabetes;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * 
 * Defines methods to retrieve and store settings from and into the default shared preferences.<br>
 * Also static key values needed in other classes to use the shared preferences.<br>
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class Preferences {
    /**
     * used for logging
     */
    @SuppressWarnings("unused")
    private static final String TAG = "Preferences";
    /**
     * set to true for debugging
     */
    @SuppressWarnings("unused")
    private static final boolean D = true;
    
    /**
     * key for preferences
     */
    public static final String KEY_INSULIN_RATIO_BREAKFAST = "INSULIN_RATIO_BREAKFAST";
    /**
     * Default value
     */
    private static final String DEFVALUE_INSULIN_RATIO_BREAKFAST = "0";
    
    /**
     * key for preferences
     */
    public static final String KEY_INSULIN_RATIO_LUNCH = "INSULIN_RATIO_LUNCH";
    /**
     * Default value
     */
    private static final String DEFVALUE_INSULIN_RATIO_LUNCH = "0";
    
    /**
     * key for preferences
     */
    public static final String KEY_INSULIN_RATIO_SNACK = "INSULIN_RATIO_SNACK";
    /**
     * Default value
     */
    private static final String DEFVALUE_INSULIN_RATIO_SNACK = "0";
    
    /**
     * key for preferences
     */
    public static final String KEY_INSULIN_RATIO_DINNER = "INSULIN_RATIO_DINNER";
    /**
     * Default value
     */
    private static final String DEFVALUE_INSULIN_RATIO_DINNER = "0";
    
    /**
     * key for preferences
     */
    public static final String KEY_TIME_BREAKFAST_TO_LUNCH = "TIME_BREAKFAST_TO_LUNCH";
    /**
     * Default value
     */
    public static final String DEFVALUE_TIME_BREAKFAST_TO_LUNCH = "10:00";
    
    /**
     * key for preferences
     */
    public static final String KEY_TIME_LUNCH_TO_SNACK = "TIME_LUNCH_TO_SNACK";
    /**
     * Default value
     */
    public static final String DEFVALUE_TIME_LUNCH_TO_SNACK = "15:30";
    
    /**
     * key for preferences
     */
    public static final String KEY_TIME_SNACK_TO_DINNER = "TIME_SNACK_TO_DINNER";
    /**
     * Default value
     */
    public static final String DEFVALUE_TIME_SNACK_TO_DINNER = "17:00";
    
    /**
     * returns the insulin ratio for the specified meal
     * @param ctx
     * @return the insulin ratio for specific meal
     */
    public static double getInsulinRatioBreakfast(Context ctx) {
	return Double.parseDouble(PreferenceManager.getDefaultSharedPreferences(ctx)
	.getString(KEY_INSULIN_RATIO_BREAKFAST, DEFVALUE_INSULIN_RATIO_BREAKFAST));
    }
    
    /**
     * set the ratio of the specified meal to the new value
     * @param ctx the context necessary to get the defaultSharedPreferences
     * @param newvalue
     */
    public static void setInsulineRatioBreakFast(Context ctx,double newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit() 
	.putString(KEY_INSULIN_RATIO_BREAKFAST, Double.toString(newvalue))
	.commit();
    }
    
    /**
     * returns the insulin ratio for the specified meal
     * @param ctx
     * @return the insulin ratio for specific meal
     */
    public static double getInsulinRatioLunch(Context ctx) {
	return Double.parseDouble(PreferenceManager.getDefaultSharedPreferences(ctx)
		.getString(KEY_INSULIN_RATIO_LUNCH, DEFVALUE_INSULIN_RATIO_LUNCH));
    }
    
    /**
     * set the ratio of the specified meal to the new value
     * @param ctx the context necessary to get the defaultSharedPreferences
     * @param newvalue
     */
    public static void setInsulineRatioLunch(Context ctx,double newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit() 
	.putString(KEY_INSULIN_RATIO_LUNCH, Double.toString(newvalue))
	.commit();
    }
    
    /**
     * returns the insulin ratio for the specified meal
     * @param ctx
     * @return the insulin ratio for specific meal
     */
    public static double getInsulinRatioSnack(Context ctx) {
	return Double.parseDouble(PreferenceManager.getDefaultSharedPreferences(ctx)
		.getString(KEY_INSULIN_RATIO_SNACK, DEFVALUE_INSULIN_RATIO_SNACK));
    }
    
    /**
     * set the ratio of the specified meal to the new value
     * @param ctx the context necessary to get the defaultSharedPreferences
     * @param newvalue
     */
    public static void setInsulineRatioSnack(Context ctx,double newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit() 
	.putString(KEY_INSULIN_RATIO_SNACK, Double.toString(newvalue))
	.commit();
    }
    
    /**
     * returns the insulin ratio for the specified meal
     * @param ctx
     * @return the insulin ratio for specific meal
     */
    public static double getInsulinRatioDinner(Context ctx) {
	return Double.parseDouble(PreferenceManager.getDefaultSharedPreferences(ctx)
		.getString(KEY_INSULIN_RATIO_DINNER, DEFVALUE_INSULIN_RATIO_DINNER));
    }
    
    /**
     * set the ratio of the specified meal to the new value
     * @param ctx the context necessary to get the defaultSharedPreferences
     * @param newvalue
     */
    public static void setInsulineRatioDinner(Context ctx,double newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit() 
	.putString(KEY_INSULIN_RATIO_DINNER, Double.toString(newvalue))
	.commit();
    }
    
    /**
     * returns the insulin ratio for the specified meal
     * @param ctx
     * @return the insulin ratio for specific meal
     */
    static long getSwitchTimeBreakfastToLunch(Context ctx) {
	String time = PreferenceManager.getDefaultSharedPreferences(ctx)
	.getString(KEY_TIME_BREAKFAST_TO_LUNCH, DEFVALUE_TIME_BREAKFAST_TO_LUNCH);
	return timeAsStringToLong(time);
    }
    
    /**
     * set the ratio of the specified meal to the new value
     * @param ctx the context necessary to get the defaultSharedPreferences
     * @param newvalue
     */
    public static void setSwitchTimeBreakfastToLunch(Context ctx,String newvalue) {
	
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit()
	.putString(KEY_TIME_BREAKFAST_TO_LUNCH, newvalue);
    }
    
    /**
     * returns the insulin ratio for the specified meal
     * @param ctx
     * @return the insulin ratio for specific meal
     */
    public static long getSwitchTimeLunchToSnack(Context ctx) {
	String time = PreferenceManager.getDefaultSharedPreferences(ctx)
	.getString(KEY_TIME_LUNCH_TO_SNACK, DEFVALUE_TIME_LUNCH_TO_SNACK);
	return timeAsStringToLong(time);
    }
    
    /**
     * set the switch time
     * @param ctx the context necessary to get the defaultSharedPreferences
     * @param newvalue
     */
    public static void setSwitchTimeLunchToSnack(Context ctx,String newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit()
	.putString(KEY_TIME_LUNCH_TO_SNACK, newvalue);
    }
    
    /**
     * returns the insulin ratio for the specified meal
     * @param ctx
     * @return the insulin ratio for specific meal
     */
    public static long getSwitchTimeSnackToDinner(Context ctx) {
	String time = PreferenceManager.getDefaultSharedPreferences(ctx)
	.getString(KEY_TIME_SNACK_TO_DINNER, DEFVALUE_TIME_SNACK_TO_DINNER);
	return timeAsStringToLong(time);
    }
    
    /**
     * set the switch time
     * @param ctx the context necessary to get the defaultSharedPreferences
     * @param newvalue
     */
    public static void setSwitchTimeSnackToDinner(Context ctx,String newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit()
	.putString(KEY_TIME_SNACK_TO_DINNER, newvalue);
    }
    
    /**
     * helper method also used in other classes
     * @param time in a string in the format HH:mm example 10:35 or 10:9, an hour or time of 1 digit is also allowed
     * @return the tima in milliseconds
     */
    static public long timeAsStringToLong(String time) {
	return (Integer.parseInt(time.split(":")[0])*60 + Integer.parseInt(time.split(":")[1]))*60*1000;
    }
}
