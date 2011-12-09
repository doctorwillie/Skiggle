package com.android.skiggle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class TestView extends View {

	public TestView(Context context) {
		super(context);
		
	}
	
	private void showText(String c, Canvas canvas) {
		Rect rect = new Rect(100, 100, 150, 150);
		Paint paint = new Paint();
		int charWidth = 15; // TODO - Need to compute this dynamically

		paint.setColor(0xff66ff00); // Green;    0xff33ff00); // Bright Green
		
		canvas.drawRect(rect, paint);
		
		paint.setColor(0xff444444); // Dark Gray for character
		paint.setTextSize(20);
		//float[] widths = {15};
		canvas.drawText(c, 120, 120, paint);	
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		showText("&", canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			showText("@", Skiggle.sCanvas);
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
