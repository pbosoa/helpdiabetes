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

import junit.framework.Assert;
import junit.framework.TestCase;

public class FoodItemTest extends TestCase {
    
    /**
     * main goal is to test the constructor, but implicitly all other methods are also tested, 
     * except compareTo, which is tested separately
     */

    public void testFoodItemTest()  {
	byte[] sourceline1 = "Apple| imported| raw,grams,100,49.00,0.30,10.60,0.2,piece,1,96,0.7,23.2,0,,,,,,".getBytes();
	byte[] sourceline2 = "Bread| white| bake-off| ready to eat,grams,100,259.00,9.10,50.20,1.6,slice=50,1,130,4.55,25.1,0.8,,,,,,".getBytes();
	byte[] sourceline3 = "Bread| white| industry made,grams,100,258.00,9.40,46.90,2.9,slice (25 grams)=25,1,65,2.35,11.725,0.725,slice (50 grams)=50,1,129,4.7,23,1.45".getBytes();
	byte[] sourceline4 = "test without unit description,,100,258.00,9.40,46.90,2.9".getBytes();
	byte[] sourceline5 = "test without standard amount,piece,,258.00,9.40,46.90,2.9".getBytes();
	byte[] sourceline6 = "test without kcals proteins and fats,piece,1,,,46.90,".getBytes();
	byte[] sourceline7 = "first unit has invalid carb value,piece,1,,,,,,,blabla".getBytes();
	byte[] sourceline8 = "first unit has invalid carb value,piece,1,,,,,,,".getBytes();
	byte[] sourceline9 = "Bread| white| bake-off| ready to eat,grams=100.1,100,259.00,9.10,50.20,1.6,slice=50,1,130,4.55,25.1,0.8,,,,,,".getBytes();
	byte[] sourceline10 = "Bread| white| bake-off| ready to eat,grams=100,100,259.00,9.10,50.20,1.6,slice=hello,1,130,4.55,25.1,0.8,,,,,,".getBytes();
	byte[] sourceline11= "invalid kcal value,piece,1,blabla,,10,,,,".getBytes();
	byte[] sourceline12= "invalid protein value,piece,1,,blabla,10,,,,".getBytes();
	byte[] sourceline13= "invalid fat value,piece,1,,,10,blabla,,,".getBytes();
	FoodItem result1 = null;
	FoodItem result2 = null;
	FoodItem result3 = null;
	FoodItem result4 = null;
	FoodItem result5 = null;
	FoodItem result6 = null;
	try {
	    result1 = new FoodItem(sourceline1);
	    
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	try {
	    result2 = new FoodItem(sourceline2);
	    
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	try {
	    result3 = new FoodItem(sourceline3);
	    
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	try {
	    result4 = new FoodItem(sourceline4);
	    
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	try {
	    result5 = new FoodItem(sourceline5);
	    
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	try {
	    result6 = new FoodItem(sourceline6);
	    
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	
	
	//assert result 1
	Assert.assertEquals("ItemDescription of sourceline1 not correctly read","Apple, imported, raw"	 , result1.getItemDescription());
	Assert.assertEquals("UnitDescription 1 in sourceLine1 not correctly read", "grams", result1.getUnit(0).getDescription());
	Assert.assertEquals("standardamount 1 value of sourceline1 not correctly read", 100.00,result1.getUnit(0).getStandardAmount(),0.0001);
	Assert.assertEquals("kcal value 1 of sourceline1 not correctly read", 49,result1.getUnit(0).getKcal());
	Assert.assertEquals("protein value 1 of sourceline1 not correctly read", 0.3,result1.getUnit(0).getProtein(),0.0001);
	Assert.assertEquals("fat value of 1 sourceline1 not correctly read", 0.2,result1.getUnit(0).getFat(),0.0001);
	Assert.assertEquals("carb value of 1 sourceline1 not correctly read", 10.6,result1.getUnit(0).getCarbs(),0.0001);
	Assert.assertEquals("UnitWeight value 1 of sourceline1 not correctly read", -1,result1.getUnit(0).getWeight());
	Assert.assertEquals("number of units of sourceline1 not correctly read",2,result1.getNumberOfUnits());
	Assert.assertEquals("UnitDescription 2 in sourceline1 not correctly read", "piece", result1.getUnit(1).getDescription());
	Assert.assertEquals("standardamount value 2 of sourceline1 not correctly read", 1,result1.getUnit(1).getStandardAmount(),0.0001);
	Assert.assertEquals("kcal value 2 of sourceline1 not correctly read", 96,result1.getUnit(1).getKcal());
	Assert.assertEquals("protein value 2 of sourceline1 not correctly read", 0.7,result1.getUnit(1).getProtein(),0.0001);
	Assert.assertEquals("fat value 2 of sourceline1 not correctly read", 0,result1.getUnit(1).getFat(),0.0001);
	Assert.assertEquals("carb value 2 of sourceline1 not correctly read", 23.2,result1.getUnit(1).getCarbs(),0.0001);
	Assert.assertEquals("UnitWeight value 2 of sourceline1 not correctly read", -1,result1.getUnit(1).getWeight());

	//assert result 3
	Assert.assertEquals("ItemDescription of sourceline3 not correctly read","Bread, white, industry made"	 , result3.getItemDescription());
	Assert.assertEquals("UnitDescription 1 in sourceline3 not correctly read", "grams", result3.getUnit(0).getDescription());
	Assert.assertEquals("standardamount value 1 of sourceline3 not correctly read", 100.00,result3.getUnit(0).getStandardAmount(),0.0001);
	Assert.assertEquals("kcal value 1 of sourceline3 not correctly read", 258,result3.getUnit(0).getKcal());
	Assert.assertEquals("protein value 1  of sourceline3 not correctly read",9.40,result3.getUnit(0).getProtein(),0.0001);
	Assert.assertEquals("fat value 1 of sourceline3 not correctly read", 2.9,result3.getUnit(0).getFat(),0.0001);
	Assert.assertEquals("carb value 1 of sourceline3 not correctly read", 46.90,result3.getUnit(0).getCarbs(),0.0001);
	Assert.assertEquals("UnitWeight value 1 of sourceline3 not correctly read", -1,result3.getUnit(0).getWeight());
	Assert.assertEquals("number of units  of sourceline3 not correctly read",3,result3.getNumberOfUnits());
	Assert.assertEquals("UnitDescription 2 in sourceLine3 not correctly read", "slice (25 grams)", result3.getUnit(1).getDescription());
	Assert.assertEquals("standardamount value 2 of sourceline3 not correctly read", 1,result3.getUnit(1).getStandardAmount(),0.0001);
	Assert.assertEquals("kcal value 2 of sourceline3 not correctly read", 65,result3.getUnit(1).getKcal());
	Assert.assertEquals("protein value 2 of sourceline3 not correctly read", 2.35,result3.getUnit(1).getProtein(),0.0001);
	Assert.assertEquals("fat value 2 of sourceline3 not correctly read", 0.725,result3.getUnit(1).getFat(),0.0001);
	Assert.assertEquals("carb value 2 of sourceline3 not correctly read", 11.725,result3.getUnit(1).getCarbs(),0.0001);
	Assert.assertEquals("UnitWeight value 2  of sourceline3 not correctly read", 25,result3.getUnit(1).getWeight());
	//slice (50 grams)=50,1,129,4.7,23,1.45
	Assert.assertEquals("UnitDescription 3 in sourceLine3 not correctly read", "slice (50 grams)", result3.getUnit(2).getDescription());
	Assert.assertEquals("standardamount value 3 of sourceline3 not correctly read", 1,result3.getUnit(2).getStandardAmount(),0.0001);
	Assert.assertEquals("kcal value 3 of sourceline3 not correctly read", 129,result3.getUnit(2).getKcal());
	Assert.assertEquals("protein value 3 of sourceline3 not correctly read", 4.7,result3.getUnit(2).getProtein(),0.0001);
	Assert.assertEquals("fat value 3 of sourceline3 not correctly read", 1.45,result3.getUnit(2).getFat(),0.0001);
	Assert.assertEquals("carb value 3 of sourceline3 not correctly read", 23,result3.getUnit(2).getCarbs(),0.0001);
	Assert.assertEquals("UnitWeight value 3  of sourceline3 not correctly read", 50,result3.getUnit(2).getWeight());

	//assert result 2 slice=50,1,130,4.55,25.1,0.8,,,,,,	
	Assert.assertEquals("UnitDescription 3 in sourceLine2 not correctly read", "slice", result2.getUnit(1).getDescription());
	Assert.assertEquals("standardamount value 3 of sourceline2 not correctly read", 1,result2.getUnit(1).getStandardAmount(),0.0001);
	Assert.assertEquals("kcal value 3 of sourceline2 not correctly read", 130,result2.getUnit(1).getKcal());
	Assert.assertEquals("protein value 3 of sourceline2 not correctly read", 4.55,result2.getUnit(1).getProtein(),0.0001);
	Assert.assertEquals("fat value 3 of sourceline2 not correctly read", 0.8,result2.getUnit(1).getFat(),0.0001);
	Assert.assertEquals("carb value 3 of sourceline2 not correctly read",25.1,result2.getUnit(1).getCarbs(),0.0001);
	Assert.assertEquals("UnitWeight value 3  of sourceline2 not correctly read", 50,result2.getUnit(1).getWeight());
	
	//result 4
	Assert.assertEquals("UnitDescription 4 in sourceLine4 not correctly read", "", result4.getUnit(0).getDescription());
	//result 5
	Assert.assertEquals("Standardamount value 5 in sourceLine5 not correctly read", 1, result5.getUnit(0).getStandardAmount());
	//result 6
	Assert.assertEquals("kcal value 6 in sourceline6 not correctly read", -1, result6.getUnit(0).getKcal());
	Assert.assertEquals("protein value 6 in sourceline6 not correctly read", -1.0, result6.getUnit(0).getProtein(),0.0001);
	Assert.assertEquals("fat value 6 in sourceline6 not correctly read", -1.0, result6.getUnit(0).getFat(),0.0001);
	//result 7
	int position = 0;
	String text = null;
	FoodItem result7;
	try {
	     result7 = new FoodItem(sourceline7);
	} catch (InvalidSourceLineException e) {
	    position = e.getPosition();
	    text = e.getMessage();
	    
	}
	Assert.assertEquals("result7 generates exception with wrong position value",45,position);
	Assert.assertEquals("result7 generates exception with wrong message text","First Unit does not contain a parsable Carb value",text);
	//result 8
	position = 0;
	text = null;
	FoodItem result8;
	try {
	     result8 = new FoodItem(sourceline8);
	} catch (InvalidSourceLineException e) {
	    position = e.getPosition();
	    text = e.getMessage();
	    
	}
	Assert.assertEquals("result8 generates exception with wrong position value",43,position);
	Assert.assertEquals("result8 generates exception with wrong message text","First Unit does not contain a parsable Carb value",text);
	//result 9
	position = 0;
	text = null;
	FoodItem result9;
	try {
	     result9 = new FoodItem(sourceline9);
	} catch (InvalidSourceLineException e) {
	    position = e.getPosition();
	    text = e.getMessage();
	    
	}
	Assert.assertEquals("result9 generates exception with wrong position value",43,position);
	Assert.assertEquals("result9 generates exception with wrong message text","Invalid value for unit weight, must be Integer value",text);
	//result 10
	position = 0;
	text = null;
	FoodItem result10;
	try {
	     result10 = new FoodItem(sourceline10);
	} catch (InvalidSourceLineException e) {
	    position = e.getPosition();
	    text = e.getMessage();
	    
	}
	Assert.assertEquals("result10 generates exception with wrong position value",79,position);
	Assert.assertEquals("result10 generates exception with wrong message text","Invalid value for unit weight, must be Integer value",text);
	//result 11
	position = 0;
	text = null;
	FoodItem result11;
	try {
	     result11 = new FoodItem(sourceline11);
	} catch (InvalidSourceLineException e) {
	    position = e.getPosition();
	    text = e.getMessage();
	    
	}
	Assert.assertEquals("result11 generates exception with wrong position value",28,position);
	Assert.assertEquals("result11 generates exception with wrong message text","Invalid value for kcal, must be decimal value",text);
	//result 12
	position = 0;
	text = null;
	FoodItem result12;
	try {
	     result12 = new FoodItem(sourceline12);
	} catch (InvalidSourceLineException e) {
	    position = e.getPosition();
	    text = e.getMessage();
	    
	}
	Assert.assertEquals("result12 generates exception with wrong position value",32,position);
	Assert.assertEquals("result12 generates exception with wrong message text","Invalid value for protein, must be decimal value",text);
	//result 13
	position = 0;
	text = null;
	FoodItem result13;
	try {
	     result13 = new FoodItem(sourceline13);
	} catch (InvalidSourceLineException e) {
	    position = e.getPosition();
	    text = e.getMessage();
	    
	}
	Assert.assertEquals("result13 generates exception with wrong position value",32,position);
	Assert.assertEquals("result13 generates exception with wrong message text","Invalid value for fat, must be decimal value",text);
    }
    
    
    public void testsetItemDescription() {
	byte[] sourceline1 = "This is a test,grams,100,49.00,0.30,10.60,0.2,piece,1,96,0.7,23.2,0,,,,,,".getBytes();
	FoodItem result1 = null;
	try {
	     result1 = new FoodItem(sourceline1);
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	result1.setItemDescription("new text");
	Assert.assertEquals("testsetItemDescription failed", "new text", result1.getItemDescription());
    }
    
    
    public void testFoodItem2() {
	byte[] sourceline1 = "Apple| imported| raw,grams,100,49.00,0.30,10.60,0.2,piece,1,96,0.7,23.2,0,,,,,,".getBytes();
	FoodItem resultx = null;
	try {
	    resultx = new FoodItem(sourceline1);
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	FoodItem result1 = new FoodItem(resultx);
	//assert result 1
	Assert.assertEquals("ItemDescription of sourceline1 not correctly read","Apple, imported, raw"	 , result1.getItemDescription());
	Assert.assertEquals("UnitDescription 1 in sourceLine1 not correctly read", "grams", result1.getUnit(0).getDescription());
	Assert.assertEquals("standardamount 1 value of sourceline1 not correctly read", 100.00,result1.getUnit(0).getStandardAmount(),0.0001);
	Assert.assertEquals("kcal value 1 of sourceline1 not correctly read", 49,result1.getUnit(0).getKcal());
	Assert.assertEquals("protein value 1 of sourceline1 not correctly read", 0.3,result1.getUnit(0).getProtein(),0.0001);
	Assert.assertEquals("fat value of 1 sourceline1 not correctly read", 0.2,result1.getUnit(0).getFat(),0.0001);
	Assert.assertEquals("carb value of 1 sourceline1 not correctly read", 10.6,result1.getUnit(0).getCarbs(),0.0001);
	Assert.assertEquals("UnitWeight value 1 of sourceline1 not correctly read", -1,result1.getUnit(0).getWeight());
	Assert.assertEquals("number of units of sourceline1 not correctly read",2,result1.getNumberOfUnits());
	Assert.assertEquals("UnitDescription 2 in sourceline1 not correctly read", "piece", result1.getUnit(1).getDescription());
	Assert.assertEquals("standardamount value 2 of sourceline1 not correctly read", 1,result1.getUnit(1).getStandardAmount(),0.0001);
	Assert.assertEquals("kcal value 2 of sourceline1 not correctly read", 96,result1.getUnit(1).getKcal());
	Assert.assertEquals("protein value 2 of sourceline1 not correctly read", 0.7,result1.getUnit(1).getProtein(),0.0001);
	Assert.assertEquals("fat value 2 of sourceline1 not correctly read", 0,result1.getUnit(1).getFat(),0.0001);
	Assert.assertEquals("carb value 2 of sourceline1 not correctly read", 23.2,result1.getUnit(1).getCarbs(),0.0001);
	Assert.assertEquals("UnitWeight value 2 of sourceline1 not correctly read", -1,result1.getUnit(1).getWeight());
	
    }
    
    
    public void testcompareTo( ) throws InvalidSourceLineException {
	byte[] sourceline1 = "This is a test with special characters e e e e e e,grams,100,49.00,0.30,10.60,0.2,piece,1,96,0.7,23.2,0,,,,,,".getBytes();
	byte[] sourceline2 = "This is a test with special characters e è é ë ê E,grams,100,259.00,9.10,50.20,1.6,slice=50,1,130,4.55,25.1,0.8,,,,,,".getBytes();
	byte[] sourceline3 = "This is a test with special characters e è é ë ê a,grams,100,258.00,9.40,46.90,2.9,slice (25 grams)=25,1,65,2.35,11.725,0.725,slice (50 grams)=50,1,129,4.7,23,1.45".getBytes();
	byte[] sourceline4 = "abcdaaa,,100,258.00,9.40,46.90,2.9".getBytes();
	byte[] sourceline5 = "abcdäbb,piece,,258.00,9.40,46.90,2.9".getBytes();
	byte[] sourceline6 = "abcdäbbzz,piece,1,,,46.90,".getBytes();
	byte[] sourceline7 = "first unit has invalid carb value,piece,1,,,,,,,blabla".getBytes();
	byte[] sourceline8 = "first unit has invalid carb value,piece,1,,,,,,,".getBytes();
	byte[] sourceline9 = "Bread| white| bake-off| ready to eat,grams=100.1,100,259.00,9.10,50.20,1.6,slice=50,1,130,4.55,25.1,0.8,,,,,,".getBytes();
	byte[] sourceline10 = "Bread| white| bake-off| ready to eat,grams=100,100,259.00,9.10,50.20,1.6,slice=hello,1,130,4.55,25.1,0.8,,,,,,".getBytes();
	byte[] sourceline11= "invalid kcal value,piece,1,blabla,,10,,,,".getBytes();
	byte[] sourceline12= "invalid protein value,piece,1,,blabla,10,,,,".getBytes();
	byte[] sourceline13= "invalid fat value,piece,1,,,10,blabla,,,".getBytes();
	
	FoodItem result1 = new FoodItem(sourceline1);
	FoodItem result2 = new FoodItem(sourceline2);
	FoodItem result3 = new FoodItem(sourceline3);
	FoodItem result4 = new FoodItem(sourceline4);
	FoodItem result5 = new FoodItem(sourceline5);
	FoodItem result6 = new FoodItem(sourceline6);
	
	Assert.assertEquals("comparison result1 to result2 failed", 0,result1.compareTo(result2));
	Assert.assertEquals("comparison result2 to result3 failed", 1,result2.compareTo(result3));
	Assert.assertEquals("comparison result3 to result2 failed",-1,result3.compareTo(result2));
	Assert.assertEquals("comparison result4 to result5 failed",-1,result4.compareTo(result5));
	Assert.assertEquals("comparison result5 to result4 failed", 1,result5.compareTo(result4));
	Assert.assertEquals("comparison result5 to result6 failed",-1,result5.compareTo(result6));
	Assert.assertEquals("comparison result6 to result5 failed", 1,result6.compareTo(result5));
	Assert.assertEquals("comparison result1 to result4 failed", 1,result1.compareTo(result4));
	Assert.assertEquals("comparison result4 to result1 failed",-1,result4.compareTo(result1));
    }
    
 
}


