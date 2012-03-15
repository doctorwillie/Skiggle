package com.android.skiggle.softkeyboard;

import com.android.skiggle.SkiggleSoftKeyboard;
import com.android.skiggle.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class TestView extends View {
	 private static final int WHITE = 0xFFFFFFFF;
	 private static final int AQUA = 0xFF00FFFF;
	 private static int KEY_CODE_FOR_SMALL_W = 119; // 119 is letter "w" (for white color)
	 private static int KEY_CODE_FOR_SMALL_A = 97; // 103 is letter "a" (for aqua color)
	 
	 private boolean mIsGray;
	 private int mCurrBackgroundColor = WHITE;
	 protected SkiggleSoftKeyboard parentSoftKeyboard;

	public TestView(Context context, SkiggleSoftKeyboard sSoftKeyboard) {
		super(context);
		this.setBackgroundColor(WHITE);
		mCurrBackgroundColor = WHITE;
		mIsGray = true;
		parentSoftKeyboard = sSoftKeyboard;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int keyCode;
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (mIsGray == true) {
				mCurrBackgroundColor = AQUA;
				mIsGray = false;
				keyCode = KEY_CODE_FOR_SMALL_A;
			}
			else {
				mCurrBackgroundColor = WHITE;
				mIsGray = true;
				keyCode = KEY_CODE_FOR_SMALL_W;
			}

			this.setBackgroundColor(mCurrBackgroundColor);
			parentSoftKeyboard.onKey(keyCode, null);
		}

				
		return true;		
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint paint = new Paint();
		
		paint.setColor(mCurrBackgroundColor);
		
		canvas.drawRect(100, 150, 200, 240, paint);
		invalidate();
	}

}
