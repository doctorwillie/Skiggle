/*
*   This file is part of Skiggle, an online handwriting recognition
*   Java application.
*   Copyright (C) 2009-2011 Willie Lim <drwillie650@gmail.com>
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


import com.android.skiggle.chinese.SegmentBitSetCn;
import com.android.skiggle.english.SegmentBitSetEn;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Skiggle extends Activity {
	//	 implements ColorPickerDialog.OnColorChangedListener {
	
	protected static final String APP_TITLE = "Skiggle"; // Title of the app
	
	protected static final String CHINESE_MODE = "Chinese"; // Chinese handwriting mode
	protected static final String ENGLISH_MODE = "English"; // Code for 
	protected static String sLanguage = ENGLISH_MODE; // Set default language to English

	protected static int sDefaultPenColor = 0xFF00FFFF;
	protected static int sDefaultCanvasColor = 0xFFFFFFFF;  // White color
	protected static float sDefaultStrokeWidth = 12.0F;	
	protected static float sDefaultFontSize = 14.0F; //12.0F;

	protected static int sDefaultWritePadWidth = 320;
	public static int sDefaultWritePadHeight = 480;
	
	protected static int sVirtualKeyboardLeft = 5; // Location of left edge of virtual keyboard (for candidate characters recognized)
	protected static int sVirutalKeyhoardTop = 5; // Location of top edge of virtual keyboard (for candidate characters recognized)
	
	protected static CandidatesKeyBoard sTestKeyBoard = null;

//	public static BoxView sBoxView;
	
	protected static Canvas sCanvas;
	private Paint mPaint;
	private Paint mTextPaint;
	protected static Context sContext;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sContext = this.getApplication().getBaseContext();
//		sBoxView = new BoxView(this);
		setContentView(new BoxView(this));
//		setContentView(sBoxView);
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(sDefaultPenColor);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(sDefaultStrokeWidth);

		mTextPaint = new Paint();
		mTextPaint.setTextSize(sDefaultFontSize);

		// Set language specifics globals
		if (sLanguage == CHINESE_MODE) {
			SegmentBitSetCn.initializeSegmentBitSetGlobals();
		}
		else if (sLanguage == ENGLISH_MODE) {
			SegmentBitSetEn.initializeSegmentBitSetGlobals();
		}

		this.setTitle(APP_TITLE + "-" + sLanguage);
	}
	
	public class BoxView extends View {

		private Bitmap mBitmap;

//		private Canvas mCanvas;
		private Path mPath;
		private Paint mBitmapPaint;
		private float mX, mY;
		private static final float TOUCH_TOLERANCE = 4;
		private PenStroke mPenStroke;
		//		private PenSegment mPenSegment;
		//		private int mStrokeNumber = 0;
		//		private int mSegmentNumber = 0;
		
		public PenCharacter penCharacter = new PenCharacter();

		public BoxView(Context context) {
			super(context);

			mBitmap = Bitmap.createBitmap(sDefaultWritePadWidth, sDefaultWritePadHeight, Bitmap.Config.ARGB_8888);
			sCanvas = new Canvas(mBitmap);
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);
			mPath = new Path();

			

		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(sDefaultCanvasColor);

			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

			canvas.drawPath(mPath, mPaint);
			
			if (Skiggle.sTestKeyBoard != null) {
				sTestKeyBoard.draw(canvas);			
			}

		}

		private void touchStart(float x, float y) {
			mPath.reset();
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
		}

		private void touchMove(float x, float y) {
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
				mX = x;
				mY = y;
			}
		}

		private void touchUp() {
			mPath.lineTo(mX, mY);

			// commit the path to our off screen
			sCanvas.drawPath(mPath, mPaint);

			// If the stroke is a point of zero length , make it a filled circle of
			// diameter Skiggle.sDefaultStrokeWidth and add it to the path
			PathMeasure pMeasure = new PathMeasure(mPath, false);
			if (pMeasure.getLength() == 0) {
				RectF boundingRectF = new RectF(); 
				mPath.computeBounds(boundingRectF, false);

				// Create a line of 1 pixel length
				mPath.lineTo(boundingRectF.centerX(), boundingRectF.centerY() + 1);

			}

			// Set pen stroke to a copy of the stroke
			mPenStroke = new PenStroke(mPath);
			mPenStroke.addPath(mPath);
			penCharacter.addStroke(mPenStroke);

			// Paint the copy of the stroke with the new pen color
			sCanvas.drawPath( mPenStroke, mPaint);
			
			// Check to see if the stroke is a jagged "clear screen" stroke
			if ((mPenStroke.penStrokeLength/(mPenStroke.boundingRectWidth + mPenStroke.boundingRectHeight)) > 2) {
				this.clear();
				
				// Clear the virtual keyboard if there is one
				if (sTestKeyBoard != null) sTestKeyBoard = null;
				// Clear the Pen Character object
				
				penCharacter = new PenCharacter();
			}
			else {

				penCharacter.addSegments(mPenStroke, sCanvas, mTextPaint);			
				penCharacter.findMatchingCharacter(sCanvas, mTextPaint, penCharacter, sLanguage);
			}

			// kill this so we don't double draw
			mPath.reset();
		}


		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float x = event.getX();
			float y = event.getY();
			
			/*
			// Trap touch event that is inside mTestView (should be taken care of by Android's nested event handlers)
			if ((sTestKeyBoard != null) && sTestKeyBoard.mRect.contains((int) x, (int) y)) {
				invalidate();
				return sTestKeyBoard.onTouchEvent(event);
			}
	*/
			
//			mTestView.onTouchEvent(event);
			
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:		
				touchStart(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				touchMove(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				touchUp();
				invalidate();
				break;
			}
			return true;
		}

		
		public void clear() {
			mBitmap.eraseColor(sDefaultCanvasColor);
			mPath.reset();
			penCharacter.resetStrokes();
			penCharacter.resetSegments();
			invalidate();
		}

		/*
		@Override
		protected void onLayout(boolean arg0, int arg1, int arg2, int arg3,
				int arg4) {
			// TODO Auto-generated method stub
			
		}
		*/

	}
}
