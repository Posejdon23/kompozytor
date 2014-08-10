package com.kamilu.kompozytor.drawers;

import static com.vaadin.server.Sizeable.Unit.*;
import static com.vaadin.ui.Notification.Type.*;

import java.io.Serializable;

import org.vaadin.hezamu.canvas.Canvas;
import org.vaadin.hezamu.canvas.Canvas.CanvasClickListener;

import com.kamilu.kompozytor.entities.Song;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
public class SongDrawer extends HorizontalLayout implements Drawer {

	public static final float WIDTH = 850.0f;
	public static final float HEIGHT = 600.0f;
	private DrawCursor cursor;
	private VoiceDrawer voiceDrawer;
	private final Canvas canvas;

	private Song song;

	public SongDrawer() {
		canvas = new Canvas();
		prepareCanvas();
		cursor = new DrawCursor(canvas);
		voiceDrawer = new VoiceDrawer(canvas, cursor);
	}

	private void prepareCanvas() {
		removeAllComponents();
		canvas.setHeight(HEIGHT, PIXELS);
		canvas.setWidth(WIDTH, PIXELS);
		addComponents(canvas);
		canvas.addListener(new CanvasClickListenerImplementation());
	}

	public void drawSong(Song song) {
		this.song = song;
		canvas.clear();
		cursor.reset();
		voiceDrawer = new VoiceDrawer(canvas, cursor);
		if (song == null) {
			Notification.show("Wrong song", WARNING_MESSAGE);
			return;
		}
		cursor.drawStave();
		canvas.fillText(song.getName(), 400, 10, 450);
		canvas.fill();
		canvas.fillText("Autor: " + song.getAuthor(), 700, 10, 150);
		canvas.fill();
		voiceDrawer.drawVoices(song.getVoices());
	}

	public int getTactNumberFromRect(float xPos, float yPos) {
		return voiceDrawer.getTactNumber(xPos, yPos);
	}

	public int getSelectedTact() {
		return voiceDrawer
				.getTactNumber(cursor.getMouseX(), cursor.getMouseY());
	}

	public int getSelectedNote() {
		return voiceDrawer
				.getNoteNumber(cursor.getMouseX(), cursor.getMouseY());
	}

	public void setCurrentCursorPosition(int mouseX, int mouseY) {
		TactField selectedTField = voiceDrawer.getTactField(mouseX, mouseY);
		NoteField noteField = voiceDrawer.getNoteField(mouseX, mouseY);
		if (selectedTField != null) {
			cursor.drawTactSelectBox(selectedTField, mouseX, mouseY);
			cursor.drawNoteSelectBox(noteField, mouseX, mouseY);
		}
	}

	private final class CanvasClickListenerImplementation implements
			CanvasClickListener, Serializable {
		@Override
		public void clicked(MouseEventDetails med) {
			drawSong(song);
			int relX = med.getRelativeX();
			int relY = med.getRelativeY();
			setCurrentCursorPosition(relX, relY);
			int selectedTact = getSelectedTact();
			Notification.show("Tact: " + selectedTact + ", Note: "
					+ getSelectedNote(), TRAY_NOTIFICATION);
			// Notification.show("" + getTactNumberFromRect(relX, relY),
			// TRAY_NOTIFICATION);

		}
	}
}
