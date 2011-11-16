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



import com.android.skiggle.en.PenCharacterEn;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Skiggle extends Activity {
	//	 implements ColorPickerDialog.OnColorChangedListener {
	
	public static String sLanguage = "En"; // Set default language to English

	public static int sDefaultPenColor = 0xFF00FFFF;   //;
	public static int sDefaultCanvasColor = 0xFFFFFFFF;  //0xFFAAAAAA);
	public static float sDefaultStrokeWidth = 12.0F;	
	public static float sDefaultFontSize = 14.0F; //12.0F;

	public static int sDefaultWritePadWidth = 320;
	public static int sDefaultWritePadHeight = 480;

	private Paint mPaint;
	private Paint mTextPaint;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new BoxView(this));

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



	}

	public class BoxView extends View {

		private Bitmap mBitmap;

		private Canvas mCanvas;
		private Path mPath;
		private Paint mBitmapPaint;
		private float mX, mY;
		private static final float TOUCH_TOLERANCE = 4;
		private PenStroke mPenStroke;
		//		private PenSegment mPenSegment;
		//		private int mStrokeNumber = 0;
		//		private int mSegmentNumber = 0;
		
		public PenCharacter penCharacter = new PenCharacter();

		public BoxView(Context c) {
			super(c);

			mBitmap = Bitmap.createBitmap(sDefaultWritePadWidth, sDefaultWritePadHeight, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);
			mPath = new Path();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(sDefaultCanvasColor);

			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

			canvas.drawPath(mPath, mPaint);
		}

		private void touch_start(float x, float y) {
			mPath.reset();
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
		}

		private void touch_move(float x, float y) {
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
				mX = x;
				mY = y;
			}
		}

		private void touch_up() {
			mPath.lineTo(mX, mY);

			// commit the path to our off screen
			mCanvas.drawPath(mPath, mPaint);

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
			// mPenStroke = new PenStroke();
			mPenStroke = new PenStroke(mPath);
			mPenStroke.addPath(mPath);
			penCharacter.addStroke(mPenStroke);
			//			mStrokeNumber = mStrokeNumber + 1;
			// Set pen color to a different color for the copy of the stroke
			//  penStrokeMeasure = new PathMeasure( mPenStroke, false);
			// if ( penStrokeMeasure.isClosed())
			//	mPaint.setColor(0xFFFF0000);
			// Paint the copy of the stroke with the new pen color
			mCanvas.drawPath( mPenStroke, mPaint);
			
			// Check to see if the stroke is a jagged "clear screen" stroke
			if ((mPenStroke.penStrokeLength/(mPenStroke.boundingRectWidth + mPenStroke.boundingRectHeight)) > 2) {
				this.clear();
				// Clear the Pen Character object
				
				penCharacter = new PenCharacter();
			}
			else {

				// Add segment(s) for the PenStroke
				// mPenSegment = new PenSegment(mPenStroke);
				penCharacter.addSegments(mPenStroke, mCanvas, mTextPaint);
				//				mSegmentNumber = mSegmentNumber + 1;
				
				penCharacter.findMatchingCharacter(mCanvas, mTextPaint, penCharacter, sLanguage);

			}

			// kill this so we don't double draw
			mPath.reset();
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {

			float x = event.getX();
			float y = event.getY();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touch_start(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				touch_move(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				touch_up();
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

	}
}
