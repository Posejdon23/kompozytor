package com.kamilu.kompozytor.drawers;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NoteField implements Serializable {
	private float start, end, yCenter;
	private int orderNumber;

	public NoteField(float start, float end, float yCenter, int orderNumber) {
		this.start = start;
		this.end = end;
		this.yCenter = yCenter;
		this.orderNumber = orderNumber;
	}

	public int getNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
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
