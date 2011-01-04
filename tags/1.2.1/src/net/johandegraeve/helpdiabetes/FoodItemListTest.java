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
import android.test.AndroidTestCase;
import junit.framework.Assert;



/**
 * Test class FoodItemList
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class FoodItemListTest extends AndroidTestCase {
    FoodItemList testfooditemlist;
    String[] fooditems ;
    Context  callingContext = null;

    /**
     * @throws java.lang.Exception
     */
    
    public void setUp()  {
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
	fooditems[29] = new String("Apple (0),gram,100,10,10,10,10");

	callingContext = getContext();

	testfooditemlist = new FoodItemList(
		callingContext, 
		R.layout.helpdiabetesrow, 
		10);
	
	for (int i = 0;i < fooditems.length;i++)
	    try {
		testfooditemlist.addFoodItem(new FoodItem(fooditems[i].getBytes()));
	    } catch (InvalidSourceLineException e) {
		e.printStackTrace();
	    }
	testfooditemlist.setFoodTableSource("test table");
	
    }

    /**
     * @throws java.lang.Exception
     */
    
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link net.johandegraeve.helpdiabetes.FoodItemList#FoodItemList(android.content.Context)}.
    
    public void testFoodItemList() {
	fail("Not yet implemented");
    }
    */
 
    /**
     * Test method for {@link net.johandegraeve.helpdiabetes.FoodItemList#getListOfFoodItemDescriptions()}.
     */
    
    public void testGetListOfFoodItemDescriptions() {
	String[] teststringarray = testfooditemlist.getListOfFoodItemDescriptions();
	Assert.assertEquals("item description not correct", "Apple (0)", teststringarray[0]);
	Assert.assertEquals("item description not correct", "Damson (13)",teststringarray[13]);
	Assert.assertEquals("item description not correct", "Watermelon (29)",teststringarray[29]);
	Assert.assertEquals("string array size not correct", 30, teststringarray.length);
    }

    /**
     * updates item 10, with a new item that has a description ABC.. this should become item 0
     * Test method for {@link net.johandegraeve.helpdiabetes.FoodItemList#updateFoodItem(net.johandegraeve.helpdiabetes.FoodItem, int)}.
     */
    
    public void testUpdateFoodItem() {
	FoodItem newitem = null;
	try {
	     newitem = new FoodItem("ABCABCABC,gram,100,10,10,10,10".getBytes());
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	testfooditemlist.updateFoodItem(newitem, 10);
	newitem.setItemDescription("blablabla");
	newitem = testfooditemlist.getFoodItem(0);
	Assert.assertEquals("update failed", "ABCABCABC", newitem.getItemDescription());
    }

    /**
     * Test method for {@link net.johandegraeve.helpdiabetes.FoodItemList#addFoodItem(net.johandegraeve.helpdiabetes.FoodItem)}.
     */
    
    public void testAddFoodItem() {
	callingContext = getContext();
	FoodItemList list1 = new FoodItemList(callingContext, 0, 10);
	try {
	    list1.addFoodItem(new FoodItem(fooditems[0].getBytes()));
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	try {
	    list1.addFoodItem(new FoodItem(fooditems[1].getBytes()));
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	try {
	    list1.addFoodItem(new FoodItem(fooditems[2].getBytes()));
	} catch (InvalidSourceLineException e) {	   
	    e.printStackTrace();
	}
	FoodItem result1 = list1.getFoodItem(0);
	Assert.assertEquals("Test AddFoodItem failed, itemdescription is not correct", "Sharon Fruit (27)", result1.getItemDescription());
	result1 = list1.getFoodItem(1);
	Assert.assertEquals("Test AddFoodItem failed, itemdescription is not correct", "Ugli Fruit (28)", result1.getItemDescription());
	result1 = list1.getFoodItem(2);
	Assert.assertEquals("Test AddFoodItem failed, itemdescription is not correct", "Watermelon (29)", result1.getItemDescription());
    }

    /**
     * Test method for {@link net.johandegraeve.helpdiabetes.FoodItemList#getFirstMatchingItem(java.lang.String)}.
     */
    
    public void testGetFirstMatchingItem() {
	int result;
	result = testfooditemlist.getFirstMatchingItem("b");
	Assert.assertEquals(3, result);
	result = testfooditemlist.getFirstMatchingItem("B");
	Assert.assertEquals(3, result);
	result = testfooditemlist.getFirstMatchingItem("");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem("a");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem("ap");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem("app");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem("appl");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem("");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem("BL");
	Assert.assertEquals(5, result);
	result = testfooditemlist.getFirstMatchingItem("apple (0)");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem("apple (0)");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem("apple (0) blabqsdfdlabla");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem("applqsdfqsdfdlabla");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem("Ž");
	Assert.assertEquals(16, result);
	result = testfooditemlist.getFirstMatchingItem("l");
	Assert.assertEquals(17, result);
	result = testfooditemlist.getFirstMatchingItem("gT");
	Assert.assertEquals(19, result);
	result = testfooditemlist.getFirstMatchingItem("Gr");
	Assert.assertEquals(20, result);
	result = testfooditemlist.getFirstMatchingItem("zzZZ");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem(" ");
	Assert.assertEquals(0, result);
	result = testfooditemlist.getFirstMatchingItem("abcdefghijk ");
	Assert.assertEquals(0, result);

    }
     

    /**
     * Test method for {@link net.johandegraeve.helpdiabetes.FoodItemList#setFoodTableSource(java.lang.String)}.
     */
    
    public void testSetFoodTableSource() {
	testfooditemlist.setFoodTableSource("Testsource");
	Assert.assertEquals("Test setFoodTablesource failed", "Testsource", testfooditemlist.getFoodTableSource());
    }
    
    
    public void testgetFoodItemCarbValue() {
	try {
	    testfooditemlist.addFoodItem(new FoodItem("Bread| white| industry made,grams,100,258.00,9.40,46.90,2.9,slice (25 grams)=25,1,65,2.35,11.725,0.725,slice (50 grams)=50,1,129,4.7,23,1.45".getBytes()));
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	//should be element 8
	Assert.assertEquals(46.90, testfooditemlist.getFoodItem(8).getUnit(0).getCarbs(), 0.0001);
	Assert.assertEquals("grams", testfooditemlist.getFoodItem(8).getUnit(0).getDescription());
	Assert.assertEquals(100, testfooditemlist.getFoodItem(8).getUnit(0).getStandardAmount());
	Assert.assertEquals(-1, testfooditemlist.getFoodItem(8).getUnit(0).getWeight());
	Assert.assertEquals(258, testfooditemlist.getFoodItem(8).getUnit(0).getKcal());
	Assert.assertEquals(9.4, testfooditemlist.getFoodItem(8).getUnit(0).getProtein(),0.0001);
	Assert.assertEquals(2.9, testfooditemlist.getFoodItem(8).getUnit(0).getFat(),0.0001);
	Assert.assertEquals(3, testfooditemlist.getFoodItemNumberOfUnits(8));
	Assert.assertEquals("slice (25 grams)", testfooditemlist.getFoodItem(8).getUnit(1).getDescription());
	Assert.assertEquals(25, testfooditemlist.getFoodItem(8).getUnit(1).getWeight());


    }
    
    
    public void testremoveFoodItem() {
	try {
	    testfooditemlist.addFoodItem(new FoodItem("Bread| white| industry made,grams,100,258.00,9.40,46.90,2.9,slice (25 grams)=25,1,65,2.35,11.725,0.725,slice (50 grams)=50,1,129,4.7,23,1.45".getBytes()));
	} catch (InvalidSourceLineException e) {
	    e.printStackTrace();
	}
	//should be element 8
	testfooditemlist.removeFoodItem(8);
	Assert.assertEquals("Apple (0)", testfooditemlist.getFoodItemDescription(0));

    }
    
}
