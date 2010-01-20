/*  
 *  Copyright (C) 2009  Johan Degraeve
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
import android.util.Log;

/**
 * 
 * Defines methods to retrieve and store settings from and into the default shared preferences.
 * Also static key values needed in other classes to use the shared preferences.
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class Preferences {
    private static final String TAG = "Preferences";
    /**
     * set to true for debugging
     */
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
    private static final long DEFVALUE_TIME_BREAKFAST_TO_LUNCH = 32400000;
    
    /**
     * key for preferences
     */
    public static final String KEY_TIME_LUNCH_TO_SNACK = "TIME_LUNCH_TO_SNACK";
    /**
     * Default value
     */
    private static final long DEFVALUE_TIME_LUNCH_TO_SNACK = 50400000;
    
    /**
     * key for preferences
     */
    public static final String KEY_TIME_SNACK_TO_DINNER = "TIME_SNACK_TO_DINNER";
    /**
     * Default value
     */
    private static final long DEFVALUE_TIME_SNACK_TO_DINNER = 61200000;
    
    /**
     * Filename used to store preferences. This value is default value used by Android.
     */
    private static final String PREFERENCES_FILENAME ="net.johandegraeve.helpdiabetes";
    
    static int getInsulinRatioBreakfast(Context ctx) {
	return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(ctx)
	.getString(KEY_INSULIN_RATIO_BREAKFAST, DEFVALUE_INSULIN_RATIO_BREAKFAST));
    }
    
    static void setInsulineRatioBreakFast(Context ctx,int newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit() 
	.putString(KEY_INSULIN_RATIO_BREAKFAST, Integer.toString(newvalue))
	.commit();
	if (D) Log.i(TAG,"hello 1");
    }
    
    static int getInsulinRatioLunch(Context ctx) {
	return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(ctx)
		.getString(KEY_INSULIN_RATIO_LUNCH, DEFVALUE_INSULIN_RATIO_LUNCH));
    }
    
    static void setInsulineRatioLunch(Context ctx,int newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit() 
	.putString(KEY_INSULIN_RATIO_LUNCH, Integer.toString(newvalue))
	.commit();
	if (D) Log.i(TAG,"hello 2");
    }
    
    static int getInsulinRatioSnack(Context ctx) {
	return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(ctx)
		.getString(KEY_INSULIN_RATIO_SNACK, DEFVALUE_INSULIN_RATIO_SNACK));
    }
    
    static void setInsulineRatioSnack(Context ctx,int newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit() 
	.putString(KEY_INSULIN_RATIO_SNACK, Integer.toString(newvalue))
	.commit();
	if (D) Log.i(TAG,"hello 3");
    }
    
    static int getInsulinRatioDinner(Context ctx) {
	return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(ctx)
		.getString(KEY_INSULIN_RATIO_DINNER, DEFVALUE_INSULIN_RATIO_DINNER));
    }
    
    static void setInsulineRatioDinner(Context ctx,int newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit() 
	.putString(KEY_INSULIN_RATIO_DINNER, Integer.toString(newvalue))
	.commit();
	if (D) Log.i(TAG,"hello 4");
    }
    
    static long getSwitchTimeBreakfastToLunch(Context ctx) {
	return ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE)
	.getLong(KEY_TIME_BREAKFAST_TO_LUNCH, DEFVALUE_TIME_BREAKFAST_TO_LUNCH);
    }
    
    static void setSwitchTimeBreakfastToLunch(Context ctx,int newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit()
	.putLong(KEY_TIME_BREAKFAST_TO_LUNCH, DEFVALUE_TIME_BREAKFAST_TO_LUNCH)
	.commit();
	
    }
    
    static long getSwitchTimeLunchToSnack(Context ctx) {
	return ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE)
	.getLong(KEY_TIME_LUNCH_TO_SNACK, DEFVALUE_TIME_LUNCH_TO_SNACK);
    }
    
    static void setSwitchTimeLunchToSnack(Context ctx,int newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit()
	.putLong(KEY_TIME_LUNCH_TO_SNACK, DEFVALUE_TIME_LUNCH_TO_SNACK)
	.commit();
	
    }
    
    static long getSwitchTimeSnackToDinner(Context ctx) {
	return ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE)
	.getLong(KEY_TIME_SNACK_TO_DINNER, DEFVALUE_TIME_SNACK_TO_DINNER);
    }
    
    static void setSwitchTimeSnackToDinner(Context ctx,int newvalue) {
	PreferenceManager.getDefaultSharedPreferences(ctx)
	.edit()
	.putLong(KEY_TIME_SNACK_TO_DINNER, DEFVALUE_TIME_SNACK_TO_DINNER)
	.commit();
    }
    
}
