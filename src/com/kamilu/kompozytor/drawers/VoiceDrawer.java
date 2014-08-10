package com.kamilu.kompozytor.drawers;

import java.util.List;

import org.vaadin.hezamu.canvas.Canvas;

import com.kamilu.kompozytor.entities.Voice;

@SuppressWarnings("serial")
public class VoiceDrawer implements Drawer {

	private final Canvas canvas;
	private final TactDrawer tactDrawer;
	private final DrawCursor cursor;

	public VoiceDrawer(Canvas canvas, DrawCursor cursor) {
		this.canvas = canvas;
		this.cursor = cursor;
		tactDrawer = new TactDrawer(canvas, cursor);
	}

	public void drawVoices(List<Voice> voices) {

		for (Voice track : voices) {
			tactDrawer.drawTacts(track.getTacts());
		}
	}

	public int getTactNumber(float mouseX, float mouseY) {
		return tactDrawer.getTactNumber(mouseX, mouseY);
	}

	public TactField getTactField(float xPos, float yPos) {
		return tactDrawer.getTactField(xPos, yPos);
	}

	public int getNoteNumber(int mouseX, int mouseY) {
		return tactDrawer.getNoteNumber(mouseX, mouseY);
	}

	public NoteField getNoteField(float xPos, float yPos) {
		return tactDrawer.getNoteField(xPos, yPos);
	}
}
