/*  
 *  Copyright (C) 2009  Johan Degraeve
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity that handles the screen for adding fooditems to the list of selected fooditems.
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class AddSelectFoodItem extends Activity{
    /**
     * tag to be used by any method in this calss, when using {@link android.util.Log}
     */
    @SuppressWarnings("unused")
    private static final String LOG_TAG = "HD-AddSelectedFoodItem";
    
    /**
     * the SelectedFoodItem
     */
    private SelectedFoodItem selectedFoodItem;

    /**
     * Holds the text like "100 grams of Apple, Norwegian, raw contains 10.0 grams of carbs."
     */
    private TextView standardAmountView;

    /**
     * EditText which allows entering amounts. 
     */
    private EditText choseamount;

    /**
     * Displays the toal amount of carbs, for the chosen unit and amount 
     */
    private TextView resultView;
    
    /**
     * The button to confirm the amount entered
     */
    private Button okButton;
    
    /**
     * used in override of {@link #dispatchTrackballEvent(MotionEvent)}
     */
    private long previousTimeStamp;
    
    /**
     * Will refer to this instance of the AddSelectedFoodItem class
     */
    private Context thisContext;
    
    /**
     * true means new food item will be added to the database, false means existing item will be updated.
     */
    private boolean itIsANewItemToAdd;

    /**
     * used as Alert dialing with a list of units from which user can select
     */
    AlertDialog.Builder builder;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.addselectedfooditem);
	thisContext = this;
	SetupView();
    }

    /**
     * Sets up the views {@link #choseamount}, {@link #resultView}, {@link #standardAmountView}, builds the AlertDialog 
     * {@link #builder} with the list of units in the selected fooditem.
     */
    private void SetupView() {
	Intent startingIntent = getIntent();
	String[] units;

	Bundle b = startingIntent.getExtras();
	itIsANewItemToAdd = startingIntent.getBooleanExtra("isItaNewItemToAdd", true);
	
	if (b != null) {
	    selectedFoodItem = SelectedFoodItem.fromBundle(b);

	    builder = new AlertDialog.Builder(this);
	    builder.setTitle(getResources().getString(R.string.title_select_unit));
	    units = new String[selectedFoodItem.getFoodItem().getNumberOfUnits()];
	    for (int i = 0; i<units.length;i++) {
		units[i] = selectedFoodItem.getFoodItem().getUnit(i).getDescription();
	    }
	    builder.setItems(units, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        selectedFoodItem.setChosenUnitNumber(item);
		        int amount;
		        if ((amount = selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getStandardAmount()) == 100)
		            selectedFoodItem.setChosenAmount(0);
		        else
		            selectedFoodItem.setChosenAmount(amount);
		        updateChoseAmount();
		        setStandardAmountViewText();
		        calculateNewResultandUpdateView(choseamount.getText());
		    }
		});
	    builder.create();

	    standardAmountView = (TextView) findViewById(R.id.standardamount);
	    if (standardAmountView != null) {
		setStandardAmountViewText();
	    }

	    resultView = (TextView) findViewById(R.id.result);

	    choseamount = (EditText) findViewById(R.id.choseamnt_edittext);
	    if (choseamount != null) {
		
		updateChoseAmount();
		
		calculateNewResultandUpdateView(choseamount.getText());
		
		choseamount.addTextChangedListener(new TextWatcher() {

		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			;
		    }

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
			calculateNewResultandUpdateView(s);
		    }
		    public void afterTextChanged(Editable s) {
			if (s.length() > 0) {
			    try {
				if (Double.parseDouble(s.toString()) > 0) {
				if (s.charAt(0) == '0') {
				    //here's the case where for example s = 05
				    //in such case remove the 0 so that 05 becomes 5
				    s.replace(0, 1, "", 0, 0);
				}
				}
			    } catch (NumberFormatException e) {
				//this may happen for example of s = "."
				;
			    }
			}
		    }
		});
	    }
	    
	    okButton = (Button)findViewById(R.id.choseamnt_button);
	    okButton.setOnClickListener(new OnClickListener() {
	        //@Override
	        public void onClick(View v) {
	            if (choseamount.getText().length() == 0)
	        	choseamount.setText("0");
	            if ((choseamount.getText().charAt(0) == '.') && (choseamount.getText().length() == 1)) 
	        	choseamount.setText("0");
	            selectedFoodItem.setChosenAmount(Double.parseDouble(choseamount.getText().toString()));
	            SelectedFoodItemDatabase db = new SelectedFoodItemDatabase(thisContext);
	            if (itIsANewItemToAdd) 
	        	db.addSelectedFoodItem(selectedFoodItem);
	            else {
	        	db.updateSelectedFoodItem(selectedFoodItem);
	        	setResult(RESULT_OK);
	            }
	            finish();
	        }
	    }
	    );
	    
	    //now if the list of units is larger dan 1 then show immediately the list
	    if (units.length > 1)
		builder.show();

	} /* there's no else branch, b should always be != null  */
    }

    /**
     * Set Text in {@link #standardAmountView}, takes into account the selected unit
     */
    private void setStandardAmountViewText() {
	standardAmountView.setText(
		selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getStandardAmount() +
		" " +
		selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getDescription() +
		" " +
		selectedFoodItem.getFoodItem().getItemDescription() +
		" " +
		getResources().getString(R.string.contains) +
		" " +
		((double)Math.round(selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getCarbs() * 10))/10 +
		" " +
		getResources().getString(R.string.amount_of_carbs) + 
		"."
	);
    }

    /**
     * Calculates result based on entered digits and updates {@link #resultView} with the result
     * @param inputsequence the entered digits
     */
    private void calculateNewResultandUpdateView(CharSequence inputsequence) {
	String s = inputsequence.toString();
	if (s.length() == 0) {
	    s = "0";
	}
	    try {
		resultView.setText(getResources().getString(R.string.result)
			+ "\n"
			+ s
			+ " "
			+ selectedFoodItem.getFoodItem().getUnit(
				selectedFoodItem.getChosenUnitNumber())
				.getDescription()
			+ " "
			+ getResources().getString(R.string.contains)
			+ " "
			+ ((double)Math.round(
				Double.parseDouble(s)
			* selectedFoodItem.getFoodItem().getUnit(
				selectedFoodItem.getChosenUnitNumber())
				.getCarbs()
			/ selectedFoodItem.getFoodItem().getUnit(
				selectedFoodItem.getChosenUnitNumber())
				.getStandardAmount()
			* 10
				)) / 10.0 + " "
			+ getResources().getString(R.string.amount_of_carbs)
			+ ".");
	    } catch (NumberFormatException e) {
		//this may happen for example of s = "."
	    }
	;
    }

    /**
     * @see android.app.Activity#dispatchTrackballEvent(android.view.MotionEvent)
     * The trackball is used to let the user change the chosen unit.<br>
     * When the unit is changed, the chosenUnitNumber is immediately set in {@link #selectedFoodItem} with method
     *  {@link net.johandegraeve.helpdiabetes#SelectedFoodItem.setChosenUnitNumber(int)}<br>
     * The views will immediately be updated. 
     */
    @Override
    public boolean dispatchTrackballEvent (MotionEvent ev) {
	long newTimeStamp = new Date().getTime();
	if ((newTimeStamp - previousTimeStamp) > 100) {
	    previousTimeStamp = newTimeStamp;
	    if (selectedFoodItem.getFoodItem().getNumberOfUnits() > 1) {
		if (ev.getY() > 0) {
		    if (selectedFoodItem.getChosenUnitNumber() 
			    == selectedFoodItem.getFoodItem().getNumberOfUnits() - 1) {
			selectedFoodItem.setChosenUnitNumber(0);
		    }
		    else
			selectedFoodItem.setChosenUnitNumber(
				selectedFoodItem.getChosenUnitNumber() + 1);
		    setStandardAmountViewText();
		    calculateNewResultandUpdateView(choseamount.getText());
		    return true;
		}
		else if (ev.getY() < 0) {
		    if (selectedFoodItem.getChosenUnitNumber() 
			    == 0) {
			selectedFoodItem.setChosenUnitNumber(
				selectedFoodItem.getFoodItem().getNumberOfUnits() - 1);
		    }
		    else
			selectedFoodItem.setChosenUnitNumber(
				selectedFoodItem.getChosenUnitNumber() - 1);
		    setStandardAmountViewText();
		    calculateNewResultandUpdateView(choseamount.getText());
		    return true;
		}
	    }
	}
	return false;
    }
    
    /**
     * Handles the menu options.
     * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
     */
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
	switch (item.getItemId()) {
	case R.id.SelectUnit_menu_item:
	    builder.show();
	}
	return super.onMenuItemSelected(featureId, item);
    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	boolean result = super.onCreateOptionsMenu(menu);
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.addselectedfooditemmenu,menu);
	return result;
    }

    /**
     * updates the edittextfield {@link #updateChoseAmount()}, based on value of {@link #selectedFoodItem.getChosenAmount}
     */
    private void updateChoseAmount() {
	double toCheckIfInt;
	if (selectedFoodItem.getChosenAmount() == 0) {
	    choseamount.setText("0");
	} else {
	    toCheckIfInt = selectedFoodItem.getChosenAmount() - new Double(selectedFoodItem.getChosenAmount()).intValue();
	    if (toCheckIfInt == 0.0) {//choseamount is is an integer value
		String temp = new Double(selectedFoodItem.getChosenAmount()).toString();
		int index = temp.indexOf('.');
		if (index < 0) index = temp.length() - 1;
		choseamount.setText(new Double(selectedFoodItem.getChosenAmount()).toString().substring(0, index));
	    }
	    else
		choseamount.setText(Double.toString(selectedFoodItem.getChosenAmount()));
	}
	choseamount.setSelection(choseamount.getText().length());
    }
}
