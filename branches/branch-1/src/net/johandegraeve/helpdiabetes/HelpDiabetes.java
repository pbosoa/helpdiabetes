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

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;


/**
 * This is the activity class which will define the main screen with the list of
 * fooditems, from which user can select, and also menu options are defined here
 *
 * @version 1.1
 * @author Johan Degraeve
 *
 */
public class HelpDiabetes extends ListActivity {
    /**
     * that's the view where the user enters the search string
     */
    private EditText searchTextView ;

    /**
     * will always be set to current searchstring, needed in case config change happens, to reset the same string in the 
     * searchTextview
     */
    private String searchTextViewString = null;
    
    /**
     * a ListActivity needs an {@link android.widget.ArrayAdapter}, that's what the {@link net.johandegraeve.helpdiabetes.FoodItemList} is here for. 
     */
    private FoodItemList fooditemlist = null;

    /**
     * tag to be used by any method in this classs, when using {@link android.util.Log}
     */
    private static final String TAG = "HelpDiabetes";
    /**
     * set to true for debugging
     */
    private static final boolean D = true;

    /**
     * Static used in {@link #handleEmptyList()}, which is used for asking the user if list of selections should be emptied.
     */
    protected static final int OK = 0;
    
    /**
     * Will refer to this instance of HelpDiabetes class
     */
    private static  Context thisContext;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");
	thisContext = this;
	setContentView(R.layout.helpdiabetes);
	fooditemlist = null;
	setupViews(savedInstanceState);
	if (databaseList() == null || databaseList().length == 0) {
	    new AlertDialog.Builder(thisContext)
	    .setTitle(R.string.licence_info)
	    .setMessage("HelpDiabetes " + 
		    getResources().getString(R.string.version) + " " + 
		    getResources().getString(R.string.version_number) + " " + 
		    getResources().getString(R.string.license_message_part))
	    .setPositiveButton(R.string.accept_license,new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton)
		{
		    ;
		}
	    })
	    .show();
	}
    }
    
    /**
     * Sets up the views, and initializes the fooditemlist if null
     * @param savedInstanceState saved bundle
     */
    private void setupViews(Bundle savedInstanceState) {
	searchTextView = (EditText) findViewById(R.id.SearchTextView);
	if (searchTextViewString != null)
	    if (searchTextViewString.length() > 0) {
		searchTextView.setText(searchTextViewString);
		searchTextView.setSelection(searchTextViewString.length());
	    }
	searchTextView.addTextChangedListener(new TextWatcher() {

	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		;
	    }

	    public void onTextChanged(CharSequence s, int start, int before, int count) {
		searchTextViewString = s.toString();
		setSelection(fooditemlist.getFirstMatchingItem(s));
	    }

	    public void afterTextChanged(Editable s) {
		;

	    }
	});

	if (fooditemlist == null) {
	    fooditemlist = new FoodItemList(this, R.layout.helpdiabetesrow, Integer.parseInt(this.getString(R.string.maximumSearchStringLength)));
	    //fooditemlist.initializeFoodItemList( savedInstanceState != null ? savedInstanceState.getBundle("fooditemlist"):null);
	    //not calling following method with the bundle because it doesn't solve my problem when using this.
	    fooditemlist.initializeFoodItemList(null);
	    setListAdapter(fooditemlist);
	} else {
	    setListAdapter(fooditemlist);
	    setSelection(fooditemlist.getFirstMatchingItem(searchTextView.getText())); 
	}
    }
    
    /**
     * this method is called when phone changes from landscape to portrait or vice versa
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     */
    @Override
    public void onConfigurationChanged (Configuration newConf) {
	super.onConfigurationChanged(newConf);
	setupViews(null);
    }

    /**
     * with the text from {@link HelpDiabetes#searchTextView} a call is made to {@link net.johandegraeve.helpdiabetes.FoodItemList#getFirstMatchingItem(CharSequence)}, the
     * result of it is used to set the selected food item in the list. 
     */
    public void triggerSearching() {
	setSelection(fooditemlist.getFirstMatchingItem(searchTextView.getText()));    
    }

    /**
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	boolean result = super.onCreateOptionsMenu(menu);
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.helpdiabetesmenu,menu);
	return result;
    }
    
    /**
     * Handles menu
     * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
     */
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
	Intent newIntent;
	switch (item.getItemId()) {
	case R.id.ShowList_menu_item:
	    newIntent = new Intent(this, SelectedFoodItemList.class);
	    startActivity( newIntent);
	    return true;
	case R.id.EmptyList_menu_item:
	    handleEmptyList();
	    return true;
	case R.id.CalculateTotal_menu_item:
	    startActivity(new Intent(this,ViewTotals.class));
	    return true;
	case R.id.Info_Screen_menu_item:
	    new AlertDialog.Builder(thisContext)
	    .setTitle(R.string.info_screen_title)
	    .setMessage("HelpDiabetes " + 
		    getResources().getString(R.string.version) + " " +
		    getResources().getString(R.string.version_number) + " " +
		    getResources().getString(R.string.info_screen_message) + fooditemlist.getFoodTableSource())
	    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {;}
	    })
	    .show();
	case R.id.Settings_menu_item:
	    newIntent = new Intent(this, MyPreferencesActivity.class);
	    startActivity( newIntent);
	    return true;
	}
	return super.onMenuItemSelected(featureId, item);
    }

    /**
     * When user clicks an item, onListItemClick is called, which will create an Intent for  {@link net.johandegraeve.helpdiabetes.AddSelectFoodItem},
     * add {@link net.johandegraeve.helpdiabetes.SelectedFoodItem#toBundle() Selected FoodItem bundle}, and start a new Activity.
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
	    Intent newIntent = new Intent(this, AddSelectFoodItem.class);
	    SelectedFoodItem selectedFoodItem = new SelectedFoodItem(fooditemlist.getFoodItem(position),0,0);
	    Bundle b = selectedFoodItem.toBundle();
	    newIntent.putExtras(b);
	    newIntent.putExtra("isItaNewItemToAdd", true);
	    startActivity( newIntent);
    }
    
    /**
     * creates and shows an AlertDialog.Builder, which asks for confirmation to  
     * {@link net.johandegraeve.helpdiabetes.SelectedFoodItemDatabase#cleanUp() cleanUp} the SelectedFoodItemDatabase.
     */
    private void handleEmptyList() {
	new AlertDialog.Builder(thisContext)
	.setMessage(R.string.confirm_empty_list)
	.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int whichButton)
	    {
		new SelectedFoodItemDatabase(thisContext).cleanUp();
	    }
	})
	.show();
    }
    
    /**
     * overriding purely for logging
     * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
     */
    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");
    }

    /**
     * overriding purely for logging
     * @see android.app.Activity#onResume()
     */
    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "++ ON RESUME ++");
    }

    /**
     * overriding purely for logging
     * @see android.app.Activity#onPause()
     */
    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    /**
     * overriding purely for logging
     * @see android.app.Activity#onStop()
     */
    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    /**
     * overriding purely for logging
     * @see android.app.Activity#onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    /**
     * overriding purely for logging
     * @see android.app.Activity#onSaveInstanceState(Bundle)
     */
    @Override
    public void onSaveInstanceState(Bundle savedBundle) {
        super.onSaveInstanceState( savedBundle);
        if(D) Log.e(TAG, "--- ON SaveInstanceState ---");
    }

    /**
     * overriding purely for logging
     * @see android.app.Activity#onRestoreInstanceState(Bundle)
     */
    @Override
    public void onRestoreInstanceState(Bundle savedBundle) {
        super.onRestoreInstanceState( savedBundle);
        if(D) Log.e(TAG, "--- ON restoreInstanceState ---");
    }



}
