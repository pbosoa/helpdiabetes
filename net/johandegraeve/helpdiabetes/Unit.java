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

/**
* A unit consists of 
* <ul> <li>unitDescription 
* 	<li>unitWeight
* 	<li>standardAmount<p>
* 	The following amounts are for 'StandardAmount' of 'UnitDescription'.<p>
* 	  For Example, if UnitDescription = gram, StandardAmount = 100, then the following amounts
* 	  are given for 100 grams
* 	<li>kcal : amount if kilocalories
* 	<li>protein : amount of Proteins in grams
* 	<li>carbs : amount of carbohydrates in grams
* 	<li>fat : amount of Fat in grams
* </ul>
* @version 1.0
* @author Johan Degraeve
*
*/ 
public class Unit {
    /**
     * Desriptive text
     */
    private String unitDescription;

    /**
     * the weight of one unit, < 0 if unitweight is unknown
     */
    private int unitWeight;
    
    /**
     * The amount of units, to which number of kilocalories, proteins, carbs and fat correspond, always > 0
     */
    private int standardAmount;
    
    /**
     * amount of kilocalories, < 0 if amount is unknown
     */
    private int kcal;
    
    /**
     * amount of Proteins in grams, < 0 if amount is unknown 
     */
    private float protein;
    
    /**
     * amount of carbs in grams, always >= 0
     */
    private float carbs;
    
    /**
     * amount of fat in grams, < 0 if amount is unknown
     */
    private float fat;

    /**
     * Creates a Unit. 
     * @param unitDescription
     * @param unitWeight if unitWeight < 0 then the new Unit will have unitWeight = -1
     * @param standardAmount if standardAmount <= 0, then the new Unit will have standardAmount = -1
     * @param kcal if kcal < 0 then the new Unit will have kcal = -1
     * @param protein if protein < 0 then the new Unit will have protein = -1
     * @param carbs if carbs < 0 then the new Unit will have carbs value = 0
     * @param fat if fat < 0 then the new Unit will have fat = -1
     */
    public Unit(String unitDescription,
	        int unitWeight,
	        int standardAmount,
	        int kcal,
	        float protein,
	        float carbs,
	        float fat) {
	this.unitDescription = unitDescription;
	if (unitWeight >= 0) {
	    this.unitWeight = unitWeight; 
	} else {
	    this.unitWeight = -1;
	}
	if (standardAmount > 0) {
	    this.standardAmount = standardAmount; 
	} else {
	    this.standardAmount = -1;
	}
	if (kcal >= 0) {
	    this.kcal = kcal; 
	} else {
	    this.kcal = -1;
	}
	if (protein >= 0) {
	    this.protein = protein; 
	} else {
	    this.protein = -1;
	}
	if (carbs >= 0) {
	    this.carbs = carbs; 
	} else {
	    this.carbs = 0;
	}
	if (fat >= 0) {
	    this.fat = fat; 
	} else {
	    this.fat = -1;
	}
    }
    
    /**
     * constructor
     * @param newUnit
     */
    public Unit (Unit newUnit) {
	this.carbs = newUnit.carbs;
	this.fat = newUnit.fat;
	this.kcal = newUnit.kcal;
	this.protein = newUnit.protein;
	this.standardAmount = newUnit.standardAmount;
	this.unitDescription = new String(newUnit.unitDescription);
	this.unitWeight = newUnit.unitWeight;
    }

    /**
     * @return the unitDescription in a new String
     */
    public String getDescription() {
	return new String(unitDescription);
    }

    /**
     * @return the unitWeight
     */
    public int getWeight() {
	return unitWeight;
    }

    /**
     * @return the standard Amount
     */
    public int getStandardAmount() {
	return standardAmount;
    }

    /**
     * @return kcal
     */
    public int getKcal() {
	return kcal;
    }

    /**
     * @return protein
     */
    public float getProtein() {
	return protein;
    }

    /**
     * @return carbs
     */
    public float getCarbs() {
	return carbs;
    }

    /**
     * @return fat
     */
    public float getFat() {
	return fat;
    }
    
    /**
     * @return the Unit in a Bundle
     */
    public  Bundle toBundle() {
	Bundle b = new Bundle();
	b.putFloat("carbs", carbs);
	b.putFloat("fat", fat);
	b.putInt("kcal", kcal);
	b.putFloat("protein", protein);
	b.putInt("standardAmount", standardAmount);
	b.putString("unitDescription", unitDescription);
	b.putInt("unitWeight", unitWeight);
	return b;
    }
    
    /**
     * @param b Bundle that contains a Unit
     * @return a unit from a Bundle
     */
    public static Unit fromBundle(Bundle b) { 
	Unit newunit = new Unit(b.getString("unitDescription"),
			   b.getInt("unitWeight"),
			   b.getInt("standardAmount"),
			   b.getInt("kcal"),
			   b.getFloat("protein"),
			   b.getFloat("carbs"),
			   b.getFloat("fat"));
	return newunit;
    }	
}