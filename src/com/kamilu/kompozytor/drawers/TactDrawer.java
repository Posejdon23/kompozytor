package com.kamilu.kompozytor.drawers;

import java.util.List;

import com.kamilu.kompozytor.entities.Tact;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.kamilu.kompozytor.propenums.Clef;
import com.kamilu.kompozytor.propenums.Metrum;
import com.kamilu.kompozytor.propenums.Tonation;

@SuppressWarnings("serial")
public class TactDrawer implements Drawer {
	private final DrawingCanvas canvas;
	private final NoteDrawer noteDrawer;

	public TactDrawer(DrawingCanvas canvas) {
		this.canvas = canvas;
		noteDrawer = new NoteDrawer(canvas);
	}

	public void drawTacts(List<Tact> tacts) {
		Clef lastClef = null;
		Tonation lastTonation = null;
		Metrum lastMetrum = null;
		int lastTempo = 0;
		for (Tact tact : tacts) {
			if (tact.isMarkedToRemove()) {
				continue;
			}
			Clef clef = tact.getClef();
			Tonation tonation = tact.getTonation();
			Metrum metrum = tact.getMetrum();
			int tempo = tact.getTempo();
			int number = tact.getNumber();
			drawClef(clef, lastClef);
			drawTempo(lastTempo, tempo);
			drawTonation(lastTonation, tonation);
			drawMetrum(lastMetrum, metrum);
			noteDrawer.drawNotes(tact.getNotes(), metrum);
			drawLineAndCounter(number);
			lastClef = clef;
			lastTonation = tonation;
			lastMetrum = metrum;
			lastTempo = tempo;
		}

	}

	private void drawTempo(int currentTempo, int newTempo) {
		if (currentTempo == 0 || !(currentTempo == newTempo)) {
			// TODO rysowanie tekstu
			// canvas.fillText("t = " + newTempo, cursor.getXPos(),
			// cursor.getYPos() - TAKT_HEIGHT, 40);
		}
	}

	private void drawClef(Clef clef, Clef lastClef) {
		if (lastClef == null && clef != null && !clef.equals(lastClef)) {
			canvas.drawObject(-20, clef.getWidth(), clef.getHeight(),
					clef.getUrl());
		}
	}

	private void drawTonation(Tonation lastTonation, Tonation tonation) {
		if (lastTonation == null || !lastTonation.equals(tonation)) {
			canvas.drawObject(0, tonation.getWidth(), tonation.getHeight(),
					tonation.getUrl());
		}
	}

	private void drawMetrum(Metrum currentMetrum, Metrum metrum) {
		if (currentMetrum == null || !currentMetrum.equals(metrum)) {
			canvas.drawObject(-10, metrum.getWidth(), metrum.getHeight(),
					metrum.getUrl());
		}
	}

	private void drawLineAndCounter(int number) {
		canvas.drawTactBar(number);
	}
}
