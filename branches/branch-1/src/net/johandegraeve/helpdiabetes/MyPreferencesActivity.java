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
