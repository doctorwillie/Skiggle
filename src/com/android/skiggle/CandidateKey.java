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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class CandidateKey extends View {
	
/*	KeyEvent constants
			KeyEvent.KEYCODE_0 = 7 (0x00000007)
			KeyEvent.KEYCODE_1 = 8 (0x00000008)
			KeyEvent.KEYCODE_2 = 9 (0x00000009)
			KeyEvent.KEYCODE_3 = 10 (0x0000000a)
			KeyEvent.KEYCODE_3D_MODE = 206 (0x000000ce)
			KeyEvent.KEYCODE_4 = 11 (0x0000000b)
			KeyEvent.KEYCODE_5 = 12 (0x0000000c)
			KeyEvent.KEYCODE_6 = 13 (0x0000000d)
			KeyEvent.KEYCODE_7 = 14 (0x0000000e)
			KeyEvent.KEYCODE_8 = 15 (0x0000000f)
			KeyEvent.KEYCODE_9 = 16 (0x00000010)
			KeyEvent.KEYCODE_A = 29 (0x0000001d)
			KeyEvent.KEYCODE_ALT_LEFT = 57 (0x00000039)
			KeyEvent.KEYCODE_ALT_RIGHT = 58 (0x0000003a)
			KeyEvent.KEYCODE_APOSTROPHE = 75 (0x0000004b)
			KeyEvent.KEYCODE_APP_SWITCH = 187 (0x000000bb)
			KeyEvent.KEYCODE_AT = 77 (0x0000004d)
			KeyEvent.KEYCODE_AVR_INPUT = 182 (0x000000b6)
			KeyEvent.KEYCODE_AVR_POWER = 181 (0x000000b5)
			KeyEvent.KEYCODE_B = 30 (0x0000001e)
			KeyEvent.KEYCODE_BACK = 4 (0x00000004)
			KeyEvent.KEYCODE_BACKSLASH = 73 (0x00000049)
			KeyEvent.KEYCODE_BOOKMARK = 174 (0x000000ae)
			KeyEvent.KEYCODE_BREAK = 121 (0x00000079)
			KeyEvent.KEYCODE_BUTTON_1 = 188 (0x000000bc)
			KeyEvent.KEYCODE_BUTTON_10 = 197 (0x000000c5)
			KeyEvent.KEYCODE_BUTTON_11 = 198 (0x000000c6)
			KeyEvent.KEYCODE_BUTTON_12 = 199 (0x000000c7)
			KeyEvent.KEYCODE_BUTTON_13 = 200 (0x000000c8)
			KeyEvent.KEYCODE_BUTTON_14 = 201 (0x000000c9)
			KeyEvent.KEYCODE_BUTTON_15 = 202 (0x000000ca)
			KeyEvent.KEYCODE_BUTTON_16 = 203 (0x000000cb)
			KeyEvent.KEYCODE_BUTTON_2 = 189 (0x000000bd)
			KeyEvent.KEYCODE_BUTTON_3 = 190 (0x000000be)
			KeyEvent.KEYCODE_BUTTON_4 = 191 (0x000000bf)
			KeyEvent.KEYCODE_BUTTON_5 = 192 (0x000000c0)
			KeyEvent.KEYCODE_BUTTON_6 = 193 (0x000000c1)
			KeyEvent.KEYCODE_BUTTON_7 = 194 (0x000000c2)
			KeyEvent.KEYCODE_BUTTON_8 = 195 (0x000000c3)
			KeyEvent.KEYCODE_BUTTON_9 = 196 (0x000000c4)
			KeyEvent.KEYCODE_BUTTON_A = 96 (0x00000060)
			KeyEvent.KEYCODE_BUTTON_B = 97 (0x00000061)
			KeyEvent.KEYCODE_BUTTON_C = 98 (0x00000062)
			KeyEvent.KEYCODE_BUTTON_L1 = 102 (0x00000066)
			KeyEvent.KEYCODE_BUTTON_L2 = 104 (0x00000068)
			KeyEvent.KEYCODE_BUTTON_MODE = 110 (0x0000006e)
			KeyEvent.KEYCODE_BUTTON_R1 = 103 (0x00000067)
			KeyEvent.KEYCODE_BUTTON_R2 = 105 (0x00000069)
			KeyEvent.KEYCODE_BUTTON_SELECT = 109 (0x0000006d)
			KeyEvent.KEYCODE_BUTTON_START = 108 (0x0000006c)
			KeyEvent.KEYCODE_BUTTON_THUMBL = 106 (0x0000006a)
			KeyEvent.KEYCODE_BUTTON_THUMBR = 107 (0x0000006b)
			KeyEvent.KEYCODE_BUTTON_X = 99 (0x00000063)
			KeyEvent.KEYCODE_BUTTON_Y = 100 (0x00000064)
			KeyEvent.KEYCODE_BUTTON_Z = 101 (0x00000065)
			KeyEvent.KEYCODE_C = 31 (0x0000001f)
			KeyEvent.KEYCODE_CALCULATOR = 210 (0x000000d2)
			KeyEvent.KEYCODE_CALENDAR = 208 (0x000000d0)
			KeyEvent.KEYCODE_CALL = 5 (0x00000005)
			KeyEvent.KEYCODE_CAMERA = 27 (0x0000001b)
			KeyEvent.KEYCODE_CAPS_LOCK = 115 (0x00000073)
			KeyEvent.KEYCODE_CAPTIONS = 175 (0x000000af)
			KeyEvent.KEYCODE_CHANNEL_DOWN = 167 (0x000000a7)
			KeyEvent.KEYCODE_CHANNEL_UP = 166 (0x000000a6)
			KeyEvent.KEYCODE_CLEAR = 28 (0x0000001c)
			KeyEvent.KEYCODE_COMMA = 55 (0x00000037)
			KeyEvent.KEYCODE_CONTACTS = 207 (0x000000cf)
			KeyEvent.KEYCODE_CTRL_LEFT = 113 (0x00000071)
			KeyEvent.KEYCODE_CTRL_RIGHT = 114 (0x00000072)
			KeyEvent.KEYCODE_D = 32 (0x00000020)
			KeyEvent.KEYCODE_DEL = 67 (0x00000043)
			KeyEvent.KEYCODE_DPAD_CENTER = 23 (0x00000017)
			KeyEvent.KEYCODE_DPAD_DOWN = 20 (0x00000014)
			KeyEvent.KEYCODE_DPAD_LEFT = 21 (0x00000015)
			KeyEvent.KEYCODE_DPAD_RIGHT = 22 (0x00000016)
			KeyEvent.KEYCODE_DPAD_UP = 19 (0x00000013)
			KeyEvent.KEYCODE_DVR = 173 (0x000000ad)
			KeyEvent.KEYCODE_E = 33 (0x00000021)
			KeyEvent.KEYCODE_ENDCALL = 6 (0x00000006)
			KeyEvent.KEYCODE_ENTER = 66 (0x00000042)
			KeyEvent.KEYCODE_ENVELOPE = 65 (0x00000041)
			KeyEvent.KEYCODE_EQUALS = 70 (0x00000046)
			KeyEvent.KEYCODE_ESCAPE = 111 (0x0000006f)
			KeyEvent.KEYCODE_EXPLORER = 64 (0x00000040)
			KeyEvent.KEYCODE_F = 34 (0x00000022)
			KeyEvent.KEYCODE_F1 = 131 (0x00000083)
			KeyEvent.KEYCODE_F10 = 140 (0x0000008c)
			KeyEvent.KEYCODE_F11 = 141 (0x0000008d)
			KeyEvent.KEYCODE_F12 = 142 (0x0000008e)
			KeyEvent.KEYCODE_F2 = 132 (0x00000084)
			KeyEvent.KEYCODE_F3 = 133 (0x00000085)
			KeyEvent.KEYCODE_F4 = 134 (0x00000086)
			KeyEvent.KEYCODE_F5 = 135 (0x00000087)
			KeyEvent.KEYCODE_F6 = 136 (0x00000088)
			KeyEvent.KEYCODE_F7 = 137 (0x00000089)
			KeyEvent.KEYCODE_F8 = 138 (0x0000008a)
			KeyEvent.KEYCODE_F9 = 139 (0x0000008b)
			KeyEvent.KEYCODE_FOCUS = 80 (0x00000050)
			KeyEvent.KEYCODE_FORWARD = 125 (0x0000007d)
			KeyEvent.KEYCODE_FORWARD_DEL = 112 (0x00000070)
			KeyEvent.KEYCODE_FUNCTION = 119 (0x00000077)
			KeyEvent.KEYCODE_G = 35 (0x00000023)
			KeyEvent.KEYCODE_GRAVE = 68 (0x00000044)
			KeyEvent.KEYCODE_GUIDE = 172 (0x000000ac)
			KeyEvent.KEYCODE_H = 36 (0x00000024)
			KeyEvent.KEYCODE_HEADSETHOOK = 79 (0x0000004f)
			KeyEvent.KEYCODE_HOME = 3 (0x00000003)
			KeyEvent.KEYCODE_I = 37 (0x00000025)
			KeyEvent.KEYCODE_INFO = 165 (0x000000a5)
			KeyEvent.KEYCODE_INSERT = 124 (0x0000007c)
			KeyEvent.KEYCODE_J = 38 (0x00000026)
			KeyEvent.KEYCODE_K = 39 (0x00000027)
			KeyEvent.KEYCODE_L = 40 (0x00000028)
			KeyEvent.KEYCODE_LANGUAGE_SWITCH = 204 (0x000000cc)
			KeyEvent.KEYCODE_LEFT_BRACKET = 71 (0x00000047)
			KeyEvent.KEYCODE_M = 41 (0x00000029)
			KeyEvent.KEYCODE_MANNER_MODE = 205 (0x000000cd)
			KeyEvent.KEYCODE_MEDIA_CLOSE = 128 (0x00000080)
			KeyEvent.KEYCODE_MEDIA_EJECT = 129 (0x00000081)
			KeyEvent.KEYCODE_MEDIA_FAST_FORWARD = 90 (0x0000005a)
			KeyEvent.KEYCODE_MEDIA_NEXT = 87 (0x00000057)
			KeyEvent.KEYCODE_MEDIA_PAUSE = 127 (0x0000007f)
			KeyEvent.KEYCODE_MEDIA_PLAY = 126 (0x0000007e)
			KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE = 85 (0x00000055)
			KeyEvent.KEYCODE_MEDIA_PREVIOUS = 88 (0x00000058)
			KeyEvent.KEYCODE_MEDIA_RECORD = 130 (0x00000082)
			KeyEvent.KEYCODE_MEDIA_REWIND = 89 (0x00000059)
			KeyEvent.KEYCODE_MEDIA_STOP = 86 (0x00000056)
			KeyEvent.KEYCODE_MENU = 82 (0x00000052)
			KeyEvent.KEYCODE_META_LEFT = 117 (0x00000075)
			KeyEvent.KEYCODE_META_RIGHT = 118 (0x00000076)
			KeyEvent.KEYCODE_MINUS = 69 (0x00000045)
			KeyEvent.KEYCODE_MOVE_END = 123 (0x0000007b)
			KeyEvent.KEYCODE_MOVE_HOME = 122 (0x0000007a)
			KeyEvent.KEYCODE_MUSIC = 209 (0x000000d1)
			KeyEvent.KEYCODE_MUTE = 91 (0x0000005b)
			KeyEvent.KEYCODE_N = 42 (0x0000002a)
			KeyEvent.KEYCODE_NOTIFICATION = 83 (0x00000053)
			KeyEvent.KEYCODE_NUM = 78 (0x0000004e)
			KeyEvent.KEYCODE_NUMPAD_0 = 144 (0x00000090)
			KeyEvent.KEYCODE_NUMPAD_1 = 145 (0x00000091)
			KeyEvent.KEYCODE_NUMPAD_2 = 146 (0x00000092)
			KeyEvent.KEYCODE_NUMPAD_3 = 147 (0x00000093)
			KeyEvent.KEYCODE_NUMPAD_4 = 148 (0x00000094)
			KeyEvent.KEYCODE_NUMPAD_5 = 149 (0x00000095)
			KeyEvent.KEYCODE_NUMPAD_6 = 150 (0x00000096)
			KeyEvent.KEYCODE_NUMPAD_7 = 151 (0x00000097)
			KeyEvent.KEYCODE_NUMPAD_8 = 152 (0x00000098)
			KeyEvent.KEYCODE_NUMPAD_9 = 153 (0x00000099)
			KeyEvent.KEYCODE_NUMPAD_ADD = 157 (0x0000009d)
			KeyEvent.KEYCODE_NUMPAD_COMMA = 159 (0x0000009f)
			KeyEvent.KEYCODE_NUMPAD_DIVIDE = 154 (0x0000009a)
			KeyEvent.KEYCODE_NUMPAD_DOT = 158 (0x0000009e)
			KeyEvent.KEYCODE_NUMPAD_ENTER = 160 (0x000000a0)
			KeyEvent.KEYCODE_NUMPAD_EQUALS = 161 (0x000000a1)
			KeyEvent.KEYCODE_NUMPAD_LEFT_PAREN = 162 (0x000000a2)
			KeyEvent.KEYCODE_NUMPAD_MULTIPLY = 155 (0x0000009b)
			KeyEvent.KEYCODE_NUMPAD_RIGHT_PAREN = 163 (0x000000a3)
			KeyEvent.KEYCODE_NUMPAD_SUBTRACT = 156 (0x0000009c)
			KeyEvent.KEYCODE_NUM_LOCK = 143 (0x0000008f)
			KeyEvent.KEYCODE_O = 43 (0x0000002b)
			KeyEvent.KEYCODE_P = 44 (0x0000002c)
			KeyEvent.KEYCODE_PAGE_DOWN = 93 (0x0000005d)
			KeyEvent.KEYCODE_PAGE_UP = 92 (0x0000005c)
			KeyEvent.KEYCODE_PERIOD = 56 (0x00000038)
			KeyEvent.KEYCODE_PICTSYMBOLS = 94 (0x0000005e)
			KeyEvent.KEYCODE_PLUS = 81 (0x00000051)
			KeyEvent.KEYCODE_POUND = 18 (0x00000012)
			KeyEvent.KEYCODE_POWER = 26 (0x0000001a)
			KeyEvent.KEYCODE_PROG_BLUE = 186 (0x000000ba)
			KeyEvent.KEYCODE_PROG_GREEN = 184 (0x000000b8)
			KeyEvent.KEYCODE_PROG_RED = 183 (0x000000b7)
			KeyEvent.KEYCODE_PROG_YELLOW = 185 (0x000000b9)
			KeyEvent.KEYCODE_Q = 45 (0x0000002d)
			KeyEvent.KEYCODE_R = 46 (0x0000002e)
			KeyEvent.KEYCODE_RIGHT_BRACKET = 72 (0x00000048)
			KeyEvent.KEYCODE_S = 47 (0x0000002f)
			KeyEvent.KEYCODE_SCROLL_LOCK = 116 (0x00000074)
			KeyEvent.KEYCODE_SEARCH = 84 (0x00000054)
			KeyEvent.KEYCODE_SEMICOLON = 74 (0x0000004a)
			KeyEvent.KEYCODE_SETTINGS = 176 (0x000000b0)
			KeyEvent.KEYCODE_SHIFT_LEFT = 59 (0x0000003b)
			KeyEvent.KEYCODE_SHIFT_RIGHT = 60 (0x0000003c)
			KeyEvent.KEYCODE_SLASH = 76 (0x0000004c)
			KeyEvent.KEYCODE_SOFT_LEFT = 1 (0x00000001)
			KeyEvent.KEYCODE_SOFT_RIGHT = 2 (0x00000002)
			KeyEvent.KEYCODE_SPACE = 62 (0x0000003e)
			KeyEvent.KEYCODE_STAR = 17 (0x00000011)
			KeyEvent.KEYCODE_STB_INPUT = 180 (0x000000b4)
			KeyEvent.KEYCODE_STB_POWER = 179 (0x000000b3)
			KeyEvent.KEYCODE_SWITCH_CHARSET = 95 (0x0000005f)
			KeyEvent.KEYCODE_SYM = 63 (0x0000003f)
			KeyEvent.KEYCODE_SYSRQ = 120 (0x00000078)
			KeyEvent.KEYCODE_T = 48 (0x00000030)
			KeyEvent.KEYCODE_TAB = 61 (0x0000003d)
			KeyEvent.KEYCODE_TV = 170 (0x000000aa)
			KeyEvent.KEYCODE_TV_INPUT = 178 (0x000000b2)
			KeyEvent.KEYCODE_TV_POWER = 177 (0x000000b1)
			KeyEvent.KEYCODE_U = 49 (0x00000031)
			KeyEvent.KEYCODE_UNKNOWN = 0 (0x00000000)
			KeyEvent.KEYCODE_V = 50 (0x00000032)
			KeyEvent.KEYCODE_VOLUME_DOWN = 25 (0x00000019)
			KeyEvent.KEYCODE_VOLUME_MUTE = 164 (0x000000a4)
			KeyEvent.KEYCODE_VOLUME_UP = 24 (0x00000018)
			KeyEvent.KEYCODE_W = 51 (0x00000033)
			KeyEvent.KEYCODE_WINDOW = 171 (0x000000ab)
			KeyEvent.KEYCODE_X = 52 (0x00000034)
			KeyEvent.KEYCODE_Y = 53 (0x00000035)
			KeyEvent.KEYCODE_Z = 54 (0x00000036)
			KeyEvent.KEYCODE_ZOOM_IN = 168 (0x000000a8)
			KeyEvent.KEYCODE_ZOOM_OUT = 169 (0x000000a9)
*/	
	
	// TODO - These are temporary constants used in place of those missing from the KeyEvent class.
//	private static final int KEYCODE_NUMPAD_APOSTROPHE = 75; // Not used
	private static final int KEYCODE_SHIFT_LEFT = 59;
//	private static final int KEYCODE_NUMPAD_LEFT_PAREN = 162;
//	private static final int KEYCODE_NUMPAD_RIGHT_PAREN = 163;
	private static final int KEYCODE_PERIOD = 56; // '.'
	private static final int KEYCODE_SHIFT_UNDERSCORE = 69; // '_'
	private static final int KEYCODE_SHIFT_LEFT_BRACE = 71; // '{'
	private static final int KEYCODE_SHIFT_RIGHT_BRACE = 72; // '}'
	private static final int KEYCODE_SHIFT_VERTICAL_BAR = 73; // '|'

	// These are used with KeyEvent.KEYCODE_NUM
	static final int KEYCODE_NUMPAD_RIGHT_PAREN = 7;		// 7 is ')'
	static final int KEYCODE_NUMPAD_EXCLAIMATION = 8;		// 8 is '!'
//	static final int KEYCODE_NUMPAD_AT = 9;		// 9 is '@', same as 77 or KeyEvent.AT
//	static final int KEYCODE_NUMPAD_POUND = 10;		// 10 is '#', same as 18 or KeyEvent.POUND
	static final int KEYCODE_NUMPAD_DOLLAR = 11;		// 11 is '$', SAME AS 37?
	static final int KEYCODE_NUMPAD_PERCENT = 12;		// 12 is '%'
	static final int KEYCODE_NUMPAD_CARET = 13;		// 13 is '^'
	static final int KEYCODE_NUMPAD_AMPERSAND = 14;		// 14 is '&', same as 48
	static final int KEYCODE_NUMPAD_ASTERISK = 15;		// 15 is '*'
	static final int KEYCODE_NUMPAD_LEFT_PAREN = 16;		// 16 is '('
	// 17 is '*', same as 14?
	// 18 is '#', same as 10?
	static final int KEYCODE_NUMPAD_LESS_THAN = 30;		// 30 is '<'
	static final int KEYCODE_NUMPAD_MINUS = 35;		// 35 is '-'
	static final int KEYCODE_NUMPAD_LEFT_BRACKET = 36;		// 36 is '['
	// 37 is '$', SAME AS 11?
	static final int KEYCODE_NUMPAD_RIGHT_BRACKET = 38;		// 38 is ']'
	static final int KEYCODE_NUMPAD_QUOTE = 39;		// 39 is '''
	static final int KEYCODE_NUMPAD_APOSTROPHE = 40;		// 40 is ''', same asKeyEvent.KEYCODE_APOSTROPHE = 75
	// 41 is '!'
	static final int KEYCODE_NUMPAD_GREATER_THAN = 42;		// 42 is '>'
	// 42 is '('
	// 43 is ')'
	// 44 is '*'
	// 45 is '3'
	// 46 is '4'
	static final int KEYCODE_NUMPAD_PLUS = 47;		// 47 is '>'
	// 48 is '&'
	// 50 is '=', same as KeyEvent.KEYCODE_EQUALS = 70
	// 51 is '1'
	// 52 is '8'
	// 53 is '%'
	// 54 is '7'
	// 55 is ';', KeyEvent.KEYCODE_SEMICOLON = 74
	static final int KEYCODE_NUMPAD_COLON = 56;			// 55 is ':'
	// 57 is '1'
	// 66 is '`',  KeyEventKEYCODE_GRAVE = 68
	static final int KEYCODE_NUMPAD_QUESTION_MARK = 76; // 76 IS '?'
	
	
	private int mColor = Skiggle.GRAY_80; // Defaults to Gray80
	private Character mChar;
	private boolean mIsAppInstance = false; // Flag to indicate if Skiggle is an app or a soft key board; defaults to soft key board (not an app instance)
	private SkiggleSoftKeyboard mSoftKeyboard = null;
	protected Rect mRect;

	public CandidateKey(Context context, int left, int top, int right, int bottom, char c, int color, SkiggleSoftKeyboard softKeyboard, boolean isAppInstance) {
		super(context);
		mChar = c;
		mColor = color;
		mRect = new Rect(left, top, right, bottom);
		mIsAppInstance = isAppInstance;
		mSoftKeyboard = softKeyboard;
	} // End of CandidateKey(), with corners, character and color specified, constructor
	
	private void drawKey(Canvas canvas, int keyColor, int textColor) {

		Paint paint = new Paint();
		int charWidth = 15; // TODO - Need to compute this dynamically

		paint.setColor(keyColor);
		
		canvas.drawRect(mRect, paint);
		paint.setColor(textColor);
		paint.setTextSize(20);
		canvas.drawText(String.valueOf(mChar), (float) (mRect.left  + Math.floor((mRect.right - mRect.left - charWidth))/2), mRect.bottom - 5, paint);	

	}

	@Override
	public void draw(Canvas canvas) {
		drawKey(canvas, mColor, Skiggle.GRAY_26);  // Use Dark Gray26 for character
	}
	
	private void showBigChar() {
		if (mChar != null) {
			int textSize = 200;
			int canvasWidth = Skiggle.sBoxView.canvas.getWidth();
			int canvasHeight = Skiggle.sBoxView.canvas.getHeight();			
			int left = (canvasWidth - textSize)/2;
			int top = canvasHeight - (canvasHeight - textSize)/2;

			Paint paint = new Paint();
			paint.setColor(mColor);			
			paint.setTextSize(textSize);

			Skiggle.sBoxView.canvas.drawText(mChar.toString(), left, top, paint);
//			Skiggle.sBoxView.invalidate();

		}
	}
	
	// Sends letter key to the app
	private void letterKeyDownUp(Character c) {
		if ((c >= 'A') && (c <= 'Z')) {
			// Send shift key (left shift key) for upper case letters
			mSoftKeyboard.keyDownUp(KEYCODE_SHIFT_LEFT); // KeyEvent.KEYCODE_SHIFT_LEFT = 59
		}
		mSoftKeyboard.keyDownUp(Character.getNumericValue(mChar) - Character.getNumericValue('A') + KeyEvent.KEYCODE_A);	// Convert to key code values (all lower case only) with KeyEvent.KEYCODE_A = 29
	}
	
	// Sends numeric key to the app
	private void digitKeyDownUp(Character c) {
		mSoftKeyboard.keyDownUp(c - '0' + KeyEvent.KEYCODE_0); // KeyEvent.KEYCODE_0 = 7
	}
	
	// Sends symbols key to the app
	private void symbolKeyDownUp(int keyCode) {
		mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_NUM);
		mSoftKeyboard.keyDownUp(keyCode); 
	}
	
	// Sends shifted symbols key to the app
	private void shiftedSymbolKeyDownUp(int keyCode) {
		mSoftKeyboard.keyDownUp(KEYCODE_SHIFT_LEFT);
		mSoftKeyboard.keyDownUp(keyCode);
	}
	
	// TODO - this is for English keys only; need to accommodate Chinese  (and other languages') keys
	private void keyPressed() {
		
/*		for (int i = 111; i < 151; i++) {
			mSoftKeyboard.keyDownUp(KEYCODE_SHIFT_LEFT);
			mSoftKeyboard.keyDownUp(i);
//			symbolKeyDownUp(i);
		}
*/
/*		mSoftKeyboard.keyDownUp(75);
		symbolsKeyPressed(39);
		mSoftKeyboard.keyDownUp(40);
*/
		
		int keyCharCode = Character.getNumericValue(mChar);
		if ((mChar >= '0') && (mChar <= '9')) {
			digitKeyDownUp(mChar);

		}
		else if (Character.isLetter(mChar)) {
			letterKeyDownUp(mChar);
		}
		else {
			switch(mChar) {
/*			case '␠':
			     keyCharCode = KeyEvent.KEYCODE_
			     break;
*/			case '!':
			     symbolKeyDownUp(KEYCODE_NUMPAD_EXCLAIMATION);
			     break;
			case '"':
			     symbolKeyDownUp(KEYCODE_NUMPAD_QUOTE); //KeyEvent.KEYCODE_QUOTE;
			     break;
			case '#':
			     mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_POUND); // KEYCODE_POUND = 18
			     break;
			case '$':
			     symbolKeyDownUp(KEYCODE_NUMPAD_DOLLAR);
			     break;
			case '%':
			     symbolKeyDownUp(KEYCODE_NUMPAD_PERCENT);
			     break;
			case '&':
			     symbolKeyDownUp(KEYCODE_NUMPAD_AMPERSAND);
			     break;
			case '\'':
			     mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_APOSTROPHE); // KeyEvent.KEYCODE_APOSTROPHE = 75
			     break;
 			case '(':
			// TODO - The KeyEvent.KEYCODE_NUMPAD_LEfT_PAREN is only available at API Level 11 and above
				symbolKeyDownUp(KEYCODE_NUMPAD_LEFT_PAREN); // KEYCODE_NUMPAD_LEFT_PAREN; // KEYCODE_NUMPAD_LEFT_PAREN = 162
 				break;
 			case ')':
 			// TODO - The KeyEvent.KEYCODE_NUMPAD_RIGHT_PAREN is only available at API Level 11 and above
				symbolKeyDownUp(KEYCODE_NUMPAD_RIGHT_PAREN); // KEYCODE_NUMPAD_RIGHT_PAREN; // KEYCODE_NUMPAD_RIGT_PAREN = 163
 				break;
 			case '*':
 				mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_STAR); // KeyEvent.KEYCODE_STAR = 17
 				break;
 			case '+':
 				mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_PLUS); // KEYCODE_PLUS = 81
 				break;
 			case ',':
 				mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_COMMA); // KEYCODE_PLUS = 55
 				break;
 			case '-':
 				mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_MINUS); // KEYCODE_NUMPAD_MINUS = 69
 				break;
 			case '.':
 				mSoftKeyboard.keyDownUp(KEYCODE_PERIOD); // KEYCODE_PERIOD = 56
 				break;
 			case '/':
 				mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_SLASH); // KeyEvent.KEYCODE_SLASH = 76
 				break;
			case ':':
			     symbolKeyDownUp(KEYCODE_NUMPAD_COLON);
			     break;
			case ';':
			     mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_SEMICOLON); // KeyEvent.KEYCODE_SEMICOLON = 74
			     break;
			case '<':
			     symbolKeyDownUp(KEYCODE_NUMPAD_LESS_THAN);
			     break;
			case '=':
			     mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_EQUALS); // KeyEvent.KEYCODE_EQUALS = 70
			     break;
			case '>':
			     symbolKeyDownUp(KEYCODE_NUMPAD_GREATER_THAN);
			     break;
			case '?':
			     symbolKeyDownUp(KEYCODE_NUMPAD_QUESTION_MARK);
			     break;
			case '@':
			     mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_AT); // KeyEvent.KEYCODE_AT = 77
			     break;
			case '[':
			     mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_LEFT_BRACKET); // KeyEvent.KEYCODE_LEFT_BRACKET = 71
			     break;
			case '\\':
			     mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_BACKSLASH); // KeyEvent.KEYCODE_BACKSLASH = 73
			     break;
			case ']':
			     mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_RIGHT_BRACKET); // KeyEvent.KEYCODE_RIGHT_BRACKET = 72
			     break;
			case '^':
			     symbolKeyDownUp(KEYCODE_NUMPAD_CARET);
			     break;
			case '_':
			     shiftedSymbolKeyDownUp(KEYCODE_SHIFT_UNDERSCORE);
			     break;
			case '`':
			     mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_GRAVE); // KeyEventKEYCODE_GRAVE = 68
			     break;
			case '{':
			     shiftedSymbolKeyDownUp(KEYCODE_SHIFT_LEFT_BRACE);
			     break;
			case '|':
			     shiftedSymbolKeyDownUp(KEYCODE_SHIFT_VERTICAL_BAR);
			     break;
			case '}':
			     shiftedSymbolKeyDownUp(KEYCODE_SHIFT_RIGHT_BRACE);
			     break;
/*			case '~':
			     mSoftKeyboard.keyDownUp(KeyEvent.KEYCODE_TILDE);
			     break;
*/			default:
				break;
			}
		}
	}




	@Override
	public boolean onTouchEvent(MotionEvent event) {	
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
//			if (mSoftKeyboard != null) {
			if (mIsAppInstance) {
				showBigChar();
				invalidate();
			}
			else if (mSoftKeyboard != null) {
					keyPressed();
					invalidate();
			}
//			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
//			invalidate();
			break;
		case MotionEvent.ACTION_UP:
//			invalidate();
			break;
		}
		return true;
	}
	
	protected void clear(Canvas canvas) {
//		drawKey(canvas, Skiggle.sDefaultCanvasColor, Skiggle.sDefaultCanvasColor);	
	}

}
