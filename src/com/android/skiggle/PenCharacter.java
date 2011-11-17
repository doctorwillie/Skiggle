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

import com.android.skiggle.PenStroke;
import com.android.skiggle.en.PenCharacterEn;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Iterator;
import java.util.Vector;

/**
 * Represents the character written
 * PenCharacterEn is a class representing the character written.  It has:
 *    - one or more PenSegment representing the strokes that make up the character
 *    - one or more PenSegment representing the basic segments (building blocks like '-', 'C', '|') that make up a character
*/
public class PenCharacter {
	
	// TAG for logging debugging info
//	private static final String TAG = "MyPenCharacter";
	
	protected static int DEFAULT_PENCHARACTER_STROKE_WIDTH = 4;

	protected Vector<PenStroke> mPenStrokes;
	public float penStrokesMinX = Skiggle.sDefaultWritePadWidth; //x-coord of left edge of rectangle bounding all strokes
	public float penStrokesMaxX = 0.0F; //x-coord of right edge of rectangle bounding all strokes
	public float penStrokesMinY = Skiggle.sDefaultWritePadHeight; //y-coord of top edge of rectangle bounding all strokes
	public float penStrokesMaxY = 0.0F; //y-coord of bottom edge of rectangle bounding all strokes

	public Vector<PenSegment> penSegments;

	public Character penCharacter = null;
	public String penCharacterCandidates = "";
	protected float mFontSize = Skiggle.sDefaultFontSize;

	public PenCharacter () {
		mPenStrokes = new Vector<PenStroke>();
		penSegments = new Vector<PenSegment>();
		//		mPenStrokes = new Vector<PenSegment>();
	}

	public void addStroke (PenStroke penStroke) {

		// Update the x, y coordinates of the rectangle bounding all the strokes for the character
		penStrokesMinX = Math.min(penStrokesMinX, penStroke.boundingRectF.left);
		penStrokesMaxX = Math.max(penStrokesMaxX, penStroke.boundingRectF.right);
		penStrokesMinY = Math.min(penStrokesMinY, penStroke.boundingRectF.top);
		penStrokesMaxY = Math.max(penStrokesMaxY, penStroke.boundingRectF.bottom);

		mPenStrokes.add(penStroke);
	}

	// Break up stroke into one or more segments
	public void addSegments (PenStroke penStroke, Canvas canvas, Paint textPaint) {

		penSegments.addAll(penStroke.segmentStroke(canvas, textPaint));
		
		printSegmentCharacters(penSegments.elementAt(0).boundingRectF, canvas, textPaint);
	}

	// Reset mPenStrokes
	public void resetStrokes() {
		for (Iterator<PenStroke> i = mPenStrokes.iterator(); i.hasNext();) {
			i.next().reset();
		}
	}

	// Reset penSegments
	public void resetSegments() {
		for (Iterator<PenSegment> i = penSegments.iterator(); i.hasNext();) {
			i.next().reset();
		}
	}
	
	public void findMatchingCharacter (Canvas canvas, Paint textPaint, PenCharacter pChar, String lang) {
		// TODO: Test sLanguage flag to determine which PenCharacterEn class to instantiate
		PenCharacterEn langChar = new PenCharacterEn();

		langChar.getCharacterCandidates(pChar);

		int len = pChar.penCharacterCandidates.length();
		for (int i = 0; i < len; i++) {
			if (langChar.matchCharacter(pChar.penCharacterCandidates.charAt(i), pChar))
				break;
		}

		// penCharacter.showStrokes(mCanvas);
		// Print the stroke statistics on the screen
		// mPenStroke.printPenStrokeStatsOnScreen(this, mCanvas, mPaint, mTextPaint);
		// Reset the pen color back to default
		// mPaint.setColor(sDefaultPenColor);
		// mPenStroke.printMatchedCharacter(mCanvas, penCharacter.mPenCharacter, 100.0F, 300.0F, mTextPaint);
		pChar.printPenCharacter(canvas, 50.0F, 400.0F, textPaint);
		pChar.printPenCharacterCandidates(canvas, 60.0F, 400.0F, textPaint);
		
		pChar.printCharacterSegmentsData();

		//		PenUtil.printString(Float.toString(pChar.penSegments.elementAt(0).mPointsY[0]), 50.0F, 410.F,
		//				pChar.penSegments.elementAt(0).mBoundingRectF, canvas, textPaint);

		// PenSegment pSegment = pChar.penSegments.elementAt(0);
		// pSegment.printSegmentStats(canvas, textPaint);
	}
	
	// Methods for printing PenCharacterEn
	private void printString(String str, Canvas canvas, float x, float y, Paint paint) {

		if (str != null) {

			Paint tempPaint = new Paint();
			tempPaint.set(paint);

			//tempPaint.setColor(Skiggle.sDefaultCanvasColor + 2);
			//			tempPaint.setStrokeWidth(Skiggle.sDefaultStrokeWidth);
			//			canvas.drawRect(boundingRectF, tempPaint);

			tempPaint.setColor(0xFFFFFFFF);
			canvas.drawRect(x, y-mFontSize-3, x+200, y+3, tempPaint);

			tempPaint.setTextSize(mFontSize);
			tempPaint.setColor(0xFFFF0000);
			canvas.drawText(str, x, y, tempPaint);
		}
	}

	public void printPenCharacter(Canvas canvas, float x, float y, Paint paint) {

		if (penCharacter != null) {
			printString(penCharacter.toString(), canvas, x, y, paint);
		}
	}

	public void printPenCharacterCandidates(Canvas canvas, float x, float y, Paint paint) {

		if (penCharacterCandidates != null) {
			printString(penCharacterCandidates, canvas, x, y, paint);
		}
	}
	
	
	public void printSegmentCharacters(RectF mBoundingRectF, Canvas canvas, Paint textPaint) {

		int numOfSegments = penSegments.size();
		PenSegment segment;
		String str = "Len:" + Integer.toString(numOfSegments);
		for (int i =0; i < numOfSegments; i++) {
			segment = penSegments.elementAt(i);
			str = str + ", " + segment.penSegmentCharacter;
			//Log.i(PenCharacterEn.TAG, "Segment " + i);
			//segment.printSegmentPointsData();
			
		}
		
		PenUtil.printString(str, 10, 15, mBoundingRectF, canvas, textPaint);
	/*	
		ConsoleHandler cons = new ConsoleHandler();
		cons.publish(new LogRecord(Level.SEVERE, "abcde"));
		cons.flush();
		System.out.print("ABCDEFG");
		Log.i(PenCharacterEn.TAG, "ABBBB");
		try {
		BufferedWriter f = new BufferedWriter(new FileWriter("c:\\Users\\Willie\\Programming\\android.log"));
		f.write("ABCDEFGH");
		f.close();
		} 
		catch (IOException e) {
			System.out.println("IO Error");

		}
	*/	
		

	}
	
	public void printCharacterSegmentsData() {

		int numOfSegments = penSegments.size();
		PenSegment segment;
		//String str = "Len:" + Integer.toString(numOfSegments);
		//Log.i(PenCharacterEn.TAG, "Character: " + penCharacter);
		for (int i =0; i < numOfSegments; i++) {
			segment = penSegments.elementAt(i);
			//str = str + ", " + segment.mPenSegmentCharacter;
			//Log.i(PenCharacterEn.TAG, "Segment " + i);
			//segment.printSegmentPointsData();
			
		}
	}
}
