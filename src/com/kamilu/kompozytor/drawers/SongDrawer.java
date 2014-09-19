package com.kamilu.kompozytor.drawers;

import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.vaadin.ui.HorizontalLayout;

@SuppressWarnings("serial")
public class SongDrawer extends HorizontalLayout implements Drawer {

	private DrawingCanvas canvas;

	public SongDrawer(DrawCursor cursor) {
		this.canvas = cursor.getCanvas();
	}

	public void drawSong(Song song) {
		// canvas.fillText(song.getName(), 400, 40, 450);
		// canvas.fillText("Autor: " + song.getAuthor(), 700, 40, 150);
	}
}
