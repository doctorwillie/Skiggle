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

public class CandidatesKeyBoard extends View {
	private ShapeDrawable mDrawable;
	private String mCandidateCharacters;
	private CandidateKey[] mKeys;
	private int mX = Skiggle.sVirtualKeyboardLeft;
	private int mY = Skiggle.sVirutalKeyhoardTop;
	private int mKeyWidth = 30;
	private int mKeyHeight = 30;
	private int mKeySpacing = 2;
	protected Rect mRect = new Rect(mX, mY, mX + Skiggle.sDefaultWritePadWidth, mY + mKeyHeight + (2 * mKeyHeight));


	public CandidatesKeyBoard(Context context, Character c, String str) {
		super(context);
		int keyColor = Skiggle.GRAY_80; // Gray80 (default key color)
		mCandidateCharacters = str;
		
		int width = str.length() * (mKeyWidth + mKeySpacing);

		mDrawable = new ShapeDrawable(new RectShape());
		//mDrawable.getPaint().setColor(Skiggle.GRAY_80); // Gray80
		mDrawable.getPaint().setColor(Skiggle.sDefaultCanvasColor); // Light Gray
		mDrawable.setBounds(mX, mY, mX + width, mY + mKeyHeight);
		
		mKeys= new CandidateKey[str.length()];
		
		
		int iBase = 0;
		if (c != null) {
			keyColor = Skiggle.TRUE_GREEN; // Green for best guess (first char in the string);
			mKeys[iBase] = new CandidateKey(context, mX, mY, mX + mKeyWidth, mY + mKeyHeight, c, keyColor);
			iBase = iBase + 1;
		}
		
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

			mKeys[i] = new CandidateKey(context, left, top, right, bottom, mCandidateCharacters.charAt(i), keyColor);
			
		}
		
	} // End of OldCandidatesKeyBoard() constructor

	
	/**
	 * Draws the keys for the best candidate followed by the other candidates
	 */
	@Override
	public void onDraw(Canvas canvas) {

		for (int i = 0; i < mKeys.length; i++ ) {
			mKeys[i].draw(canvas);
		}
		
	}
	
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
	
	/*
	@Override
	public void draw(Canvas canvas) {
//		mTestView.draw(canvas);
		
		for (int i = 0; i < mKeys.length; i++) {
			mKeys[i].draw(canvas);			
			
		}

			
	}
	*/

		
} // End of OldCandidatesKeyBoard class
