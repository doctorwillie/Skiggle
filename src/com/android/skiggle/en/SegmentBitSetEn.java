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

package com.android.skiggle.en;

import java.util.BitSet;

import com.android.skiggle.PenSegment;

/**
 * Character segment bit sets for English characters
 * @author Willie Lim
 *
 */
public class SegmentBitSetEn {

	public BitSet mSegmentBitSet = new BitSet();

	/******************************************************/
	/* BitSet for digits, letters, and special characters */
	/******************************************************/

	//	public static final String DIGITS_LETTERS_SPECIALS_STRING = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
	// Note: " and \ are escaped with backslash - "\"" and "\\"
	public static final String DIGITS_LETTERS_SPECIALS_STRING = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz !\"#$%&'()*+,-./:;<=>?@[\\]^`{|}~");
	public static final SegmentBitSetEn DIGITS_LETTERS_SPECIALS_STRING_BITSET = new SegmentBitSetEn(DIGITS_LETTERS_SPECIALS_STRING);

	// 42 characters with VLINE - 1, 4, 5, 9, B, D, E, F, G, H, I, J, K, L, M, N, P, R, T, Y, a, b, d, h, i, k, l, m, n, p, q, r, t, <space>, !, ", $, ', +, [, ], | 
	public static final SegmentBitSetEn VLINE_BITSET           = new SegmentBitSetEn("01001100010101111111111101010100001011010001101111011101000000111010010001000000000001010000100");

	// 28 characters with HLINE - 1, 2, 4, 5, 7, A, E, F, G, H, I, J, L, T, Z, e, f, t, z, <space>, #, *, +, -, =, [, ], _ 
	public static final SegmentBitSetEn HLINE_BITSET           = new SegmentBitSetEn("01101101001000111111010000000100000100001100000000000001000001100100000011010000010001010100000");

	// 24 characters with FSLASH - 1, 4, 7, A, K, M, V, W, X, Y, Z, k, v, w, x, y, z, #, %, *, /, <, >, ^ 
	public static final SegmentBitSetEn FSLASH_BITSET          = new SegmentBitSetEn("01001001001000000000101000000001111100000000001000000000011111000101000010000100101000001000000");

	// 21 characters with BSLASH - A, K, M, N, Q, R, V, W, X, Y, k, v, w, x, y, *, <, >, \, ^, ` 
	public static final SegmentBitSetEn BSLASH_BITSET          = new SegmentBitSetEn("00000000001000000000101100110001111000000000001000000000011110000000000010000000101000101010000");

	// 16 characters with BC (back C) - 2, 3, 5, B, D, P, R, S, b, p, s, $, ), ,, ;, ? 
	public static final SegmentBitSetEn BC_BITSET              = new SegmentBitSetEn("00110100000101000000000001011000000001000000000000010010000000000010000100100001000100000000000");

	// 7 characters with CIRCLE - 0, 6, 8, O, Q, o, % 
	public static final SegmentBitSetEn CIRCLE_BITSET          = new SegmentBitSetEn("10000010100000000000000010100000000000000000000000100000000000000001000000000000000000000000000");

	// 13 characters with FC (regular C) - 6, 9, C, G, S, a, c, d, e, q, s, $, ( 
	public static final SegmentBitSetEn FC_BITSET              = new SegmentBitSetEn("00000010010010001000000000001000000010111000000000001010000000000010001000000000000000000000000");

	// 7 (capital letters) characters with DOT - i, j, !, ., :, ;, ? 
	public static final SegmentBitSetEn DOT_BITSET             = new SegmentBitSetEn("00000000000000000000000000000000000000000000110000000000000000010000000000001011000100000000000");

	// 4 characters with U - J, U, j, u
	public static final SegmentBitSetEn U_BITSET               = new SegmentBitSetEn("00000000000000000001000000000010000000000000010000000000100000000000000000000000000000000000000");

	// 23 one-segment characters - 0, C, O, U, c, f, h, l, n, o, r, u, ', (, ), ,, -, ., /, \, _, `, | 
	public static final SegmentBitSetEn ONE_SEGMENT_BITSET      = new SegmentBitSetEn("10000000000010000000000010000010000000100101000101100100100000000000011100111100000000100110100");

	// 39 two-segment characters - 2, 3, 6, 7, 8, 9, D, G, L, P, Q, S, T, V, X, a, b, d, e, i, j, m, p, q, s, t, v, x, y, !, ", +, :, ;, <, =, >, ?, ^ 
	public static final SegmentBitSetEn TWO_SEGMENTS_BITSET     = new SegmentBitSetEn("00110011110001000000010001101101010011011000110010011011010110011000000001000011111100001000000");

	// 22 three-segment characters - 1, 4, 5, A, B, F, H, I, J, K, N, R, Y, Z, k, z, <space>, $, %, *, [, ] 
	public static final SegmentBitSetEn THREE_SEGMENTS_BITSET   = new SegmentBitSetEn("01001100001100011111100100010000001100000000001000000000000001100011000010000000000001010000000");

	// 5 four-segment characters - E, M, W, w, # 
	public static final SegmentBitSetEn FOUR_SEGMENTS_BITSET    = new SegmentBitSetEn("00000000000000100000001000000000100000000000000000000000001000000100000000000000000000000000000");


	public SegmentBitSetEn(){};

	public SegmentBitSetEn(String bitString) {
		int bitStringLength = bitString.length();
		for (int i = 0; i < bitStringLength; i++) {

			if (bitString.charAt(i) == '1') {
				mSegmentBitSet.set(i);
			}
		}
	}

	public void copy(SegmentBitSetEn sBitSet) {
		this.mSegmentBitSet = (BitSet) sBitSet.mSegmentBitSet.clone();
	}

	private SegmentBitSetEn clone(SegmentBitSetEn sBitSet) {
		SegmentBitSetEn nBitSet = new SegmentBitSetEn();
		nBitSet.copy(sBitSet);
		return nBitSet;
	}

	public String getCharacters() {
		String characters = "";
		int bitStringLength = mSegmentBitSet.length();
		for (int i = 0; i < bitStringLength; i++) {

			if (mSegmentBitSet.get(i) == true) {
				characters = characters + DIGITS_LETTERS_SPECIALS_STRING.charAt(i);
			}
		}
		return characters;
	}

	private void printCharSet(SegmentBitSetEn bitSet) {
		System.out.println(bitSet.getCharacters());
		System.out.println();
	}

	private String getCharSetForSegment(char segmentChar) {
		String chars = "";
		switch (segmentChar) {
		case PenSegment.HLINE_CHAR:
			chars = HLINE_BITSET.getCharacters();
			break;
		case PenSegment.FSLASH_CHAR:
			chars = FSLASH_BITSET.getCharacters();
			break;
		case PenSegment.VLINE_CHAR:
			chars = VLINE_BITSET.getCharacters();
			break;
		case PenSegment.BSLASH_CHAR:
			chars = BSLASH_BITSET.getCharacters();
			break;
		case PenSegment.BC_CHAR:
			chars = BC_BITSET.getCharacters();
			break;
		case PenSegment.FC_CHAR:
			chars = FC_BITSET.getCharacters();
			break;
		case PenSegment.CIRCLE_CHAR:
			chars = CIRCLE_BITSET.getCharacters();
			break;
		case PenSegment.U_CHAR:
			chars = U_BITSET.getCharacters();
			break;
		case PenSegment.DOT_CHAR:
			chars = DOT_BITSET.getCharacters();
			break;
		default:
			break;
		}
		return chars;
	}

	private void printCharSetForSegment(char segmentChar) {
		switch (segmentChar) {
		case PenSegment.HLINE_CHAR:
			printCharSet(HLINE_BITSET);
			break;
		case PenSegment.FSLASH_CHAR:
			printCharSet(FSLASH_BITSET);
			break;
		case PenSegment.VLINE_CHAR:
			printCharSet(VLINE_BITSET);
			break;
		case PenSegment.BSLASH_CHAR:
			printCharSet(BSLASH_BITSET);
			break;
		case PenSegment.BC_CHAR:
			printCharSet(BC_BITSET);
			break;
		case PenSegment.FC_CHAR:
			printCharSet(FC_BITSET);
			break;
		case PenSegment.CIRCLE_CHAR:
			printCharSet(CIRCLE_BITSET);
			break;
		case PenSegment.U_CHAR:
			printCharSet(U_BITSET);
			break;
		case PenSegment.DOT_CHAR:
			printCharSet(DOT_BITSET);
			break;
		default:
			break;

		}
	}

	public static SegmentBitSetEn getSegmentBitSetForChar(char segmentChar) {
		SegmentBitSetEn sBitSet = new SegmentBitSetEn();
		switch (segmentChar) {
		case PenSegment.HLINE_CHAR:
			sBitSet.copy(HLINE_BITSET);
			break;
		case PenSegment.FSLASH_CHAR:
			sBitSet.copy(FSLASH_BITSET);
			break;
		case PenSegment.VLINE_CHAR:
			sBitSet.copy(VLINE_BITSET);
			break;
		case PenSegment.BSLASH_CHAR:
			sBitSet.copy(BSLASH_BITSET);
			break;
		case PenSegment.BC_CHAR:
			sBitSet.copy(BC_BITSET);
			break;		
		case PenSegment.FC_CHAR:
			sBitSet.copy(FC_BITSET);
			break;
		case PenSegment.CIRCLE_CHAR:
			sBitSet.copy(CIRCLE_BITSET);
			break;
		case PenSegment.U_CHAR:
			sBitSet.copy(U_BITSET);
			break;
		case PenSegment.DOT_CHAR:
			sBitSet.copy(DOT_BITSET);
			break;
		default:
			break;

		}

		return sBitSet;
	}

	public static void testPrintSegmentBitSet() {
		SegmentBitSetEn s = SegmentBitSetEn.VLINE_BITSET;
		System.out.println("VLINE" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(VLINE_BITSET);

		s = SegmentBitSetEn.HLINE_BITSET;
		System.out.println("HLINE" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(HLINE_BITSET);

		s = SegmentBitSetEn.FSLASH_BITSET;
		System.out.println("FSLASH" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(FSLASH_BITSET);

		s = SegmentBitSetEn.BSLASH_BITSET;
		System.out.println("BSLASH_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(BSLASH_BITSET);

		s = SegmentBitSetEn.BC_BITSET;
		System.out.println("BC_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(BC_BITSET);

		s = SegmentBitSetEn.CIRCLE_BITSET;
		System.out.println("OH_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(CIRCLE_BITSET);

		s = SegmentBitSetEn.FC_BITSET;
		System.out.println("FC_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(FC_BITSET);

		s = SegmentBitSetEn.DOT_BITSET;
		System.out.println("DOT_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(DOT_BITSET);

		s = SegmentBitSetEn.U_BITSET;
		System.out.println("U_STRING" + s.mSegmentBitSet.toString() + s.mSegmentBitSet.cardinality());
		s.printCharSet(U_BITSET);

	}

}
