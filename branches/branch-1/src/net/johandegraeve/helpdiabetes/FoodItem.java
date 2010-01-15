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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;


/**
 * A FoodItem holds<p>
 * <ul><li>the description of the FoodItem</li> 
 * <li>a list of Units.</li>
 * </ul> 
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class FoodItem implements Comparable <FoodItem> {
    /**
     * tag to be used by any method in this calss, when using {@link android.util.Log}
     */
    private static final String LOG_TAG = "HD-FoodItem";

    /**
     * the ItemDescription
     */
    private String itemDescription;
    /**
     * List of Units
     */
    private List<Unit> unitList = null;

    /**
     * FoodItem constructor, taking a byte array as input. This array is the char representation of the input string.<p>
     * Examples (note that aSourceLine should be the char representation of such an example):<p>
     * <ul>
     * 	<li><i>Apple| imported| raw,grams,100,49.00,0.30,10.60,0.2,piece,1,96,0.7,23.2,0,,,,,,</i><p>
     * 	    	In this example :<br>
     * 	    	Description = "Apple, imported, raw" &nbsp &nbsp Note that ',' is represend in aSourceLine by '|'<br>
     * 	    	The first unit has the name "grams"<br>
     * 		This first unit has "100" as standard amount, meaning the next values are for 100 grams of Apple<br>
     * 		100 grams of Apple contain 49 kilocalories, 0.30 grams of proteins, 10.60 grams of carbohydrates and 0.2 grams of fat<br>
     * 		In this example there's a second unit : 1 piece of apple contains 96 kilocalories, 0.7 grams of proteins, 23.2 grams of carbohydrates and
     * 		0 grams of fat<br>
     * 		There's no indication of the weight of a unit, the default value '-1' will be taken, to show that the value should not be taken into account.<br>
     * 	</li>
     * 	<li><i>Bread| white| bake-off| ready to eat,grams,100,259.00,9.10,50.20,1.6,slice=50,1,130,4.55,25.1,0.8,,,,,,</i><p>
     * 	    	In this example :<br>
     * 	    	Description = "Bread, white, bake-off, ready to eat"<br>
     * 	    	The first unit has the name "grams", has "100" as standard amount<br>
     * 		100 grams of bread contain 259 kilocalories, 9.10 grams of proteins, 50.20 grams of carbohydrates and 1.6 grams of fat<br>
     * 		In this example there's a second unit : 1 slice. Note "slice=50". This means that 1 slice weighs 50 grams, the weight of this unit will be 50<br>
     * 	</li>
     * 	<li><i>Bread| white| industry made,grams,100,258.00,9.40,46.90,2.9,slice (25 grams)=25,1,65,2.35,11,725,0.725,slice (50 grams)=50,1,129,4.7,23,1.45</i><p>
     * 	    	This example is similar to previous example but the unit description also contains the amount of grams for one unit.<br>
     * 		This is purely informative and belongs to the unitdescription field.<br>
     * 		In this example there's also a second unit for a slice of bread of 50 grams. Here the unitdescription = "slice (50 grams)", which weighs 50 grams
     * 	</li>
     * </ul>
     * <p>
     * <b>Important</b><br>
     * If no Description field is present then an empty string is used. (however this seems very useless)<br>
     * If no UnitDescription field is present then an empty string is used. (this can be useful, it means just x amount of the thing)<br>
     * If the UnitDescription contains no "=" or it contains an "=" but the text after it is not parsable to int, then value for UnitWeight = -1<br>
     * If the StandardAmount field is not present, then the value is set to 1. (can be useful)<br>
     * If the field Fat is not present, then the value will be set to -1. <br>
     * If the field Protein is not present, then the value will be set to -1.<br> 
     * If the field Kcal is not present, then the value will be set to -1.<br>
     * The first unit must contain a valid field for Carbs, parsable to Int, otherwise an InvalidParameterException will be thrown.<br>
     * There will be as mainy units as there are valid parsable Carb values (ie column ..., ...)<br>
     * @param aSourceLine a line as read from a typical comma separate value source file, in a byte array. After calling the constructor, 
     * aSourceLine can be changed or deleted by the caller.<br>
     * @throws InvalidSourceLineException 
     */
    public FoodItem(byte[] aSourceLine) throws InvalidSourceLineException {
	String readline = null;
	try {
	    readline = new String(aSourceLine, "ISO-8859-1");
	} catch (UnsupportedEncodingException e1) {
	    // TODO Auto-generated catch block
	    readline = new String(aSourceLine);
	}
	String[] parts;// whill hold each part from the input , split by ','
	String[] descandweight;//will hold description and weight
	int unitWeightInSource = 0;
	int standardAmountInSource = 0;
	int kcalInSource = 0;
	float proteinInSource = 0;
	float carbsInSource = 0;
	float fatInSource = 0;

	parts = readline.split(",");
	this.setItemDescription(parts[0].replace('|', ','));

	unitList = new ArrayList<Unit>();

	/* now get to the list of units, as long as the 'carb' value is not empty
	 * we can continue. The other values in the source file are optional
	 */
	for (int i = 1;i<parts.length;i=i+6) {
	    if (!(i + 4 > parts.length - 1) ) {
		try {
		    carbsInSource = Float.parseFloat(parts[i+4]);
		} catch (NumberFormatException e) {
		    //parsing to float failed, that means the parsed string was empty,
		    //so there's no more units
		    if (i == 1) {//the first unit must contain a valid carb amount
			throwInvalidSourceLineException("First Unit does not contain a parsable Carb value", parts, i + 4,aSourceLine);
		    } else
			break;
		}
	    } else 	{ //the string array parts is not long enough to hold an additional unit
		//if it was the first unit then throw exception
		if (i == 1) {
		    throwInvalidSourceLineException("First Unit does not contain a parsable Carb value", parts, i + 4,aSourceLine);
		} else {//it was not the first unit so just stop
		    break;
		}
	    }

	    /*now parse also the other attributes */
	    /* first column B, .. need to be split by '='
	     * first part contains the actual unit description like 'gram' or 'piece'
	     * second part, optionally contains the weight in grams of one unit
	     */
	    descandweight = parts[i].split("=");
	    //unit.data.setUnitDescription(descandweight[0]);
	    if (descandweight.length > 1) { 
		try {
		    unitWeightInSource = Integer.parseInt(descandweight[1]);
		} catch (NumberFormatException e) {
		    try {
			throwInvalidSourceLineException(
				"Invalid value for unit weight, must be Integer value",
				parts, i, aSourceLine);
		    } catch (InvalidSourceLineException e2) {
			// here catching this exception in stead of throwing it further up
			// because the position is not yet correct
			InvalidSourceLineException tothrow = new InvalidSourceLineException(
				e2.getMessage(), 
				e2.getPosition() + descandweight[0].length(), aSourceLine );
			throw (tothrow);
		    }
		}
	    } else {
		//there was no "="
		unitWeightInSource = -1;
	    }
	    try {
		standardAmountInSource = (Math.round(Float.parseFloat(parts[i+1])));
	    } catch (NumberFormatException e) {
		if (parts[i+1].length() == 0) {
		    standardAmountInSource = 1;//default value for amount
		} else {
		    throwInvalidSourceLineException("Invalid value for Standard amount, must be decimal value", parts, i + 1, aSourceLine);
		}
	    }
	    try {
		kcalInSource = (Math.round(Float.parseFloat(parts[i+2])));
	    } catch (NumberFormatException e) {
		if (parts[i+2].length() == 0) {
		    kcalInSource = -1;
		} else {
		    throwInvalidSourceLineException("Invalid value for kcal, must be decimal value", parts, i + 2, aSourceLine);
		}
	    }
	    try {
		proteinInSource = (Float.parseFloat(parts[i+3]));
	    } catch (NumberFormatException e) {
		if (parts[i+3].length() == 0) {
		    proteinInSource = -1;
		} else {
		    throwInvalidSourceLineException("Invalid value for protein, must be decimal value", parts, i + 3, aSourceLine);
		}
	    }
	    try {
		fatInSource = (Float.parseFloat(parts[i+5]));
	    } catch (NumberFormatException e) {
		if (parts[i+5].length() == 0) {
		    fatInSource = -1;
		} else {
		    throwInvalidSourceLineException("Invalid value for fat, must be decimal value", parts, i + 5, aSourceLine);
		}
	    } catch (ArrayIndexOutOfBoundsException e2) {
		fatInSource = -1;
	    }
	    this.unitList.add(new Unit(descandweight[0],
		                       unitWeightInSource,
		                       standardAmountInSource,
		                       kcalInSource,
		                       proteinInSource,
		                       carbsInSource,
		                       fatInSource));
	}
    }

    /**
     * FoodItem constructor, taking a FoodItem as parameter. The input object is copied, so it can be updated
     * or deleted after calling the constructor.
     * @param newfooditem
     */
    public FoodItem(FoodItem newfooditem) {
	this.itemDescription = new String(newfooditem.getItemDescription());
	this.unitList = new ArrayList<Unit>();
	for (int i = 0;i < newfooditem.getNumberOfUnits(); i++) {
	    this.unitList.add(new Unit(newfooditem.getUnit(i)));
	}
    }
    
    /**
     * Creates a new FoodItem with itemDescription and one single Unit.
     * @param itemDescription
     * @param firstUnit the unit to be added
     */
    public FoodItem(String itemDescription, Unit firstUnit) {
	this.itemDescription = new String(itemDescription);
	this.unitList = new ArrayList<Unit>();
	this.unitList.add(new Unit(firstUnit));
    }
    
    /**
     *  returns a new String with the ItemDescription
     * @see java.lang.Object#toString()
     * @return a new String with the ItemDescription
     */
    public String toString()  {
	return new String(itemDescription);
    }

    /**
     *  returns a new String with the ItemDescription
     * @return the a new String with the ItemDescription
     */
    public String getItemDescription () {
	return new String(this.itemDescription);
    }

    /**
     * sets ItemDescription
     * @param newDescription String with the new ItemDescription, a copy is taken so the 
     * input parameter can be deleted or updated by the caller.
     */
    public  void setItemDescription (String newDescription) {
	itemDescription = new String(newDescription);
    }

    /**
     * @param location the first unit has location 0
     * @return the Unit
     */
    public Unit getUnit(int location) {
	return new Unit(unitList.get(location));
    }
    
    /**
     * @param newUnit the new Unit
     */
    public void addUnit(Unit newUnit) {
	unitList.add(new Unit(newUnit));
    }
    
    /** calls {@link #compareItemDescriptionTo(String) compareItemDescriptionTo(String)} with FoodItemToCompare's ItemDescription as argument
     * @param FoodItemToCompare the fooditem whose ItemDescription needs to be compared 
     * @return the value 0 if the argument's ItemDescription is a string lexicographically equal to this ItemDescription;<br> 
     * -1 if the argument's ItemDescription is a string lexicographically greater than this FoodItem's ItemDescription; <br>
     * and +1 if the argument's ItemDescription is a string lexicographically less than this FoodItem's ItemDescription;<br>
     * the method uses ExcelCharacter.compareToAsInExcel to compare character by character in the String; The first argument used
     * is a character from this object's ItemDescription, the second argument is a character from FoodItemToCompare's ItemDescription.
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo (FoodItem FoodItemToCompare) {
	return compareItemDescriptionTo (FoodItemToCompare.itemDescription);
    }

    /** compares the ItemDescription of the FoodItem to the itemDescriptionToCompareString,
     * @param itemDescriptionToCompareString the itemDescription to compare to 
     * @return the value 0 if the argument is a string lexicographically equal to this FoodItem's ItemDescription ; <br>
     * -1 if the argument is a string lexicographically greater than this FoodItem's ItemDescription;<br> 
     * and +1 if the argument is a string lexicographically less than this FoodItem's ItemDescription;<br>
     * the method uses ExcelCharacter.compareToAsInExcel to compare character by character in the String; The first argument used
     * is a character from this object's ItemDescription, the second argument is a character from itemDescriptionToCompareString.
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareItemDescriptionTo (String itemDescriptionToCompareString) {
	int returnvalue = 0;
	int index = 0;

	char[] thisItemDescription = itemDescription.toCharArray(); 
	char[] itemDescriptionToCompare = itemDescriptionToCompareString.toCharArray(); 

	while ((index < thisItemDescription.length) && 
		(index < itemDescriptionToCompare.length)) {
	    if (ExcelCharacter.compareToAsInExcel(thisItemDescription[index], itemDescriptionToCompare[index]) != 0) {
		break;
	    }
	    index++;	
	}
	if ((index < itemDescription.length()) && 
		(index < itemDescriptionToCompare.length)) {
	    if (ExcelCharacter.compareToAsInExcel(thisItemDescription[index], itemDescriptionToCompare[index]) < 0)
		return -1;
	    if (ExcelCharacter.compareToAsInExcel(thisItemDescription[index], itemDescriptionToCompare[index]) > 0) 
		return 1;
	}
	//for sure thisItemDescription[index] = ItemDescriptionToCompare[index]
	//now it could still be that the lengths are different, we much be checked
	if ((index >= itemDescription.length()) || 
		(index >= itemDescriptionToCompare.length)) {
	    if (thisItemDescription.length < itemDescriptionToCompare.length) return -1;
	    if (thisItemDescription.length > itemDescriptionToCompare.length) return  1;
	}
	return returnvalue;
    }

    /**
     * Get the number of Units in this FoodItem
     * @return the number of units in this FoodItem
     */
    public int getNumberOfUnits() {
	return unitList.size();
    }

    /**
     * Helper function with some code that is repeated a few times.
     * Throws an InvalidSourceLineException with text and position, position being calculated based on parts and i, whereby
     * i denotes the number of parts of which size should be calculated. Position will be the sum of the sizes of the first i parts.
     * If i > length of parts array then each the size of each part in the array will be summed.
     * 
     * @param msg String used when creating superclass Exception
     * @param parts String Array with the different parts, whose first i parts should be summed up
     * @param i gives amount of parts of which size should be summed up
     * @param sourceLine the sourceLine that generated the error
     * @throws InvalidSourceLineException
     */
    private void throwInvalidSourceLineException(String msg, String[] parts, int i, byte [] sourceLine) throws InvalidSourceLineException  { 

	int position = 0;
	for (int j = 0;(j < i) && (j < parts.length);j++) {
	    position = position + parts[j].length();//adding length of each part
	    position++;//adding also size of ','
	}
	position++;//adding size of ","
	InvalidSourceLineException tothrow = new InvalidSourceLineException(msg, position, sourceLine);
	Log.i(LOG_TAG,"Invalid Source Line : " + msg + " at position " + position);
	throw (tothrow);
    }
    
    /**
     * convert the FoodItem to a Bundle
     * @return the FoodItem in a Bundle
     */
    public Bundle toBundle() {
	Bundle b = new Bundle();
	b.putString("itemDescription", itemDescription);
	for (int i = 0;i< unitList.size();i++)
	    b.putBundle("unit" + Integer.toString(i), unitList.get(i).toBundle());
	return b;
    }
    
    /**
     * convert a Bundle to a FoodItem
     * @param b the Bundle that holds a FoodItem
     * @return the FoodItem
     */
    static public FoodItem fromBundle(Bundle b) {
	FoodItem foodItem = new FoodItem(b.getString("itemDescription"), Unit.fromBundle(b.getBundle("unit0")));
	for (int i = 1;b.containsKey("unit" + Integer.toString(i));i++)
	    foodItem.addUnit(Unit.fromBundle(b.getBundle("unit" + Integer.toString(i))));
	return foodItem;
    }

}
