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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.TimePickerPreference;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * PreferenceActivity class, as defined by Android. 
 * Implements also OnSharedPreferenceChangeListener in order to change preference summaries as soon as any preference
 * changed.
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class MyPreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    private static final String TAG = "PreferencesActivity";
    /**
     * set to true for debugging
     */
    private static final boolean D = true;
    
    EditTextPreference insulinRatioBreakFastEditTextPreference;
    String originalSummaryInsulinRatioBreakFastEditTextPreference;
    EditTextPreference insulinRatioLunchEditTextPreference;
    String originalSummaryInsulinRatioLunchEditTextPreference;
    EditTextPreference insulinRatioSnackEditTextPreference;
    String originalSummaryInsulinRatioSnackEditTextPreference;
    EditTextPreference insulinRatioDinnerEditTextPreference;
    String originalSummaryInsulinRatioDinnerEditTextPreference;
    
    TimePickerPreference BreakFastToLunchTimePickerPref;
    String originalSummaryBreakFastToLunchTimePickerPref;
    TimePickerPreference LunchToSnackTimePickerPref;
    String originalSummaryLunchToSnackTimePickerPref;
    TimePickerPreference SnackToDinnerTimePickerPref;
    String originalSummarySnackToDinnerTimePickerPref;

    /**
     * this Context
     */
    Context thisContext;
    
    
    /**
     * used to verify if preferences are set for first time, used to decide if app will ask whether all 
     * ratios should get the new value 
     */
    private boolean firstCall;
    
    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "++ ON CREATE ++");
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        
        
        thisContext = this;
        
        setup();
    }
    
    /**
     * general setup, initialization of Preferences and originalSummaries
     */
    private void setup() {
	//initialize following EditTextPreferences and originalSummary because they are needed in onResume
	insulinRatioBreakFastEditTextPreference = (EditTextPreference) findPreference(Preferences.KEY_INSULIN_RATIO_BREAKFAST);
	originalSummaryInsulinRatioBreakFastEditTextPreference = (String) insulinRatioBreakFastEditTextPreference.getSummary();

	insulinRatioLunchEditTextPreference = (EditTextPreference) findPreference(Preferences.KEY_INSULIN_RATIO_LUNCH);
	originalSummaryInsulinRatioLunchEditTextPreference = (String) insulinRatioLunchEditTextPreference.getSummary();

	insulinRatioSnackEditTextPreference = (EditTextPreference) findPreference(Preferences.KEY_INSULIN_RATIO_SNACK);
	originalSummaryInsulinRatioSnackEditTextPreference = (String) insulinRatioBreakFastEditTextPreference.getSummary();

	insulinRatioDinnerEditTextPreference = (EditTextPreference) findPreference(Preferences.KEY_INSULIN_RATIO_DINNER);
	originalSummaryInsulinRatioDinnerEditTextPreference = (String) insulinRatioBreakFastEditTextPreference.getSummary();
	
	//initialize also timepicker preferences for switch times
	//setDefaultValue call is necessary because the it's not automatically called by Android - seems a bug somewhere
	BreakFastToLunchTimePickerPref = (TimePickerPreference) findPreference(Preferences.KEY_TIME_BREAKFAST_TO_LUNCH);
        BreakFastToLunchTimePickerPref.setDefaultValue(Preferences.DEFVALUE_TIME_BREAKFAST_TO_LUNCH);
        //originalSummary not used, I kept it just in case I want to add a xml resource based summary in the future
        originalSummaryBreakFastToLunchTimePickerPref = "";
	
        LunchToSnackTimePickerPref = (TimePickerPreference) findPreference(Preferences.KEY_TIME_LUNCH_TO_SNACK);
        LunchToSnackTimePickerPref.setDefaultValue(Preferences.DEFVALUE_TIME_LUNCH_TO_SNACK);
        //originalSummary not used, I kept it just in case I want to add a xml resource based summary in the future
        originalSummaryLunchToSnackTimePickerPref = "";
        
        SnackToDinnerTimePickerPref = (TimePickerPreference) findPreference(Preferences.KEY_TIME_SNACK_TO_DINNER);
        SnackToDinnerTimePickerPref.setDefaultValue(Preferences.DEFVALUE_TIME_SNACK_TO_DINNER);
        //originalSummary not used, I kept it just in case I want to add a xml resource based summary in the future
        originalSummarySnackToDinnerTimePickerPref = "";
}

    /** Overriding necessary to be notified about preference changes, so that summaries can immediately be changed,
     * reflecting the new preference value.
     * @see android.content.SharedPreferences.OnSharedPreferenceChangeListener#onSharedPreferenceChanged(android.content.SharedPreferences, java.lang.String)
     */
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
	    String key) {
	if (key.equals(Preferences.KEY_INSULIN_RATIO_BREAKFAST)) {
	    resetInsulineRatioSummary(insulinRatioBreakFastEditTextPreference, 
		    originalSummaryInsulinRatioBreakFastEditTextPreference,
		    Double.toString(Preferences.getInsulinRatioBreakfast(this)));
	} else if (key.equals(Preferences.KEY_INSULIN_RATIO_LUNCH)) {
	    resetInsulineRatioSummary(insulinRatioLunchEditTextPreference, 
		    originalSummaryInsulinRatioLunchEditTextPreference,
		    Double.toString(Preferences.getInsulinRatioLunch(this)));
	} else if (key.equals(Preferences.KEY_INSULIN_RATIO_SNACK)) {
	    resetInsulineRatioSummary(insulinRatioSnackEditTextPreference, 
		    originalSummaryInsulinRatioSnackEditTextPreference,
		    Double.toString(Preferences.getInsulinRatioSnack(this)));
	} else if (key.equals(Preferences.KEY_INSULIN_RATIO_DINNER)) {
	    resetInsulineRatioSummary(insulinRatioDinnerEditTextPreference, 
		    originalSummaryInsulinRatioDinnerEditTextPreference,
		    Double.toString(Preferences.getInsulinRatioDinner(this)));
	} else if (key.equalsIgnoreCase(Preferences.KEY_TIME_BREAKFAST_TO_LUNCH)) {
	    resetSwitchTimeSummary(BreakFastToLunchTimePickerPref, 
		    originalSummaryBreakFastToLunchTimePickerPref, 
		    Preferences.getSwitchTimeBreakfastToLunch(this));
	} else if (key.equalsIgnoreCase(Preferences.KEY_TIME_LUNCH_TO_SNACK)) {
	    resetSwitchTimeSummary(LunchToSnackTimePickerPref, 
		    originalSummaryLunchToSnackTimePickerPref, 
		    Preferences.getSwitchTimeLunchToSnack(this));
	} else if (key.equalsIgnoreCase(Preferences.KEY_TIME_SNACK_TO_DINNER)) {
	    resetSwitchTimeSummary(SnackToDinnerTimePickerPref, 
		    originalSummarySnackToDinnerTimePickerPref, 
		    Preferences.getSwitchTimeSnackToDinner(this));
	}

	
	
    }
    
    /**
     * Overriding necessary  to call registerOnSharedPreferenceChangeListener
     * It is here that summaries will get correct initial values.
     * @see android.app.Activity#onResume()
     */
    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "++ ON RESUME ++");

        // Set up a listener whenever a preference changes            
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        //now reset all the summaries
        resetInsulineRatioSummary(insulinRatioBreakFastEditTextPreference, 
	    originalSummaryInsulinRatioBreakFastEditTextPreference,
	    Double.toString(Preferences.getInsulinRatioBreakfast(this)));
        
        resetInsulineRatioSummary(insulinRatioLunchEditTextPreference, 
    	    originalSummaryInsulinRatioLunchEditTextPreference,
    	Double.toString(Preferences.getInsulinRatioLunch(this)));
        
        resetInsulineRatioSummary(insulinRatioSnackEditTextPreference, 
    	    originalSummaryInsulinRatioSnackEditTextPreference,
    	    Double.toString(Preferences.getInsulinRatioSnack(this)));
        
        resetInsulineRatioSummary(insulinRatioDinnerEditTextPreference, 
    	    originalSummaryInsulinRatioDinnerEditTextPreference,
    	    Double.toString(Preferences.getInsulinRatioDinner(this)));

        if (D) Log.e(TAG,"calling resetSwitchTimeSummary for BreakFastToLunchTimePickerPref");
        resetSwitchTimeSummary(BreakFastToLunchTimePickerPref, 
        	originalSummaryBreakFastToLunchTimePickerPref, 
        	Preferences.getSwitchTimeBreakfastToLunch(this));
        if (D) Log.e(TAG,"calling resetSwitchTimeSummary for LunchToSnackhTimePickerPref");

        resetSwitchTimeSummary(LunchToSnackTimePickerPref, 
        	originalSummaryLunchToSnackTimePickerPref, 
        	Preferences.getSwitchTimeLunchToSnack(this));
        
        if (D) Log.e(TAG,"calling resetSwitchTimeSummary for SnacktoDinnerTimePickerPref");

        resetSwitchTimeSummary(SnackToDinnerTimePickerPref, 
        	originalSummarySnackToDinnerTimePickerPref, 
        	Preferences.getSwitchTimeSnackToDinner(this));

        firstCall = true;
    }

    /**
     * Overriding necessary to call unregisterOnSharedPreferenceChangeListener
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "++ ON PAUSE ++");

        // Unregister the listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
    
    }

    /**
     * Sets the summmary of the preference. Goal is that the summary is built of the summary from preferences.xml
     * + a text containing the actual value of the preference.
     * It check also the value firstCall and if firstCall dialog pops up to ask of all ratios should get the new value.
     * @param preference The EditTextPreference of which the summary needs to be changed
     * @param originalSummary the originalsummary as was stored in preferences.xml
     * @param theSetting the value of the preference
     */
    private void resetInsulineRatioSummary(EditTextPreference preference, 
	    String originalSummary,
	    final String theSetting) {

	preference.setSummary(originalSummary  +
		    " " + theSetting);
	
	//if firstcall then ask user if all ratios should get this new value
	if (firstCall) {
	    new AlertDialog.Builder(this)
	    .setMessage(R.string.change_all_ratios)
	    .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton)
		{
		    firstCall = false;
		    Preferences.setInsulineRatioBreakFast(thisContext, Double.parseDouble(theSetting));
		    resetInsulineRatioSummary(insulinRatioBreakFastEditTextPreference, 
			    originalSummaryInsulinRatioBreakFastEditTextPreference, 
			    theSetting);
		    Preferences.setInsulineRatioLunch(thisContext, Double.parseDouble(theSetting));
		    resetInsulineRatioSummary(insulinRatioLunchEditTextPreference, 
			    originalSummaryInsulinRatioLunchEditTextPreference, 
			    theSetting);
		    Preferences.setInsulineRatioSnack(thisContext, Double.parseDouble(theSetting));
		    resetInsulineRatioSummary(insulinRatioSnackEditTextPreference, 
			    originalSummaryInsulinRatioSnackEditTextPreference, 
			    theSetting);
		    Preferences.setInsulineRatioDinner(thisContext, Double.parseDouble(theSetting));
		    resetInsulineRatioSummary(insulinRatioDinnerEditTextPreference, 
			    originalSummaryInsulinRatioDinnerEditTextPreference, 
			    theSetting);
		}
	    })
	    .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton)
		{
		    firstCall = false;
		}    
	     })		
	    
	    .show();
	}
    }
    
    private void resetSwitchTimeSummary(TimePickerPreference preference, 
	    String originalSummary,
	    final long theSetting) {

	long hourLong = theSetting/(60*60*1000);

	String hour = Long.toString(hourLong);
	if (hour.length() < 2) {
	    hour = "0" + hour;
	}
	
	String minute = Long.toString((theSetting - hourLong * (60*60*1000))/(60*1000));
	if (minute.length() < 2) {
	    minute = "0" + minute;
	}
	
	preference.setSummary(originalSummary  +
		    " " + hour + ":" + minute);
	
    }


}
