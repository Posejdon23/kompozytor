package com.kamilu.kompozytor.drawers;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NoteField implements Serializable {
	private float start, end, yCenter;

	public NoteField(float start, float end, float yCenter) {
		this.start = start;
		this.end = end;
		this.yCenter = yCenter;
	}

	public float getStart() {
		return start;
	}

	public void setStart(float start) {
		this.start = start;
	}

	public void setEnd(float end) {
		this.end = end;
	}

	public float getEnd() {
		return end;
	}

	public float getYCenter() {
		return yCenter;
	}

	public void setYCenter(float yCenter) {
		this.yCenter = yCenter;
	}

	@Override
	public String toString() {
		return "start: " + start + ", end: " + end + ", yCenter: " + yCenter;
	}
}
