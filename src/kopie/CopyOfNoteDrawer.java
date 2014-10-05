package kopie;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.kamilu.kompozytor.drawers.Drawer;
import com.kamilu.kompozytor.drawers.NoteField;
import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.entities.NoteGroup;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.kamilu.kompozytor.propenums.Accidental;
import com.kamilu.kompozytor.propenums.Metrum;
import com.kamilu.kompozytor.propenums.NotePitch;
import com.kamilu.kompozytor.propenums.NoteValue;

@SuppressWarnings("serial")
public class CopyOfNoteDrawer implements Drawer {
	public static final int NOTE_SPACE = 18;
	private final DrawingCanvas canvas;
	// private final DrawCursor cursor;
	private List<NoteField> noteFields;
	private Map<NotePitch, Accidental> accidsByPitch;

	public CopyOfNoteDrawer(DrawingCanvas canvas) {
		this.canvas = canvas;
		// this.cursor = cursor;
	}

	public void drawNotes(List<Note> notes, Metrum metrum) {
		noteFields = new ArrayList<NoteField>();
		accidsByPitch = new EnumMap<NotePitch, Accidental>(NotePitch.class);
		// List<NoteGroup> notesGroup = divideToGroupOfNotes(notes, metrum);
		// for (NoteGroup group : notesGroup) {
		// if (group.isGroupOf8()) {
		// drawGroupOf8(group);
		// } else {
		for (Note note : notes) {
			// cursor.moveRight(NOTE_SPACE);
			// float xPos = cursor.getXPos();
			// float yPos = cursor.getYPos();
			float xPos = 0;
			float yPos = 0;
//			int number = note.getNumber();
//			NoteField field = new NoteField(xPos, -1, yPos, number);
			NoteValue noteValue = note.getValue();
			NotePitch pitch = note.getPitch();
			// Accidental accid = note.getAccidental();
			Accidental accid = pitch.getAccid();
			String noteUrl = null;
			if (pitch == NotePitch.Pause) {
				noteUrl = noteValue.getPauseUrl();
			} else {
				drawAccidental(pitch, accid);
				noteUrl = noteValue.getNoteUrl();
			}
			canvas.drawObject(noteUrl, pitch.getHeight());
			// canvas.drawImage1(noteUrl, cursor.getXPos(),
			// cursor.getYPos() - 37 + pitch.getHeight());
			// cursor.moveRight(NOTE_SPACE);
			// field.setEnd(cursor.getXPos());
//			noteFields.add(field);
		}
		// }
		// }
	}

	// FIXME duplikacja
	private void drawGroupOf8(NoteGroup group) {
		float x1 = 0.0f, y1 = 0.0f, x2 = 0.0f, y2 = 0.0f;
		List<Note> notes = group.getNotes();
		for (Note note : notes) {
			// cursor.moveRight(NOTE_SPACE);
			// float xPos = cursor.getXPos();
			// float yPos = cursor.getYPos();
			float xPos = 0;
			float yPos = 0;
			int number = note.getNumber();
			NoteField field = new NoteField(xPos, -1, yPos, number);
			NoteValue noteValue = note.getValue();
			NotePitch pitch = note.getPitch();
			Accidental accid = pitch.getAccid();
			// float upperLeftX = cursor.getXPos();
			// float upperLeftY = cursor.getYPos() - 37 + pitch.getHeight();
			float upperLeftX = 0;
			float upperLeftY = 0;
			if (notes.indexOf(note) == 0) {
				x1 = upperLeftX + 13;
				y1 = upperLeftY;
			}
			if (notes.indexOf(note) == group.getNoteCount() - 1) {
				x2 = upperLeftX + 13;
				y2 = upperLeftY;
			}
			String noteUrl = null;
			if (pitch == NotePitch.Pause) {
				noteUrl = noteValue.getPauseUrl();
			} else {
				drawAccidental(pitch, accid);
				noteUrl = NoteValue.QuarterNote.getNoteUrl();
			}
			canvas.drawObject(noteUrl, pitch.getHeight());
			// canvas.drawImage1(noteUrl, upperLeftX, upperLeftY);
			// cursor.moveRight(NOTE_SPACE);
			field.setEnd(upperLeftX);
			noteFields.add(field);
		}
		drawNoteBar(x1, y1, x2, y2);
	}

	private void drawNoteBar(float x1, float y1, float x2, float y2) {
		// canvas.moveTo(x1, y1);
		// canvas.lineTo(x2, y2);
		// canvas.setLineWidth(3);
		// canvas.stroke();
	}

	public List<NoteGroup> divideToGroupOfNotes(List<Note> notes, Metrum metrum) {
		List<NoteGroup> notesGroup = new ArrayList<NoteGroup>();
		int down = metrum.getDown();
		NoteGroup group = new NoteGroup(100 / down);
		NoteValue lastNoteValue = null;
		for (Note note : notes) {
			if (!note.isMarkToRemove()) {
				NoteValue currentNoteValue = note.getValue();
				if (!group.isFull()
						|| ((lastNoteValue == currentNoteValue) && !group
								.isFull())) {
					group.addNote(note);
				} else {
					notesGroup.add(group);
					group = new NoteGroup(100 / down);
					group.addNote(note);
				}
				lastNoteValue = currentNoteValue;
			}
		}
		notesGroup.add(group);
		return notesGroup;
	}

	private void drawAccidental(NotePitch pitch, Accidental newAccid) {
		Accidental lastAccid = getLastPitch(pitch);
		if (newAccid == lastAccid || newAccid == null) {
			return;
		} else {
			// canvas.drawImage1(newAccid.getUrl(), cursor.getXPos(),
			// cursor.getYPos() - 18.5d + pitch.getHeight());
			// cursor.moveRight(NOTE_SPACE - 10);
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

	public List<NoteField> getFields() {
		return noteFields;
	}

}
