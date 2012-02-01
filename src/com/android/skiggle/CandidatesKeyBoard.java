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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.MotionEvent;
import android.view.View;

public class CandidatesKeyboard extends View {
	private ShapeDrawable mDrawable;
	private String mCandidateCharacters;
	private CandidateKey[] mKeys;
	private int mX = Skiggle.sVirtualKeyboardLeft;
	private int mY = Skiggle.sVirutalKeyhoardTop;
	private int mKeyWidth = 30;
	private int mKeyHeight = 30;
	private int mKeySpacing = 2;
	protected Rect mRect = new Rect(mX, mY, mX + Skiggle.sDefaultWritePadWidth, mY + mKeyHeight + (2 * mKeyHeight));

	public CandidatesKeyboard(Context context, Character c, String str, SkiggleSoftKeyboard softKeyboard, boolean isAppInstance) {
		super(context);
		setAttributes(context, c, str, softKeyboard, isAppInstance);
		
	} // End of CandidatesKeyboard() constructor

	/**
	 * Sets the rectangular area and the keys for the keyboard
	 * @param context
	 * @param c - character to highlight in green (matched character)
	 * @param str - other possible matching characters highlighted in gray
	 */
	public void setAttributes(Context context, Character c, String str, SkiggleSoftKeyboard softKeyBoard, boolean isAppInstance) {
		
		int keyColor = Skiggle.GRAY_80; // Gray80 (default key color)
		mCandidateCharacters = str;
		
		int width = str.length() * (mKeyWidth + mKeySpacing);

		mDrawable = new ShapeDrawable(new RectShape());
		mDrawable.getPaint().setColor(Skiggle.sDefaultCanvasColor); // Same background as the writing area
		mDrawable.setBounds(mX, mY, mX + width, mY + mKeyHeight);
		
		mKeys = new CandidateKey[str.length()];
		
		int iBase = 0;
		if (c != null) {
			keyColor = Skiggle.TRUE_GREEN; // Green for best guess (first char in the string);
			mKeys[iBase] = new CandidateKey(context, mX, mY, mX + mKeyWidth, mY + mKeyHeight, c, keyColor, softKeyBoard, isAppInstance);
			iBase = iBase + 1;
		}
		
		if (str.length() > 0) {
			for (int i = iBase; i < str.length(); i++ ) {
				int left = mX + (mKeyWidth + mKeySpacing) * i;
				int top = mY;
				int right = left + mKeyWidth;
				int bottom = top + mKeyHeight;

				if (i == 0) { // First char is the best guess
					keyColor = Skiggle.TRUE_GREEN; // Green for best guess (first char in the string);
				}
				else {
					keyColor = Skiggle.GRAY_80; // Gray, default color;
				}

				mKeys[i] = new CandidateKey(context, left, top, right, bottom, str.charAt(i), keyColor, softKeyBoard, isAppInstance);

			}
		}

	} // End of setAttributes() method
	
	/**
	 * Draws the keys for the best candidate followed by the other candidates
	 */
	@Override
	public void onDraw(Canvas canvas) {
//		super.draw(canvas);
		draw(canvas);
		
/*		for (int i = 0; i < mKeys.length; i++ ) {
			mKeys[i].draw(canvas);
		}
		
*/	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		
		if (mKeys != null) {
			for (int i = 0; i < mKeys.length; i++) {
				if (mKeys[i].mRect.contains((int) x, (int) y)) {
					return (mKeys[i].onTouchEvent(event));
				}
			}
		}
		
		return true; //true;
	}
	
	protected void clear(Canvas canvas) {
		if (mKeys != null) {
			for (int i = 0; i < mKeys.length; i++ ) {
				mKeys[i].clear(canvas);
			}
		}
	}
	
	@Override
	public void draw(Canvas canvas) {

		for (int i = 0; i < mKeys.length; i++) {
			mKeys[i].draw(canvas);			
			
		}
	
	}
	

		
} // End of CandidatesKeyboard class
