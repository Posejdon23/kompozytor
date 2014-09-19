package com.kamilu.kompozytor.components;

import static com.vaadin.server.Sizeable.Unit.*;
import static com.vaadin.ui.Notification.Type.*;

import java.awt.Checkbox;
import java.util.Arrays;
import java.util.List;

import com.kamilu.kompozytor.drawers.TactDrawer;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.entities.Tact;
import com.kamilu.kompozytor.entities.Voice;
import com.kamilu.kompozytor.propenums.Clef;
import com.kamilu.kompozytor.propenums.Tonation;
import com.kamilu.kompozytor.utils.TactFactory;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class TactMenu extends VerticalLayout {

	private Song song;
	private Button newTact;
	private Button removeTact;
	private TactDrawer tactDrawer;
	private ComboBox clefs;
	private ComboBox tonations;
	private TextField tempo;
	private CompositorEditor editor;
	private CheckBox repeatStart, repeatEnd;
	private Slider repeatCount;

	public TactMenu(Song song, CompositorEditor editor) {
		this.song = song;
		this.editor = editor;
		this.tactDrawer = editor.getTactDrawer();
		newTact = new Button("Dodaj Takt");
		removeTact = new Button("Usuñ Takt");
		clefs = new ComboBox("Klucz", Arrays.asList(Clef.values()));
		tonations = new ComboBox("Tonacja", Arrays.asList(Tonation.values()));
		tempo = new TextField("Tempo");
		tempo.addValueChangeListener(new ChangeTempo());
		clefs.setNullSelectionAllowed(false);
		tonations.setNullSelectionAllowed(false);
		clefs.select(Clef.Violin.name());
		tonations.select(Tonation.C_dur.name());
		clefs.addValueChangeListener(new ChangeClef());
		tonations.addBlurListener(new ChangeTonation());
		repeatStart = new CheckBox("Pocz¹tek powtórki", false);
		repeatEnd = new CheckBox("Koniec powtórki", false);
		repeatCount = new Slider(1, 100, 0);
		repeatCount.setCaption("Liczba powtórzeñ");
		repeatCount.setWidth(150, PIXELS);
		repeatCount.setValue(1D);
		addComponents(newTact, removeTact, clefs, tonations, tempo,
				repeatStart, repeatEnd, repeatCount);
		newTact.addClickListener(new InsertTact());
		removeTact.addClickListener(new DeleteTact());
	}

	private final class DeleteTact implements ClickListener {
		public void buttonClick(ClickEvent event) {
			int index = tactDrawer.getSelectedTactIndex();
			if (index == 0) {
				Notification.show("Nie mo¿esz usun¹æ pierwszego taktu",
						WARNING_MESSAGE);
				return;
			}
			if (index == -1) {
				Notification.show("Zaznacz takt", WARNING_MESSAGE);
				return;
			}
			song.getVoices().get(0).removeTact(index);
			drawSong();
		}
	}

	private final class InsertTact implements ClickListener {
		public void buttonClick(ClickEvent event) {
			int index = tactDrawer.getSelectedTactIndex();
			if (index == -1) {
				index = song.getTactCount(0) - 1;
			}
			Tact tact = song.getVoice(0).getTact(index);
			song.getVoices().get(0)
					.addTact(index + 1, TactFactory.createNewTact(tact));
			drawSong();
		}
	}

	private final class ChangeTempo implements ValueChangeListener {
		@Override
		public void valueChange(ValueChangeEvent event) {
			int index = tactDrawer.getSelectedTactIndex();
			int tempoValue = Integer.parseInt(tempo.getValue());
			song.getVoices().get(0).getTact(index).setTempo(tempoValue);
			List<Tact> tacts = song.getVoices().get(0).getTacts();
			Tact selectedTact = tacts.get(index);
			for (Tact tact : tacts) {
				if (tact.getNumber() >= selectedTact.getNumber()) {
					tact.setTempo(tempoValue);
				}
			}
			drawSong();
		}
	}

	private final class ChangeClef implements ValueChangeListener {
		@Override
		public void valueChange(ValueChangeEvent event) {
			int index = tactDrawer.getSelectedTactIndex();
			if (index == -1) {
				Notification.show("Zaznacz takt");
				return;
			}
			List<Voice> voices = song.getVoices();
			Voice voice = voices.get(0);
			List<Tact> tacts = voice.getTacts();
			Tact selectedTact = tacts.get(index);
			Clef clef = ((Clef) clefs.getValue());
			for (Tact tact : tacts) {
				if (tact.getNumber() >= selectedTact.getNumber()) {
					tact.setClef(clef);
				}
			}
			drawSong();
		}
	}

	final class ChangeTonation implements BlurListener {
		@Override
		public void blur(BlurEvent event) {
			int index = tactDrawer.getSelectedTactIndex();
			if (index == -1) {
				Notification.show("Zaznacz takt");
				return;
			}
			List<Tact> tacts = song.getVoices().get(0).getTacts();
			Tact selectedTact = tacts.get(index);
			Tonation tonation = ((Tonation) tonations.getValue());
			for (Tact tact : tacts) {
				if (tact.getNumber() >= selectedTact.getNumber()) {
					tact.setTonation(tonation);
				}
			}
			drawSong();
		}
	}

	public void drawSong() {
		editor.drawSong();
	}
}
