package com.kamilu.kompozytor.components;

import java.util.Arrays;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.common.collect.ImmutableList;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.drawers.SongDrawer;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.entities.Voice;
import com.kamilu.kompozytor.propenums.Instrument;
import com.kamilu.kompozytor.utils.EntityTable;
import com.kamilu.kompozytor.utils.TableDataLoader;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;

@SuppressWarnings("serial")
public class VoiceMenu extends VerticalLayout {

	private final ComboBox instrument;
	private final ComboBox number;
	private final TextField name;
	private final Table voices;
	private final HorizontalLayout topMenu;

	public VoiceMenu(Song song, SongDrawer songDrawer) {
		name = new TextField("Nazwa");
		instrument = new ComboBox("Instrument", Arrays.asList(Instrument
				.values()));
		number = new ComboBox("Numer g³osu", ImmutableList.of(1L, 2L, 3L, 4L));
		name.addBlurListener(new ChangeName());
		instrument.addValueChangeListener(new ChangeInstrument());
		number.addValueChangeListener(new ChangeNumber());
		voices = new EntityTable.Builder()//
				.kind(Voice.KIND)//
				.build();
		voices.setSizeFull();
		voices.setCaption("Lista g³osów");
		topMenu = new HorizontalLayout(name, instrument, number);
		addComponents(topMenu, voices);
		setComponentAlignment(voices, Alignment.TOP_CENTER);
	}

	private final class ChangeName implements BlurListener {
		@Override
		public void blur(BlurEvent event) {
			Key key = (Key) voices.getValue();
			if (key == null) {
				Notification.show("Zaznacz g³os na liœcie",
						Type.WARNING_MESSAGE);
			} else {
				Entity voiceEntity = DataStoreWrapper.getById(Voice.KIND,
						key.getId());
				Voice voice = new Voice(voiceEntity);
				voice.setName((String) name.getValue());
				TableDataLoader.loadData(voices);
			}
		}
	}

	private final class ChangeNumber implements ValueChangeListener {
		@Override
		public void valueChange(ValueChangeEvent event) {
			Key key = (Key) voices.getValue();
			if (key == null) {
				Notification.show("Zaznacz g³os na liœcie",
						Type.WARNING_MESSAGE);
			} else {
				Entity voiceEntity = DataStoreWrapper.getById(Voice.KIND,
						key.getId());
				Voice voice = new Voice(voiceEntity);
				voice.setNumber((Long) number.getValue());
				TableDataLoader.loadData(voices);
			}
		}
	}

	private final class ChangeInstrument implements ValueChangeListener {
		@Override
		public void valueChange(ValueChangeEvent event) {
			Key key = (Key) voices.getValue();
			if (key == null) {
				Notification.show("Zaznacz g³os na liœcie",
						Type.WARNING_MESSAGE);
			} else {
				Entity voiceEntity = DataStoreWrapper.getById(Voice.KIND,
						key.getId());
				Voice voice = new Voice(voiceEntity);
				voice.setInstrument((Instrument) instrument.getValue());
				TableDataLoader.loadData(voices);
			}
		}
	}

	public void loadVoicesToTable() {
		TableDataLoader.loadData(voices);
	}
}
