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

package com.android.skiggle.english;

import com.android.skiggle.PenSegment;
import com.android.skiggle.SegmentBitSet;

import java.util.BitSet;


/**
 * SegmentBitSetEN contains the character segment bit sets and methods for English characters.
 * @author Willie Lim
 *
 */
public class SegmentBitSetEn extends SegmentBitSet {

	public BitSet mSegmentBitSet = new BitSet();

	/******************************************************/
	/* BitSet for digits, letters, and special characters */
	/******************************************************/


	public SegmentBitSetEn(){};

	public SegmentBitSetEn(String bitString) {
		int bitStringLength = bitString.length();
		for (int i = 0; i < bitStringLength; i++) {

			if (bitString.charAt(i) == '1') {
				mSegmentBitSet.set(i);
			}
		}
	}
	
	public static void initializeSegmentBitSetGlobals() {
		// Note: " and \ are escaped with backslash - "\"" and "\\"
		// The alphabet letters are sorted by frequency of appearance/use
		SegmentBitSet.setSAllCharactersString(new String("0123456789ETAOINSHRDLCUMWFGYPBVKJXQZetaoinshrdlcumwfgypbvkjxqz␠!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"));
		// 41 characters with VLINE - |: 1, 4, 5, 9, E, T, I, N, H, R, D, L, M, F, Y, P, B, K, J, t, a, i, n, h, r, d, l, m, p, b, k, q, ␠, !, ", $, ', +, [, ], |
		SegmentBitSet.setSVLineBitset(new SegmentBitSet("01001100011100110111100101011101100001101101111001000011010010111010010001000000000001010000100"));
		// 28 characters with HLINE - —: 1, 2, 4, 5, 7, E, T, A, I, H, L, F, G, J, Z, e, t, f, z, ␠, #, *, +, -, =, [, ], _
		SegmentBitSet.setSHLineBitset(new SegmentBitSet("01101101001110100100100001100000100111000000000000010000000001100100000011010000010001010100000"));
		// 24 characters with FSLASH - /: 1, 4, 7, A, M, W, Y, V, K, X, Z, w, y, v, k, x, z, #, %, *, /, <, >, ^
		SegmentBitSet.setSFSlashBitset(new SegmentBitSet("01001001000010000000000110010011010100000000000000100100110101000101000010000100101000001000000"));
		// 21 characters with BSLASH - \: A, N, R, M, W, Y, V, K, X, Q, w, y, v, k, x, *, <, >, \, ^, `
		SegmentBitSet.setSBSlashBitset(new SegmentBitSet("00000000000010010010000110010011011000000000000000100100110100000000000010000000101000101010000"));
		// 13 characters with FC (regular C) - (: 6, 9, S, C, G, e, a, s, d, c, q, $, (
		SegmentBitSet.setSFcBitset(new SegmentBitSet("00000010010000001000010000100000000010100010010100000000000010000010001000000000000000000000000"));
		// 7 characters with CIRCLE - O: 0, 6, 8, O, Q, o, %
		SegmentBitSet.setSCircleBitset(new SegmentBitSet("10000010100001000000000000000000001000010000000000000000000000000001000000000000000000000000000"));
		// 16 characters with BC (back C) - ): 2, 3, 5, S, R, D, P, B, s, p, b, $, ), ,, ;, ?
		SegmentBitSet.setSBcBitset(new SegmentBitSet("00110100000000001011000000001100000000000010000000000011000000000010000100100001000100000000000"));
		// 7 characters with DOT - .: i, j, !, ., :, ;, ?
		SegmentBitSet.setSDotBitset(new SegmentBitSet("00000000000000000000000000000000000000001000000000000000001000010000000000001011000100000000000"));
		// 4 characters with U - U: U, J, u, j
		SegmentBitSet.setSUBitset(new SegmentBitSet("00000000000000000000001000000000100000000000000010000000001000000000000000000000000000000000000"));
		// 23 one-segment characters - 0, 1, C, O, U, c, f, h, l, n, o, r, u, ', (, ), ,, -, ., /, \, _, `, | 
		SegmentBitSet.setSOneSegmentBitset(new SegmentBitSet("11000000000001000000011000000000000000010101101110010000000000000000011100111100000000100110100"));
		// 39 two-segment characters - 1, 2, 3, 6, 7, 8, 9, D, G, L, P, Q, S, T, V, X, a, b, d, e, i, j, m, p, q, s, t, v, x, y, !, ", +, :, ;, <, =, >, ?, ^ 
		SegmentBitSet.setSTwoSegmentsBitset(new SegmentBitSet("01110011110100001001100000101010011011101010010001000111101110011000000001000011111100001000000"));
		// 22 three-segment characters - 1, 4, 5, A, B, F, H, I, J, K, N, R, Y, Z, k, z, <space>, $, %, *, [, ] 
		SegmentBitSet.setSThreeSegmentsBitset(new SegmentBitSet("01001100000010110110000001010101100100000000000000000000010001100011000010000000000001010000000"));
		// 5 four-segment characters - E, M, W, w, # 
		SegmentBitSet.setSFourSegmentsBitset(new SegmentBitSet("00000000001000000000000110000000000000000000000000100000000000000100000000000000000000000000000"));
	}

	public static SegmentBitSetEn getSegmentBitSetForChar(char segmentChar) {
		SegmentBitSetEn sBitSet = new SegmentBitSetEn();
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

}
