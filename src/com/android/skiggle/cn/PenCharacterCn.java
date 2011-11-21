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


	private boolean checkForHorizontalStroke(PenCharacter pChar) {
		//boolean matchedP = false;
		boolean matchedP = true;

		int numOfSegments = pChar.penSegments.size();

		/*
		if (numOfSegments == 1) {
			char c = pChar.penSegments.elementAt(0).penSegmentCharacter;
			matchedP = (pChar.penSegments.elementAt(0).penSegmentCharacter == PenSegment.HLINE_CHAR);
		}
		*/
		for (int i = 0; i < numOfSegments; i++ ) {
			matchedP = matchedP & (pChar.penSegments.elementAt(i).penSegmentCharacter == PenSegment.HLINE_CHAR);
		}
		return matchedP;
	} // End of checkForHorizontalStroke() method
	
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
			foundP = checkForHorizontalStroke(pChar);
			if (foundP) penChar = '一';			
		case '二':
			foundP = checkForHorizontalStroke(pChar);
			if (foundP) penChar = '二';
			break;
		case '三':
			foundP = checkForHorizontalStroke(pChar);
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
//			foundP = checkFor9(pChar);
//			if (foundP) penChar = '9';
			break;

			/**********************************************/
			/* Letters A-Z, c, k, o, p, u, v, w, x, and z */
			/**********************************************/

		default:

		}
		pChar.penCharacter = penChar;
		return foundP;
	}

}
