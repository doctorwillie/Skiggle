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

package com.android.skiggle.chinese;

import com.android.skiggle.SegmentBitSet;

/**
 * SegmentBitSetCn contains the character bit sets for Chinese Characters
 * @author Willie Lim
 *
 */
public class SegmentBitSetCn extends SegmentBitSet {
	

	//SegmentBitSet sbs = new SegmentBitSet();
	
	//str = new String("abc");
	//SegmentBitSet.setSAllCharactersString(str);

	public static void initializeSegmentBitSetGlobals() {
		SegmentBitSet.setSAllCharactersString(new String("〇一二三四五六七八九十?"));
//		SegmentBitSet.setSAllCharactersStringBitset();
		SegmentBitSet.setSVLineBitset(new SegmentBitSet("00001000001"));
		SegmentBitSet.setSHLineBitset(new SegmentBitSet("01111110001"));
		SegmentBitSet.setSFSlashBitset(new SegmentBitSet("00001000100"));
		SegmentBitSet.setSBSlashBitset(new SegmentBitSet("00000000100"));
		SegmentBitSet.setSBcBitset(new SegmentBitSet("00000000000"));
		SegmentBitSet.setSCircleBitset(new SegmentBitSet("10000000000"));
		SegmentBitSet.setSFcBitset(new SegmentBitSet("00000000000"));
		SegmentBitSet.setSDotBitset(new SegmentBitSet("00000000000"));
		SegmentBitSet.setSUBitset(new SegmentBitSet("00000000000"));
		SegmentBitSet.setSOneSegmentBitset(new SegmentBitSet("11000110000"));
		SegmentBitSet.setSTwoSegmentsBitset(new SegmentBitSet("00100000101"));
		SegmentBitSet.setSThreeSegmentsBitset(new SegmentBitSet("00011000000"));
		SegmentBitSet.setSFourSegmentsBitset(new SegmentBitSet("00000000000"));
	}

	public SegmentBitSetCn() {};

}
