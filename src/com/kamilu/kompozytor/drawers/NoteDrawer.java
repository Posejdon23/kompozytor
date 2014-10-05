package com.kamilu.kompozytor.drawers;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.kamilu.kompozytor.propenums.Accidental;
import com.kamilu.kompozytor.propenums.Metrum;
import com.kamilu.kompozytor.propenums.NotePitch;
import com.kamilu.kompozytor.propenums.NoteValue;

@SuppressWarnings("serial")
public class NoteDrawer implements Drawer {
	private final DrawingCanvas canvas;
	private Map<NotePitch, Accidental> accidsByPitch;

	public NoteDrawer(DrawingCanvas canvas) {
		this.canvas = canvas;
	}

	public void drawNotes(List<Note> notes, Metrum metrum) {
		accidsByPitch = new EnumMap<NotePitch, Accidental>(NotePitch.class);
		for (Note note : notes) {
			NoteValue value = note.getValue();
			Accidental accid = note.getAccidental();
			NotePitch pitch = note.getPitch();
			if (pitch == NotePitch.Pause) {
				drawPause(value);
			} else {
				drawNote(value, accid, pitch);
			}
		}
	}

	private void drawNote(NoteValue value, Accidental accid, NotePitch pitch) {
		String url = value.getNoteUrl();
		drawAccidental(pitch, accid);
		canvas.drawObject(pitch.getHeight(), value.getWidth(),
				value.getHeight(), url);
	}

	private void drawPause(NoteValue value) {
		String url = value.getPauseUrl();
		canvas.drawObject(value.getYAdjust(), value.getPauseWidth(),
				value.getPauseHeight(), url);
	}

	private void drawAccidental(NotePitch pitch, Accidental newAccid) {
		Accidental lastAccid = getLastPitch(pitch);
		if (newAccid == lastAccid || newAccid == null) {
			return;
		} else {
			canvas.drawObject(pitch.getHeight(), newAccid.getWidth(),
					newAccid.getHeight(), newAccid.getUrl());
		}
		accidsByPitch.put(pitch, newAccid);
	}

	private Accidental getLastPitch(NotePitch pitch) {
		Accidental accidental = accidsByPitch.get(pitch);
		if (accidental == null) {
			return Accidental.Natural;
		}
		return accidental;
	}
}
