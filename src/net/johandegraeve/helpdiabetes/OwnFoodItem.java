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

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity for adding own food item to the list of selected items
 *
 * @author Johan Degraeve
 *
 */
public class OwnFoodItem extends Activity {

    /**
     * Will refer to this instance of the OwnFoodItem class
     */
    private Context thisContext;
    
    private EditText nameEditTextView;

    private EditText percentageOfCarbsEditTextView;

    private EditText amountInGramsEditTextView;

    private EditText unitNameEditTextView;

    private Button okButton;

    protected void onCreate(android.os.Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.ownfooditem);
	thisContext = this;
	SetupView();
    }

    private void SetupView() {
	nameEditTextView = (EditText) findViewById(R.id.name_edittext);
	percentageOfCarbsEditTextView = (EditText) findViewById(R.id.percentage_of_carbs_edittext);
	amountInGramsEditTextView = (EditText) findViewById(R.id.chosen_amount_own_food_item_edittext);
	unitNameEditTextView = (EditText) findViewById(R.id.unit_name_edittext);
	
	okButton = (Button)findViewById(R.id.own_food_item_button);
	okButton.setOnClickListener(new OnClickListener() {
	    //@Override
	    public void onClick(View v) {

		double percentage = Float.parseFloat(percentageOfCarbsEditTextView.getText().toString());
		double amount = Float.parseFloat(amountInGramsEditTextView.getText().toString());

		SelectedFoodItemDatabase db = new SelectedFoodItemDatabase(thisContext);
		FoodItem foodItem = new FoodItem(
			nameEditTextView.getText().toString(), 
			new Unit(unitNameEditTextView.getText().toString(), 100, 100,-1,-1, percentage, -1));
		SelectedFoodItem selectedFoodItem = new SelectedFoodItem(foodItem, (amount * percentage/100), 0);
		db.addSelectedFoodItem(selectedFoodItem);
		finish();
	    }
	});
    };
}
