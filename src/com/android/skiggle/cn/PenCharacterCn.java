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

package com.android.skiggle.cn;

import com.android.skiggle.PenCharacter;
import com.android.skiggle.PenSegment;
import com.android.skiggle.PenUtil;


/**
 * Represents the handwritten Chinese character.
 * @author Willie
 *
 * PenCharacterEn is a class representing the Chinese character written.  It has:
 *    - one or more PenSegment representing the strokes that make up the character
 *    - one or more PenSegment representing the basic segments (building blocks like '-', 'C', '|') that make up a character
 */
public class PenCharacterCn extends PenCharacter {
	
	// TODO: Make this a PenCharacter class method or a PenCharacterUtils class method since is used in PenCharacterCn and PenCharacterEn
	/**
	 * Checks for the zero numeral
	 * Copy of checkForOShape method in PenCharacterEn
	 * @see - PenCharacterEn.checkForOShape() method
	 * @param pChar - PenCharacter object to check
	 * @return - true if matched, false otherwise
	 */
	private boolean checkForOShape(PenCharacter pChar) {
		boolean matchedP = false;

		int numOfSegments = pChar.penSegments.size();

		// 'O'or 'o' has only one pen stroke character.
		if (numOfSegments == 1) {
			matchedP = (pChar.penSegments.elementAt(0).penSegmentCharacter == PenSegment.CIRCLE_CHAR);
		}
		return matchedP;
	}  // End of checkForOShape() method


	private boolean checkForHorizontalStrokes(PenCharacter pChar, int numSegs) {
		//boolean matchedP = false;
		boolean matchedP = true;

		int numOfSegments = pChar.penSegments.size();

		if (numOfSegments == numSegs) {
			for (int i = 0; i < numOfSegments; i++ ) {
				matchedP = matchedP & (pChar.penSegments.elementAt(i).penSegmentCharacter == PenSegment.HLINE_CHAR);
			}
		}
		return matchedP;
	} // End of checkForHorizontalStroke() method
	
	// TODO: Exact copy of the same method from PenCharacterEn.   Need to replace with a single common method.
	// Get the x,y coordinates of the top and bottom of a stroke (like a '/', '\', or '|')
	// and return a 4-element array containing the topX, topY, bottomX and bottomY respectively
	private float[] getTopBottomCoordsOfSegment(PenSegment pSegment) {
		// Initially assume the start of the stroke is the top
		float topX = pSegment.posStart[0]; // x-coord of top end of the stroke
		float topY = pSegment.posStart[1]; // y-coord of top end of the stroke
		float bottomX = pSegment.posEnd[0]; // x-coord of bottom end of the stroke
		float bottomY = pSegment.posEnd[1]; // y-coord of bottom end of the stroke
		// Swap the top and bottom ends of the stroke if necessary
		if (bottomY < topY) {
			topX = bottomX;
			topY = bottomY;
			bottomX = pSegment.posStart[0];
			bottomY = pSegment.posStart[1];
		}
		float coords[] = {topX, topY, bottomX, bottomY};
		return coords;
	}
	
	// TODO: Exact copy of the same method from PenCharacterEn.   Need to replace with a single common method.
	// Check for  '+'
	private boolean checkForPlusSign(PenCharacter pChar) {
		boolean matchedP = false;
		int numOfSegments = pChar.penSegments.size();


		if (numOfSegments == 2) {
			int hLineIndex = -1; // first stroke E to W
			int vLineIndex = -1; // second stroke N to S

			for (int i = 0; i < numOfSegments; i++) {
				// '+' has only two pen stroke characters
				switch (pChar.penSegments.elementAt(i).penSegmentCharacter) {
				case PenSegment.HLINE_CHAR:
					hLineIndex = i;
					break;
				case PenSegment.VLINE_CHAR:
					vLineIndex = i;
					break;
				default:
					break;
				}
			}
			// Check to make sure that the two component strokes for '+' are there, i.e.,
			// vLineIndex and hLineIndex are both not negative
			if ((hLineIndex >= 0) & (vLineIndex >= 0)) {
				float coords[] = getTopBottomCoordsOfSegment(pChar.penSegments.elementAt(hLineIndex));		
				float hLineTopX = coords[0]; // x-coord of top end of the HLINE stroke
				float hLineTopY = coords[1]; // y-coord of top end of the HLINE stroke
				float hLineBottomX = coords[2]; // x-coord of bottom end of the HLINE stroke
				float hLineBottomY = coords[3]; // y-coord of bottom end of the HLINE stroke
				float hLineAvgX = (hLineTopX + hLineBottomX)/2; // average of x-coord (mid-point) of HLINE stroke
				float hLineAvgY = (hLineTopY + hLineBottomY)/2; // average of y-coord (mid-point) of HLINE stroke

				coords = getTopBottomCoordsOfSegment(pChar.penSegments.elementAt(vLineIndex));		
				float vLineTopX = coords[0]; // x-coord of top end of the VLINE stroke
				float vLineTopY = coords[1]; // y-coord of top end of the VLINE stroke
				float vLineBottomX = coords[2]; // x-coord of bottom end of the VLINE stroke
				float vLineBottomY = coords[3]; // y-coord of bottom end of the VLLINE stroke
				float vLineAvgX = (vLineTopX + vLineBottomX)/2; // average of x-coord (mid-point) of VLINE stroke
				float vLineAvgY = (vLineTopY + vLineBottomY)/2; // average of y-coord (mid-point) of VLINE stroke

				float topGap = PenUtil.distanceBetween2Points(vLineTopX, vLineTopY, hLineTopX, hLineTopY);
				float bottomGap = PenUtil.distanceBetween2Points(vLineBottomX, vLineBottomY, hLineBottomX, hLineBottomY);
				float midGap = PenUtil.distanceBetween2Points(vLineAvgX, vLineAvgY, hLineAvgX, hLineAvgY);
				float maxGap = Math.max(topGap, bottomGap);

				// Check to make sure HLINE and VLINE cross sufficiently to form the '+'
				matchedP = (midGap < (.25 * maxGap)); // gap between the mid points of HLINE and VLINE must be small enough
			}
		}
		return matchedP;
	} // End of checkForPlusSign() method

	
	/**
	 * Matches the pen strokes to the given Chinese character c
	 * @param c: character to match
	 * @param pChar: PenCharacter object containing the pen strokes
	 * @return
	 */
	public boolean matchCharacter(char c, PenCharacter pChar) {
		boolean foundP = false;
		char penChar = '?';

		switch (c) {
		/****************************/
		/* Chinese numerals 0 to 10 */
		/****************************/
		case '〇':
			foundP = checkForOShape(pChar);
			if (foundP) penChar = '〇';
			break;
		case '一':
			foundP = checkForHorizontalStrokes(pChar, 1);
			if (foundP) penChar = '一';
			break;
		case '二':
			foundP = checkForHorizontalStrokes(pChar, 2);
			if (foundP) penChar = '二';
			break;
		case '三':
			foundP = checkForHorizontalStrokes(pChar, 3);
			if (foundP) penChar = '三';
			break;
		case '四':
//			foundP = checkFor4(pChar);
//			if (foundP) penChar = '4';
			break;
		case '五':
//			foundP = checkFor5(pChar);
//			if (foundP) penChar = '5';
			break;
		case '六':
			break;
		case '七':
//			foundP = checkFor7(pChar);
//			if (foundP) penChar = '7';
			break;
		case '八':
			break;
		case '九':
//			foundP = checkFor9(pChar);
//			if (foundP) penChar = '9';
			break;
		case '十':
			foundP = checkForPlusSign(pChar);
			if (foundP) penChar = '十';
			break;
		default:
			break;

		}
		pChar.penCharacter = penChar;
		return foundP;
	}

}
