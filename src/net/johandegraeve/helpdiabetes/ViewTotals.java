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
import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


/**
 * Activity to display the total amounts
 *
 * @author Johan Degraeve
 *
 */
public class ViewTotals extends Activity {
    /**
     * used for logging
     */
    @SuppressWarnings("unused")
    private static final String TAG = "ViewTotals";
    /**
     * set to true for logging
     */
    @SuppressWarnings("unused")
    private static final boolean D = true;;
    
    /**
     * Textview to display the totals
     */
    TextView totals;
    
    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.viewtotals);
	setUpViews();
    }
    
    /**
     * sets up the TextView, sets text to totals for cabs, fats, proteins and kilocalories,
     * calculate needed insulin based on personal settings
     */
    private void setUpViews() {
	Calendar cal;
	long currentTime;
  	String meal;
  	double  insulinRatio;
	String toDisplay; 
  	
	SelectedFoodItemDatabase db1 = new SelectedFoodItemDatabase(this);
  	long totalKcal = Math.round(db1.getTotalKcal());
  	long totalFats= Math.round(db1.getTotalFats());
  	long totalProteins= Math.round(db1.getTotalProteins());

  	totals = (TextView) findViewById(R.id.overview);
	
  	//first calculate amount of carbs, fats, proteins and kilocalories
	toDisplay = 
		 getResources().getString(R.string.the_selection_contains) +
		 ": \n\n" +
		Math.round(db1.getTotalCarbs()) +
		" " +
		getResources().getString(R.string.amount_of_carbs) +
		"\n" +
		
		//add fats 
		(totalFats == -1 ? "": Math.round(db1.getTotalFats()) +
		" " +
		getResources().getString(R.string.amount_of_fats) +
		"\n") +
		
		//add proteins
		(totalProteins == -1 ? "": Math.round(db1.getTotalProteins()) +
		" " +
		getResources().getString(R.string.amount_of_proteins) +
		"\n") +
		
		//add kilocalories
		(totalKcal == -1 ? "": Math.round(db1.getTotalKcal()) +
		" " +
		getResources().getString(R.string.amount_of_kcal) +
		"\n");

	//check which value should be used for meal and insulinratio
	insulinRatio = 0;

	//get the currentTime in milliseconds, local time
        cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set( Calendar.DAY_OF_MONTH, 1 );
        cal.set( Calendar.YEAR, 1970 );
        cal.setTimeZone(TimeZone.getDefault());
        currentTime = Preferences.timeAsStringToLong(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
        
	if (currentTime < Preferences.getSwitchTimeBreakfastToLunch(this)) {
	    insulinRatio = Preferences.getInsulinRatioBreakfast(this);
	    meal = getResources().getString(R.string.breakfastratio_title);
	}
	else if (currentTime < Preferences.getSwitchTimeLunchToSnack(this)) {
	    insulinRatio = Preferences.getInsulinRatioLunch(this);
	    meal = getResources().getString(R.string.lunchratio_title);
	}
	else if (currentTime < Preferences.getSwitchTimeSnackToDinner(this)) {
	    insulinRatio = Preferences.getInsulinRatioSnack(this);
	    meal = getResources().getString(R.string.snackratio_title);
	}
	else {
	    insulinRatio = Preferences.getInsulinRatioDinner(this);
	    meal = getResources().getString(R.string.dinnerratio_title);
	}

	//now add the necessary amount of insulin if needed
	if (insulinRatio > 0) {
	    
	    toDisplay = toDisplay +
	    "\n" +
	    meal +
	    "\n" +
	    getResources().getString(R.string.insulin) + 
	    ": " +
	    //round the number of units of insulin to 0.1
	    ((double)Math.round(db1.getTotalCarbs()/insulinRatio * 10))/10 +
	    " " +
	    getResources().getString(R.string.insulinUnits);
	    
	}
	totals.setText(toDisplay);
	
    }

}
