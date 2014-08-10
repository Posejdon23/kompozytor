package com.kamilu.kompozytor.drawers;

import static com.kamilu.kompozytor.drawers.DrawCursor.*;
import static com.kamilu.kompozytor.drawers.NoteDrawer.*;

import java.util.*;

import org.vaadin.hezamu.canvas.Canvas;

import com.kamilu.kompozytor.entities.Tact;
import com.kamilu.kompozytor.propenums.Clef;
import com.kamilu.kompozytor.propenums.Metrum;
import com.kamilu.kompozytor.propenums.Tonation;

@SuppressWarnings("serial")
public class TactDrawer implements Drawer {
	// public static final float TAKT_HEIGHT = 25;
	public static final double TAKT_HEIGHT = 40;
	public static final double LINE_WIDTH = 1;
	private final Canvas canvas;
	private final DrawCursor cursor;
	private final NoteDrawer nutaDrawer;
	private Map<TactField, Integer> coords;

	public TactDrawer(Canvas canvas, DrawCursor cursor) {
		this.canvas = canvas;
		this.cursor = cursor;
		coords = new HashMap<TactField, Integer>();
		nutaDrawer = new NoteDrawer(canvas, cursor);
	}

	public void drawTacts(List<Tact> tacts) {
		Clef currentClef = null;
		Tonation currentTonation = null;
		Metrum currentMetrum = null;
		int currentTempo = 0;
		for (Tact tact : tacts) {
			Clef newClef = tact.getClef();
			Tonation newTonation = tact.getTonation();
			Metrum newMetrum = tact.getMetrum();
			int newTempo = tact.getTempo();
			int number = tact.getOrderNumber();
			float xPos = cursor.getXPos();
			float yPos = cursor.getYPos();

			drawClef(currentClef, newClef);
			drawTempo(currentTempo, newTempo);
			drawTonation(currentTonation, newTonation);
			drawMetrum(currentMetrum, newMetrum);
			TactField field = new TactField(xPos, -1, yPos);
			coords.put(field, number);
			nutaDrawer.drawNotes(tact.getNotes());
			field.addNoteFields(nutaDrawer.getCoords());
			cursor.moveRight(NOTE_SPACE);
			drawLineAndCounter(number, field, tacts.get(0).equals(tact));
			currentClef = newClef;
			currentTonation = newTonation;
			currentMetrum = newMetrum;
			currentTempo = newTempo;
		}
	}

	private void drawTempo(int currentTempo, int newTempo) {
		if (currentTempo == 0 || !(currentTempo == newTempo)) {
			canvas.fillText("t = " + newTempo, cursor.getXPos(),
					cursor.getYPos() - TAKT_HEIGHT, 40);
			canvas.fill();
		}
	}

	private void drawClef(Clef currentClef, Clef newClef) {
		if (currentClef == null || !currentClef.equals(newClef)) {
			canvas.drawImage1(newClef.getUrl(), cursor.getXPos(),
					cursor.getYPos() - newClef.getWysokosc());
			canvas.fill();
			cursor.moveRight(newClef.getWysokosc());
		}
	}

	private void drawTonation(Tonation currentTonation, Tonation newTonation) {
		if (currentTonation == null || !currentTonation.equals(newTonation)) {
			canvas.drawImage1(newTonation.getUrl(), cursor.getXPos(),
					cursor.getYPos() - newTonation.getYAdjust());
			canvas.fill();
			cursor.moveRight(newTonation.getXAdjust());
		}
	}

	private void drawMetrum(Metrum currentMetrum, Metrum newMetrum) {
		if (currentMetrum == null || !currentMetrum.equals(newMetrum)) {
			canvas.drawImage1(newMetrum.getUrl(), cursor.getXPos(),
					cursor.getYPos() - 10);
			canvas.fill();
			cursor.moveRight(50);
		}
		currentMetrum = newMetrum;
	}

	private void drawLineAndCounter(int number, TactField field, boolean isFirst) {
		canvas.fillRect(cursor.getXPos(), cursor.getYPos() - (TAKT_HEIGHT / 2),
				LINE_WIDTH, TAKT_HEIGHT);
		field.setEnd(cursor.getXPos());
		canvas.fillText("" + number, cursor.getXPos(), cursor.getYPos()
				- TAKT_HEIGHT + 15, 10);
	}

	public TactField getTactField(float mouseX, float mouseY) {
		for (TactField field : coords.keySet()) {
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

	public int getTactNumber(float xPos, float yPos) {
		for (TactField tactRect : coords.keySet()) {
			float start = tactRect.getStart();
			float end = tactRect.getEnd();
			float yCenter = tactRect.getYCenter();
			float down = yCenter + ROWS_SPACE / 2;
			float up = yCenter - ROWS_SPACE / 2;
			if (start >= end) {
				if ((xPos >= start && ((yPos - ROWS_SPACE) <= down) && ((yPos - ROWS_SPACE) >= up))
						|| (xPos <= end && ((yPos) <= down) && ((yPos) >= up))) {
					return coords.get(tactRect).intValue();
				}
			} else {
				if (xPos >= start && xPos <= end && yPos <= down && yPos >= up) {
					return coords.get(tactRect).intValue();
				}
			}
		}
		return -1;
	}

	public int getNoteNumber(int mouseX, int mouseY) {
		TactField tactField = getTactField(mouseX, mouseY);
		if (tactField == null) {
			return -1;
		}
		Map<NoteField, Integer> noteFields = tactField.getNoteFields();
		for (NoteField noteField : noteFields.keySet()) {
			float start = noteField.getStart();
			float end = noteField.getEnd();
			if (mouseX >= start && mouseX <= end) {
				return noteFields.get(noteField);
			}
		}
		return -1;
	}

	public NoteField getNoteField(float mouseX, float mouseY) {

		TactField tactField = getTactField(mouseX, mouseY);
		if (tactField == null) {
			return null;
		}
		Map<NoteField, Integer> noteFields = tactField.getNoteFields();
		if (noteFields == null) {
			return null;
		}
		for (NoteField field : noteFields.keySet()) {
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
