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


import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.Log;

// PenSegment is part of a PenStroke???
public class PenSegment extends Path {

	// TAG for logging debugging info
	private static final String TAG = "MyPenSegment";

	// Class constants - primitive stroke elements making up a character
	public static final char VLINE_CHAR = '|'; // Vertical line segment
	public static final char HLINE_CHAR = '-'; // Horizontal line segment
	public static final char FSLASH_CHAR = '/'; //Forward slash segment
	public static final char BSLASH_CHAR = '\\'; // Back slash segment
	public static final char FC_CHAR = '('; // Forward "C" or left (or open) parenthesis used to represent the curved stroke that looks like a regular, forward 'C'
	public static final char CIRCLE_CHAR = 'O'; // Circle (closed loop) segment
	public static final char BC_CHAR = ')'; // Backward "C" or right (or closed) parenthesis used to represent the curved stroke that looks like a backward 'C'
	public static final char DOT_CHAR = '.'; // Dot or period segment
	public static final char U_CHAR = 'U'; // U segment

	private static final float MAX_CURVATURE_FOR_STRAIGHTLINE = 0.005F; // Maximum curvature (kappa) for a stroke to be a straight line

	private static final float VLINE_ANGLE = 90.0F; // Line goes from N to S
	private static final float HLINE_ANGLE = 0.0F; // Line goes from W to E
	private static final float BSLASH_ANGLE= 45.0F; // Line goes from NW to SE
	private static final float FSLASH_ANGLE = 135.0F; // Line goes from NE to SW

	private static final float VLINE_MAX_ANGLE_SPREAD = 15.0F; //10.0F;  // Max tilt angle spread from the vertical for vertical line
	private static final float HLINE_MAX_ANGLE_SPREAD = 15.0F; //10.0F;	 // Max tilt angle from spread the horizontal for a horizontal line segment
	private static final float BSLASH_MAX_ANGLE_SPREAD = 30.0F; // Maximum tilt angle from the horizontal for a back slash
	private static final float FSLASH_MAX_ANGLE_SPREAD = 30.0F; // Maximum tilt angle from the horizontal for a forward slash

	private static final float MAX_ABS_KAPPA_DIFF_THRESHOLD = 0.025F; // Max difference between the curvature (kappa) values of a stroke segment

	private static final int NUM_OF_POINTS_ON_STROKE = 20;

	// Members
	public Path penSegmentPath;
	public PathMeasure penStrokeMeasure;
	public float penStrokeLength;
	public RectF boundingRectF;
	public float boundingRectHeight;
	public float boundingRectWidth;
	public float posStart[] = {0.0F, 0.0F};
	private float mTanStart[] = {0.0F, 0.0F};
	public float posEnd[] = {0.0F, 0.0F};
	private float mTanEnd[] = {0.0F, 0.0F};
	private float mAvgAngle = 0.0F;
	public float avgKappa = 0.0F; // a measure of average curvature
	public float avgX = 0.0F; // average X-coord of points on the stroke
	public float avgY = 0.0F; // average Y-coord of points on the stroke
	private float mMaxAbsKappaX = 0.0F; // x-coord of the max absolute Kappa value
	private float mMaxAbsKappaY = 0.0F; // y-coord of the max absolute Kappa value
	private float mMaxAbsKappa = 0.0F; //temp
	private float mMaxAbsKappaDiffX = 0.0F; // x-coord of the max absolute Kappa diff value
	private float mMaxAbsKappaDiffY = 0.0F; // y-coord of the max absolute Kappa diff value
	public int maxAbsKappaDiffIndex = -1; // array index or position of the max absolute Kappa duff point
	public float tempMaxAbsKappaDiff = 0.0F; // temp
	private String mTempHistBucketsStr = ""; // temp
	public Character penSegmentCharacter;

	// Twenty element arrays
	public float pointsX[] = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
			0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
	public float pointsY[] = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
			0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
	private float mTanAngle[] = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
			0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}; // tangent angle in degrees
	private float mKappa[] = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
			0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}; // array of curvature
	private float mKappaDiff[] = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
			0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}; //


	public PenSegment() {

		super();
	}

	public PenSegment(Path path) {
		penSegmentPath = new Path(path);
		penStrokeMeasure = new PathMeasure(penSegmentPath, false);
		penStrokeLength = penStrokeMeasure.getLength();
		penStrokeMeasure.getPosTan(0, posStart, mTanStart);
		penStrokeMeasure.getPosTan(penStrokeLength, posEnd, mTanEnd);
		boundingRectF = new RectF();
		path.computeBounds(boundingRectF, false);
		boundingRectHeight = Math.abs(boundingRectF.top - boundingRectF.bottom);
		boundingRectWidth = Math.abs(boundingRectF.left - boundingRectF.right);
	}

	public void addPath(Path srcPath) {

		super.addPath(srcPath);		
		penStrokeMeasure = new PathMeasure(srcPath, false);
		penStrokeLength = penStrokeMeasure.getLength();
		penStrokeMeasure.getPosTan(0, posStart, mTanStart);
		penStrokeMeasure.getPosTan(penStrokeLength, posEnd, mTanEnd);
		boundingRectF = new RectF();	
		srcPath.computeBounds(boundingRectF, false);
		boundingRectHeight = Math.abs(boundingRectF.top - boundingRectF.bottom);
		boundingRectWidth = Math.abs(boundingRectF.left - boundingRectF.right);
	}

	/*
	private double getAbsAngle(double y, double x) {

		double angle = 0.0F;
		// atan2 is positive in the lower right quadrant and negative in upper right quadrant and measured
		// from the horizontal (positive x-axis)
		angle = Math.IEEEremainder(360 + Math.toDegrees(Math.atan2(y, x)), 360);

		return angle;
	}
	 */

	// If the maximum kappa difference is more than 10 times the kappa average then the stroke has more than one segment
	public boolean hasMultipleSegments() {

		return (tempMaxAbsKappaDiff > MAX_ABS_KAPPA_DIFF_THRESHOLD) & (tempMaxAbsKappaDiff > 5.0 * Math.abs(avgKappa));
	}

	// Check to see if the segment length is at least .1 the total length
	private boolean minSegmentLengthCheck(float segLength, float pathLength)
	{
		return (Math.min(pathLength - segLength, segLength) > .1 * pathLength);
	}

	private boolean checkLineAngle (double lineAngle, double angleThreshold) {

		return 		
		(Math.abs(lineAngle) < angleThreshold) ||   // line is in one direction, e.g., left to right, W to E, or NE to SW.
		(Math.abs(180 - Math.abs(lineAngle)) < angleThreshold);  // line is in the other direction, e.g. right to left, E to W, or SW to NE.
	}

	private boolean isStraight(double kappa) {

		return (Math.abs(kappa) < MAX_CURVATURE_FOR_STRAIGHTLINE);
	}

	private boolean isCurved(double kappa) {

//		return !isStraight(kappa) && (tempMaxAbsKappaDiff < MAX_ABS_KAPPA_DIFF_THRESHOLD);
		return !isStraight(kappa);
	}

	// Check to see if the curve's center of gravity (average x and y) is to the left of the line joining its end.
	// That is, it opens to the right, like a regular 'C' or open parenthesis '('.
	private boolean isCOGLeftOfEndLine() {

		float endLineMidX = (posStart[0] + posEnd[0])/2;
		float endLineMidY = (posStart[1] + posEnd[1])/2;
		float gapX = Math.abs(endLineMidX - avgX);

		return ((avgX < endLineMidX) && (Math.abs(avgY - endLineMidY) < .25 * gapX));
	}

	// Check to see if the curve's center of gravity (average x and y) is to the right of the line joining its end.
	// That is, it opens to the left like a backward C or closed parenthesis ')'
	private boolean isCOGRightOfEndLine() {

		float endLineMidX = (posStart[0] + posEnd[0])/2;
		float endLineMidY = (posStart[1] + posEnd[1])/2;
		float gapX = Math.abs(endLineMidX - avgX);

		return ((endLineMidX < avgX) && (Math.abs(avgY - endLineMidY) < .5 * gapX));
//		return ((endLineMidX < avgX));
	}

	private boolean isCOGBelowEndLine() {

		float endLineMidX = (posStart[0] + posEnd[0])/2;
		float endLineMidY = (posStart[1] + posEnd[1])/2;
		float gapY = Math.abs(endLineMidY - avgY);

		return ((avgY > endLineMidY) && (Math.abs(avgX - endLineMidX) < .25 * gapY));
	}

	/*
	// Get the gap between two stroke points (x1, y1) and (x2, y2)
	public static float distanceBetween2Points(float x1, float y1, float x2, float y2) {

		return PointF.length(Math.abs(x1 - x2), Math.abs(y1 - y2));
	}
	 */

	// Check to see if the stroke is a closed one, that is, the gap between the two ends of the stroke
	// is less than one tenth the length of the stroke
	private boolean isClosedStroke() {

		return (PenUtil.distanceBetween2Points(posStart[0], posStart[1], posEnd[0], posEnd[1]) <
				(.1 * penStrokeLength));
	}
	private boolean isHLine() {

		// line can be left to right (W to E) or right to left (E to W)
		return (isStraight(avgKappa) &&
				checkLineAngle(mAvgAngle - HLINE_ANGLE, HLINE_MAX_ANGLE_SPREAD));
	}

	private boolean isBSlash() {

		// line can be NW to SE or SE to NW
		return (isStraight(avgKappa) &&
				checkLineAngle(mAvgAngle - BSLASH_ANGLE, BSLASH_MAX_ANGLE_SPREAD));
	}

	private boolean isVLine() {

		// line can be top to bottom (N to S) or bottom to top (S to N)
		return (isStraight(avgKappa) &&
				checkLineAngle(mAvgAngle - VLINE_ANGLE, VLINE_MAX_ANGLE_SPREAD));
	}

	private boolean isFSlash() {

		// line can be NE to SW or SW to NE
		return (isStraight(avgKappa) &&
				checkLineAngle(mAvgAngle - FSLASH_ANGLE, FSLASH_MAX_ANGLE_SPREAD));
	}

	// Check to see if stroke is a backward C (looks like a more curved version of the left parenthesis ')' )
	private boolean isBC() {

		return (isCurved(avgKappa) && isCOGRightOfEndLine());
	}

	// Check to see if stroke is a regular forward C
	private boolean isFC() {

//		return (isCurved(avgKappa) && isCOGLeftOfEndLine());
		return (isCurved(avgKappa) && isCOGLeftOfEndLine());
	}

	private boolean isCircle() {

		return (isClosedStroke() && isCurved(avgKappa));
	}

	private boolean isU() {

		return (isCurved(avgKappa) && isCOGBelowEndLine());
	}

	private boolean isDot() {

		return (boundingRectWidth < 2) && (boundingRectHeight < 2);
	}

	public void getExtremaPoint() {


	}

	// Compute the curvature at various points of the stroke
	public void getCurvaturePoints(Canvas canvas, Paint textPaint) {

		int numOfSegments = NUM_OF_POINTS_ON_STROKE;
		float posStart[] = {0.0F, 0.0F};
		float tanStart[] = {0.0F, 0.0F};
//		float posEnd[] = {0.0F, 0.0F};
		float posX[] = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
				0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
		float posY[] = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
				0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
		float tanAngle[] = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
				0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}; // tangent angle in degrees
//		float tanEnd[] = {0.0F, 0.0F};
		float kappa[] = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
				0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}; // array of curvature
		float kappaDiff[] = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
				0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}; // array of difference of curvature
		float sumAngle = 0.0F;
		float sumKappa = 0.0F;
		float sumX = 0.0F;
		float sumY = 0.0F;
		float maxAbsKappa = 0.0F;
		float maxAbsKappaDiff = 0.0F;
		float segmentLength = penStrokeLength/numOfSegments;
		for (int i = 0; i < numOfSegments; i++) {

			penStrokeMeasure.getPosTan(i * segmentLength, posStart, tanStart);
			posX[i] = posStart[0];
			posY[i] = posStart[1];
			sumX = sumX + posX[i];
			sumY = sumY + posY[i];
			tanAngle[i] = (float) PenUtil.getAbsAngle(tanStart[1], tanStart[0]);

			if (i > 0) {

				sumAngle = sumAngle + tanAngle[i]; 

				if ((i > 1) && (i < numOfSegments - 1)) {
					// need 3 points to compute kappa so ignore start and end points
					kappa[i-1] = PenUtil.computeCurvatureM2003(posX[i-2], posY[i-2], posX[i-1], posY[i-1], posX[i], posY[i]);
					// kappa[i-1] = computeCurvatureHK2003(posX[i-2], posY[i-2], posX[i-1], posY[i-1], posX[i], posY[i]);
					sumKappa = sumKappa + kappa[i-1];
					if (Math.abs(kappa[i-1])> maxAbsKappa) {

						// Update the x,y coordinates and max absolute kappa value
						mMaxAbsKappaX = posX[i-1];
						mMaxAbsKappaY = posY[i-1];
						maxAbsKappa = Math.abs(kappa[i-1]);
					}

					kappaDiff[i-2] = kappa[i-1] - kappa[i-2]; // get difference in kappa of point and its next neighbor
					if (Math.abs(kappaDiff[i-2])> maxAbsKappaDiff) {

						// Update the x,y coordinates, index, and max absolute kappa difference accordingly
						mMaxAbsKappaDiffX = posX[i-2];
						mMaxAbsKappaDiffY = posY[i-2];
						maxAbsKappaDiffIndex = i-2;
						maxAbsKappaDiff = Math.abs(kappaDiff[i-2]);
					}
				}
			}

			avgX = sumX/numOfSegments;
			avgY = sumY/numOfSegments;
			mAvgAngle = sumAngle/(numOfSegments - 1);
			avgKappa = sumKappa/(numOfSegments - 2);
			mMaxAbsKappa = maxAbsKappa;
			tempMaxAbsKappaDiff = maxAbsKappaDiff;

			pointsX = posX;
			pointsY = posY;
			mTanAngle = tanAngle;
			mKappa = kappa;
			mKappaDiff = kappaDiff; 
		}
		//printSegmentEndPoints(boundingRectF, posX, posY, tanAngle, kappa, canvas, textPaint);

		int histBuckets[] = PenUtil.histogram(kappaDiff);
		for (int i = 0; i < histBuckets.length; i++) {
			mTempHistBucketsStr = mTempHistBucketsStr + ", "  + histBuckets[i];
		}		

	}

	public Vector<PenSegment> getStrokeSegments(Canvas canvas, Paint textPaint) {

		Vector<PenSegment> pSegments = new Vector<PenSegment>();

		getCurvaturePoints(canvas, textPaint); // Get the curvature of each point of the segment


		if (hasMultipleSegments()) {

			int offSet = 5;

			float pathLength1 = penStrokeMeasure.getLength();
			float headLength1 = Math.max(0, maxAbsKappaDiffIndex + 1 - offSet) * (pathLength1/NUM_OF_POINTS_ON_STROKE);
			float tailLength1 = Math.min(pathLength1, maxAbsKappaDiffIndex + offSet) * (pathLength1/NUM_OF_POINTS_ON_STROKE);
			Path path2 = new Path();

			if (penStrokeMeasure.getSegment(headLength1, tailLength1, path2, true)) {
				PenSegment pSegment2 = new PenSegment(path2);
				pSegment2.getCurvaturePoints(canvas, textPaint);

				float pathLength2 = pSegment2.penStrokeMeasure.getLength();
				// Set length of the head (first segment) to a default value - start of path to the first max abs kappa diff point
				float headLength2 = Math.max(0, maxAbsKappaDiffIndex) * (pathLength1/NUM_OF_POINTS_ON_STROKE);

				// See if the max abs kappa diff of the new shorter segment is more than that of the longer segment
				if (pSegment2.tempMaxAbsKappaDiff > tempMaxAbsKappaDiff)
					headLength2 = Math.max(0, pSegment2.maxAbsKappaDiffIndex) * (pathLength2/NUM_OF_POINTS_ON_STROKE);
				// float tailLength2 = Math.min(pathLength1, pSegment2.mMaxAbsKappaDiffIndex + offSet) * (pathLength1/NUM_OF_POINTS_ON_STROKE);

				if (minSegmentLengthCheck(headLength2, pathLength1)) {
					Path path3 = new Path();
					Path path4 = new Path();
					if (penStrokeMeasure.getSegment(0, headLength1 + headLength2, path3, true)
							&& penStrokeMeasure.getSegment(headLength1 + headLength2 + 1, tailLength1, path4, true)) {
						PenSegment pSegment3 = new PenSegment(path3);
						PenSegment pSegment4 = new PenSegment(path4);

						if (Skiggle.sDebugOn) {
							PenUtil.printString(String.format(".(%1$3.1f,%2$3.1f), k:%2$3.1f", pSegment3.posEnd[0], pSegment3.posEnd[1], pSegment3.mKappa),
									pSegment3.posEnd[0], pSegment3.posEnd[1], canvas, textPaint);


						//				PenUtil.printString(String.format("!!%1$3.1f, %2$3.1f, %3$3.1f, %4$3.1f, %5$3.1f", tempMaxAbsKappaDiff, pSegment2.mMaxAbsKappa, tailLength1 - headLength1, pSegment2.mPenStrokeLength, penStrokeLength), 100, 420, boundingRectF, canvas, textPaint);
						pSegment2.printSegmentStats(canvas, textPaint);
						}
						pSegments.addAll(pSegment3.getStrokeSegments(canvas, textPaint));
						pSegments.addAll(pSegment4.getStrokeSegments(canvas, textPaint));						
						return pSegments;
					}

				}

			}

			getExtremaPoint();


		}
		findMatchingCharacter();
		pSegments.add(this);



		return pSegments;
	}

	public void findMatchingCharacter() {

		// Check for the DOT stroke first as it has the length of one pixel
		if (isDot())
			penSegmentCharacter = DOT_CHAR;
		else if (isHLine())
			penSegmentCharacter = HLINE_CHAR;
		else if (isBSlash())
			penSegmentCharacter = BSLASH_CHAR;
		else if (isVLine())
			penSegmentCharacter = VLINE_CHAR;
		else if (isFSlash())
			penSegmentCharacter = FSLASH_CHAR;
		else if (isBC())
			penSegmentCharacter = BC_CHAR;
		else if (isFC())
			penSegmentCharacter = FC_CHAR;
		else if (isCircle())
			penSegmentCharacter = CIRCLE_CHAR;
		else if (isU())
			penSegmentCharacter = U_CHAR;
		else 
			penSegmentCharacter = new Character('?');
	}

	public void printSegmentStats(Canvas canvas, Paint textPaint) {

		/*
		double angle = 0.0;

		// Paint the copy of the stroke with the new pen color
		canvas.drawText(String.format("Len:%1$3.1f, len/(wd + ht):%2$3.1f",
				penStrokeLength, penStrokeLength/(boundingRectWidth + boundingRectHeight)), 
				posStart[0] + 5.0F, posStart[1], textPaint);
		canvas.drawText(String.format("Pos:(%1$3.1f,%2$3.1f); angle:%3$3.1f;", 
				posStart[0], posStart[1],
				getAbsAngle(mTanStart[1], mTanStart[0])),
				posStart[0] + 5.0F, posStart[1] + 15.0F, textPaint);
		canvas.drawText(String.format("ht=%1$3.1f, wd=%2$3.1f, ht/wd=%3$3.1f", 
				boundingRectHeight,
				boundingRectWidth,
				boundingRectHeight/boundingRectWidth),
				posStart[0] + 5.0F, posStart[1] + 30.0F, textPaint);

		PenUtil.printString(penSegmentCharacter.toString(), posEnd[0], posEnd[1], boundingRectF, canvas, textPaint);

		 */
		String msg = String.format("k:%1$3.3f, maxKD:%2$3.3f, x:%3$3.3f, y:%4$3.3f",
				avgKappa, tempMaxAbsKappaDiff, mMaxAbsKappaDiffX, mMaxAbsKappaDiffY);
		
//		int y = canvas.getHeight() - 75;
		int y = (int) Math.floor(canvas.getHeight() * .825);
		PenUtil.printString(mTempHistBucketsStr, 10, y, canvas, textPaint);
//		PenUtil.printString(mTempHistBucketsStr, 10, 420, boundingRectF, canvas, textPaint);
		canvas.drawText(msg, mMaxAbsKappaDiffX , mMaxAbsKappaDiffY, textPaint);
		//Log.i(PenSegment.TAG, msg);
		//printSegmentPointsData();
	}

	public void printSegmentEndPoints(RectF mBoundingRectF, float x[], float y[], float tanAngle[], float kappa[], Canvas canvas, Paint textPaint) {

		int numOfSegments = x.length;
		String msg = "";
		for (int i =0; i < numOfSegments; i++) {
			msg = String.format("%1$d, k:%2$2.4f", i, kappa[i]);
			PenUtil.printString(msg, x[i], y[i], canvas, textPaint);
			//Log.i(PenSegment.TAG, msg);
		}

	}

	public void printSegmentPointsData() {
		String msg = "";
		/*
		Log.i(PenSegment.TAG, String.format(
				"posStart = (%1$2.4f, %2$2.4f), posEnd = (%3$2.4f, %4$2.4f), " +
				"boundingRectF.left = %5$2.4f, boundingRectF.top = %6$2.4f, " +
				"boundingRectF.right = %7$2.4f, boundingRectF.bottom = %8$2.4f, " +
				"boundingRectF.center = (%9$2.4f, %10$2.4f)",
				posStart[0], posStart[1], posEnd[0], posEnd[1],
				boundingRectF.left, boundingRectF.top, boundingRectF.right, boundingRectF.bottom,
				boundingRectF.centerX(), boundingRectF.centerY()));
				*/
		Log.i(PenSegment.TAG, "i, pointsX[i], pointsY[i], mTanAngle[i], mKappa[i], mKappaDiff[i]");
		for (int i =0; i < NUM_OF_POINTS_ON_STROKE; i++) {
			msg = String.format("%1$d, %2$2.4f, %3$2.4f, %4$2.4f, %5$2.4f, %6$2.4f", i,
					pointsX[i],
					pointsY[i],
					mTanAngle[i],
					mKappa[i],
					mKappaDiff[i]);
			Log.i(PenSegment.TAG, msg);
		}
	}

}

