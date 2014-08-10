package com.kamilu.kompozytor.drawers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class TactField implements Serializable {
	private float start, end, yCenter;
	private Map<NoteField, Integer> noteFields;

	public TactField(float start, float end, float yCenter) {
		noteFields = new HashMap<>();
		this.start = start;
		this.end = end;
		this.yCenter = yCenter;
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

	public void addNoteFields(Map<NoteField, Integer> noteFields) {
		if (noteFields != null && !noteFields.isEmpty())
			this.noteFields.putAll(noteFields);
	}

	public Map<NoteField, Integer> getNoteFields() {
		return noteFields;
	}

	@Override
	public String toString() {
		return "start: " + start + ", end: " + end + ", yCenter: " + yCenter;
	}
}
