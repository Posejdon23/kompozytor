package com.kamilu.kompozytor.entities;

import static com.vaadin.ui.Notification.Type.*;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.common.collect.ImmutableMap;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.propenums.Instrument;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
public class Voice implements MusicObject {
	public static final String KEY = "Key";
	public static final String SONG_ID = "songId";
	public static final String KIND = "Voice";
	public static final String NAME = "name";
	public static final String INSTRUMENT = "instrument";
	private final Key key;
	private Long songId;
	private Long number;
	private String name;
	private Instrument instrument;
	private List<Tact> tacts;
	private boolean markToRemove = false;
	public static final ImmutableMap<String, Class<?>> PROPERTIES = new ImmutableMap.Builder<String, Class<?>>()
			.put(KEY, Key.class)//
			.put(NAME, String.class)//
			.put(INSTRUMENT, String.class)//
			.put(NUMBER, Long.class)//
			.build();

	public Voice(Entity voice) {
		tacts = new ArrayList<Tact>();
		key = voice.getKey();
		songId = (Long) voice.getProperty(SONG_ID);
		number = (Long) voice.getProperty(NUMBER);
		name = (String) voice.getProperty(NAME);
		markToRemove = (boolean) voice.getProperty(TO_REMOVE);
		instrument = Instrument.valueOf((String) voice.getProperty(INSTRUMENT));
		if (!markToRemove) {
			loadTacts();
		}
	}

	private void loadTacts() {
		List<Entity> tactEntites = DataStoreWrapper.getChildrenWithOrderColumn(
				Tact.KIND, Tact.VOICE_ID, key, Tact.NUMBER);
		for (Entity tactEntity : tactEntites) {
			Tact tact = new Tact(tactEntity);
			if (!tact.isMarkedToRemove()) {
				tacts.add(tact);
			}
		}
	}

	public void persist() {
		if (!markToRemove) {
			Entity voice = new Entity(key);
			voice.setUnindexedProperty(NAME, name);
			voice.setProperty(NUMBER, number);
			voice.setUnindexedProperty(INSTRUMENT, instrument.name());
			voice.setUnindexedProperty(TO_REMOVE, markToRemove);
			voice.setProperty(SONG_ID, songId);
			DataStoreWrapper.save(voice);
		} else {
			DataStoreWrapper.delete(key);
		}
		for (MusicObject tact : tacts) {
			tact.persist();
		}
	}

	public void markToRemove() {
		for (MusicObject tact : tacts) {
			tact.markToRemove();
		}
		markToRemove = true;
		Entity voice = new Entity(key);
		voice.setUnindexedProperty(TO_REMOVE, markToRemove);
		DataStoreWrapper.save(voice);

	}

	public boolean isMarkedToRemove() {
		return markToRemove;
	}

	public void removeTact(int index) {
		if (index <= -1) {
			Notification.show("Zaznacz takt", WARNING_MESSAGE);
			return;
		}
		if (index >= tacts.size()) {
			Notification.show("Zaznacz takt", WARNING_MESSAGE);
			return;
		}
		tacts.get(index).markToRemove();
		tacts.remove(index);
		updateNumbersFrom(index);
	}

	private void updateNumbersFrom(int index) {
		for (int i = index; i < tacts.size(); i++) {
			((Tact) tacts.get(i)).setOrderNumber(i);
		}
	}

	public Key getKey() {
		return key;
	}

	public Long getNumber() {
		return number;
	}

	public void addTact(Tact tact) {
		tacts.add(tact);
	}

	public void addTact(int index, Tact tact) {
		tacts.add(index, tact);
		updateNumbersFrom(index);
	}

	public Tact getTact(int index) {
		if (index < 0) {
			return null;
		}
		return (Tact) tacts.get(index);
	}

	public List<Tact> getTacts() {
		return tacts;
	}

	public String getName() {
		return name;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public Long getSongId() {
		return songId;
	}

}
