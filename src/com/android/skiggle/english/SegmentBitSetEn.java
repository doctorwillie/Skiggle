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
		SegmentBitSet.setSAllCharactersString(new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz !\"#$%&'()*+,-./:;<=>?@[\\]^`{|}~"));
		// 42 characters with VLINE - 1, 4, 5, 9, B, D, E, F, G, H, I, J, K, L, M, N, P, R, T, Y, a, b, d, h, i, k, l, m, n, p, q, r, t, <space>, !, ", $, ', +, [, ], | 
		SegmentBitSet.setSVLineBitset(new SegmentBitSet("01001100010101111111111101010100001011010001101111011101000000111010010001000000000001010000100"));
		// 28 characters with HLINE - 1, 2, 4, 5, 7, A, E, F, G, H, I, J, L, T, Z, e, f, t, z, <space>, #, *, +, -, =, [, ], _ 
		SegmentBitSet.setSHLineBitset(new SegmentBitSet("01101101001000111111010000000100000100001100000000000001000001100100000011010000010001010100000"));
		// 24 characters with FSLASH - 1, 4, 7, A, K, M, V, W, X, Y, Z, k, v, w, x, y, z, #, %, *, /, <, >, ^ 
		SegmentBitSet.setSFSlashBitset(new SegmentBitSet("01001001001000000000101000000001111100000000001000000000011111000101000010000100101000001000000"));
		// 21 characters with BSLASH - A, K, M, N, Q, R, V, W, X, Y, k, v, w, x, y, *, <, >, \, ^, ` 
		SegmentBitSet.setSBSlashBitset(new SegmentBitSet("00000000001000000000101100110001111000000000001000000000011110000000000010000000101000101010000"));
		// 16 characters with BC (back C) - 2, 3, 5, B, D, P, R, S, b, p, s, $, ), ,, ;, ? 
		SegmentBitSet.setSBcBitset(new SegmentBitSet("00110100000101000000000001011000000001000000000000010010000000000010000100100001000100000000000"));
		// 7 characters with CIRCLE - 0, 6, 8, O, Q, o, % 
		SegmentBitSet.setSCircleBitset(new SegmentBitSet("10000010100000000000000010100000000000000000000000100000000000000001000000000000000000000000000"));
		// 13 characters with FC (regular C) - 6, 9, C, G, S, a, c, d, e, q, s, $, ( 
		SegmentBitSet.setSFcBitset(new SegmentBitSet("00000010010010001000000000001000000010111000000000001010000000000010001000000000000000000000000"));
		// 7 (capital letters) characters with DOT - i, j, !, ., :, ;, ? 
		SegmentBitSet.setSDotBitset(new SegmentBitSet("00000000000000000000000000000000000000000000110000000000000000010000000000001011000100000000000"));
		// 4 characters with U - J, U, j, u
		SegmentBitSet.setSUBitset(new SegmentBitSet("00000000000000000001000000000010000000000000010000000000100000000000000000000000000000000000000"));
		// 23 one-segment characters - 0, 1, C, O, U, c, f, h, l, n, o, r, u, ', (, ), ,, -, ., /, \, _, `, | 
		SegmentBitSet.setSOneSegmentBitset(new SegmentBitSet("11000000000010000000000010000010000000100101000101100100100000000000011100111100000000100110100"));
		// 39 two-segment characters - 1, 2, 3, 6, 7, 8, 9, D, G, L, P, Q, S, T, V, X, a, b, d, e, i, j, m, p, q, s, t, v, x, y, !, ", +, :, ;, <, =, >, ?, ^ 
		SegmentBitSet.setSTwoSegmentsBitset(new SegmentBitSet("01110011110001000000010001101101010011011000110010011011010110011000000001000011111100001000000"));
		// 22 three-segment characters - 1, 4, 5, A, B, F, H, I, J, K, N, R, Y, Z, k, z, <space>, $, %, *, [, ] 
		SegmentBitSet.setSThreeSegmentsBitset(new SegmentBitSet("01001100001100011111100100010000001100000000001000000000000001100011000010000000000001010000000"));
		// 5 four-segment characters - E, M, W, w, # 
		SegmentBitSet.setSFourSegmentsBitset(new SegmentBitSet("00000000000000100000001000000000100000000000000000000000001000000100000000000000000000000000000"));
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
