package com.kamilu.kompozytor.components;

import static com.kamilu.kompozytor.propenums.NoteValue.*;
import static com.vaadin.ui.Notification.Type.*;

import com.kamilu.kompozytor.drawers.SongDrawer;
import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.entities.Tact;
import com.kamilu.kompozytor.propenums.NoteValue;
import com.kamilu.kompozytor.utils.NoteFactory;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class NoteMenu extends HorizontalLayout {

	private final Song song;
	private final Button inserNote;
	private final Button deleteNote;
	private final Button wholeNote;
	private final Button halfNote;
	private final Button quarterNote;
	private final Button eighthNote;
	private final Button sixteenNote;
	private final SongDrawer songDrawer;

	public NoteMenu(Song song, SongDrawer songDrawer) {
		this.song = song;
		this.songDrawer = songDrawer;
		inserNote = new Button("Wstaw Nutê", new InsertNote());
		deleteNote = new Button("Usuñ Nutê", new DeleteNote());
		wholeNote = new Button(new ThemeResource("../notes/1.png"));
		halfNote = new Button(new ThemeResource("../notes/2.png"));
		quarterNote = new Button(new ThemeResource("../notes/3.png"));
		eighthNote = new Button(new ThemeResource("../notes/4.png"));
		sixteenNote = new Button(new ThemeResource("../notes/5.png"));
		wholeNote.addClickListener(new ChangeValue(WholeNote));
		halfNote.addClickListener(new ChangeValue(HalfNote));
		quarterNote.addClickListener(new ChangeValue(QuarterNote));
		eighthNote.addClickListener(new ChangeValue(EighthNote));
		sixteenNote.addClickListener(new ChangeValue(SixteenthNote));
		addComponents(wholeNote, halfNote, quarterNote, eighthNote,
				sixteenNote, inserNote, deleteNote);
	}

	private final class DeleteNote implements ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			int tactIndex = songDrawer.getSelectedTact();
			int noteIndex = songDrawer.getSelectedNote();
			song.getVoices().get(0).getTact(tactIndex).removeNote(noteIndex);
			songDrawer.drawSong(song);
		}
	}

	private final class InsertNote implements ClickListener {
		public void buttonClick(ClickEvent event) {
			int tactIndex = songDrawer.getSelectedTact();
			if (tactIndex == -1) {
				Notification.show("Zaznacz nutê", WARNING_MESSAGE);
				return;
			}
			Tact tact = song.getVoices().get(0).getTact(tactIndex);
			int noteIndex = songDrawer.getSelectedNote();
			if (noteIndex == -1) {
				Notification.show("Zaznacz nutê", WARNING_MESSAGE);
				return;
			}
			Note note = tact.getNote(noteIndex);
			tact.addNote(NoteFactory.createNewNote(tact.getKey(), note,
					note.getValue()));
			songDrawer.drawSong(song);
		}
	}

	private final class ChangeValue implements ClickListener {

		private NoteValue value;

		public ChangeValue(NoteValue value) {
			this.value = value;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			int tactIndex = songDrawer.getSelectedTact();
			if (tactIndex == -1) {
				Notification.show("Zaznacz nutê", WARNING_MESSAGE);
				return;
			}
			int noteIndex = songDrawer.getSelectedNote();
			if (noteIndex == -1) {
				Notification.show("Zaznacz nutê", WARNING_MESSAGE);
				return;
			}
			song.getVoices().get(0).getTact(tactIndex).getNote(noteIndex)
					.setValue(value);
		}
	}
}
