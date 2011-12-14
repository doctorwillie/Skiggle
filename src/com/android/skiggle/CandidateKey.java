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
	private int mColor = 0xffcccccc; // Gray
	private Character mChar;
	public Rect mRect;
	
	public CandidateKey(Context context) {
		super(context);
	} // End of OldCandidateKey() constructor
	
	public CandidateKey(Context context, int left, int top, int right, int bottom, char c) {
		super(context);
		mChar = c;
		mRect = new Rect(left, top, right, bottom);
	} // End of OldCandidateKey(), with corners and character specified, constructor
	
	public CandidateKey(Context context, int left, int top, int right, int bottom, char c, int color) {
		super(context);
		mChar = c;
		mColor = color;
		mRect = new Rect(left, top, right, bottom);
		
	} // End of OldCandidateKey(), with corners, character and color specified, constructor

	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		int charWidth = 15; // TODO - Need to compute this dynamically

		paint.setColor(mColor); // Green;    0xff33ff00); // Bright Green
		
		canvas.drawRect(mRect, paint);
		
		paint.setColor(0xff444444); // Dark Gray for character
		paint.setTextSize(20);
		//float[] widths = {15};
		canvas.drawText(String.valueOf(mChar), (float) (mRect.left  + Math.floor((mRect.right - mRect.left - charWidth))/2), mRect.bottom - 5, paint);	

	}

	private void showChar() {
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
			showChar();
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			invalidate();
			break;
		}
		return true;
	}

}
