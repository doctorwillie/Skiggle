package com.android.skiggle;

import com.android.skiggle.Skiggle;
//import com.android.skiggle.Skiggle.BoxView;
import com.android.skiggle.chinese.SegmentBitSetCn;
import com.android.skiggle.english.SegmentBitSetEn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class WritingArea extends View {
	private Bitmap mBitmap;
	protected Canvas canvas;
	private Path mPath;
	private Paint mStrokePaint;
	private Paint mTextPaint;
	private Paint mBitmapPaint; // Same as mStrokePaint???
	private int mBitmapPaintColor;
	private boolean mIsAppInstance = false; // Flag to indicate if Skiggle is an app or a soft key board; defaults to soft key board (not an app instance)
	private CandidatesKeyboard mCandidatesKeyboard;
	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
	private PenStroke mPenStroke;
	//		private PenSegment mPenSegment;
	//		private int mStrokeNumber = 0;
	//		private int mSegmentNumber = 0;	
	protected PenCharacter penCharacter = new PenCharacter();
	private SkiggleSoftKeyboard mSoftKeyboard;
	
	public WritingArea(Context context, int width, int height, SkiggleSoftKeyboard softKeyboard, boolean isAppInstance) {
		super(context);
		mStrokePaint = new Paint();
		mStrokePaint.setAntiAlias(true);
		mStrokePaint.setDither(true);
		mStrokePaint.setStyle(Paint.Style.STROKE);
		mStrokePaint.setStrokeJoin(Paint.Join.ROUND);
		mStrokePaint.setStrokeCap(Paint.Cap.ROUND);
		// Set canvas defaults
		mStrokePaint.setColor(Skiggle.sDefaultPenColor);
		mStrokePaint.setStrokeWidth(Skiggle.sDefaultStrokeWidth);
		mTextPaint = new Paint();
		// Set text paint defaults
		mTextPaint.setTextSize(Skiggle.sDefaultFontSize);	
		mBitmap = Bitmap.createBitmap((width > 0) ? width : Skiggle.sDefaultWritePadWidth,
									  (height > 0) ? height : Skiggle.sDefaultWritePadHeight,
									  Bitmap.Config.ARGB_8888);
		mBitmapPaintColor = Skiggle.sDefaultCanvasColor;
		canvas = new Canvas(mBitmap);
		mIsAppInstance = isAppInstance;
		mSoftKeyboard = softKeyboard;	
		mCandidatesKeyboard = new CandidatesKeyboard(context, null, "", mSoftKeyboard, mIsAppInstance);
		mPath = new Path();
		setLanguageMode(Skiggle.sLanguage);
	} // End of WritingArea constructor

	/**
	 * Sets the language mode for Skiggle
	 * @param language - ENGLISH_MODE (default) or CHINESE_MODE
	 */
	public void setLanguageMode(String language) {
		// Set language specifics globals
		Skiggle.sLanguage = language;
		if (language == Skiggle.CHINESE_MODE) {
			SegmentBitSetCn.initializeSegmentBitSetGlobals();
		}
		else if (language == Skiggle.ENGLISH_MODE) {
			SegmentBitSetEn.initializeSegmentBitSetGlobals();
		}
	}	
	
	@Override
	protected void onDraw(Canvas canvas) {	
		canvas.drawColor(mBitmapPaintColor); // Allow dynamic changing of background color by changing the preferences/settings.
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		canvas.drawPath(mPath, mStrokePaint);
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
		canvas.drawPath(mPath, mStrokePaint);
		// If the stroke is a point of zero length , make it a filled circle of
		// diameter Skiggle.Skiggle.sDefaultStrokeWidth and add it to the path
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
		canvas.drawPath( mPenStroke, mStrokePaint);		
		// Check to see if the stroke is a jagged "clear screen" stroke
		if ((mPenStroke.penStrokeLength/(mPenStroke.boundingRectWidth + mPenStroke.boundingRectHeight)) > 2) {
			this.clear();
		}
		else {
			penCharacter.addSegments(mPenStroke, canvas, mTextPaint);					
			penCharacter.findMatchingCharacter(canvas, mTextPaint, Skiggle.sLanguage);		
			// TODO: Temp from findMacthingCharacter
			String str = penCharacter.penCharacterCandidates;
			if (penCharacter.matchedChar != null) {
				str = penCharacter.matchedChar + PenUtil.removeCharFromString(penCharacter.matchedChar, penCharacter.penCharacterCandidates);
			}
			if (mCandidatesKeyboard != null) {
				mCandidatesKeyboard.setAttributes(SkiggleSoftKeyboard.sContext, penCharacter.matchedChar, str, mSoftKeyboard, mIsAppInstance);
				mCandidatesKeyboard.draw(canvas);
			}
		}
		// kill this so we don't double draw
		mPath.reset();
	}

	/**
	 * Handles touch events
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		if (Skiggle.sDebugOn) {
			// Trap touch event that is inside isAppInstance (should be taken care of by Android's nested event handlers)
			if ((mCandidatesKeyboard != null) && mCandidatesKeyboard.mRect.contains((int) x, (int) y)) {
				boolean flag =  mCandidatesKeyboard.onTouchEvent(event);
				// Clear writing area when character is entered
				if (!mIsAppInstance) {
					clear();
				}
				invalidate();
				return flag;
			}
		}
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
	
	/**
	 * Clears the writing area
	 */
	public void clear() {		
		// Re-initialize the soft keyboard
		mCandidatesKeyboard = new CandidatesKeyboard(getContext(), null, "", mSoftKeyboard, mIsAppInstance);;
		mBitmap.eraseColor(mBitmapPaintColor);
		mPath.reset();		
		// Create a new PenCharacter object
		penCharacter = new PenCharacter();
		invalidate();
	}
	
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3,
			int arg4) {
		// TODO Auto-generated method stub		
	}
}
