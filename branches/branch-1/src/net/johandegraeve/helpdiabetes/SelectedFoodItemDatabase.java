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
 *  Please contact  Johan Degraeve at johan.degraeve@johandegraeve.net if you need
 *  additional information  or have any questions.
 */
package net.johandegraeve.helpdiabetes;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class allows storage of the selected fooditems in a database. Methods are available for retrieval,
 * update, add.<br>
 * When adding or updating a SelectedFoodItem, a current timestamp is added in the database.<br>
 * <br>
 * Method {@link #cleanUp()} will mark all SelectedFoodItems in the database as Deleted, although they are not effectively Deleted.<br>
 * <br>
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class SelectedFoodItemDatabase {
    /**
     * tag to be used by any method in this classs, when using {@link android.util.Log}
     */
    private static final String LOG_TAG = "HD-SelectedFoodItemDatabase";
    
    /**
     * Database name 
     */
    private static final String SELECTED_ITEMS_DB_NAME = "selectedFoodItems";
    /**
     * Table Name for storing the SelectedFoodItems
     */
    private static final String SELECTED_ITEM_TABLE_NAME = "t_items";
    /**
     * Database versions
     */
    private static final int DATABASE_VERSION = 1;
    
    /**
     * Keys used as column names in the table t_items
     */
    private static final String KEY_ID = "Id"; 
    @SuppressWarnings(value = { "all" })
    private static final String KEY_ITEMDESCRIPTION = "ItemDescription"; 
    @SuppressWarnings(value = { "all" })
    private static final String KEY_UNITDESCRIPTION = "UnitDescription";
    @SuppressWarnings(value = { "all" })
    private static final String KEY_CARBS = "Carbs";
    @SuppressWarnings(value = { "all" })
    private static final String KEY_FAT = "Fat";
    @SuppressWarnings(value = { "all" })
    private static final String KEY_PROTEIN = "Protein"; 
    @SuppressWarnings(value = { "all" })
    private static final String KEY_KCAL = "Kcal";
    @SuppressWarnings(value = { "all" })
    private static final String KEY_STANDARDAMOUNT = "StandardAmount"; 
    @SuppressWarnings(value = { "all" })
    private static final String KEY_UNITWEIGHT = "UnitWeight";
    @SuppressWarnings(value = { "all" })
    private static final String KEY_TIMESTAMP = "TimeStamp";
    @SuppressWarnings(value = { "all" })
    private static final String KEY_DELETED = "Deleted";
    @SuppressWarnings(value = { "all" })
    private static final String KEY_CHOSENAMOUNT = "ChosenAmount";
    
    /**
     * Used to create the database.
     */
    private static final String DATABASE_CREATE = 
	"create table if not exists " +
	SELECTED_ITEM_TABLE_NAME +
	" (" +
	KEY_ID + " integer primary key autoincrement, " +
	KEY_ITEMDESCRIPTION + " text not null, " + 
	KEY_UNITDESCRIPTION + " text, " +
	KEY_CARBS + " float, " +
	KEY_FAT + " float, " +
	KEY_PROTEIN + " float, " +
	KEY_KCAL + " integer, " +
	KEY_STANDARDAMOUNT + " integer, " + 
	KEY_UNITWEIGHT + " integer, " +
	KEY_TIMESTAMP + " text, " +
	KEY_DELETED + " integer, " +
	KEY_CHOSENAMOUNT + " float); ";
    
    /**
     * The context the application is running in, which cna be used for creating the database.
     */
    private final Context context;
    
    /**
     * @see   DatabaseHelper
     */
    private DatabaseHelper DBHelper;
	
    /**
     * The database
     */
    private SQLiteDatabase itemDatabase;
    
    /**
     * Constructor taking a context as parameter, which will initialize the local field context.
     * Constructor initializes the DBHelper.
     * @param ctx
     */
    public SelectedFoodItemDatabase(Context ctx) {
	this.context = ctx;
	DBHelper = new DatabaseHelper(this.context);
    }
    
    /**
     * DatabaseHelper extends {@link android.database.sqlite.SQLiteOpenHelper#SQLiteOpenHelper(Context, String, android.database.sqlite.SQLiteDatabase.CursorFactory, int)}
     *
     * @version 1.0
     * @author Johan Degraeve
     *
     */
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
	/**
	 * Constructor, calls super constructor, see 
	 * @see android.database.sqlite.SQLiteOpenHelper#SQLiteOpenHelper(Context, String, android.database.sqlite.SQLiteDatabase.CursorFactory, int)
	 * @param context
	 */
	DatabaseHelper(Context context) 
	{
	    super(context, SELECTED_ITEMS_DB_NAME, null, DATABASE_VERSION);
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
	    db.execSQL(DATABASE_CREATE);
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, 
		int newVersion) 
	{
	    Log.w(LOG_TAG, "Upgrading database from version " + oldVersion 
		    + " to "
		    + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + SELECTED_ITEM_TABLE_NAME);
	    onCreate(db);
	}
    }    

    /**
     * Opens the database
     * 
     * @return this
     * @throws SQLException
     */
    private SelectedFoodItemDatabase open() throws SQLException 
    {
        itemDatabase = DBHelper.getWritableDatabase();
        return this;
    }
    
    /**
     * closes the database 
     */
    private void close() 
    {
        DBHelper.close();
        itemDatabase = null;
    }
    
    /**
     * adds a SelectedFoodItem to the database. A column 'timeStamp' will be set to the current time, being a long value equal to the
     * number of milliseconds since January 1, 1970, 00:00:00 GMT 
     * @param selectedFoodItem
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addSelectedFoodItem(SelectedFoodItem selectedFoodItem) {
	long returnvalue;
	open();
	ContentValues values = new ContentValues();
	Log.i(LOG_TAG, "Adding selectedFooditem " + selectedFoodItem.getFoodItem().getItemDescription());
	values.put(KEY_ITEMDESCRIPTION, selectedFoodItem.getFoodItem().getItemDescription());
	values.put(KEY_UNITDESCRIPTION, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getDescription());
	values.put(KEY_CARBS, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getCarbs());
	values.put(KEY_FAT, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getFat());
	values.put(KEY_PROTEIN, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getProtein());
	values.put(KEY_KCAL, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getKcal());
	values.put(KEY_STANDARDAMOUNT, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getStandardAmount());
	values.put(KEY_UNITWEIGHT, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getWeight() );
	values.put(KEY_TIMESTAMP, Long.toString((new Date()).getTime()));
	values.put(KEY_DELETED, 0);
	values.put(KEY_CHOSENAMOUNT, selectedFoodItem.getChosenAmount());
	returnvalue = itemDatabase.insert(SELECTED_ITEM_TABLE_NAME, KEY_UNITDESCRIPTION, values);
	close();
	return returnvalue;
    }
    
    /**
     * 
     * @return an array of {@link SelectedFoodItem}, all elements in the database that
     * have Delete = 0 (false) will be returned, returns null if the list is empty or there are no 'not deleted' items
     */
    public SelectedFoodItem[] getSelectedFoodItemList() {
	SelectedFoodItem[] returnvalue = null;
	Cursor cursor;
	int  ItemDescriptionColumn;
        int  UnitDescriptionColumn;
        int  CarbsColumn;
        int  FatColumn;
        int  ProteinColumn;
        int  KcalColumn;
        int  StandardAmountColumn;
        int  UnitWeightColumn;
        @SuppressWarnings("unused")
	int  TimeStampColumn;
        @SuppressWarnings("unused")
	int  DeletedColumn;
        int  ChosenAmountColumn;
        int IdColumn;
 	
        open();
	cursor = itemDatabase.query(
		false,
		SELECTED_ITEM_TABLE_NAME, 
		null,
		"Deleted like 0",//the non deleted items
		null, 
		null, 
		null, 
		"TimeStamp",
		null);
	
	if (cursor !=  null) {
	    ItemDescriptionColumn = cursor.getColumnIndex(KEY_ITEMDESCRIPTION);
	    UnitDescriptionColumn = cursor.getColumnIndex(KEY_UNITDESCRIPTION);
	    CarbsColumn = cursor.getColumnIndex(KEY_CARBS);
	    FatColumn = cursor.getColumnIndex(KEY_FAT);
	    ProteinColumn = cursor.getColumnIndex(KEY_PROTEIN);
	    KcalColumn = cursor.getColumnIndex(KEY_KCAL);
	    StandardAmountColumn = cursor.getColumnIndex(KEY_STANDARDAMOUNT);
	    UnitWeightColumn = cursor.getColumnIndex(KEY_UNITWEIGHT);
	    TimeStampColumn = cursor.getColumnIndex(KEY_TIMESTAMP);
	    DeletedColumn = cursor.getColumnIndex(KEY_DELETED);
	    ChosenAmountColumn = cursor.getColumnIndex(KEY_CHOSENAMOUNT);
	    IdColumn = cursor.getColumnIndex(KEY_ID);
	    
	    if (cursor.moveToFirst()) {
		returnvalue = new SelectedFoodItem[cursor.getCount()];
		int i = 0;
		do {
		      returnvalue[i] = 
			  new SelectedFoodItem(
				  new FoodItem(cursor.getString(ItemDescriptionColumn),
					       new Unit(cursor.getString(UnitDescriptionColumn), 
						        cursor.getInt(UnitWeightColumn), 
					                cursor.getInt(StandardAmountColumn), 
						        cursor.getInt(KcalColumn), 
						        cursor.getFloat(ProteinColumn), 
						        cursor.getFloat(CarbsColumn), 
						        cursor.getFloat(FatColumn))),	          
			          cursor.getFloat(ChosenAmountColumn), 
				  0,
				  cursor.getInt(IdColumn));
		      i = i  + 1;
		} while (cursor.moveToNext());
	    }
	}
	close();
	return returnvalue;
    }
    
    /**
     * Marks all SelectedFoodItems in the database as 'Deleted'.<br>
     * If the database was not open while calling this function then it will be opened first and closed afterwards.
     */
    public void cleanUp() {
	ContentValues values;
	open();
	    values = new ContentValues();
	    values.put("Deleted", 1);
	    itemDatabase.update(SELECTED_ITEM_TABLE_NAME, values, null, null);

	close();
    }
    
    /**
     * Effectively deletes all SelectedFoodItems from the database.<br>
     * If the database was not open while calling this function then it will be opened first and closed afterwards.
     */
    public void deleteAll() {
	open();
	itemDatabase.execSQL("DROP TABLE IF EXISTS " + SELECTED_ITEM_TABLE_NAME);
	DBHelper.onCreate(itemDatabase);
	close();

    }
    
    /**
     * gets total amount of carbs for the selected FoodItems
     * @return total amount of carbs for the selected FoodItems
     */
    public float getTotalCarbs() {
	SelectedFoodItem[] list = getSelectedFoodItemList();
	
	if (list == null) {
	    return 0;
	} else {
	    float returnvalue = 0;
	    for (int i = 0;i < list.length; i++ ) {
		returnvalue = returnvalue +
			list[i].getFoodItem().getUnit(list[i].getChosenUnitNumber()).getCarbs() *
			list[i].getChosenAmount() /  
			list[i].getFoodItem().getUnit(list[i].getChosenUnitNumber()).getStandardAmount();
	    }
	    return returnvalue;
	}
    }

    /**
     * gets total amount of fats for the selected FoodItems
     * @return total amount of fats for the selected FoodItems
     */
    public float getTotalFats() {
	SelectedFoodItem[] list = getSelectedFoodItemList();
	
	if (list == null) {
	    return 0;
	} else {
	    float returnvalue = 0;
	    for (int i = 0;i < list.length; i++ ) {
		returnvalue = returnvalue +
			list[i].getFoodItem().getUnit(list[i].getChosenUnitNumber()).getFat() *
			list[i].getChosenAmount() /  
			list[i].getFoodItem().getUnit(list[i].getChosenUnitNumber()).getStandardAmount();
	    }
	    return returnvalue;
	}
    }

    /**
     * gets total amount of kilocalories for the selected FoodItems
     * @return total amount of kilocalories for the selected FoodItems
     */
    public float getTotalKcal() {
	SelectedFoodItem[] list = getSelectedFoodItemList();
	
	if (list == null) {
	    return 0;
	} else {
	    float returnvalue = 0;
	    for (int i = 0;i < list.length; i++ ) {
		returnvalue = returnvalue +
			list[i].getFoodItem().getUnit(list[i].getChosenUnitNumber()).getKcal() *
			list[i].getChosenAmount() /  
			list[i].getFoodItem().getUnit(list[i].getChosenUnitNumber()).getStandardAmount();
	    }
	    return returnvalue;
	}
    }
    
    /**
     * gets total amount of proteins for the selected FoodItems
     * @return total amount of proteins for the selected FoodItems
     */
    public float getTotalProteins() {
	SelectedFoodItem[] list = getSelectedFoodItemList();
	
	if (list == null) {
	    return 0;
	} else {
	    float returnvalue = 0;
	    for (int i = 0;i < list.length; i++ ) {
		returnvalue = returnvalue +
			list[i].getFoodItem().getUnit(list[i].getChosenUnitNumber()).getProtein() *
			list[i].getChosenAmount() /  
			list[i].getFoodItem().getUnit(list[i].getChosenUnitNumber()).getStandardAmount();
	    }
	    return returnvalue;
	}
    }

    /**
     * makes an update in the database for the selectedFoodItem with ID matching {@link net.johandegraeve.helpdiabetes.SelectedFoodItem#getId()}
     * for this item.
     * @param selectedFoodItem
     * @return the number of rows affected which should normally be 1, if the value is 0 then something is wrong
     */
    public int updateSelectedFoodItem(SelectedFoodItem selectedFoodItem) {
	int returnvalue;
	open();
	ContentValues values = new ContentValues();
	Log.i(LOG_TAG, "Updating selectedFooditem " + selectedFoodItem.getFoodItem().getItemDescription());
	values.put(KEY_ITEMDESCRIPTION, selectedFoodItem.getFoodItem().getItemDescription());
	values.put(KEY_UNITDESCRIPTION, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getDescription());
	values.put(KEY_CARBS, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getCarbs());
	values.put(KEY_FAT, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getFat());
	values.put(KEY_PROTEIN, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getProtein());
	values.put(KEY_KCAL, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getKcal());
	values.put(KEY_STANDARDAMOUNT, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getStandardAmount());
	values.put(KEY_UNITWEIGHT, selectedFoodItem.getFoodItem().getUnit(selectedFoodItem.getChosenUnitNumber()).getWeight() );
	values.put(KEY_TIMESTAMP, Long.toString((new Date()).getTime()));
	values.put(KEY_DELETED, 0);
	values.put(KEY_CHOSENAMOUNT, selectedFoodItem.getChosenAmount());
	Log.i(LOG_TAG,"selectedFoodItem.getId() = " + selectedFoodItem.getId());
	returnvalue = itemDatabase.update(SELECTED_ITEM_TABLE_NAME, values, KEY_ID + " =  " + selectedFoodItem.getId(), null);
	close();
	return returnvalue;
    }
    
}
 