package com.android.skiggle;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CandidateKey extends Drawable {
	private int mColor = 0xffcccccc; // Gray
	private char mCandidateChar;
	private Rect mRect;
	
	public CandidateKey() {
		super();
	} // End of CandidateKey() constructor
	
	public CandidateKey(int left, int top, int right, int bottom, char c) {
		super();
		mCandidateChar = c;
		mRect = new Rect(left, top, right, bottom);
	} // End of CandidateKey(), with corners and character specified, constructor
	
	public CandidateKey(int left, int top, int right, int bottom, char c, int color) {
		super();
		mCandidateChar = c;
		mColor = color;
		mRect = new Rect(left, top, right, bottom);
		
	} // End of CandidateKey(), with corners, character and color specified, constructor

	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		int charWidth = 15; // TODO - Need to compute this dynamically

		paint.setColor(mColor); // Green;    0xff33ff00); // Bright Green
		
		canvas.drawRect(mRect, paint);
		
		paint.setColor(0xff444444); // Dark Gray for character
		paint.setTextSize(20);
		//float[] widths = {15};
		canvas.drawText(String.valueOf(mCandidateChar), (float) (mRect.left  + Math.floor((mRect.right - mRect.left - charWidth))/2), mRect.bottom - 5, paint);	


	}

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

}
