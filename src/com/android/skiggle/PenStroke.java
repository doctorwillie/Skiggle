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

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Vector;

//PenStroke is a continuous path (between pen down and pen up) drawn by the pen.
public class PenStroke extends Path {
	
	// TAG for logging debugging info
	//private static final String TAG = "MyPenStroke";

	// Members
	public Path penStrokePath;
	public PathMeasure penStrokeMeasure;
	public float penStrokeLength;
	public RectF boundingRectF;
	public float boundingRectHeight;
	public float boundingRectWidth;
	public float posStart[] = {0.0F, 0.0F};
	private float mTanStart[] = {0.0F, 0.0F};
	public float posEnd[] = {0.0F, 0.0F};
	private float mTanEnd[] = {0.0F, 0.0F};
	//	private float mAvgAngle = 0.0F;
	//	private float avgKappa = 0.0F; // a measure of average curvature
	public float avgX = 0.0F; // average X-coord of points on the stroke
	public float avgY = 0.0F; // average Y-coord of points on the stroke
	//	private float mMaxAbsKappaX = 0.0F; // x-coord of the max absolute Kappa value
	//	private float mMaxAbsKappaY = 0.0F; // y-coord of the max absolute Kappa value
	//	private float mMaxAbsKappa = 0.0F; //temp
	//	private float mMaxAbsKappaDiffX = 0.0F; // x-coord of the max absolute Kappa diff value
	//	private float mMaxAbsKappaDiffY = 0.0F; // y-coord of the max absolute Kappa diff value
	//	private float tempMaxAbsKappaDiff = 0.0F; // temp
	//	private String mHistBucketsStr = ""; // temp
	//  public Character mPpenStrokeCharacter;

	public PenStroke(Path path) {
		penStrokePath = new Path(path);
	}
	
	public void addPath(Path srcPath) {
		super.addPath(srcPath);
		
		penStrokePath = new Path(srcPath);
		penStrokeMeasure = new PathMeasure(this, false);
		penStrokeLength = penStrokeMeasure.getLength();
		penStrokeMeasure.getPosTan(0, posStart, mTanStart);
		penStrokeMeasure.getPosTan(penStrokeLength, posEnd, mTanEnd);
		boundingRectF = new RectF();	
		this.computeBounds(boundingRectF, false);
		boundingRectHeight = Math.abs(boundingRectF.top - boundingRectF.bottom);
		boundingRectWidth = Math.abs(boundingRectF.left - boundingRectF.right);

	}

	public Vector<PenSegment> segmentStroke(Canvas canvas, Paint textPaint) {
		// Vector<PenSegment> pSegments = new Vector<PenSegment>();
		PenSegment pSegment1 = new PenSegment(this.penStrokePath);
		return pSegment1.getStrokeSegments(canvas, textPaint);
	}
	

	
	/*	
	public void printPenStrokeStatsOnScreen(Boxview sBoxView, Canvas canvas, Paint strokePaint, Paint textPaint) {
		double angle = 0.0;

		PenUtil.printString(mPpenStrokeCharacter.toString(), posEnd[0], posEnd[1], boundingRectF, canvas, textPaint);
		PenUtil.printString(sBoxView.mPenCharacter.mPenCharacter.toString(), posEnd[0], posEnd[1], boundingRectF, canvas, textPaint);


	}
	 */

}
