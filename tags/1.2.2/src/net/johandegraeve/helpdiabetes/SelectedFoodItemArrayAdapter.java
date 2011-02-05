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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * ArrayAdapter for SelectedFoodItem
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class SelectedFoodItemArrayAdapter extends ArrayAdapter<SelectedFoodItem>{
    /**
     * tag to be used by any method in this calss, when using {@link android.util.Log}
     */
    @SuppressWarnings("unused")
    private static final String LOG_TAG = "HD-SelectedFoodItemArrayAdapter";

    /**
     * Will refer to this instance of SelectedFoodItemArrayAdapter class
     */
    private Context callingContext;

    /**
     * Constructor
     * @param context callingContext
     * @param textViewResourceId
     */
    public SelectedFoodItemArrayAdapter(Context context, int textViewResourceId) {
	super(context, textViewResourceId);
	callingContext = context;
	SelectedFoodItemDatabase db = new SelectedFoodItemDatabase(context);
	SelectedFoodItem[] selectedFoodItemArray = db.getSelectedFoodItemList();
	if (selectedFoodItemArray != null) {
	    for (int i = 0;i < selectedFoodItemArray.length ; i++) {
		add(selectedFoodItemArray[i]);
	    }
	}
    }
    
    /**
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
	View v = convertView;
	SelectedFoodItem selectedFoodItem = getItem(position);
	FoodItem foodItem = selectedFoodItem.getFoodItem();
	if (selectedFoodItem != null) {
	    if (v == null) {
		LayoutInflater vi = (LayoutInflater) callingContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = vi.inflate(R.layout.selectedfooditemlistrow, null);
	    }
	    TextView tt = (TextView) v.findViewById(R.id.selectedfooditemlist_toptext);
	    if (tt != null) {

		tt.setText(
			selectedFoodItem.getChosenAmount() +
			" " +
			foodItem.getUnit(selectedFoodItem.getChosenUnitNumber()).getDescription() +
			" " +
			foodItem.getItemDescription());                            
	    }
	    TextView bt = (TextView) v.findViewById(R.id.selectedfooditemlist_bottomtext);
	    if (bt != null) {

		bt.setText(
			" " + 
			((float)Math.round(selectedFoodItem.getChosenAmount() *
			foodItem.getUnit(selectedFoodItem.getChosenUnitNumber()).getCarbs() /
			foodItem.getUnit(selectedFoodItem.getChosenUnitNumber()).getStandardAmount()
			*10)) / 10.0 +
			" " +
			callingContext.getResources().getString(R.string.amount_of_carbs));                            
	    }
	}
	return v;
    }

}
