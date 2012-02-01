/*
*   This file is part of Skiggle, an Android Input Method Editor (IME)
*   for handwritten input.
*
*   Copyright (C) 2009-2012 Willie Lim <drwillie650@gmail.com>
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/

package com.android.skiggle;


import com.android.skiggle.R;
import com.android.skiggle.english.SegmentBitSetEn;
import com.android.skiggle.chinese.SegmentBitSetCn;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class Skiggle extends Activity {
	
	// Global constants
	
	protected static final String APP_TITLE = "Skiggle"; // Title of the app
	protected static final String PREFERENCES_FILENAME = "Skiggle_preferences";
	
	protected static final String CHINESE_MODE = "Chinese"; // Chinese handwriting mode
	protected static final String ENGLISH_MODE = "English"; // English handwriting mode
	protected static final String DEFAULT_LANGUAGE_MODE = ENGLISH_MODE; // Default language mode is English
	
	// TODO: Use res/values/colors.xml?
	// Color codes
	public static final int AQUA = 0xFF00FFFF;
	public static final int GRAY_26 = 0xFF424242;
	public static final int GRAY_80 = 0xFFCCCCCC;
	public static final int RED = 0xFFFF0000;
	public static final int TRUE_GREEN = 0xFF00AF23;
	public static final int WHITE = 0xFFFFFFFF;//0xFFFFFFFF; //R.color.white;
	
	// Global variables
	protected static String sLanguage = ENGLISH_MODE; // Set default language to English

	protected static int sDefaultPenColor = AQUA; // aqua
	protected static int sDefaultCanvasColor = WHITE;  // White color
	protected static float sDefaultStrokeWidth = 12.0F;	
	protected static float sDefaultFontSize = 14.0F; //12.0F;

	protected static int sDefaultWritePadWidth = 320;
	public static int sDefaultWritePadHeight = 480;
	
	protected static int sVirtualKeyboardLeft = 5; // Location of left edge of virtual keyboard (for candidate characters recognized)
	protected static int sVirutalKeyhoardTop = 5; // Location of top edge of virtual keyboard (for candidate characters recognized)
	
	private static boolean mIsAppInstance = true; // Flag to indicate whether Skiggle is running as a standalone app or as a soft keyboard
	
//	public static boolean sDebugOn = false; // TEMPORARY: Used for testing the virtual keyboard
	public static boolean sDebugOn = true; // TEMPORARY: Used for testing the virtual keyboard

//	protected static BoxView sBoxView;
	// TODO: Rename sBoxView to sWritingArea
	protected static WritingArea sBoxView;
	
//	protected static Canvas sCanvas;
	protected static Paint sPaint;
	protected static Paint sTextPaint;
	protected static Context sContext;

	// TODO - Move to the WritingArea class but keep the setTitle line (last line) in the Skiggle class
	public void setLanguageMode(String language) {
		// Set language specifics globals
		sLanguage = language;
		if (language == CHINESE_MODE) {
			SegmentBitSetCn.initializeSegmentBitSetGlobals();
		}
		else if (language == ENGLISH_MODE) {
			SegmentBitSetEn.initializeSegmentBitSetGlobals();
		}

		this.setTitle(APP_TITLE + "-" + language);
	}

	private void restorePreferences() {
		// Restore preferences
//		SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
		SharedPreferences prefs = getSharedPreferences(PREFERENCES_FILENAME, MODE_PRIVATE);
		sLanguage = prefs.getString("language", DEFAULT_LANGUAGE_MODE);
		sDebugOn = prefs.getBoolean("debugMode", false);
	}
	
	private void savePreferences() {
		// Save current Preferences
//		SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
		SharedPreferences prefs = getSharedPreferences(PREFERENCES_FILENAME, MODE_PRIVATE);
		SharedPreferences.Editor mPrefsEditor = prefs.edit();
		mPrefsEditor.putString("language", sLanguage);
		mPrefsEditor.putBoolean("debugMode", sDebugOn);
		mPrefsEditor.commit();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	

		sContext = this.getApplication().getBaseContext();
//		sBoxView = new BoxView(this);
		sBoxView = new WritingArea(this, 0, 0, null, mIsAppInstance);
		setContentView(sBoxView);
		//setContentView(new WritingArea(this));
		
//		testSoftKeyboard();

		sPaint = new Paint();
		sPaint.setAntiAlias(true);
		sPaint.setDither(true);
		sPaint.setStyle(Paint.Style.STROKE);
		sPaint.setStrokeJoin(Paint.Join.ROUND);
		sPaint.setStrokeCap(Paint.Cap.ROUND);
		
		// Set canvas defaults
		sPaint.setColor(sDefaultPenColor);
		sPaint.setStrokeWidth(sDefaultStrokeWidth);

		sTextPaint = new Paint();
		// Set text paint defaults
		sTextPaint.setTextSize(sDefaultFontSize);

		setLanguageMode(sLanguage);
		
		restorePreferences();
		
		/*
		// Set language specifics globals
		if (sLanguage == CHINESE_MODE) {
			SegmentBitSetCn.initializeSegmentBitSetGlobals();
		}
		else if (sLanguage == ENGLISH_MODE) {
			SegmentBitSetEn.initializeSegmentBitSetGlobals();
		}

		this.setTitle(APP_TITLE + "-" + sLanguage);
		 */
	}

	/*
	protected void onSaveInstanceState(Bundle bundle) {
		savePreferences();
	}
	*/
	
	@Override
	protected void onPause() {
		super.onPause();
		
		savePreferences();

		//sBoxView.clear(); // Need this here?
		
	}
	
	

	@Override
	protected void onStop() {
		super.onStop();
		savePreferences();
//		sBoxView.clear();
		
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.skiggle_menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.chinese:
	    	setLanguageMode(CHINESE_MODE);
	        return true;
	    case R.id.english:
	    	setLanguageMode(ENGLISH_MODE);
	        return true;
	    case R.id.debug_on:
	    	sDebugOn = true; 
	        return true;
	    case R.id.debug_off:
	    	sDebugOn = false; 
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
}
