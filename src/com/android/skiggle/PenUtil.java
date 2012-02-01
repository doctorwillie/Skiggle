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


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Paint;

public class PenUtil
{

	public PenUtil() {}

	public static double getAbsAngle(double y, double x) {

		double angle = 0.0F;
		// atan2 is positive in the lower right quadrant and negative in upper right quadrant and measured
		// from the horizontal (positive x-axis)
		angle = Math.IEEEremainder(360 + Math.toDegrees(Math.atan2(y, x)), 360);

		return angle;
	}

	// Get the gap between two stroke points (x1, y1) and (x2, y2)
	public static float distanceBetween2Points(float x1, float y1, float x2, float y2) {

		return PointF.length(Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	public static int[] histogram(float[] dataPoints) {

		int[] buckets = {0, 0, 0, 0, 0}; // 5 buckets
		float minVal = 1000000.0F;
		float maxVal = -minVal;
		int numOfDataPoints = dataPoints.length;

		// Get the min and max values of the data points
		for (int i = 0; i < numOfDataPoints; i++) {

			minVal = Math.min(minVal, Math.abs(dataPoints[i]));
			maxVal = Math.max(maxVal, Math.abs(dataPoints[i]));
		}

		float bucketSize = (maxVal - minVal)/5;
		// float bucketSize = 0.002F;

		// Count the number of points for each bucket
		for (int i = 0; i < numOfDataPoints; i++) {

			float val = Math.abs(dataPoints[i]);
			if (val <= minVal + bucketSize)
				buckets[0] = buckets[0] + 1; // between minVal and minVal + bucketSize
			else if (val <= minVal + 2* bucketSize)
				buckets[1] = buckets[1] + 1; // between minVal and minVal + 2* bucketSize
			else if (val <= minVal + 3* bucketSize)
				buckets[2] = buckets[2] + 1; // between minVal and minVal + 3* bucketSize
			else if (val <= minVal + 4* bucketSize)
				buckets[3] = buckets[3] + 1; // between minVal and minVal + 4* bucketSize
			else
				buckets[4] = buckets[4] + 1; // greater than minVal + 4* bucketSize
		}	
		return buckets;
	}

	public static void getSegmentEndPoints(RectF mBoundingRectF, float x[], float y[], float tanAngle[], float kappa[], Canvas canvas, Paint textPaint) {

		int numOfSegments = x.length;
		for (int i =0; i < numOfSegments; i++) {

			printString(String.format("%1$d, k:%2$2.4f", i, kappa[i]), x[i], y[i], canvas, textPaint);
		}

	}

	// NOT USED - Algorithm HK2003 13 (9. S. Hermann and R. Klette. Multigrid analysis of curvature estimators. In Proc.
	// Image Vision Computing New Zealand, pages 108–112, Massey University, 2003.) from
	// "A Comparative Study on 2D Curvature Estimators", Simon Hermann and Reinhard Klette
	public static float computeCurvatureHK2003(float x0, float y0, float x1, float y1, float x2, float y2) {

		double kappa = 0.0D;

		float lB = distanceBetween2Points(x1, y1, x0, y0);
		float lF = distanceBetween2Points(x1, y1, x2, y2);
		float thetaB = (float) Math.atan2(x0 - x1, y0 - y1);
		float thetaF = (float) Math.atan2(x2 - x1, y2 - y1);

		float delta = Math.abs(thetaB - thetaF)/2;

		kappa = (1/lB + 1/lF) * delta/2;
		return (float) kappa;
	}

	// Algorithm M2003 (13.  M. Marji. On the detection of dominant points on digital planar curves. PhD thesis,
	// Wayne State University, Detroit, Michigan, 2003) from "A Comparative Study on 2D Curvature Estimators",
	// Simon Hermann and Reinhard Klette
	public static float computeCurvatureM2003(float x0, float y0, float x1, float y1, float x2, float y2) {

		double kappa = 0.0D;

		float a1 = (x2 - x0)/2;
		float a2 = (x2 + x0)/2 - x1;
		float b1 = (y2 - y0)/2;
		float b2 = (y2 + y0)/2 - y1;

		// float alpha = 0.0F;
		// alpha = (a1*b2 - a2*b1);
		// float beta  = 0.0F;
		// beta = a1*a1 + b1*b1;
		// float delta = 0.0F;
		// delta = (float) Math.pow((a1*a1 + b1*b1), 1.5);

		kappa = 2*(a1*b2 - a2*b1)/ Math.pow((a1*a1 + b1*b1), 1.5);
		return (float) kappa;
	}

	/**
	 * Removes the first occurrence of the given character from the string
	 * @param c - character to remove (first occurrence)
	 * @param str- string to search
	 * @return - a copy of str without the character c
	 */
	public static String removeCharFromString(char c, String str) {
		StringBuilder resultStrBuilder = new StringBuilder(str);
		String returnStr = "";
		int charPos = str.indexOf(c);
		int strLen = str.length();
		if ((charPos >= 0) && (strLen > 0)) {
			resultStrBuilder.deleteCharAt(charPos);			
		}
		if (resultStrBuilder.length() > 0) {
			returnStr = resultStrBuilder.toString();
		}
		return returnStr;
	}
	
	public static void printString(String s, float x, float y, Canvas canvas, Paint paint) {


		if (s != null) {
			
			Paint tempPaint = new Paint();
			tempPaint.set(paint);

			tempPaint.setColor(Skiggle.sDefaultCanvasColor);
			tempPaint.setStrokeWidth(Skiggle.sDefaultStrokeWidth);
			canvas.drawRect(x, y - 12, (float) (x + (s.length() * 6.5)), y + 4, tempPaint); // Paint over old text char, if any

			tempPaint.setColor(Skiggle.RED);

			canvas.drawText(s, x, y, tempPaint);

		}
	}

}
