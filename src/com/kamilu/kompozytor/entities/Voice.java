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
import com.vaadin.ui.Notification.Type;

@SuppressWarnings("serial")
public class Voice implements MusicObject {
	public static final String KEY = "Key";
	public static final String SONG_ID = "songId";
	public static final String KIND = "Voice";
	public static final String NAME = "name";
	public static final String NUMBER = "number";
	public static final String INSTRUMENT = "instrument";

	public static final ImmutableMap<String, Class<?>> PROPERTIES = new ImmutableMap.Builder<String, Class<?>>()
			.put(KEY, Key.class)//
			.put(NAME, String.class)//
			.put(INSTRUMENT, String.class)//
			.put(NUMBER, Long.class)//
			.build();

	private Entity voice;
	private List<Tact> tacts;

	public Voice(Entity voice) {
		this.voice = voice;
		// EntityUtils.printEntity(voice);
		loadTacts();
	}

	public void loadTacts() {
		tacts = new ArrayList<Tact>();
		List<Entity> tactEntites = DataStoreWrapper.getChildrenWithSortColumn(
				Tact.KIND, Tact.VOICE_ID, getKey(), Tact.ORDER_NUMBER);
		for (Entity tactEntity : tactEntites) {
			Tact tact = new Tact(tactEntity);
			tacts.add(tact);
			tact.loadNotes();
		}
	}

	public void save() {
		DataStoreWrapper.save(voice);
		for (Tact tact : tacts) {
			tact.save();
		}
	}

	public void deleteWithChildren() {
		DataStoreWrapper.delete(voice);
		for (Tact tact : tacts) {
			tact.deleteWithChildren();
		}
	}

	public Key getKey() {
		return voice.getKey();
	}

	public void addTact(Tact tact) {
		tacts.add(tact);
	}

	public void addTact(int index, Tact tact) {
		tacts.add(tact);
	}

	public Tact getTact(int index) {
		return tacts.get(index);
	}

	public List<Tact> getTacts() {
		return tacts;
	}

	public String getName() {
		return (String) voice.getProperty(NAME);
	}

	public Instrument getInstrument() {
		return Instrument.valueOf((String) voice.getProperty(INSTRUMENT));
	}

	public void removeTact(int index) {
		tacts.get(index).deleteWithChildren();
		tacts.remove(index);
		updateOrderNumbersFromIndex(index);
	}

	private void updateOrderNumbersFromIndex(int index) {
		for (int i = index; i < tacts.size(); i++) {
			tacts.get(i).setOrderNumber((long)i);
			;
		}
	}

	public void setName(String name) {
		voice.setUnindexedProperty(NAME, name);
		DataStoreWrapper.save(voice);
	}

	public void setNumber(Long number) {
		voice.setUnindexedProperty(NUMBER, number);
		DataStoreWrapper.save(voice);
	}

	public void setInstrument(Instrument instrument) {
		voice.setUnindexedProperty(INSTRUMENT, instrument.toString());
		DataStoreWrapper.save(voice);
	}
}
