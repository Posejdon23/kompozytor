package com.kamilu.kompozytor.components;

import static com.kamilu.kompozytor.propenums.NoteValue.*;
import static com.vaadin.ui.Notification.Type.*;

import java.util.Arrays;
import java.util.List;

import com.kamilu.kompozytor.drawers.TactDrawer;
import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.entities.Tact;
import com.kamilu.kompozytor.entities.Voice;
import com.kamilu.kompozytor.propenums.Accidental;
import com.kamilu.kompozytor.propenums.NotePitch;
import com.kamilu.kompozytor.propenums.NoteValue;
import com.kamilu.kompozytor.utils.NoteFactory;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class NoteMenu extends VerticalLayout {

	private final Song song;
	private final Button insertNote;
	private final Button deleteNote;
	// private final Button wholeNote;
	// private final Button halfNote;
	// private final Button quarterNote;
	// private final Button eighthNote;
	// private final Button sixteenNote;
	private final TactDrawer tactDrawer;
	private final CompositorEditor editor;
	private final ComboBox pitchDict;
	private final ComboBox accidDict;
	private final ComboBox valueDict;

	public NoteMenu(Song song, CompositorEditor editor) {
		this.song = song;
		this.editor = editor;
		this.tactDrawer = editor.getTactDrawer();
		insertNote = new Button("Wstaw Nutê", new InsertNote());
		deleteNote = new Button("Usuñ Nutê", new DeleteNote());
		// wholeNote = new Button(new ThemeResource("../notes/1.png"));
		// halfNote = new Button(new ThemeResource("../notes/2.png"));
		// quarterNote = new Button(new ThemeResource("../notes/3.png"));
		// eighthNote = new Button(new ThemeResource("../notes/4.png"));
		// sixteenNote = new Button(new ThemeResource("../notes/5.png"));
		pitchDict = new ComboBox("DŸwiêk", Arrays.asList(NotePitch.values()));
		valueDict = new ComboBox("Wartoœæ", Arrays.asList(NoteValue.values()));
		accidDict = new ComboBox("Znak", Arrays.asList(Accidental.values()));
		pitchDict.addBlurListener(new ChangePitch());
		valueDict.addBlurListener(new ChangeValue());
		accidDict.addBlurListener(new ChangeAccid());
		pitchDict.setNullSelectionAllowed(false);
		valueDict.setNullSelectionAllowed(false);
		accidDict.setNullSelectionAllowed(false);
		// wholeNote.addClickListener(new ChangeValue(WholeNote));
		// halfNote.addClickListener(new ChangeValue(HalfNote));
		// quarterNote.addClickListener(new ChangeValue(QuarterNote));
		// eighthNote.addClickListener(new ChangeValue(EighthNote));
		// sixteenNote.addClickListener(new ChangeValue(SixteenthNote));
		// wholeNote.setHeight("50px");
		// halfNote.setHeight("50px");
		// quarterNote.setHeight("50px");
		// eighthNote.setHeight("50px");
		// sixteenNote.setHeight("50px");
		addComponents(insertNote, deleteNote, pitchDict, valueDict, accidDict);
	}

	private final class ChangeAccid implements BlurListener {
		@Override
		public void blur(BlurEvent event) {
			int tactIndex = tactDrawer.getSelectedTactIndex();
			if (tactIndex == -1) {
				Notification.show("Zaznacz nutê", WARNING_MESSAGE);
				return;
			}
			int noteIndex = tactDrawer.getSelectedNoteIndex();
			if (noteIndex == -1) {
				Notification.show("Zaznacz nutê", WARNING_MESSAGE);
				return;
			}
			List<Voice> voices = song.getVoices();
			Voice voice = voices.get(0);
			Tact tact = voice.getTact(tactIndex);
			Note note = tact.getNote(noteIndex);
			note.setAccidental((Accidental) accidDict.getValue());
			drawSong();
		}
	}

	private final class ChangeValue implements BlurListener {
		@Override
		public void blur(BlurEvent event) {
			int tactIndex = tactDrawer.getSelectedTactIndex();
			if (tactIndex == -1) {
				Notification.show("Zaznacz nutê", WARNING_MESSAGE);
				return;
			}
			int noteIndex = tactDrawer.getSelectedNoteIndex();
			if (noteIndex == -1) {
				Notification.show("Zaznacz nutê", WARNING_MESSAGE);
				return;
			}
			song.getVoices().get(0).getTact(tactIndex).getNote(noteIndex)
					.setValue((NoteValue) valueDict.getValue());
			drawSong();
		}
	}

	private final class ChangePitch implements BlurListener {
		@Override
		public void blur(BlurEvent event) {
			List<Voice> voices = song.getVoices();
			Voice voice = voices.get(0);
			int tactIndex = tactDrawer.getSelectedTactIndex();
			Tact tact;
			if (tactIndex == -1) {
				Notification.show("Zaznacz takt", WARNING_MESSAGE);
				return;
			} else {
				tact = voice.getTact(tactIndex);
				if (tact == null) {
					Notification.show("Zaznacz takt", WARNING_MESSAGE);
					return;
				}
			}
			int noteIndex = tactDrawer.getSelectedNoteIndex();
			if (noteIndex == -1) {
				Notification.show("Zaznacz nutê", WARNING_MESSAGE);
				return;
			} else {
				Note note = tact.getNote(noteIndex);
				NotePitch pitchValue = (NotePitch) pitchDict.getValue();
				note.setPitch(pitchValue);
				if (pitchValue == NotePitch.Pause) {
					note.setAccidental(Accidental.Natural);
				}
				drawSong();
			}
		}
	}

	private final class DeleteNote implements ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			int tactIndex = tactDrawer.getSelectedTactIndex();
			int noteIndex = tactDrawer.getSelectedNoteIndex();
			song.getVoices().get(0).getTact(tactIndex).removeNote(noteIndex);
			drawSong();
		}
	}

	private final class InsertNote implements ClickListener {
		public void buttonClick(ClickEvent event) {
			int tactIndex = tactDrawer.getSelectedTactIndex();
			if (tactIndex == -1) {
				Notification.show("Zaznacz takt", WARNING_MESSAGE);
				return;
			}
			Tact tact = song.getVoices().get(0).getTact(tactIndex);
			int noteIndex = tactDrawer.getSelectedNoteIndex();
			Note note = tact.getNote(noteIndex);
			if (note == null) {
				noteIndex = tact.getNoteCount();
				tact.addNote(NoteFactory.createNewNote(tact.getKey(),
						QuarterNote, (long) noteIndex, Accidental.Natural,
						NotePitch.c1));
			} else {
				int selectedNumber = note.getNumber();
				tact.addNote(selectedNumber + 1, NoteFactory.createNewNote(
						tact.getKey(), note.getValue(), (long) selectedNumber,
						Accidental.Natural, note.getPitch()));
			}

			drawSong();
		}
	}

	// private final class ChangeValue implements ClickListener {
	//
	// private NoteValue value;
	//
	// public ChangeValue(NoteValue value) {
	// this.value = value;
	// }
	//
	// @Override
	// public void buttonClick(ClickEvent event) {
	// int tactIndex = tactDrawer.getSelectedTactIndex();
	// if (tactIndex == -1) {
	// Notification.show("Zaznacz nutê", WARNING_MESSAGE);
	// return;
	// }
	// int noteIndex = tactDrawer.getSelectedNoteIndex();
	// if (noteIndex == -1) {
	// Notification.show("Zaznacz nutê", WARNING_MESSAGE);
	// return;
	// }
	// song.getVoices().get(0).getTact(tactIndex).getNote(noteIndex)
	// .setValue(value);
	// drawSong();
	// }
	// }

	public void drawSong() {
		editor.drawSong();
	}
}
