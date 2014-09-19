package com.kamilu.kompozytor.drawers;

import com.kamilu.kompozytor.entities.Voice;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;

@SuppressWarnings("serial")
public class VoiceDrawer implements Drawer {

	private final DrawingCanvas canvas;
	private final DrawCursor cursor;

	public VoiceDrawer(DrawCursor cursor) {
		this.cursor = cursor;
		this.canvas = cursor.getCanvas();
	}

	public void drawVoice(Voice voice) {
		cursor.drawStave();
		// canvas.fillText(voice.getInstrument().name() + voice.getNumber(), 50,
		// 50, 100);
	}
}
