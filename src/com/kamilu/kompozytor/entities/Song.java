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

	public static final ImmutableMap<String, Class<?>> PROPERTIES = new ImmutableMap.Builder<String, Class<?>>()
			.put(KEY, Key.class)//
			.put(NAME, String.class)//
			.put(AUTHOR, String.class)//
			.build();

	private Entity song;
	private List<Voice> voices;

	public Song(Entity song) {
		this.song = song;
		// EntityUtils.printEntity(song);
		loadVoices();
	}

	public List<Voice> getVoices() {
		return voices;
	}

	public void loadVoices() {
		voices = new ArrayList<Voice>();
		List<Entity> voiceEntities = DataStoreWrapper.getChildren(Voice.KIND,
				Voice.SONG_ID, getKey());
		for (Entity voiceEntity : voiceEntities) {
			Voice voice = new Voice(voiceEntity);
			voices.add(voice);
			voice.loadTacts();
		}
	}

	public Key getKey() {
		return song.getKey();
	}

	public void save() {
		DataStoreWrapper.save(song);
		for (Voice voice : voices) {
			voice.save();
		}
	}

	public void deleteWithChildren() {
		DataStoreWrapper.delete(song);
		for (Voice voice : voices) {
			voice.deleteWithChildren();
		}
	}

	public void addVoice(Voice voice) {
		voices.add(voice);
	}

	public String getName() {
		return (String) song.getProperty(NAME);
	}

	public String getAuthor() {
		return (String) song.getProperty(AUTHOR);
	}

	public Tact getLastTact() {
		List<Tact> tacts = voices.get(0).getTacts();
		return tacts.get(tacts.size() - 1);
	}

	public void setName(String name) {
		song.setProperty(Song.NAME, name);
		DataStoreWrapper.save(song);
	}

	public void setAuthor(String author) {
		song.setUnindexedProperty(Song.AUTHOR, author);
		DataStoreWrapper.save(song);

	}
}
