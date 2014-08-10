package com.kamilu.kompozytor.components;

import static com.vaadin.ui.Notification.Type.*;

import java.util.Arrays;
import java.util.List;

import com.kamilu.kompozytor.drawers.SongDrawer;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.entities.Tact;
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
public class TactMenu extends HorizontalLayout {

	private Song song;
	private Button newTact;
	private Button removeTact;
	private SongDrawer songDrawer;
	private ComboBox clefs;
	private ComboBox tonations;
	private TextField tempo;

	public TactMenu(Song song, SongDrawer songDrawer) {
		this.song = song;
		this.songDrawer = songDrawer;
		newTact = new Button("Dodaj Takt");
		removeTact = new Button("Usuñ Takt");
		clefs = new ComboBox("Klucz", Arrays.asList(Clef.values()));
		tonations = new ComboBox("Tonacja", Arrays.asList(Tonation.values()));
		tempo = new TextField("Tempo");
		tempo.addValueChangeListener(new ChangeTempo());
		clefs.setNullSelectionAllowed(false);
		tonations.setNullSelectionAllowed(false);
		clefs.select(Clef.Wiolinowy.name());
		tonations.select(Tonation.C_dur.name());
		clefs.addValueChangeListener(new ChangeClef());
		tonations.addBlurListener(new ChangeTonation());
		setSpacing(true);
		setMargin(true);
		addComponents(newTact, removeTact, clefs, tonations, tempo);
		newTact.addClickListener(insertTactList);
		removeTact.addClickListener(deleteTaktList);
	}

	// TODO jeœli getSelectedTact coœ zwróci to lastTact trzeba zmieniæ
	private Button.ClickListener insertTactList = new ClickListener() {
		public void buttonClick(ClickEvent event) {
			Tact lastTact = song.getLastTact();
			int index = songDrawer.getSelectedTact();
			if (index == -1) {
				index = lastTact.getOrderNumber();
			}
			song.getVoices().get(0)
					.addTact(index, TactFactory.createNewTact(lastTact));
			songDrawer.drawSong(song);
		}
	};
	private Button.ClickListener deleteTaktList = new ClickListener() {
		public void buttonClick(ClickEvent event) {
			int index = songDrawer.getSelectedTact();
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
			songDrawer.drawSong(song);
		}
	};

	private final class ChangeTempo implements ValueChangeListener {
		@Override
		public void valueChange(ValueChangeEvent event) {
			int index = songDrawer.getSelectedTact();
			int tempoValue = Integer.parseInt(tempo.getValue());
			song.getVoices().get(0).getTact(index).setTempo(tempoValue);
			List<Tact> tacts = song.getVoices().get(0).getTacts();
			Tact selectedTact = tacts.get(index);
			for (Tact tact : tacts) {
				if (tact.getOrderNumber() >= selectedTact.getOrderNumber()) {
					tact.setTempo(tempoValue);
				}
			}
			songDrawer.drawSong(song);
		}
	}

	private final class ChangeClef implements ValueChangeListener {
		@Override
		public void valueChange(ValueChangeEvent event) {
			int index = songDrawer.getSelectedTact();
			if (index == -1) {
				Notification.show("Zaznacz takt");
				return;
			}
			List<Tact> tacts = song.getVoices().get(0).getTacts();
			Tact selectedTact = tacts.get(index);
			String clef = ((Clef) clefs.getValue()).name();
			for (Tact tact : tacts) {
				if (tact.getOrderNumber() >= selectedTact.getOrderNumber()) {
					tact.setClef(clef);
				}
			}
			songDrawer.drawSong(song);
		}
	}

	final class ChangeTonation implements BlurListener {
		@Override
		public void blur(BlurEvent event) {
			int index = songDrawer.getSelectedTact();
			if (index == -1) {
				Notification.show("Zaznacz takt");
				return;
			}
			List<Tact> tacts = song.getVoices().get(0).getTacts();
			Tact selectedTact = tacts.get(index);
			String tonation = ((Tonation) tonations.getValue()).name();
			for (Tact tact : tacts) {
				if (tact.getOrderNumber() >= selectedTact.getOrderNumber()) {
					tact.setTonation(tonation);
				}
			}
			songDrawer.drawSong(song);
		}
	}
}
