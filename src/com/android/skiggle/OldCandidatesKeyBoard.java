package com.android.skiggle;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

public class OldCandidatesKeyBoard extends Drawable {
	private ShapeDrawable mDrawable;
	private String mCandidateCharacters;
	private OldCandidateKey[] mKeys;
	private int mX = Skiggle.sVirtualKeyboardLeft;
	private int mY = Skiggle.sVirutalKeyhoardTop;
	private int mKeyWidth = 30;
	private int mKeyHeight = 30;
	private int mKeySpacing = 2;
	
//	private TestKeyView[] mTestViews;

	public OldCandidatesKeyBoard(Character c, String str) {
		super();
		int keyColor = 0xffcccccc; // Gray (default key color)
		mCandidateCharacters = str;
		
//		mTestView = new TestKeyView(Skiggle.sContext);

		
		int width = str.length() * (mKeyWidth + mKeySpacing);

		mDrawable = new ShapeDrawable(new RectShape());
		//mDrawable.getPaint().setColor(0xffcccccc); // Light Gray
		mDrawable.getPaint().setColor(Skiggle.sDefaultCanvasColor); // Light Gray
		mDrawable.setBounds(mX, mY, mX + width, mY + mKeyHeight);
		
		mKeys= new OldCandidateKey[str.length()];
		
//		mTestViews = new TestKeyView[str.length()];
		
		int iBase = 0;
		if (c != null) {
			keyColor = 0xff33cc33; // Green for best guess (first char in the string);
			mKeys[iBase] = new OldCandidateKey(mX, mY, mX + mKeyWidth, mY + mKeyHeight, c, keyColor);
			iBase = iBase + 1;
		}
		
		for (int i = iBase; i < str.length(); i++ ) {
			int left = mX + (mKeyWidth + mKeySpacing) * i;
			int top = mY;
			int right = left + mKeyWidth;
			int bottom = top + mKeyHeight;
			/*
			if (i == 0) { // First char is the best guess
				keyColor = 0xff33cc33; // Green for best guess (first char in the string);
			}
			else {
				keyColor = 0xffcccccc; // Gray, default color;
			}
			*/
			keyColor = 0xffcccccc; // Gray, default color;
			mKeys[i] = new OldCandidateKey(left, top, right, bottom, mCandidateCharacters.charAt(i), keyColor);
			
//			mTestViews[i] = new TestKeyView(Skiggle.sContext, left, top, right, bottom, mCandidateCharacters.charAt(i), keyColor);
//			Skiggle.sTestView = new TestKeyView(Skiggle.sContext, 100, 100, 110, 140, mCandidateChar, mColor);
//			Skiggle.sTestView.draw(Skiggle.sCanvas);
			
		}
	} // End of OldCandidatesKeyBoard() constructor

	/**
	 * Draws the keys for the best candidate followed by the other candidates
	 */

	@Override
	public void draw(Canvas canvas) {
//		mTestView.draw(canvas);
		
		for (int i = 0; i < mKeys.length; i++) {
			mKeys[i].draw(canvas);
			
/*
		try {
			mTestViews[i].draw(canvas);
			}
			catch (Exception e) {
				e.getMessage();
			}
*/			
			
		}

			
	}

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

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}
} // End of OldCandidatesKeyBoard class
