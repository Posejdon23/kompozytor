package com.kamilu.kompozytor.components;

import com.kamilu.kompozytor.drawers.SongDrawer;
import com.kamilu.kompozytor.entities.Song;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class SongMenu extends HorizontalLayout {

	private static final long serialVersionUID = 1L;
	private final TextField songName, songAuthor;

	public SongMenu(Song song, SongDrawer songDrawer) {
		songName = new TextField("Nazwa utworu");
		songName.setValue(song.getName());
		songAuthor = new TextField("Autor utworu");
		songAuthor.setValue(song.getAuthor());
		songName.addBlurListener(new ChangeName(song));
		songAuthor.addBlurListener(new ChangeAuthor(song));
		addComponents(songName, songAuthor);
	}

	private final class ChangeName implements BlurListener {

		private static final long serialVersionUID = 1L;
		private Song song;

		public ChangeName(Song song) {
			this.song = song;
		}

		@Override
		public void blur(BlurEvent event) {
			song.setName(songName.getValue());
		}
	}

	private final class ChangeAuthor implements BlurListener {

		private static final long serialVersionUID = 1L;
		private Song song;

		public ChangeAuthor(Song song) {
			this.song = song;
		}

		@Override
		public void blur(BlurEvent event) {
			song.setAuthor(songAuthor.getValue());
		}
	}
}
