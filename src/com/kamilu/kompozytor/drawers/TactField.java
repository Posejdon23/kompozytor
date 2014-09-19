package com.kamilu.kompozytor.drawers;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class TactField implements Serializable {
	private float start, end, yCenter;
	private List<NoteField> noteFields;
	private int orderNumber;

	public TactField(float start, float end, float yCenter, int orderNumber) {
		this.start = start;
		this.end = end;
		this.yCenter = yCenter;
		this.orderNumber = orderNumber;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public float getYCenter() {
		return yCenter;
	}

	public void setYCenter(float yCenter) {
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

	public void setNoteFields(List<NoteField> noteFields) {
		if (noteFields != null && !noteFields.isEmpty())
			this.noteFields = noteFields;
	}

	public List<NoteField> getNoteFields() {
		return noteFields;
	}

	@Override
	public String toString() {
		return "start: " + start + ", end: " + end + ", yCenter: " + yCenter;
	}
}
