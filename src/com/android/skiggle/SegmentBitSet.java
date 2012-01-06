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

import java.util.BitSet;

import com.android.skiggle.PenSegment;

/**
 * SegmentBitSetEN contains the character segment bit sets and methods for characters of the specific language (English is the default)
 * @author Willie Lim
 *
 */
public class SegmentBitSet {

	public BitSet mSegmentBitSet = new BitSet();

	/*****************************************************************************************/
	/* BitSet for digits, letters, and special characters for English (the default language) */
	/*****************************************************************************************/

	// Class constants.  Use set methods to change them specific to the language (default is English).

	// Set of all character strings
	protected static String sAllCharactersString;

	// Bit set for characters with VLINE (vertical line or "-")
	protected static SegmentBitSet sVLineBitset;

	// Bit set for characters with HLINE (horizontal line or "|")
	protected static SegmentBitSet sHLineBitset;

	// Bit set for characters with FSLASH (forward slash or "/")
	protected static SegmentBitSet sFSlashBitset;

	// Bit set for characters with BSLASH (backward slash or "\")
	protected static SegmentBitSet sBSlashBitset;

	// Bit set for characters with BC (backward C or like a right parenthesis ")")
	protected static SegmentBitSet sBcBitset;

	// Bit set for characters with CIRCLE ("o", "O", or "0")
	protected static SegmentBitSet sCircleBitset;

	// Bit set for characters with FC (regular C or left parenthesis "(")
	protected static SegmentBitSet sFcBitset;

	// Bit set for characters with DOT (period or ".") 
	protected static SegmentBitSet sDotBitset;

	// Bit set for characters with U (or "U")
	protected static SegmentBitSet sUBitset;

	// Bit set for characters with one segment
	protected static SegmentBitSet sOneSegmentBitset;

	// Bit set for characters with two segments
	protected static SegmentBitSet sTwoSegmentsBitset;

	// Bit set for characters with three segments
	protected static SegmentBitSet sThreeSegmentsBitset;

	// Bit set for characters with four segments
	protected static SegmentBitSet sFourSegmentsBitset;

	public SegmentBitSet(){};

	public SegmentBitSet(String bitString) {
		
		int bitStringLength = bitString.length();

		for (int i = 0; i < bitStringLength; i++) {

			if (bitString.charAt(i) == '1') {
				mSegmentBitSet.set(i);
			}
		}
		
	}
	
	// Setter methods for SegmentBitSet class globals
	
	public static void setSAllCharactersString(String str) {
	    sAllCharactersString = str;
	}

	public static void setSVLineBitset(SegmentBitSet s) {
	    sVLineBitset = s;
	}

	public static void setSHLineBitset(SegmentBitSet s) {
	    sHLineBitset = s;
	}

	public static void setSFSlashBitset(SegmentBitSet s) {
	    sFSlashBitset = s;
	}

	public static void setSBSlashBitset(SegmentBitSet s) {
	    sBSlashBitset = s;
	}

	public static void setSBcBitset(SegmentBitSet s) {
	    sBcBitset = s;
	}

	public static void setSCircleBitset(SegmentBitSet s) {
	    sCircleBitset = s;
	}

	public static void setSFcBitset(SegmentBitSet s) {
	    sFcBitset = s;
	}

	public static void setSDotBitset(SegmentBitSet s) {
	    sDotBitset = s;
	}

	public static void setSUBitset(SegmentBitSet s) {
	    sUBitset = s;
	}

	public static void setSOneSegmentBitset(SegmentBitSet s) {
	    sOneSegmentBitset = s;
	}

	public static void setSTwoSegmentsBitset(SegmentBitSet s) {
	    sTwoSegmentsBitset = s;
	}

	public static void setSThreeSegmentsBitset(SegmentBitSet s) {
	    sThreeSegmentsBitset = s;
	}

	public static void setSFourSegmentsBitset(SegmentBitSet s) {
	    sFourSegmentsBitset = s;
	}
	

	public void copy(SegmentBitSet sBitSet) {
		this.mSegmentBitSet = (BitSet) sBitSet.mSegmentBitSet.clone();
	}

	protected SegmentBitSet clone(SegmentBitSet sBitSet) {
		SegmentBitSet nBitSet = new SegmentBitSet();
		nBitSet.copy(sBitSet);
		return nBitSet;
	}

	/**
	 * Gets all the characters from the segment bit sets.
	 * @return All the characters of the sement bit sets.
	 */
	public String getCharacters() {
		String characters = "";
		int bitStringLength = mSegmentBitSet.length();
		for (int i = 0; i < bitStringLength; i++) {

			if (mSegmentBitSet.get(i) == true) {
				characters = characters + sAllCharactersString.charAt(i);
			}
		}
		return characters;
	}

	protected void printCharSet(SegmentBitSet bitSet) {
		System.out.println(bitSet.getCharacters());
		System.out.println();
	}

	protected String getCharSetForSegment(char segmentChar) {
		String chars = "";
		switch (segmentChar) {
		case PenSegment.HLINE_CHAR:
			chars = sHLineBitset.getCharacters();
			break;
		case PenSegment.FSLASH_CHAR:
			chars = sFSlashBitset.getCharacters();
			break;
		case PenSegment.VLINE_CHAR:
			chars = sVLineBitset.getCharacters();
			break;
		case PenSegment.BSLASH_CHAR:
			chars = sBSlashBitset.getCharacters();
			break;
		case PenSegment.BC_CHAR:
			chars = sBcBitset.getCharacters();
			break;
		case PenSegment.FC_CHAR:
			chars = sFcBitset.getCharacters();
			break;
		case PenSegment.CIRCLE_CHAR:
			chars = sCircleBitset.getCharacters();
			break;
		case PenSegment.U_CHAR:
			chars = sUBitset.getCharacters();
			break;
		case PenSegment.DOT_CHAR:
			chars = sDotBitset.getCharacters();
			break;
		default:
			break;
		}
		return chars;
	}

	protected void printCharSetForSegment(char segmentChar) {
		switch (segmentChar) {
		case PenSegment.HLINE_CHAR:
			printCharSet(sHLineBitset);
			break;
		case PenSegment.FSLASH_CHAR:
			printCharSet(sFSlashBitset);
			break;
		case PenSegment.VLINE_CHAR:
			printCharSet(sVLineBitset);
			break;
		case PenSegment.BSLASH_CHAR:
			printCharSet(sBSlashBitset);
			break;
		case PenSegment.BC_CHAR:
			printCharSet(sBcBitset);
			break;
		case PenSegment.FC_CHAR:
			printCharSet(sFcBitset);
			break;
		case PenSegment.CIRCLE_CHAR:
			printCharSet(sCircleBitset);
			break;
		case PenSegment.U_CHAR:
			printCharSet(sUBitset);
			break;
		case PenSegment.DOT_CHAR:
			printCharSet(sDotBitset);
			break;
		default:
			break;

		}
	}

	public static SegmentBitSet getSegmentBitSetForChar(char segmentChar) {
		SegmentBitSet sBitSet = new SegmentBitSet();

		switch (segmentChar) {
		case PenSegment.HLINE_CHAR:
			sBitSet.copy(sHLineBitset);
			break;
		case PenSegment.FSLASH_CHAR:
			sBitSet.copy(sFSlashBitset);
			break;
		case PenSegment.VLINE_CHAR:
			sBitSet.copy(sVLineBitset);
			break;
		case PenSegment.BSLASH_CHAR:
			sBitSet.copy(sBSlashBitset);
			break;
		case PenSegment.BC_CHAR:
			sBitSet.copy(sBcBitset);
			break;		
		case PenSegment.FC_CHAR:
			sBitSet.copy(sFcBitset);
			break;
		case PenSegment.CIRCLE_CHAR:
			sBitSet.copy(sCircleBitset);
			break;
		case PenSegment.U_CHAR:
			sBitSet.copy(sUBitset);
			break;
		case PenSegment.DOT_CHAR:
			sBitSet.copy(sDotBitset);
			break;
		default:
			break;

		}

		return sBitSet;
	}

	// Not used - 12/15/2011
	public static void unused_testPrintSegmentBitSet() {
		SegmentBitSet s = SegmentBitSet.sVLineBitset;
		System.out.println("VLINE" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(sVLineBitset);

		s = SegmentBitSet.sHLineBitset;
		System.out.println("HLINE" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(sHLineBitset);

		s = SegmentBitSet.sFSlashBitset;
		System.out.println("FSLASH" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(sFSlashBitset);

		s = SegmentBitSet.sBSlashBitset;
		System.out.println("BSLASH_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(sBSlashBitset);

		s = SegmentBitSet.sBcBitset;
		System.out.println("BC_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(sBcBitset);

		s = SegmentBitSet.sCircleBitset;
		System.out.println("OH_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(sCircleBitset);

		s = SegmentBitSet.sFcBitset;
		System.out.println("FC_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(sFcBitset);

		s = SegmentBitSet.sDotBitset;
		System.out.println("DOT_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(sDotBitset);

		s = SegmentBitSet.sUBitset;
		System.out.println("U_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(sUBitset);

	}

}
