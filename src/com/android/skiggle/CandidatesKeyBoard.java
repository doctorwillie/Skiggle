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
		int keyColor = 0xffcccccc; // Gray (default key color)
		mCandidateCharacters = str;
		
		int width = str.length() * (mKeyWidth + mKeySpacing);

		mDrawable = new ShapeDrawable(new RectShape());
		//mDrawable.getPaint().setColor(0xffcccccc); // Light Gray
		mDrawable.getPaint().setColor(Skiggle.sDefaultCanvasColor); // Light Gray
		mDrawable.setBounds(mX, mY, mX + width, mY + mKeyHeight);
		
		mKeys= new CandidateKey[str.length()];
		
		
		int iBase = 0;
		if (c != null) {
			keyColor = 0xff33cc33; // Green for best guess (first char in the string);
			mKeys[iBase] = new CandidateKey(context, mX, mY, mX + mKeyWidth, mY + mKeyHeight, c, keyColor);
			iBase = iBase + 1;
		}
		
		for (int i = iBase; i < str.length(); i++ ) {
			int left = mX + (mKeyWidth + mKeySpacing) * i;
			int top = mY;
			int right = left + mKeyWidth;
			int bottom = top + mKeyHeight;

			if (i == 0) { // First char is the best guess
				keyColor = 0xff33cc33; // Green for best guess (first char in the string);
			}
			else {
				keyColor = 0xffcccccc; // Gray, default color;
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

	public void oldDraw(Canvas canvas) {
		mDrawable.draw(canvas);
		Rect rect = mDrawable.getBounds();
		int baseX = rect.left;
		int baseY = rect.bottom - 5;
		int maxNumOfCandidateKeys = Math.min(5, mCandidateCharacters.length());
		for (int i = 0; i < maxNumOfCandidateKeys; i++) {
			Paint tempPaint = new Paint();
			int xI = baseX + (mKeyWidth + mKeySpacing) * i;
			int charWidth = 15; // TODO - Need to compute this dynamically

			if (i == 0) { // First character is the best candidate
				tempPaint.setColor(0xff33cc33); // Green;    0xff33ff00); // Bright Green
			}
			else {
				tempPaint.setColor(0xffcccccc); // Gray
			}
			
			canvas.drawRect(xI, baseY - mKeyHeight, xI + mKeyWidth, baseY, tempPaint);
			tempPaint.setColor(0xff444444); // Gray

			
			tempPaint.setTextSize(20);
			//float[] widths = {15};
			canvas.drawText(mCandidateCharacters.substring(i, i+1), (float) (xI  + Math.floor((mKeyWidth - charWidth))/2), baseY - 5, tempPaint);	
		}
	} // End of draw() method
		
} // End of OldCandidatesKeyBoard class
