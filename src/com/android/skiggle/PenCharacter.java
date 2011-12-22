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
import com.android.skiggle.SegmentBitSet;

import com.android.skiggle.chinese.PenCharacterCn;
import com.android.skiggle.chinese.SegmentBitSetCn;

import com.android.skiggle.english.PenCharacterEn;
import com.android.skiggle.english.SegmentBitSetEn;

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
	private void get1SegmentCharacterCandidates(PenCharacter pChar) {
		char strokeChar0 = pChar.penSegments.elementAt(0).penSegmentCharacter;
		SegmentBitSet sBitSet0 = SegmentBitSet.getSegmentBitSetForChar(strokeChar0);
		sBitSet0.mSegmentBitSet.and(SegmentBitSet.sOneSegmentBitset.mSegmentBitSet);
		pChar.penCharacterCandidates = sBitSet0.getCharacters();
		// penCharacter = pChar.penSegments.elementAt(0).mPenSegmentCharacter;
	} // End of get1SegmentCharacterCandidates() method

	// Get candidates for 2-stroke character
	private void get2SegmentCharacterCandidates(PenCharacter pChar) {
		char strokeChar0 = pChar.penSegments.elementAt(0).penSegmentCharacter;
		char strokeChar1 = pChar.penSegments.elementAt(1).penSegmentCharacter;
		SegmentBitSet sBitSet0 = SegmentBitSet.getSegmentBitSetForChar(strokeChar0);
		SegmentBitSet sBitSet1 = SegmentBitSet.getSegmentBitSetForChar(strokeChar1);
		SegmentBitSet s2SegmentsBitSet = new SegmentBitSet();
		s2SegmentsBitSet.copy(SegmentBitSet.sTwoSegmentsBitset);

		sBitSet0.mSegmentBitSet.and(sBitSet1.mSegmentBitSet);

		sBitSet0.mSegmentBitSet.and(s2SegmentsBitSet.mSegmentBitSet);

		pChar.penCharacterCandidates = sBitSet0.getCharacters();

	} // End of get2SegmentCharacterCandidates() method

	// Get candidates for 3-stroke character
	private void get3SegmentCharacterCandidates(PenCharacter pChar) {
		char strokeChar0 = pChar.penSegments.elementAt(0).penSegmentCharacter;
		char strokeChar1 = pChar.penSegments.elementAt(1).penSegmentCharacter;
		char strokeChar2 = pChar.penSegments.elementAt(2).penSegmentCharacter;
		SegmentBitSet sBitSet0 = SegmentBitSet.getSegmentBitSetForChar(strokeChar0);
		SegmentBitSet sBitSet1 = SegmentBitSet.getSegmentBitSetForChar(strokeChar1);
		SegmentBitSet sBitSet2 = SegmentBitSet.getSegmentBitSetForChar(strokeChar2);
		SegmentBitSet s3SegmentsBitSet = new SegmentBitSet();
		s3SegmentsBitSet.copy(SegmentBitSet.sThreeSegmentsBitset);

		sBitSet0.mSegmentBitSet.and(sBitSet1.mSegmentBitSet);
		sBitSet0.mSegmentBitSet.and(sBitSet2.mSegmentBitSet);
		sBitSet0.mSegmentBitSet.and(s3SegmentsBitSet.mSegmentBitSet);

		pChar.penCharacterCandidates = sBitSet0.getCharacters();
		
	} // End of get3SegmentCharacterCandidates() method

	// Get candidates for 4-stroke character
	private void get4SegmentCharacterCandidates(PenCharacter pChar) {
		char strokeChar0 = pChar.penSegments.elementAt(0).penSegmentCharacter;
		char strokeChar1 = pChar.penSegments.elementAt(1).penSegmentCharacter;
		char strokeChar2 = pChar.penSegments.elementAt(2).penSegmentCharacter;
		char strokeChar3 = pChar.penSegments.elementAt(3).penSegmentCharacter;

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

		pChar.penCharacterCandidates = sBitSet0.getCharacters();
		//}
	} // End of get4SegmentCharacterCandidates() method
	
	/**
	 * Gets the candidate characters
	 * @param pChar: PenCharacter object with the strokes (as the character is being built up)
	 */	
	public void getCharacterCandidates(PenCharacter pChar) {

		switch (pChar.penSegments.size()) {
		case 1: 
			get1SegmentCharacterCandidates(pChar);
			break;
		case 2: 
			get2SegmentCharacterCandidates(pChar);
			break;
		case 3: 
			get3SegmentCharacterCandidates(pChar);
			break;
		case 4: 
			get4SegmentCharacterCandidates(pChar);
			break;
		default:
			pChar.penCharacterCandidates = "???";
		}
	} // ENd of getCharacterCandidates() method
	
	public boolean matcher(char c, PenCharacter pChar) {
		boolean flag = false;
		if (Skiggle.sLanguage == Skiggle.ENGLISH_MODE) {
			flag = PenCharacterEn.matchCharacter(c, pChar);			
		}
		else if (Skiggle.sLanguage == Skiggle.CHINESE_MODE) {
			flag = PenCharacterCn.matchCharacter(c, pChar);
		}
		return flag;
	} // End of matchCharacter() method
	
	public void findMatchingCharacter (Canvas canvas, Paint textPaint, PenCharacter pChar, String lang) {

		getCharacterCandidates(pChar);

		int len = pChar.penCharacterCandidates.length();
		for (int i = 0; i < len; i++) {
			if (matcher(pChar.penCharacterCandidates.charAt(i), pChar)) {
				break;
			} // Found matching character so exit the for loop

//		pChar.printPenCharacter(canvas, 50.0F, 400.0F, textPaint);
//		pChar.printPenCharacterCandidates(canvas, 60.0F, 400.0F, textPaint);
//		String str = pChar.penCharacter + penCharacterCandidates.replace(pChar.penCharacter.toString(), "");  // Doesn't work with some strings
//		String str = pChar.penCharacter + penCharacterCandidates;
		}
		if (pChar.penCharacter != null) {
			String str = pChar.penCharacter + PenUtil.removeCharFromString(pChar.penCharacter, penCharacterCandidates);
			drawPenCharactersInStringKeys(pChar.penCharacter, str, canvas);
		}
		else {
			drawPenCharactersInStringKeys(pChar.penCharacter, penCharacterCandidates, canvas);
		}
			
//			pChar.printCharacterSegmentsData();


	} // End of findMatchingCharacter() method
	
	// Not used - 12/16/2011
	// Methods for printing PenCharacterEn
	private void printString(String str, Canvas canvas, float x, float y, Paint paint) {

		if (str != null) {

			Paint tempPaint = new Paint();
			tempPaint.set(paint);

			//tempPaint.setColor(Skiggle.sDefaultCanvasColor + 2);
			//			tempPaint.setStrokeWidth(Skiggle.sDefaultStrokeWidth);
			//			canvas.drawRect(boundingRectF, tempPaint);

			tempPaint.setColor(Skiggle.WHITE);
			canvas.drawRect(x, y-mFontSize-3, x+200, y+3, tempPaint);

			tempPaint.setTextSize(mFontSize);
			tempPaint.setColor(Skiggle.RED);
			canvas.drawText(str, x, y, tempPaint);
		}
	} // End of printString() method

	public void drawPenCharactersInStringKeys(Character c, String str, Canvas canvas) {

		PenUtil.displayCandidateCharacterKeys(c, str, canvas);
		
	} // End of drawPenCharacterCandidatesKeys() method
	
	public void printPenCharacter(Canvas canvas, float x, float y, Paint paint) {

		if (penCharacter != null) {
			printString(penCharacter.toString(), canvas, x, y, paint);
		}
	} // End of prinPenCharacter() method

	public void printPenCharacterCandidates(Canvas canvas, float x, float y, Paint paint) {

		if (penCharacterCandidates != null) {
			printString(penCharacterCandidates, canvas, x, y, paint);
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
		
		PenUtil.printString(str, 10, 420, mBoundingRectF, canvas, textPaint);

	} // End of printSegmentCharacters() method
	
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
	} // End of printCharacters() method
	
}
