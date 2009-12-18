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

import android.os.Bundle;
import android.util.Log;

/**
 * a SelectedFoodItem is a {@link net.johandegraeve.helpdiabetes.FoodItem}, a
 * selected unit,  and a chosen amount. There's also an id, which is only set in objects
 * returned from methods in {@link net.johandegraeve.helpdiabetes.SelectedFoodItemDatabase} and which corresponds to the
 * row id of this object in the database.
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class SelectedFoodItem {

    /**
     * tag to be used by any method in this calss, when using {@link android.util.Log}
     */
    private static final String LOG_TAG = "HD-SelectedFoodItem";
    
    /**
     * a fooditem
     */
    private FoodItem foodItem;
    
    /**
     * the amount chosen 
     */
    private float chosenAmount;
    
    /**
     * a fooditem can have multiple units, this points to the selected unit, counting starts at 0 
     */
    private int chosenUnitNumber;

    /**
     * Only used for SelectedFoodItem's retrieved from {@link net.johandegraeve.helpdiabetes.SelectedFoodItemDatabase}.
     * When deleting/updating a SelectedFoodItem from the database, then the id should have a valid value. This is only the case for
     * SelectedFoodItem's retrieved from a {@link net.johandegraeve.helpdiabetes.SelectedFoodItemDatabase}.
     */
    private long id;
    
    /**
     * Constructor taking a FoodItem, Amount and UnitNumber as argument. The object's field 
     * {@link net.johandegraeve.helpdiabetes.SelectedFoodItem#id} will be set to 0.
     * @param newFoodItem the FoodItem to which the SelectedFoodItem's foodItem should be initialized
     * @param chosenAmount the amount to which the SelectedFoodItem's chosenAmount should be initialized
     * @param chosenUnitNumber the unitNumber to which the SelectedFoodItem's chosenUnitNumber should be initialized, 
     * the first unit has number 0
     */
    public SelectedFoodItem(FoodItem newFoodItem, float chosenAmount, int chosenUnitNumber) {
	Log.i(LOG_TAG, "Creating SelectedFoodItem " + newFoodItem.getItemDescription());
	this.foodItem = new FoodItem(newFoodItem);
	this.chosenAmount = chosenAmount;
	this.chosenUnitNumber = chosenUnitNumber;
	this.id = 0;
    }
    
    /**
     * Constructor taking a FoodItem, Amount, UnitNumber and Id as argument. Typically used by  
     * {@link net.johandegraeve.helpdiabetes.SelectedFoodItemDatabase#getSelectedFoodItemList}.
     * @param newFoodItem the FoodItem to which the SelectedFoodItem's foodItem should be initialized
     * @param chosenAmount the amount to which the SelectedFoodItem's chosenAmount should be initialized
     * @param chosenUnitNumber the unitNumber to which the SelectedFoodItem's chosenUnitNumber should be initialized, 
     * @param id the id to which the SelectedFoodItem's id should be initialized, 
     * the first unit has number 0
     */
    public SelectedFoodItem(FoodItem newFoodItem, float chosenAmount, int chosenUnitNumber, long id) {
	Log.i(LOG_TAG, "Creating SelectedFoodItem " + newFoodItem.getItemDescription());
	this.foodItem = new FoodItem(newFoodItem);
	this.chosenAmount = chosenAmount;
	this.chosenUnitNumber = chosenUnitNumber;
	this.id = id;
    }
    
    /**
     * Constructor taking an existing SelectedFoodItem as argument. All fields are copied in jew instances.
     * @param newFoodItem
     */
    public SelectedFoodItem(SelectedFoodItem newFoodItem) {
	this.foodItem = newFoodItem.getFoodItem();
	this.chosenAmount = newFoodItem.getChosenAmount();
	this.chosenUnitNumber = newFoodItem.getChosenUnitNumber();
	this.id = newFoodItem.getId();
    }
    
    /**
     * @return the FoodItem in a new object
     */
    public FoodItem getFoodItem() {
	return new FoodItem(foodItem);
    }
    
    /**
     * @return the chosenAmount
     */
    public float getChosenAmount() {
	return chosenAmount;
    }
    
    /**
     * @return the chosenUnitNumber
     */
    public int getChosenUnitNumber() {
	return chosenUnitNumber;
    }
    
    /**
     * @return the Id
     */
    public long getId() {
	return id;
    }

    /**
     * @param newFoodItem
     */
    public void setFoodItem(FoodItem newFoodItem) {
	this.foodItem = new FoodItem(newFoodItem);
    }
    
    /**
     * @param chosenAmount
     */
    public void setChosenAmount(float chosenAmount) {
	this.chosenAmount = chosenAmount;
    }
    
    /**
     * @param chosenUnitNumber
     */
    public void setChosenUnitNumber(int chosenUnitNumber) {
	this.chosenUnitNumber = chosenUnitNumber;
    }
    
    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return this.foodItem.toString();
    }
    
    /**
     * creates a Bundle for this SelectedFoodItem
     * @return the SelectedFoodItem in a Bundle
     */
    public Bundle toBundle() {
	Bundle b = new Bundle();
	b.putFloat("chosenAmount", chosenAmount);
	b.putInt("chosenUnitNumber",chosenUnitNumber);
	b.putBundle("foodItem", foodItem.toBundle());
	b.putLong("id", id);
	return b;
    }
    
    /**
     * creates a SelectedFoodItem from a Bundle
     * @param b the bundle
     * @return the SelectedFoodItem
     */
    static public SelectedFoodItem fromBundle(Bundle b) {
	SelectedFoodItem newSelectedFoodItem = 
	    new SelectedFoodItem(FoodItem.fromBundle(b.getBundle("foodItem")),
		                 b.getFloat("chosenAmount"),
		                 b.getInt("chosenUnitNumber"));
	newSelectedFoodItem.id = b.getLong("id");
	return newSelectedFoodItem;
    }
}
