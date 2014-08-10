package com.kamilu.kompozytor.drawers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.hezamu.canvas.Canvas;

import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.propenums.NoteValue;
import com.kamilu.kompozytor.propenums.NotePitch;
import com.kamilu.kompozytor.propenums.Accidental;

@SuppressWarnings("serial")
public class NoteDrawer implements Drawer {
	public static final int NOTE_SPACE = 20;
	private final Canvas canvas;
	private final DrawCursor cursor;
	private Map<NoteField, Integer> coords;

	public NoteDrawer(Canvas canvas, DrawCursor cursor) {
		coords = new HashMap<>();
		this.canvas = canvas;
		this.cursor = cursor;
	}

	public void drawNotes(List<Note> notes) {
		for (Note note : notes) {
			cursor.moveRight(NOTE_SPACE);
			float xPos = cursor.getXPos();
			float yPos = cursor.getYPos();
			int number = note.getOrderNumber();
			NoteField field = new NoteField(xPos, -1, yPos);
			coords.put(field, number);
			NoteValue noteValue = note.getValue();
			Accidental accid = note.getAccidental();
			NotePitch pitch = note.getPitch();
			if (!accid.equals(Accidental.Brak)) {
				canvas.drawImage1(accid.getUrl(), cursor.getXPos(),
						cursor.getYPos() - 18.5d + pitch.getYPos());
				canvas.fill();
				cursor.moveRight(NOTE_SPACE - 10);
			}
			String url = null;
			if (pitch.equals(NotePitch.Rest)) {
				url = noteValue.getPauseUrl();
			} else {
				url = noteValue.getNoteUrl();
			}
			canvas.drawImage1(url, cursor.getXPos(), cursor.getYPos() - 37
					+ pitch.getYPos());
			canvas.fill();
			cursor.moveRight(NOTE_SPACE);
			field.setEnd(cursor.getXPos());
		}
	}

	public Map<NoteField, Integer> getCoords() {
		return coords;
	}

}
