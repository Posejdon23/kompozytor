package com.kamilu.kompozytor.drawers;

import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class SongDrawer implements Drawer {

	private DrawingCanvas canvas;

	public SongDrawer(DrawingCanvas canvas) {
		this.canvas = canvas;
	}

	public void drawSong(Song song) {
		// TODO rysowanie tekstu
		// canvas.fillText(song.getName(), 400, 40, 450);
		// canvas.fillText("Autor: " + song.getAuthor(), 700, 40, 150);
	}
}
