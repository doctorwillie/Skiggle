package com.android.skiggle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

public class CandidateKey extends View {
	private int mColor = Skiggle.GRAY_80; // Defaults to Gray80
	private Character mChar;
	public Rect mRect;
	
	public CandidateKey(Context context) {
		super(context);
	} // End of CandidateKeyCandidateKey() constructor
	
	public CandidateKey(Context context, int left, int top, int right, int bottom, char c) {
		super(context);
		mChar = c;
		mRect = new Rect(left, top, right, bottom);
	} // End of CandidateKey(), with corners and character specified, constructor
	
	public CandidateKey(Context context, int left, int top, int right, int bottom, char c, int color) {
		super(context);
		mChar = c;
		mColor = color;
		mRect = new Rect(left, top, right, bottom);
		
	} // End of CandidateKey(), with corners, character and color specified, constructor
	
	private void drawKey(Canvas canvas, int keyColor, int textColor) {

		Paint paint = new Paint();
		int charWidth = 15; // TODO - Need to compute this dynamically

		paint.setColor(keyColor);
		
		canvas.drawRect(mRect, paint);
		paint.setColor(textColor);
		paint.setTextSize(20);
		canvas.drawText(String.valueOf(mChar), (float) (mRect.left  + Math.floor((mRect.right - mRect.left - charWidth))/2), mRect.bottom - 5, paint);	

	}

	@Override
	public void draw(Canvas canvas) {
		drawKey(canvas, mColor, Skiggle.GRAY_26);  // Use Dark Gray26 for character
		/*
		Paint paint = new Paint();
		int charWidth = 15; // TODO - Need to compute this dynamically

		paint.setColor(mColor);
		
		canvas.drawRect(mRect, paint);
//		paintKeyColor(mColor);
		paint.setColor(Skiggle.GRAY_26); // Dark Gray26 for character
		paint.setTextSize(20);
		//float[] widths = {15};
		canvas.drawText(String.valueOf(mChar), (float) (mRect.left  + Math.floor((mRect.right - mRect.left - charWidth))/2), mRect.bottom - 5, paint);	
		*/

	}
	
	private void showBigChar() {
		if (mChar != null) {
			int textSize = 200;
			int left = (Skiggle.sDefaultWritePadWidth - textSize)/2;
			int top = Skiggle.sDefaultWritePadHeight - (Skiggle.sDefaultWritePadHeight - textSize)/2;
			Paint paint = new Paint();
			paint.setColor(mColor);			
			paint.setTextSize(textSize);
			Skiggle.sCanvas.drawText(mChar.toString(), left, top, paint);
			
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			showBigChar();		
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
//			invalidate();
			break;
		case MotionEvent.ACTION_UP:
//			invalidate();
			break;
		}
		return true;
	}
	
	protected void clear(Canvas canvas) {
		drawKey(canvas, Skiggle.sDefaultCanvasColor, Skiggle.sDefaultCanvasColor);	
	}

}
