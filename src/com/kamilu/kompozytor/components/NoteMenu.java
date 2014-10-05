package com.kamilu.kompozytor.components;

import java.util.Arrays;

import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.propenums.Accidental;
import com.kamilu.kompozytor.propenums.NotePitch;
import com.kamilu.kompozytor.propenums.NoteValue;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class NoteMenu extends VerticalLayout {

	private final Song song;
	private final Button insertNote;
	private final Button deleteNote;
	private final ComboBox pitchDict;
	private final ComboBox accidDict;
	private final ComboBox valueDict;

	public NoteMenu(Song song) {
		this.song = song;
		insertNote = new Button("Wstaw Nutê");
		deleteNote = new Button("Usuñ Nutê");
		pitchDict = new ComboBox("DŸwiêk", Arrays.asList(NotePitch.values()));
		valueDict = new ComboBox("Wartoœæ", Arrays.asList(NoteValue.values()));
		accidDict = new ComboBox("Znak", Arrays.asList(Accidental.values()));
		pitchDict.setNullSelectionAllowed(false);
		valueDict.setNullSelectionAllowed(false);
		accidDict.setNullSelectionAllowed(false);
		addComponents(insertNote, deleteNote, pitchDict, valueDict, accidDict);
	}
}
