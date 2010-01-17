package net.johandegraeve.helpdiabetes;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.TextView;

public class MyPreferencesActivity extends PreferenceActivity {
    private static final String TAG = "PreferencesActivity";
    /**
     * set to true for debugging
     */
    private static final boolean D = true;
    
    //Preference Keys
    private static final String KEY_INSULIN_RATIO_BREAKFAST = "INSULIN_RATIO_BREAKFAST";
    private static final String KEY_INSULIN_RATIO_LUNCH = "INSULIN_RATIO_LUNCH";
    private static final String KEY_INSULIN_RATIO_SNACK = "INSULIN_RATIO_SNACK";
    private static final String KEY_INSULIN_RATIO_DINNER = "INSULIN_RATIO_DINNER";
    private static final String KEY_TIME_BREAKFAST_TO_LUNCH = "TIME_BREAKFAST_TO_LUNCH";
    private static final String KEY_TIME_LUNCH_TO_SNACK = "TIME_LUNCH_TO_SNACK";
    private static final String KEY_TIME_SNACK_TO_DINNER = "TIME_SNACK_TO_DINNER";

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
    

}
