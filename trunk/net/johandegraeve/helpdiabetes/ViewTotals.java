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
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


/**
 * Activity to display the total amounts
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class ViewTotals extends Activity {
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
     * sets up the TextView, and sets text to totals for cabs, fats, proteins and kilocalories
     */
    private void setUpViews() {
	totals = (TextView) findViewById(R.id.overview);
	String toDisplay; 
  	SelectedFoodItemDatabase db1 = new SelectedFoodItemDatabase(this);

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
		
	totals.setText(toDisplay);


    }

}
