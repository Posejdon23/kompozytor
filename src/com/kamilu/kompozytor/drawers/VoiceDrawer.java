package com.kamilu.kompozytor.drawers;

import com.kamilu.kompozytor.entities.Voice;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;

@SuppressWarnings("serial")
public class VoiceDrawer implements Drawer {

	private final DrawingCanvas canvas;

	public VoiceDrawer(DrawingCanvas canvas) {
		this.canvas = canvas;
	}

	public void drawVoice(Voice voice) {
		canvas.drawStave();
		// TODO rysowanie tekstu
		// canvas.fillText(voice.getInstrument().name() + voice.getNumber(), 50,
		// 50, 100);
	}
}
