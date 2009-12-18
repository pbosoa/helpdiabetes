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

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * ListActivity to display the list of selected fooditems, and to allow selecting an item that can be changed ...
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class SelectedFoodItemList extends ListActivity {
    /**
     * tag to be used by any method in this calss, when using {@link android.util.Log}
     */
    @SuppressWarnings("unused")
    private static final String LOG_TAG = "HD-SelectedFoodItemActivity";
    
    /**
     * selected fooditem list in an array adaptor
     */
    SelectedFoodItemArrayAdapter list;

    /**
     * used in startActivityForResult for AddSelectedFoodItem.class
     */
    private static final int ACTIVITY_ADDSELECTEDFOODITEM = 1;
   
    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.selectedfooditemlist);
	list =  new SelectedFoodItemArrayAdapter(this, R.layout.selectedfooditemlistrow);
	setListAdapter(list);
    }
    
    
    /**
     * when an item is clicked, a new Intent will be created with AddSelectedFoodItem.class and a Bundle containing the item
     * a new activity is then started with ActivityForResult
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
	    Intent newIntent = new Intent(this, AddSelectFoodItem.class);
	    Bundle b = list.getItem(position).toBundle();
	    newIntent.putExtras(b);
	    newIntent.putExtra("isItaNewItemToAdd", false);
	    startActivityForResult(newIntent, ACTIVITY_ADDSELECTEDFOODITEM);
    }    
    
    /**
     * Handles return value from the started activity.<br>
     * <ul>
     * 	<li><i>AddSelectedFoodItem</i><p>
     * 		<ul>
     * 			<li>
     * 		RESULT_OK<br>
     * 		Activity will finish.
     * 			</li>
     * 			<li>
     * 		Any other<br>
     * 		Activity is resumed.
     * 			</li>
     * 		</ul>
     * 	</li>
     * </ul>
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	switch (requestCode) {
	case ACTIVITY_ADDSELECTEDFOODITEM:
	    if (resultCode == RESULT_OK)
		finish();
	    break;
	}
    }
}
