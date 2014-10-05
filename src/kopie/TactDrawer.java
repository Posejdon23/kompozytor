package kopie;

import static com.kamilu.kompozytor.drawers.DrawCursor.*;
import static com.kamilu.kompozytor.drawers.NoteDrawer.*;

import java.util.ArrayList;
import java.util.List;

import com.kamilu.kompozytor.entities.Tact;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.kamilu.kompozytor.propenums.Clef;
import com.kamilu.kompozytor.propenums.Metrum;
import com.kamilu.kompozytor.propenums.Tonation;

@SuppressWarnings("serial")
public class TactDrawer implements Drawer {
	// public static final float TAKT_HEIGHT = 25;
	public static final int TAKT_HEIGHT = 40;
	public static final int LINE_WIDTH = 1;
	private final DrawingCanvas canvas;
	// private final DrawCursor cursor;
	private final NoteDrawer noteDrawer;
	private List<TactField> tactFields;

	public TactDrawer(DrawingCanvas canvas) {
		// this.cursor = cursor;
		this.canvas = canvas;
		noteDrawer = new NoteDrawer(canvas);
	}

	public void drawTacts(List<Tact> tacts) {
		tactFields = new ArrayList<TactField>();
		Clef lastClef = null;
		Tonation lastTonation = null;
		Metrum lastMetrum = null;
		int lastTempo = 0;
		boolean isFirst = true;
		for (Tact tact : tacts) {
			if (!tact.isMarkedToRemove()) {
				Clef currentClef = tact.getClef();
				Tonation currentTonation = tact.getTonation();
				Metrum currentMetrum = tact.getMetrum();
				int newTempo = tact.getTempo();
				int number = tact.getNumber();
				// float xPos = cursor.getXPos();
				// float yPos = cursor.getYPos();
				float xPos = 0;
				float yPos = 0;
				drawClef(lastClef, currentClef);
				drawTempo(lastTempo, newTempo);
				drawTonation(lastTonation, currentTonation);
				drawMetrum(lastMetrum, currentMetrum);
				TactField field = new TactField(xPos, -1, yPos, number);
				noteDrawer.drawNotes(tact.getNotes(), currentMetrum);
				field.setNoteFields(noteDrawer.getFields());
				// cursor.moveRight(NOTE_SPACE);
				drawLineAndCounter(number, field, isFirst);
				isFirst = false;
				lastClef = currentClef;
				lastTonation = currentTonation;
				lastMetrum = currentMetrum;
				lastTempo = newTempo;
			}
		}
	}

	private void drawTempo(int currentTempo, int newTempo) {
		if (currentTempo == 0 || !(currentTempo == newTempo)) {
			// canvas.fillText("t = " + newTempo, cursor.getXPos(),
			// cursor.getYPos() - TAKT_HEIGHT, 40);
		}
	}

	private void drawClef(Clef currentClef, Clef newClef) {
		if (currentClef == null || !currentClef.equals(newClef)) {
			canvas.drawObject(currentClef.getUrl(), currentClef.getHeight());
			// canvas.drawImage1(newClef.getUrl(), cursor.getXPos(),
			// cursor.getYPos() - newClef.getHeight());
			// cursor.moveRight(newClef.getHeight());
		}
	}

	private void drawTonation(Tonation currentTonation, Tonation newTonation) {
		if (currentTonation == null || !currentTonation.equals(newTonation)) {
			// canvas.drawImage1(newTonation.getUrl(), cursor.getXPos(),
			// cursor.getYPos() - newTonation.getYAdjust());
			// cursor.moveRight(newTonation.getXAdjust());
		}
	}

	private void drawMetrum(Metrum currentMetrum, Metrum newMetrum) {
		if (currentMetrum == null || !currentMetrum.equals(newMetrum)) {
			// canvas.drawImage1(newMetrum.getUrl(), cursor.getXPos(),
			// cursor.getYPos() - 10);
			// cursor.moveRight(50);
		}
		currentMetrum = newMetrum;
	}

	private void drawLineAndCounter(int number, TactField field, boolean isFirst) {
		// canvas.fillRect(cursor.getXPos(), cursor.getYPos() - (TAKT_HEIGHT /
		// 2),
		canvas.drawTactBar();
		// LINE_WIDTH, TAKT_HEIGHT);
		// field.setEnd(cursor.getXPos());
		tactFields.add(field);
		// canvas.fillText("" + (number + 1), cursor.getXPos(), cursor.getYPos()
		// - TAKT_HEIGHT + 15, 10);
	}

	public int getSelectedTactIndex() {
		// TactField tactField = cursor.getCurrTactField();
		TactField tactField = null;
		if (tactField == null) {
			return -1;
		}
		return tactField.getOrderNumber();
	}

	public TactField getSelectedTactField(float mouseX, float mouseY) {
		for (TactField field : tactFields) {
			float start = field.getStart();
			float end = field.getEnd();
			float yCenter = field.getYCenter();
			float yDown = yCenter + ROWS_SPACE / 2;
			float yUp = yCenter - ROWS_SPACE / 2;
			if (start >= end) {
				if ((mouseX >= start && mouseY <= yUp && mouseY >= yDown) || //
						(mouseX <= end && mouseY <= yUp + ROWS_SPACE && mouseY >= yDown
								+ ROWS_SPACE)) {
					return field;
				}
			} else {
				if (mouseX >= start && mouseX <= end && mouseY <= yDown
						&& mouseY >= yUp) {
					return field;
				}
			}
		}
		return null;
	}

	public int getSelectedNoteIndex() {
		// NoteField noteField = cursor.getCurrNoteField();
		NoteField noteField = null;
		if (noteField == null) {
			return -1;
		}
		return noteField.getNumber();
	}

	public NoteField getSelectedNoteField(float mouseX, float mouseY) {

		TactField tactField = getSelectedTactField(mouseX, mouseY);
		if (tactField == null) {
			return null;
		}
		List<NoteField> noteFields = tactField.getNoteFields();
		if (noteFields == null || noteFields.isEmpty()) {
			return null;
		}
		for (NoteField field : noteFields) {
			float start = field.getStart();
			float end = field.getEnd();
			float yCenter = field.getYCenter();
			float yDown = yCenter + ROWS_SPACE / 2;
			float yUp = yCenter - ROWS_SPACE / 2;
			if (start >= end) {
				if ((mouseX >= start && mouseY <= yUp && mouseY >= yDown) || //
						(mouseX <= end && mouseY <= yUp + ROWS_SPACE && mouseY >= yDown
								+ ROWS_SPACE)) {
					return field;
				}
			} else {
				if (mouseX >= start && mouseX <= end && mouseY <= yDown
						&& mouseY >= yUp) {
					return field;
				}
			}
		}
		return null;
	}
}
