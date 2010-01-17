package net.johandegraeve.helpdiabetes;

import android.content.Context;

/*
 * TO BE DELETED
 * @see android.Context
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class Preferences {
    private static final String KEY_INSULIN_RATIO_BREAKFAST = "INSULIN_RATIO_BREAKFAST";
    private static final int DEFVALUE_INSULIN_RATIO_BREAKFAST = 0;
    
    private static final String KEY_INSULIN_RATIO_LUNCH = "INSULIN_RATIO_LUNCH";
    private static final int DEFVALUE_INSULIN_RATIO_LUNCH = 0;
    
    private static final String KEY_INSULIN_RATIO_SNACK = "INSULIN_RATIO_SNACK";
    private static final int DEFVALUE_INSULIN_RATIO_SNACK = 0;
    
    private static final String KEY_INSULIN_RATIO_DINNER = "INSULIN_RATIO_DINNER";
    private static final int DEFVALUE_INSULIN_RATIO_DINNER = 0;
    
    private static final String KEY_TIME_BREAKFAST_TO_LUNCH = "TIME_BREAKFAST_TO_LUNCH";
    private static final long DEFVALUE_TIME_BREAKFAST_TO_LUNCH = 32400000;
    
    private static final String KEY_TIME_LUNCH_TO_SNACK = "TIME_LUNCH_TO_SNACK";
    private static final long DEFVALUE_TIME_LUNCH_TO_SNACK = 50400000;
    
    private static final String KEY_TIME_SNACK_TO_DINNER = "TIME_SNACK_TO_DINNER";
    private static final long DEFVALUE_TIME_SNACK_TO_DINNER = 61200000;
    
    private static final String PREFERENCES_FILENAME ="HELPDIABETES_PREFERENCES";
    
    static int getInsulinRatioBreakfast(Context ctx) {
	return ctx.getSharedPreferences(PREFERENCES_FILENAME,Context.MODE_WORLD_READABLE)
	.getInt(KEY_INSULIN_RATIO_BREAKFAST, DEFVALUE_INSULIN_RATIO_BREAKFAST);
    }
    
    static void setInsulineRatioBreakFast(Context ctx,int newvalue) {
	ctx.getSharedPreferences(PREFERENCES_FILENAME,Context.MODE_WORLD_READABLE)
	.edit() 
	.putInt(KEY_INSULIN_RATIO_BREAKFAST, newvalue)
	.commit();
	
    }
    
    int getInsulinRatioLunch(Context ctx) {
	return ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.getInt(KEY_INSULIN_RATIO_LUNCH, DEFVALUE_INSULIN_RATIO_LUNCH);
    }
    
    static void setInsulineRatioLunch(Context ctx,int newvalue) {
	ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.edit()
	.putInt(KEY_INSULIN_RATIO_LUNCH, DEFVALUE_INSULIN_RATIO_LUNCH)
	.commit();
	
    }
    
    int getInsulinRatioSnack(Context ctx) {
	return ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.getInt(KEY_INSULIN_RATIO_SNACK,DEFVALUE_INSULIN_RATIO_SNACK);
    }
    
    static void setInsulineRatioSnack(Context ctx,int newvalue) {
	ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.edit()
	.putInt(KEY_INSULIN_RATIO_SNACK, DEFVALUE_INSULIN_RATIO_SNACK)
	.commit();
	
    }
    
    int getInsulinRatioDinner(Context ctx) {
	return ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.getInt(KEY_INSULIN_RATIO_DINNER, DEFVALUE_INSULIN_RATIO_DINNER);
    }
    
    static void setInsulineRatioDinner(Context ctx,int newvalue) {
	ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.edit()
	.putInt(KEY_INSULIN_RATIO_DINNER, DEFVALUE_INSULIN_RATIO_DINNER)
	.commit();
	
    }
    
    long getSwitchTimeBreakfastToLunch(Context ctx) {
	return ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.getLong(KEY_TIME_BREAKFAST_TO_LUNCH, DEFVALUE_TIME_BREAKFAST_TO_LUNCH);
    }
    
    static void setSwitchTimeBreakfastToLunch(Context ctx,int newvalue) {
	ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.edit()
	.putLong(KEY_TIME_BREAKFAST_TO_LUNCH, DEFVALUE_TIME_BREAKFAST_TO_LUNCH)
	.commit();
	
    }
    
    long getSwitchTimeLunchToSnack(Context ctx) {
	return ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.getLong(KEY_TIME_LUNCH_TO_SNACK, DEFVALUE_TIME_LUNCH_TO_SNACK);
    }
    
    static void setSwitchTimeLunchToSnack(Context ctx,int newvalue) {
	ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.edit()
	.putLong(KEY_TIME_LUNCH_TO_SNACK, DEFVALUE_TIME_LUNCH_TO_SNACK)
	.commit();
	
    }
    
    long getSwitchTimeSnackToDinner(Context ctx) {
	return ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.getLong(KEY_TIME_SNACK_TO_DINNER, DEFVALUE_TIME_SNACK_TO_DINNER);
    }
    
    static void setSwitchTimeSnackToDinner(Context ctx,int newvalue) {
	ctx.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_WORLD_READABLE)
	.edit()
	.putLong(KEY_TIME_SNACK_TO_DINNER, DEFVALUE_TIME_SNACK_TO_DINNER)
	.commit();
    }
    
}
