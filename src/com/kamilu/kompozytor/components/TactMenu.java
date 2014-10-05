package com.kamilu.kompozytor.components;

import static com.vaadin.server.Sizeable.Unit.*;

import java.util.Arrays;

import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.propenums.Clef;
import com.kamilu.kompozytor.propenums.Tonation;
import com.vaadin.ui.*;

@SuppressWarnings("serial")
public class TactMenu extends VerticalLayout {

	private Song song;
	private Button newTact;
	private Button removeTact;
	private ComboBox clefs;
	private ComboBox tonations;
	private TextField tempo;
	private CheckBox repeatStart, repeatEnd;
	private Slider repeatCount;

	public TactMenu(Song song) {
		this.song = song;
		newTact = new Button("Dodaj Takt");
		removeTact = new Button("Usu� Takt");
		clefs = new ComboBox("Klucz", Arrays.asList(Clef.values()));
		tonations = new ComboBox("Tonacja", Arrays.asList(Tonation.values()));
		tempo = new TextField("Tempo");
		clefs.setNullSelectionAllowed(false);
		tonations.setNullSelectionAllowed(false);
		clefs.select(Clef.Violin.name());
		tonations.select(Tonation.C_dur.name());
		repeatStart = new CheckBox("Pocz�tek powt�rki", false);
		repeatEnd = new CheckBox("Koniec powt�rki", false);
		repeatCount = new Slider(1, 100, 0);
		repeatCount.setCaption("Liczba powt�rze�");
		repeatCount.setWidth(150, PIXELS);
		repeatCount.setValue(1D);
		addComponents(newTact, removeTact, clefs, tonations, tempo,
				repeatStart, repeatEnd, repeatCount);
	}
}
