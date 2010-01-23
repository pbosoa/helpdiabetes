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
import java.sql.Date;
import java.util.Calendar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


/**
 * Activity to display the total amounts
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class ViewTotals extends Activity {
    private static final String TAG = "ViewTotals";
    /**
     * set to true for debugging
     */
    private static final boolean D = false;
    
    /**
     * Textview to display the totals
     */
    TextView totals;
    private double  insulinRatio;
    
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
     * sets up the TextView, and sets text to totals for cabs, fats, proteins and kilocalories
     */
    private void setUpViews() {
	totals = (TextView) findViewById(R.id.overview);
	String toDisplay; 
  	SelectedFoodItemDatabase db1 = new SelectedFoodItemDatabase(this);
  	long timeValue;
  	String meal;

  	//first calculate amount of carbs, fats, proteins and kilocalories
	toDisplay = 
		 getResources().getString(R.string.the_selection_contains) +
		 ": \n\n" +
		Math.round(db1.getTotalCarbs()) +
		" " +
		getResources().getString(R.string.amount_of_carbs) +
		"\n" +
		Math.round(db1.getTotalFats()) +
		" " +
		getResources().getString(R.string.amount_of_fats) +
		"\n" +
		Math.round(db1.getTotalProteins()) +
		" " +
		getResources().getString(R.string.amount_of_proteins) +
		"\n" +
		Math.round(db1.getTotalKcal()) +
		" " +
		getResources().getString(R.string.amount_of_kcal) +
		"\n";

	//check which value should be used for meal and insulinratio
	timeValue = System.currentTimeMillis();
	timeValue = timeValue - (timeValue/(60*60*1000))*(60*60*1000);
	if (timeValue < Preferences.getSwitchTimeBreakfastToLunch(this)) {
	    insulinRatio = Preferences.getInsulinRatioBreakfast(this);
	    meal = getResources().getString(R.string.breakfastratio_title);
	}
	else if (timeValue < Preferences.getSwitchTimeLunchToSnack(this)) {
	    insulinRatio = Preferences.getInsulinRatioLunch(this);
	    meal = getResources().getString(R.string.lunchratio_title);
	}
	else if (timeValue < Preferences.getSwitchTimeSnackToDinner(this)) {
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
