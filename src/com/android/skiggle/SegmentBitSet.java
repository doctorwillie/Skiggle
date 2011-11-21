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

	// Class constants set to English by default.  Use set methods to change them specific to the language (other than English).
	// Note: " and \ are escaped with backslash - "\"" and "\\"

	protected static String sAllCharactersString = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz !\"#$%&'()*+,-./:;<=>?@[\\]^`{|}~");
//	protected static SegmentBitSet sAllCharactersStringBitset = new SegmentBitSet(sAllCharactersString);

	// 42 characters with VLINE - 1, 4, 5, 9, B, D, E, F, G, H, I, J, K, L, M, N, P, R, T, Y, a, b, d, h, i, k, l, m, n, p, q, r, t, <space>, !, ", $, ', +, [, ], | 
	protected static SegmentBitSet sVLineBitset           = new SegmentBitSet("01001100010101111111111101010100001011010001101111011101000000111010010001000000000001010000100");

	// 28 characters with HLINE - 1, 2, 4, 5, 7, A, E, F, G, H, I, J, L, T, Z, e, f, t, z, <space>, #, *, +, -, =, [, ], _ 
	protected static SegmentBitSet sHLineBitset           = new SegmentBitSet("01101101001000111111010000000100000100001100000000000001000001100100000011010000010001010100000");

	// 24 characters with FSLASH - 1, 4, 7, A, K, M, V, W, X, Y, Z, k, v, w, x, y, z, #, %, *, /, <, >, ^ 
	protected static SegmentBitSet sFSlashBitset          = new SegmentBitSet("01001001001000000000101000000001111100000000001000000000011111000101000010000100101000001000000");

	// 21 characters with BSLASH - A, K, M, N, Q, R, V, W, X, Y, k, v, w, x, y, *, <, >, \, ^, ` 
	protected static SegmentBitSet sBSlashBitset          = new SegmentBitSet("00000000001000000000101100110001111000000000001000000000011110000000000010000000101000101010000");

	// 16 characters with BC (back C) - 2, 3, 5, B, D, P, R, S, b, p, s, $, ), ,, ;, ? 
	protected static SegmentBitSet sBcBitset              = new SegmentBitSet("00110100000101000000000001011000000001000000000000010010000000000010000100100001000100000000000");

	// 7 characters with CIRCLE - 0, 6, 8, O, Q, o, % 
	protected static SegmentBitSet sCircleBitset          = new SegmentBitSet("10000010100000000000000010100000000000000000000000100000000000000001000000000000000000000000000");

	// 13 characters with FC (regular C) - 6, 9, C, G, S, a, c, d, e, q, s, $, ( 
	protected static SegmentBitSet sFcBitset              = new SegmentBitSet("00000010010010001000000000001000000010111000000000001010000000000010001000000000000000000000000");

	// 7 (capital letters) characters with DOT - i, j, !, ., :, ;, ? 
	protected static SegmentBitSet sDotBitset             = new SegmentBitSet("00000000000000000000000000000000000000000000110000000000000000010000000000001011000100000000000");

	// 4 characters with U - J, U, j, u
	protected static SegmentBitSet sUBitset	            = new SegmentBitSet("00000000000000000001000000000010000000000000010000000000100000000000000000000000000000000000000");

	// 23 one-segment characters - 0, C, O, U, c, f, h, l, n, o, r, u, ', (, ), ,, -, ., /, \, _, `, | 
	protected static SegmentBitSet sOneSegmentBitset      = new SegmentBitSet("10000000000010000000000010000010000000100101000101100100100000000000011100111100000000100110100");

	// 39 two-segment characters - 2, 3, 6, 7, 8, 9, D, G, L, P, Q, S, T, V, X, a, b, d, e, i, j, m, p, q, s, t, v, x, y, !, ", +, :, ;, <, =, >, ?, ^ 
	protected static SegmentBitSet sTwoSegmentsBitset     = new SegmentBitSet("00110011110001000000010001101101010011011000110010011011010110011000000001000011111100001000000");

	// 22 three-segment characters - 1, 4, 5, A, B, F, H, I, J, K, N, R, Y, Z, k, z, <space>, $, %, *, [, ] 
	protected static SegmentBitSet sThreeSegmentsBitset   = new SegmentBitSet("01001100001100011111100100010000001100000000001000000000000001100011000010000000000001010000000");

	// 5 four-segment characters - E, M, W, w, # 
	protected static SegmentBitSet sFourSegmentsBitset    = new SegmentBitSet("00000000000000100000001000000000100000000000000000000000001000000100000000000000000000000000000");

	/*
	private static String sAllCharactersString;
	private static SegmentBitSet sAllCharactersStringBitset;

	// 42 characters with VLINE - 1, 4, 5, 9, B, D, E, F, G, H, I, J, K, L, M, N, P, R, T, Y, a, b, d, h, i, k, l, m, n, p, q, r, t, <space>, !, ", $, ', +, [, ], | 
	private static SegmentBitSet sVLineBitset;

	// 28 characters with HLINE - 1, 2, 4, 5, 7, A, E, F, G, H, I, J, L, T, Z, e, f, t, z, <space>, #, *, +, -, =, [, ], _ 
	private static SegmentBitSet sHLineBitset;

	// 24 characters with FSLASH - 1, 4, 7, A, K, M, V, W, X, Y, Z, k, v, w, x, y, z, #, %, *, /, <, >, ^ 
	private static SegmentBitSet sFSlashBitset;

	// 21 characters with BSLASH - A, K, M, N, Q, R, V, W, X, Y, k, v, w, x, y, *, <, >, \, ^, ` 
	private static SegmentBitSet sBSlashBitset;

	// 16 characters with BC (back C) - 2, 3, 5, B, D, P, R, S, b, p, s, $, ), ,, ;, ? 
	private static SegmentBitSet sBcBitset;

	// 7 characters with CIRCLE - 0, 6, 8, O, Q, o, % 
	private static SegmentBitSet sCircleBitset;

	// 13 characters with FC (regular C) - 6, 9, C, G, S, a, c, d, e, q, s, $, ( 
	private static SegmentBitSet sFcBitset;

	// 7 (capital letters) characters with DOT - i, j, !, ., :, ;, ? 
	private static SegmentBitSet sDotBitset;

	// 4 characters with U - J, U, j, u
	private static SegmentBitSet sUBitset;

	// 23 one-segment characters - 0, C, O, U, c, f, h, l, n, o, r, u, ', (, ), ,, -, ., /, \, _, `, | 
	public static SegmentBitSet sOneSegmentBitset;

	// 39 two-segment characters - 2, 3, 6, 7, 8, 9, D, G, L, P, Q, S, T, V, X, a, b, d, e, i, j, m, p, q, s, t, v, x, y, !, ", +, :, ;, <, =, >, ?, ^ 
	public static SegmentBitSet sTwoSegmentsBitset;

	// 22 three-segment characters - 1, 4, 5, A, B, F, H, I, J, K, N, R, Y, Z, k, z, <space>, $, %, *, [, ] 
	public static SegmentBitSet sThreeSegmentsBitset;

	// 5 four-segment characters - E, M, W, w, # 
	public static SegmentBitSet sFourSegmentsBitset;
	*/

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

//	public static void setSAllCharactersStringBitset() {
//	    sAllCharactersStringBitset = new SegmentBitSet(sAllCharactersString);
//	}
	
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

	public static void testPrintSegmentBitSet() {
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
