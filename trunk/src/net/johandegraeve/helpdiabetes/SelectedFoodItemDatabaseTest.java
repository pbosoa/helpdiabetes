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

import android.content.Context;
import junit.framework.Assert;
import android.test.AndroidTestCase;

public class SelectedFoodItemDatabaseTest extends AndroidTestCase {
    Context  callingContext = null;
    String[] fooditems ;
    private SelectedFoodItemDatabase testdb;

  

    public void setUp() {
	callingContext = getContext();	
	testdb = new SelectedFoodItemDatabase(callingContext);


	fooditems = new String[30];
	fooditems[0] = new String("Watermelon (29),gram,100,10,10,10,10");
	fooditems[1] = new String("Ugli Fruit (28),gram,100,10,10,10,10");
	fooditems[2] = new String("Sharon Fruit (27),gram,100,10,10,10,10");
	fooditems[3] = new String("Quince (26),gram,100,10,10,10,10");
	fooditems[4] = new String("Papaya (25),gram,100,10,10,10,10");
	fooditems[5] = new String("Orange (24),gram,100,10,10,10,10");
	fooditems[6] = new String("Nectarine (23),gram,100,10,10,10,10");
	fooditems[7] = new String("Melon (22),gram,100,10,10,10,10");
	fooditems[8] = new String("Mango (21),gram,100,10,10,10,10");
	fooditems[9] = new String("Grape (20),gram,100,10,10,10,10");
	fooditems[10] = new String("Gooseberry (19),gram,100,10,10,10,10");
	fooditems[11] = new String("Feijoa (18),gram,100,10,10,10,10");
	fooditems[12] = new String("Elderberry (17),gram,100,10,10,10,10");
	fooditems[13] = new String("Eggplan (16),gram,100,10,10,10,10");
	fooditems[14] = new String("Durian (15),gram,100,10,10,10,10");
	fooditems[15] = new String("Date (14),gram,100,10,10,10,10");
	fooditems[16] = new String("Damson (13),gram,100,10,10,10,10");
	fooditems[17] = new String("Currant (12),gram,100,10,10,10,10");
	fooditems[18] = new String("Clementine (11),gram,100,10,10,10,10");
	fooditems[19] = new String("Cherry (10),gram,100,10,10,10,10");
	fooditems[20] = new String("Cherimoya (9),gram,100,10,10,10,10");
	fooditems[21] = new String("Breadfruit (8),gram,100,10,10,10,10");
	fooditems[22] = new String("Blueberry (7),gram,100,10,10,10,10");
	fooditems[23] = new String("Blackcurrant (6),gram,100,10,10,10,10");
	fooditems[24] = new String("Blackberry (5),gram,100,10,10,10,10");
	fooditems[25] = new String("Bilberry (4),gram,100,10,10,10,10");
	fooditems[26] = new String("Banana (3),gram,100,10,10,10,10");
	fooditems[27] = new String("Avocado (2),gram,100,10,10,10,10");
	fooditems[28] = new String("Apricot (1),gram,100,10,10,10,10");
	fooditems[29] = new String("Apple (0),gram,100,10,11.1,12.2,13.3");

	
    }
    
    public void tearDown() {
    }
    
    public void testCreateDatabase() {

	SelectedFoodItem selectedItem1 = null;
	SelectedFoodItem selectedItem2 = null;
	
	FoodItem retrievedFoodItem = null;
	double retrievedChosenAmount;
	int retrievedUnitNumber;
	
	//adding "apple (0)", 50.5 grams
	try {
	    selectedItem1 = new SelectedFoodItem(new FoodItem(fooditems[29].getBytes()), (double)50.5, 0);
	} catch (InvalidSourceLineException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	testdb.addSelectedFoodItem(selectedItem1);
	
	//get list of items stored in db
	SelectedFoodItem[] result = testdb.getSelectedFoodItemList();
	//there should be one element in it.
	Assert.assertEquals(1, result.length);
	
	retrievedFoodItem = result[0].getFoodItem();
	retrievedChosenAmount = result[0].getChosenAmount();
	retrievedUnitNumber = result[0].getChosenUnitNumber();
	
	Assert.assertEquals(retrievedFoodItem.getItemDescription(), "Apple (0)");
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getCarbs(), 12.2,0.0001);
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getFat(), 13.3,0.0001);
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getProtein(), 11.1,0.0001);
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getKcal(), 10,0.0001);
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getStandardAmount(), 100);
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getDescription(), "gram");
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getWeight(), -1);
	Assert.assertEquals(retrievedChosenAmount, 50.5,0.0001);
	Assert.assertEquals(retrievedUnitNumber, 0);

	//adding "Bread, white, bake-off, ready to eat", 2 slices , 50 grams per slice
	FoodItem test = null;
	try {
	    test = new FoodItem("Bread| white| bake-off| ready to eat,grams=100,100,259.00,9.10,50.20,1.6,slice=50,1,130,4.55,25.1,0.8,,,,,,".getBytes());
	} catch (InvalidSourceLineException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	selectedItem2 = new SelectedFoodItem(test,(double)2, 1);
	
	testdb.addSelectedFoodItem(selectedItem2);
	result = testdb.getSelectedFoodItemList();
	//there should be 2 elements in it.
	retrievedFoodItem = result[1].getFoodItem();
	retrievedChosenAmount = result[1].getChosenAmount();
	retrievedUnitNumber = result[1].getChosenUnitNumber();

	Assert.assertEquals(2, result.length);
	Assert.assertEquals(retrievedFoodItem.getItemDescription(), "Bread, white, bake-off, ready to eat");
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getCarbs(), 25.1,0.0001);
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getFat(), 0.8,0.0001);
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getProtein(), 4.55,0.0001);
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getKcal(), 130,0.0001);
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getStandardAmount(), 1);
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getDescription(), "slice");
	Assert.assertEquals(retrievedFoodItem.getUnit(0).getWeight(), 50);
	Assert.assertEquals(retrievedChosenAmount, 2,0.0001);
	Assert.assertEquals(retrievedUnitNumber, 0);

	testdb.deleteAll();
	
    }
    
    public void testcleanUp() {
	SelectedFoodItem selectedItem1 = null;
	SelectedFoodItem selectedItem2 = null;
	
	//adding "apple (0)", 50.5 grams
	try {
	    selectedItem1 = new SelectedFoodItem(new FoodItem(fooditems[29].getBytes()), (double)50.5, 0);
	} catch (InvalidSourceLineException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	testdb.addSelectedFoodItem(selectedItem1);

	//adding "Bread, white, bake-off, ready to eat", 2 slices , 50 grams per slice
	try {
	    selectedItem2 = new SelectedFoodItem(new FoodItem("Bread| white| bake-off| ready to eat,grams=100,100,259.00,9.10,50.20,1.6,slice=50,1,130,4.55,25.1,0.8,,,,,,".getBytes()),(double)2, 1);
	} catch (InvalidSourceLineException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	testdb.addSelectedFoodItem(selectedItem2);

	testdb.cleanUp();
	//get list of items stored in db
	SelectedFoodItem[] result = testdb.getSelectedFoodItemList();

	//Assert.assertEquals(result, null);
	
	testdb.deleteAll();
	
    }
    

}
