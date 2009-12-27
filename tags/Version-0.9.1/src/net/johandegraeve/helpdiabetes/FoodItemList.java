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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This class manages a list of {@link net.johandegraeve.helpdiabetes.FoodItem FoodItems}.<br>
 * There is only one constructor, {@link #FoodItemList(Context, int, int)} which creates an empty FoodItemList.
 * When creating a FoodItemList with the constructor, then the caller must call {@link #initialize()} to get a populated FoodItemList.<br>
 * 
 * Any methods that need long time processing, are handled in a separate thread, called {@link #backgroundThread}. 
 * Currenthly, the only method running in the separate thread is {@link #initialize()}.<br>
 *  
 * When a method is called, and this method needs any kind of access to the {@link #foodItemList}, then it will first be checked
 * if the {@link #backgroundThread} is running and if yes, an error value will be returned to indicate that a retry should occur later.
 * In addition, in such cases, it will be possible to retrieve an error or warning string via the method  {@link #getWarningMessage()}, which
 * can be shown to the user.<br>
 * 
 * @version 1.0
 * @author Johan Degraeve
 */
public class FoodItemList extends ArrayAdapter<String> {
    /**
     * The list of fooditems
     */
    private List<FoodItem> foodItemList =  null;
    /**
     * An example clarifies what is mean with FoodTableSource : "The Norwegian Food Safety Authority, 
     * the Directorate of Health and 
     * Social Affairs and the Department of Nutrition at the University of Oslo have 
     * collaborated to publish the Norwegian Food Composition Table 2006 (MVT-06)"
     */
    private String foodTableSource = null;

    /**
     * each time a call is made to {@link #getFirstMatchingItem(CharSequence) getFirstMatchingItem(String s)}, the
     * search string s will be stored in previousSearchString. For a more detailed explanation of the usage see 
     * {@link #getFirstMatchingItem(CharSequence) getFirstMatchingItem(String s)}.
     *
     */
    private String previousSearchString = null;

    /**
     * Array will be used as explained in documentation for {@link #getFirstMatchingItem(CharSequence) getFirstMatchingItem(String s)}.
     * It is important to re-initialise this array whenever an FoodItem is removed, added or updated, because then also firstIndex
     * and lastIndex are re-initialised.
     */
    private int[] firstIndex ;

    /**
     * Array will be used as explained in documentation for {@link #getFirstMatchingItem(CharSequence) getFirstMatchingItem(String s)}.
     * It is important to re-initialise this array whenever an FoodItem is removed, added or updated.
     * It is important to re-initialise this array whenever an FoodItem is removed, added or updated.
     */
    private int[] lastIndex ;

    /**
     * it's the maximum length of the search string that the user can enter
     */
    private int maximumSearchStringLength;

    /**
     * it's the context that created the instance of this class.
     */
    private Context callingContext;

    /**
     * tag to be used by any method in this calss, when using {@link android.util.Log}
     */
    private static final String LOG_TAG = "HD-FoodItemList";
    
    /**
     * Thread used for any long time tasks that need to be executed in the background
     */
    private Thread backgroundThread;
    
    /**
     * Handler used to post message to the thre that created the instance of the class
     */
    private final Handler callingThreadHandler = new Handler();
    
    /**
     * String that will be set by a method running in the background, will identify the task which is 
     * ongoing. Returned by {@link #getWarningMessage()}
     */
    private String warningMessage;

    /**
     * Creates FoodItemList with an empty fooditemlist.<br>
     * @param context the calling Context
     * @param textViewResourceId the resourceid, necessary to call the super class
     * 
     * @param maximumSearchStringLength the maximum search string length
     */
    public FoodItemList(Context context, int textViewResourceId, int maximumSearchStringLength) {
	super (context, textViewResourceId, new ArrayList<String>());
	callingContext = context;
	foodItemList = new ArrayList<FoodItem>();
	this.maximumSearchStringLength = maximumSearchStringLength;
	foodTableSource = "";
	firstIndex = new int[maximumSearchStringLength + 1];//we will not search further if the given searchstring is longer, but we need a bit larger array for the methods getFirstMatchingItem
	lastIndex = new int[maximumSearchStringLength + 1];
	firstIndex[0] = 0;
	lastIndex[0] = 0;
	previousSearchString = null;
    }
    
    /**
     * @return the {@link #warningMessage}
     */
    public String getWarningMessage() {
	return warningMessage;
    }

    /**
     * @return string array with new Strings containing all {@link FoodItem#getItemDescription() FoodItemDescriptions} for 
     * all fooditems in the list.
     */
    String[] getListOfFoodItemDescriptions() {
	int size = foodItemList.size();
	String[] returnvalue = new String[size];
	for (int i = 0;i<size;i++) {
	    returnvalue[i] = new String(foodItemList.get(i).getItemDescription());
	}
	return returnvalue;
    }

    /**
     * Returns a new FoodItem equal to the fooditem on position index, index starts at zero.
     * Returns null of index is out of range.
     * @param position points to the FoodItem in the List, starting at zero
     * @return the FoodItem, null if index is out of range
     */
    public FoodItem getFoodItem(int position) {
	try {
	    return new FoodItem(foodItemList.get(position));
	} catch (IndexOutOfBoundsException e) {
	    return null;
	}
    }

    /**
     * a private version of {@link #getFoodItem(int)}, the difference being this method returns
     * the FoodItem itself, not a new copy.
     * @param position
     * @return the FoodItem, null if index is out of range
     */
    private FoodItem getFoodItem_private (int position) {
	try {
	    return foodItemList.get(position);
	} catch (IndexOutOfBoundsException e) {
	    return null;
	}
    }

    /**
     * adds a new FoodItem, returns the position of the new item, returns -1 if addition failed
     * @param newfooditem the FoodItem that will be copied
     */
    public void addFoodItem (FoodItem newfooditem) {
	foodItemList.add(new FoodItem(newfooditem));
	Collections.sort(foodItemList);
	firstIndex[0] = 0;
	lastIndex[0] = foodItemList.size() - 1;
	previousSearchString = null;

    }

    /**
     * updates a FoodItem, returns true if successfull updated, false otherwise
     * @param newFoodItem the new FoodItem to be set at position index, the new FoodItem will be copied
     * @param index the position
     * @return true of successfully updated, false if not (only case known is IndexOutOfBoundsException)
     */
    public boolean updateFoodItem (FoodItem newFoodItem ,int index) {
	try {
	    foodItemList.set(index, new FoodItem(newFoodItem));
	} catch (IndexOutOfBoundsException e) {
	    return false;
	}
	Collections.sort(foodItemList);
	firstIndex[0] = 0;
	lastIndex[0] = foodItemList.size() - 1;
	previousSearchString = null;
	return true;
    }

    /**
     * removes the specified fooditem
     * @param index the position
     * @return true of successfully removed, false if not
     */
    public boolean removeFoodItem (int index) {
	try {
	    foodItemList.remove(index);
	} catch (IndexOutOfBoundsException e) {
	    return false;
	}
	Collections.sort(foodItemList);
	firstIndex[0] = 0;
	lastIndex[0] = foodItemList.size() - 1;
	previousSearchString = null;
	return true;
    }

    /**
     * This method is used to retrieve the item to be shown for a specified searchstring.<br>
     * The searchstring is compared to the FoodItem's ItemDescription using 
     * {@link ExcelCharacter#compareToAsInExcel(char, char) ExcelCharacter.compareToAsInExcel(char,char)}<br>
     * Examples show best what this method returns :<br>
     * Assume the FoodItemList contains FoodItems with following ItemDescriptions (the number between brackets 
     * is normally not part of an ItemDescription but is added here to show the index that this item would have:<br>
     * <ul>
     * <li>Apple (0)</li>
     * <li>Apricot (1)</li>
     * <li>Avocado (2)</li>
     * <li>Banana (3)</li>
     * <li>Bilberry (4)</li>
     * <li>Blackberry (5)</li>
     * <li>Blackcurrant (6)</li>
     * <li>Blueberry (7)</li>
     * <li>Breadfruit (8)</li>
     * <li>Cherimoya (9)</li>
     * <li>Cherry (10)</li>
     * <li>Clementine (11)</li>
     * <li>Currant (12)</li>
     * <li>Damson (13)</li>
     * <li>Date (14)</li>
     * <li>Durian (15)</li>
     * <li>Eggplan (16)</li>
     * <li>Elderberry (17)</li>
     * <li>Feijoa (18)</li>
     * <li>Gooseberry (19)</li>
     * <li>Grape (20)</li>
     * <li>Mango (21)</li>
     * <li>Melon (22)</li>
     * <li>Nectarine (23)</li>
     * <li>Orange (24)</li>
     * <li>Papaya (25)</li>
     * <li>Quince (26)</li>
     * <li>Sharon Fruit (27)</li>
     * <li>Ugli Fruit (28)</li>
     * <li>Watermelon (29)</li>
     * </ul>
     * <br>
     * Here are some examples of values that the method will return for the given inputString s :<br>
     * <ul>
     * <li>(empty string) ==> 0</li>
     * <li>a ==> 0</li>
     * <li>apple ==> 0</li>
     * <li>ZZZzz ==> 0</li>
     * <li>Strawberry ==> 27</li>
     * <li>w ==> 29</li>
     * <li>Black ==> 5</li>
     * <li>BlackC ==> 6</li>
     * </ul>
     * The method stores locally the string for which the search is being done. At the next call to the method, 
     * then first a check will be done to compare the new search string with the previous search string.<br>
     * The method will also store the 'first' and 'last' index of the item matching the string. For example of the new 
     * search string is only one character longer, then only a search will be done for the new character, in the items
     * between first and last.<br>
     * <br>
     * Example :<br>
     * Assume the list above is stored.<br>
     * Call is made to this method with s = "m". The method will return 21. 21 and 22 will be stored in a private field. 
     * 22 is the index of the last item that starts with a character equal to "m". 
     * The private field previousSearchString gets the value "m".<br>
     * Nex time the method is called with searchstring "me". Searching can now start as of the second character "e", and searching 
     * will only happen between element 21 and 22. The return value will be 22. 22 will now be stored as first and last index. <br>
     * The first and last index are stored in integer Arrays. Whenever a character is erased, the method will searching again,
     * the first and last index are stored in the Array.
     *   
     * @param s CharSequence holding the string to which FoodItem's ItemDescription should be compared.
     * @return the index if search was completed, -1 if search not completed.
     */
    public int getFirstMatchingItem (CharSequence s) {
	
	int index = 0;//index to first character that should be searched for

	int[] result = new int[2];//used for calling Searchfirst
	
	if (backgroundThread != null) {
	    if (backgroundThread.isAlive())
		return -1;
	}
	
	/**
	 * first of all check if previousSearchString contains anything and if
	 * yes then calculate index at which searching should start. 
	 */
	if (previousSearchString != null) {
	    while ((index < s.length()) &&
		    (index < previousSearchString.length()) &&
		    (ExcelCharacter.compareToAsInExcel(s.charAt(index), previousSearchString.charAt(index)) == 0)) { 
		index++;
	    }
	}

	/**
	 * now continue searching if necessary
	 */
	if (index == s.length()) {
	    //no need to search any further, firstIndex has the value to be returned, return will happen later
	} else {
	    while ((index < s.length()) && (index < (firstIndex.length - 1))) {
		result = searchFirst(firstIndex[index], lastIndex[index], s.charAt(index), index);
		if (result[0] > -1) {
		    firstIndex[index + 1] = result[0];
		    lastIndex[index + 1] = searchLast(result[0], result[1], s.charAt(index), index);
		} else {
		    //nothing matching found
		    if (index < (firstIndex.length - 1)) {
			firstIndex[index + 1] = firstIndex[index];
			lastIndex[index + 1] = lastIndex[index];
		    }
		}
		index++;
	    }
	}
	/**
	 * wrapping up
	 */
	previousSearchString = s.toString();
	return firstIndex[index];
    }

    /**
     * @return a new string with the foodTableSource
     */
    public  String getFoodTableSource() {
	return new String(foodTableSource);
    }

    /**
     * @param foodTableSource the foodTableSource to set
     */
    public  void setFoodTableSource(String foodTableSource) {
	this.foodTableSource = new String(foodTableSource);
    }

    /**
     * returns the ItemDescription for the specified fooditem
     * @param foodItemNumber
     * @return the ItemDescription
     */
    public String getFoodItemDescription(int foodItemNumber) {
	return (new String(getFoodItem_private(foodItemNumber).getItemDescription()));
    }

    /**
     * Returns the number of units for the specified fooditem
     * @param foodItemNumber
     * @return the number of units
     */
    public int getFoodItemNumberOfUnits(int foodItemNumber) {
	return (getFoodItem_private(foodItemNumber).getNumberOfUnits());
    }

    /**
     * the  {@link #backgroundThread} is started and executes {@link #initialize() initialize}. 
     * 
     */
    public void  initializeFoodItemList()  {
	final FoodItemList thislist = this;
	backgroundThread = new Thread(new Runnable() {
	    public void run() {
		thislist.initialize( );
	    }
	});
	backgroundThread.start();
    }

    /**
     * initializes {@link #foodItemList} using raw resource file as source. When finished, an update of the parent class
     * list will be done, by calling {@link #updateList()} in the UI Thread.
     */
    private void initialize()  {
	byte [] b;
	int maxblength = Integer.parseInt(callingContext.getString(R.string.maximumFoodItemLength));
	firstIndex = new int[maximumSearchStringLength + 1];//we will not search further if the given searchstring is longer, but we need a bit larger array for the methods getFirstMatchingItem
	lastIndex = new int[maximumSearchStringLength + 1];
	Resources resources = callingContext.getResources();
	InputStream is = null;
	String readline;
	@SuppressWarnings("unused")
	int nroflinesinsource;
	int readLineNumber = 0;
	int length, chr;
	foodItemList = new ArrayList<FoodItem>();

	try {
	    Log.i(LOG_TAG, "Opening source foodfile");
	    is = resources.openRawResource(R.raw.foodfile);
	    b = new byte[maxblength];
	    //First read the timestamp, I'm not going to do anything with it'
	    readline = readStringFromFile(is);
	    //Now read the source of the food table
	    readline = readStringFromFile(is);
	    foodTableSource = readline.substring(0,readline.indexOf(','));
	    // Now read the number of lines in the source file - this is to set a progress gauge if any
	    readline = readStringFromFile(is);
	    nroflinesinsource = Integer.parseInt(readline.substring(0,readline.indexOf(',')));
	    //start reading all the remaining lines from the foodfile
	    b = new byte[maxblength];
	    length = 1;//start value needs to be > 1
	    chr = 0;//start value needs to be different from -1

	    //as long as end of file not reached continue
	    //as long as line is read which contains characters continue
	    //as long as number of stored bytes is less than maximum recordsize
	    while ((chr != -1) && (length > 0) ){
		length = 0;
		chr = is.read();
		if (chr == ',') 
		{break;}//chr = , means the first empty line, that's where we stop'
		while ((chr != -1) & (chr != '\n')) {
		    if (chr != '\r') {//this LF seems to be there each time before \n
			b[length] = (byte)chr;
			length++;
		    }
		    chr = is.read();
		}
		if (length > 0) {
		    //means we've successfully read a line which contains something
		    readLineNumber = readLineNumber + 1;
		    try {
			FoodItem newitem = new FoodItem(b);
			foodItemList.add(newitem);
			//Log.i(LOG_TAG, "fooditem \""  + newitem.toString() + "\" read from the sourcefile");
		    } catch (InvalidSourceLineException e) {
			// TODO handle exceptions, for example store them locally in the class FoodItemListClass
			// and offer a method to read the errors.
			 e.printStackTrace();
			Log.i(LOG_TAG,"Exception while constructing FoodItem, sourceLine b = \"" + b.toString() + "\", Linenumber = " + readLineNumber + " on position " + e.getPosition());

		    }
		}
	    }
	    Log.i(LOG_TAG,"Closing source foodfile");
	    is.close();


	} catch (IOException e) {
	    Log.d ("FoodItemList.initialize", "Could not open Raw Resource file " +callingContext.getString(R.raw.foodfile), e);
	} finally {
	    try { is.close();} catch (IOException e) {;}
	}

	if (callingContext != null) {
	    final Runnable runInUIThread = new Runnable() {
		public void run() {
		    updateList();
		}
	    };
	    callingThreadHandler.post(runInUIThread);
	} else {
	    //now initialize lastIndex
	    lastIndex[0] = foodItemList.size() - 1;
	    Log.i(LOG_TAG,"lastIndex[0] = " + lastIndex[0] );
	}
    }

    /**
     * Method to read a string from the inputstream, the inputstream must
     * be opened before calling this method.
     * @param fis
     * @return String read from the inputstream, null if EOF is reached
     * @throws IOException
     */
    private String readStringFromFile(InputStream fis) throws IOException {
	StringBuffer returnvalue  = new StringBuffer();
	int chr;
	// Read until the end of the stream   
	chr = fis.read();
	while ((chr  != -1) & (chr != '\n')) {
	    if (chr != '\r') returnvalue.append((char) chr);
	    chr = fis.read();
	}
	return returnvalue.toString();
    }

    /**
     * Search the index of the first item 'matching' the char value. low is the index
     * of the item in de fooditemlist where searching should start, high is the last.
     * index is the index to be used in the itemdescription String, in other words it 
     * points to the character in the strings (searchstring and itemdescription strings) that
     * is compared.
     * @param low
     * @param high
     * @param value
     * @param index
     * @return array of two elements of int, first one giving the actual result, second element is to be used in
     * the call to searchlast that should normally be done immediately after searchfirst. If the first element < -2
     * then nothing is found.
     */
    private int[] searchFirst (int low, int high, char value, int index) {
	int temp = 0;
	int temp2 = 0;
	int mid = 0;
	int belength;
	low++;//doing this because i copied this code from my J2ME version, where I use recordstore and where the first record is stored in index = 1
	high++;
	//returnvalue [1] wil be set in this method to lowest value where a character was found with value > value.
	//this value will then be used when calling searchlast to reduce searching time
	int[] returnvalue = {-1,high};


	char[] be;

	temp = high + 1;//this becomes the highest start value
	while (low < temp) {
	    mid = (low + temp)/2;
	    be = this.foodItemList.get(mid - 1).getItemDescription().toCharArray();
	    belength = be.length;
	    if (!(belength > index))//b is a string which is shorter than the enteredstring, so definitely before the enteredstring (smaller than)
	    {low = mid+1;}
	    else {
		if (ExcelCharacter.compareToAsInExcel(be[index], value) < 0)
		    low = mid + 1; 
		else {
		    if (temp2 > value) {
			returnvalue[1] = mid;
		    } 
		    temp = mid; 
		}     
	    }

	}
	if (low > high) {
	    ;
	} else {
	    be = this.foodItemList.get(low - 1).getItemDescription().toCharArray();
	    belength = be.length;
	    if (belength > index) {
		if ((low < (high + 1)) & (ExcelCharacter.compareToAsInExcel(be[index], value) == 0))
		    returnvalue[0] = low;
	    } else {
		;
	    }
	}
	returnvalue[0] = returnvalue[0] -1; 
	returnvalue[1] = returnvalue[1] -1; 
	return returnvalue;
    }

    /**
     * For a full description see {@link #searchFirst(int, int, char, int) searchfirst}
     * @param low
     * @param high
     * @param value
     * @param index
     * @return the result in theory it can happen that returnvalue = -1 however if searchFirst has been called
     * this should not happen.
     */
    private int searchLast (int low, int high, char value, int index) {
	int temp = 0;
	int mid = 0;
	int returnvalue = -1;
	char[] be;
	int belength;

	low++;//doing this because i copied this code from my J2ME version, where I use recordstore and where the first record is stored in index = 1
	high++;
	temp = low -1;
	while (high > temp) {
	    if ((high + temp)%2 > 0) {mid = (high+temp)/2+1;}
	    else {mid = (high+temp)/2;}
	    be = this.foodItemList.get(mid - 1).getItemDescription().toCharArray();
	    belength = be.length;
	    if (!(belength > index))//be is a string which is shorter than the enteredstring, so definitely before the enteredstring (smaller than)
	    {temp = mid;}
	    else {
		if (ExcelCharacter.compareToAsInExcel(be[index], value) > 0)
		    high = mid - 1; 
		else
		    //can't be high = mid-1: here A[mid] >= value,
		    //so high can't be < mid if A[mid] == value
		    temp = mid; 
	    }

	}
	if (high < low) {
	    ;//returnvalue = -1;
	} else {
	    be = this.foodItemList.get(high - 1).getItemDescription().toCharArray();
	    belength = be.length;
	    if (belength>index) {
		if (((low-1) < high) & (ExcelCharacter.compareToAsInExcel(be[index], value) == 0))
		    returnvalue = high;
	    } else {
		;//returnvalue = -1;
	    }
	}    
	returnvalue= returnvalue -1; 
	return returnvalue;
    }

    /**
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
	View v = convertView;
	if (v == null) {
	    LayoutInflater vi = (LayoutInflater) callingContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    v = vi.inflate(R.layout.helpdiabetesrow, null);
	}
	TextView tt = (TextView) v.findViewById(R.id.fooditemlist_text);
	if (tt != null) {
	    tt.setText((String)getItem(position));                            
	}
	return v;
    }

    /**
     * updates the activitylist, should be called from uithread<br>
     * When finished then calls {@link net.johandegraeve.helpdiabetes.HelpDiabetes#triggerSearching()}. This is because the
     * user may have been entering text in the search box.
     */
    private void updateList() {
	Log.i(LOG_TAG,"method updateList called");	
	lastIndex[0] = foodItemList.size() - 1;
	previousSearchString = null;
	clear();
	for (int index = 0;index < foodItemList.size();index++) {
	    add(foodItemList.get(index).toString());
	}
	((HelpDiabetes)callingContext).triggerSearching();
    }
    
}

