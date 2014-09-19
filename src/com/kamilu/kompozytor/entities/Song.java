package com.kamilu.kompozytor.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.common.collect.ImmutableMap;
import com.kamilu.kompozytor.DataStoreWrapper;

@SuppressWarnings("serial")
public class Song implements MusicObject {

	public static final String KIND = "Song";
	public static final String NAME = "name";
	public static final String AUTHOR = "author";
	public static final String KEY = "Key";
	private final Key key;
	private String name;
	private String author;
	private List<Voice> voices;
	private boolean markToRemove = false;

	public static final ImmutableMap<String, Class<?>> PROPERTIES = new ImmutableMap.Builder<String, Class<?>>()
			.put(KEY, Key.class)//
			.put(NAME, String.class)//
			.put(AUTHOR, String.class)//
			.build();

	public Song(Entity song) {
		voices = new ArrayList<Voice>();
		name = (String) song.getProperty(NAME);
		author = (String) song.getProperty(AUTHOR);
		markToRemove = (boolean) song.getProperty(TO_REMOVE);
		key = song.getKey();
		if (!markToRemove) {
			loadVoices();
		}
	}

	private void loadVoices() {
		List<Entity> voiceEntities = DataStoreWrapper.getChildren(Voice.KIND,
				Voice.SONG_ID, getKey());
		for (Entity voiceEntity : voiceEntities) {
			Voice voice = new Voice(voiceEntity);
			if (!voice.isMarkedToRemove()) {
				voices.add(voice);
			}
		}
	}

	public void persist() {
		if (!markToRemove) {
			Entity song = new Entity(key);
			song.setProperty(NAME, name);
			song.setUnindexedProperty(Song.AUTHOR, author);
			song.setUnindexedProperty(TO_REMOVE, markToRemove);
			DataStoreWrapper.save(song);
		} else {
			DataStoreWrapper.delete(key);
		}
		for (Voice voice : voices) {
			voice.persist();
		}
	}

	public boolean isMarkedToRemove() {
		return markToRemove;
	}

	public void markToRemove() {
		for (Voice voice : voices) {
			voice.markToRemove();
		}
		markToRemove = true;
		Entity song = new Entity(key);
		song.setUnindexedProperty(TO_REMOVE, true);
		DataStoreWrapper.save(song);
	}

	public List<Voice> getVoices() {
		return voices;
	}

	public int getTactCount(int voiceIndex) {
		return voices.get(0).getTacts().size();
	}

	public Voice getVoice(int index) {
		return voices.get(index);
	}

	public Key getKey() {
		return key;
	}

	public void addVoice(Voice voice) {
		voices.add(voice);
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
