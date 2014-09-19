package com.kamilu.kompozytor.entities;

import java.util.ArrayList;
import java.util.List;

import com.kamilu.kompozytor.propenums.NoteValue;

public class NoteGroup {
	private List<Note> notes;
	private double length, currentLength;

	public NoteGroup(int length) {
		this.length = length;
		currentLength = 0;
		notes = new ArrayList<Note>();
	}

	public void addNote(Note note) {
		notes.add(note);
		NoteValue value = note.getValue();
		currentLength += value.getValue();
		if (note.isDot()) {
			currentLength += value.getValue() / 2;
		}
	}

	public double getCurrentLength() {
		return currentLength;
	}

	public boolean isFull() {
		return currentLength >= length;
	}

	public int getNoteCount() {
		return notes.size();
	}

	public List<Note> getNotes() {
		return notes;
	}

	public boolean isGroupOf8() {
		if (notes.size() < 2) {
			return false;
		}
		return notes.get(0).getValue() == NoteValue.EighthNote
				&& notes.get(1).getValue() == NoteValue.EighthNote;
	}
}
