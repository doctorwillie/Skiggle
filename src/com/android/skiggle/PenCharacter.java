/*
*   This file is part of Skiggle, an Android Input Method Editor (IME)
*   for handwritten input.
*
*   Copyright (C) 2009-2012 Willie Lim <drwillie650@gmail.com>
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

import com.android.skiggle.english.PenCharacterEn;
import com.android.skiggle.PenStroke;
import com.android.skiggle.SegmentBitSet;
import com.android.skiggle.english.SegmentBitSetEn;

import com.android.skiggle.chinese.PenCharacterCn;
import com.android.skiggle.chinese.SegmentBitSetCn;


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

	public Character matchedChar = null; // Character matched so far
	public String penCharacterCandidates = "";
	protected float mFontSize = Skiggle.sDefaultFontSize;

	public PenCharacter () {
		mPenStrokes = new Vector<PenStroke>();
		penSegments = new Vector<PenSegment>();
		//		mPenStrokes = new Vector<PenSegment>();
	} // End of PenCharacter() constructor

	public void addStroke (PenStroke penStroke) {

		// Update the x, y coordinates of the rectangle bounding all the strokes for the character
		penStrokesMinX = Math.min(penStrokesMinX, penStroke.boundingRectF.left);
		penStrokesMaxX = Math.max(penStrokesMaxX, penStroke.boundingRectF.right);
		penStrokesMinY = Math.min(penStrokesMinY, penStroke.boundingRectF.top);
		penStrokesMaxY = Math.max(penStrokesMaxY, penStroke.boundingRectF.bottom);

		mPenStrokes.add(penStroke);
	} // End of addStroke() method

	// Break up stroke into one or more segments
	public void addSegments (PenStroke penStroke, Canvas canvas, Paint textPaint) {

		penSegments.addAll(penStroke.segmentStroke(canvas, textPaint));
		
		printSegmentCharacters(penSegments.elementAt(0).boundingRectF, canvas, textPaint);
	} // End of addSegments() method

	// Reset mPenStrokes
	public void resetStrokes() {
		for (Iterator<PenStroke> i = mPenStrokes.iterator(); i.hasNext();) {
			i.next().reset();
		}
	} // End of resetStrokes() method

	// Reset penSegments
	public void resetSegments() {
		for (Iterator<PenSegment> i = penSegments.iterator(); i.hasNext();) {
			i.next().reset();
		}
	} // End of resetSegments() method
	
	// Method for getting candidate characters
	// Get candidates for 1-stroke character
	private String get1SegmentCharacterCandidates() {
		char strokeChar0 = penSegments.elementAt(0).penSegmentCharacter;
		SegmentBitSet sBitSet0 = SegmentBitSet.getSegmentBitSetForChar(strokeChar0);
		sBitSet0.mSegmentBitSet.and(SegmentBitSet.sOneSegmentBitset.mSegmentBitSet);
//		pChar.penCharacterCandidates = sBitSet0.getCharacters();
		return sBitSet0.getCharacters();
	} // End of get1SegmentCharacterCandidates() method

	// Get candidates for 2-stroke character
	private String get2SegmentCharacterCandidates() {
		char strokeChar0 = penSegments.elementAt(0).penSegmentCharacter;
		char strokeChar1 = penSegments.elementAt(1).penSegmentCharacter;
		SegmentBitSet sBitSet0 = SegmentBitSet.getSegmentBitSetForChar(strokeChar0);
		SegmentBitSet sBitSet1 = SegmentBitSet.getSegmentBitSetForChar(strokeChar1);
		SegmentBitSet s2SegmentsBitSet = new SegmentBitSet();
		s2SegmentsBitSet.copy(SegmentBitSet.sTwoSegmentsBitset);

		sBitSet0.mSegmentBitSet.and(sBitSet1.mSegmentBitSet);

		sBitSet0.mSegmentBitSet.and(s2SegmentsBitSet.mSegmentBitSet);

//		pChar.penCharacterCandidates = sBitSet0.getCharacters();
		return sBitSet0.getCharacters();

	} // End of get2SegmentCharacterCandidates() method

	// Get candidates for 3-stroke character
	private String get3SegmentCharacterCandidates() {
		char strokeChar0 = penSegments.elementAt(0).penSegmentCharacter;
		char strokeChar1 = penSegments.elementAt(1).penSegmentCharacter;
		char strokeChar2 = penSegments.elementAt(2).penSegmentCharacter;
		SegmentBitSet sBitSet0 = SegmentBitSet.getSegmentBitSetForChar(strokeChar0);
		SegmentBitSet sBitSet1 = SegmentBitSet.getSegmentBitSetForChar(strokeChar1);
		SegmentBitSet sBitSet2 = SegmentBitSet.getSegmentBitSetForChar(strokeChar2);
		SegmentBitSet s3SegmentsBitSet = new SegmentBitSet();
		s3SegmentsBitSet.copy(SegmentBitSet.sThreeSegmentsBitset);

		sBitSet0.mSegmentBitSet.and(sBitSet1.mSegmentBitSet);
		sBitSet0.mSegmentBitSet.and(sBitSet2.mSegmentBitSet);
		sBitSet0.mSegmentBitSet.and(s3SegmentsBitSet.mSegmentBitSet);

//		pChar.penCharacterCandidates = sBitSet0.getCharacters();
		return sBitSet0.getCharacters();
		
	} // End of get3SegmentCharacterCandidates() method

	// Get candidates for 4-stroke character
	private String get4SegmentCharacterCandidates() {
		char strokeChar0 = penSegments.elementAt(0).penSegmentCharacter;
		char strokeChar1 = penSegments.elementAt(1).penSegmentCharacter;
		char strokeChar2 = penSegments.elementAt(2).penSegmentCharacter;
		char strokeChar3 = penSegments.elementAt(3).penSegmentCharacter;

		SegmentBitSet sBitSet0 = SegmentBitSet.getSegmentBitSetForChar(strokeChar0);
		SegmentBitSet sBitSet1 = SegmentBitSet.getSegmentBitSetForChar(strokeChar1);
		SegmentBitSet sBitSet2 = SegmentBitSet.getSegmentBitSetForChar(strokeChar2);
		SegmentBitSet sBitSet3 = SegmentBitSet.getSegmentBitSetForChar(strokeChar3);
		SegmentBitSet s4SegmentsBitSet = new SegmentBitSet();
		s4SegmentsBitSet.copy(SegmentBitSet.sFourSegmentsBitset);

		sBitSet0.mSegmentBitSet.and(sBitSet1.mSegmentBitSet);
		sBitSet0.mSegmentBitSet.and(sBitSet2.mSegmentBitSet);
		sBitSet0.mSegmentBitSet.and(sBitSet3.mSegmentBitSet);
		sBitSet0.mSegmentBitSet.and(s4SegmentsBitSet.mSegmentBitSet);

//		pChar.penCharacterCandidates = sBitSet0.getCharacters();
		return sBitSet0.getCharacters();
	} // End of get4SegmentCharacterCandidates() method
	
	/**
	 * Gets the candidate characters
	 * @param pChar: PenCharacter object with the strokes (as the character is being built up)
	 */	
	public String getCharacterCandidates() {
		String str = "";
		switch (penSegments.size()) {
		case 1: 
			str = get1SegmentCharacterCandidates();
			break;
		case 2: 
			str = get2SegmentCharacterCandidates();
			break;
		case 3: 
			str = get3SegmentCharacterCandidates();
			break;
		case 4: 
			str = get4SegmentCharacterCandidates();
			break;
		default:
			str = "???";
		}
		return str;
	} // ENd of getCharacterCandidates() method
	
	public boolean matcher(char c) {
		boolean flag = false;
		if (Skiggle.sLanguage == Skiggle.ENGLISH_MODE) {
			flag = PenCharacterEn.matchCharacter(c, this);			
		}
		else if (Skiggle.sLanguage == Skiggle.CHINESE_MODE) {
			flag = PenCharacterCn.matchCharacter(c, this);
		}
		return flag;
	} // End of matchCharacter() method
	
	public void findMatchingCharacter (Canvas canvas, Paint textPaint, String lang) {

		penCharacterCandidates = getCharacterCandidates();

		int len = penCharacterCandidates.length();
		
		for (int i = 0; i < len; i++) {
			if (matcher(penCharacterCandidates.charAt(i))) {
				break;
			} // Found matching character so exit the for loop

		}

	} // End of findMatchingCharacter() method

/*	
	// Not used - 12/16/2011
	// Methods for printing PenCharacterEn
	private void printString(String str, Canvas canvas, float x, float y, Paint paint) {

		if (str != null) {

			Paint tempPaint = new Paint();
			tempPaint.set(paint);

			tempPaint.setColor(Skiggle.WHITE);
			canvas.drawRect(x, y-mFontSize-3, x+200, y+3, tempPaint);

			tempPaint.setTextSize(mFontSize);
			tempPaint.setColor(Skiggle.RED);
			canvas.drawText(str, x, y, tempPaint);
		}
	} // End of printString() method
*/	
	public void printPenCharacter(Canvas canvas, float x, float y, Paint paint) {

		if (matchedChar != null) {
//			printString(matchedChar.toString(), canvas, x, y, paint);
			PenUtil.printString(matchedChar.toString(), x, y, canvas, paint);
		}
	} // End of prinPenCharacter() method

	public void printPenCharacterCandidates(Canvas canvas, float x, float y, Paint paint) {

		if (penCharacterCandidates != null) {
//			printString(penCharacterCandidates, canvas, x, y, paint);
			PenUtil.printString(penCharacterCandidates, x, y, canvas, paint);
		}
	} // End of printPenCharacter() method
	
	
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
		
//		PenUtil.printString(str, 10, 420, mBoundingRectF, canvas, textPaint);
//		int y = canvas.getHeight() - 55;
		int y = (int) Math.floor(canvas.getHeight() * .875);
		
		PenUtil.printString(str, 10, y, canvas, textPaint);


	} // End of printSegmentCharacters() method
	
	public void printCharacterSegmentsData() {

		int numOfSegments = penSegments.size();
		PenSegment segment;

		for (int i =0; i < numOfSegments; i++) {
			segment = penSegments.elementAt(i);			
		}
	} // End of printCharacters() method
	
}
