package com.kamilu.kompozytor.entities;

import static com.vaadin.ui.Notification.Type.*;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.propenums.Clef;
import com.kamilu.kompozytor.propenums.Metrum;
import com.kamilu.kompozytor.propenums.Tonation;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
public class Tact implements MusicObject {
	public static final String KIND = "Tact";
	public static final String METRUM = "metrum";
	public static final String TONATION = "tonation";
	public static final String VOICE_ID = "voiceId";
	public static final String TEMPO = "tempo";
	public static final String CLEF = "clef";
	public static final String REPEAT_START = "repeatStart";
	public static final String REPEAT_END = "repeatEnd";
	public static final String REPEAT_COUNT = "repeatCount";
	public static final String DOUBLE_LINE = "doubleLine";
	private List<Note> notes;
	private final Key key;
	private int tempo;
	private Long voiceId;
	private Metrum metrum;
	private Tonation tonation;
	private Clef clef;
	private int number, repeatCount;
	private boolean repeatStart, repeatEnd, doubleLine;
	private boolean markToRemove = false;

	public Tact(Entity tact) {
		this.key = tact.getKey();
		notes = new ArrayList<Note>();
		voiceId = (Long) tact.getProperty(VOICE_ID);
		number = ((Long) tact.getProperty(NUMBER)).intValue();
		tempo = ((Long) tact.getProperty(TEMPO)).intValue();
		metrum = Metrum.valueOf((String) tact.getProperty(METRUM));
		clef = Clef.valueOf((String) tact.getProperty(CLEF));
		repeatStart = (boolean) tact.getProperty(REPEAT_START);
		repeatEnd = (boolean) tact.getProperty(REPEAT_END);
		doubleLine = (boolean) tact.getProperty(DOUBLE_LINE);
		repeatCount = ((Long) tact.getProperty(REPEAT_COUNT)).intValue();
		markToRemove = (boolean) tact.getProperty(TO_REMOVE);
		tonation = Tonation.valueOf((String) tact.getProperty(TONATION));
		if (!markToRemove) {
			loadNotes();
		}
	}

	private void loadNotes() {
		List<Entity> noteEntities = DataStoreWrapper
				.getChildrenWithOrderColumn(Note.KIND, Note.TACT_ID, getKey(),
						Note.NUMBER);
		for (Entity noteEntity : noteEntities) {
			Note note = new Note(noteEntity);
			if (!note.isMarkToRemove()) {
				notes.add(note);
			}
		}
	}

	public void persist() {
		if (!markToRemove) {
			Entity tact = new Entity(key);
			tact.setProperty(NUMBER, Long.valueOf((long) number));
			tact.setProperty(VOICE_ID, voiceId);
			tact.setUnindexedProperty(CLEF, clef.name());
			tact.setUnindexedProperty(METRUM, metrum.name());
			tact.setUnindexedProperty(TONATION, tonation.name());
			tact.setUnindexedProperty(TEMPO, (long) tempo);
			tact.setUnindexedProperty(REPEAT_START, repeatStart);
			tact.setUnindexedProperty(REPEAT_END, repeatEnd);
			tact.setUnindexedProperty(DOUBLE_LINE, doubleLine);
			tact.setUnindexedProperty(REPEAT_COUNT, (long) repeatCount);
			tact.setUnindexedProperty(TO_REMOVE, markToRemove);
			DataStoreWrapper.save(tact);
		} else {
			DataStoreWrapper.delete(key);
		}
		for (Note note : notes) {
			note.persist();
		}
	}

	public void markToRemove() {
		for (Note note : notes) {
			note.markToRemove();
		}
		markToRemove = true;
		Entity tact = new Entity(key);
		tact.setUnindexedProperty(TO_REMOVE, markToRemove);
		DataStoreWrapper.save(tact);
	}

	public void removeNote(int index) {
		if (index <= -1) {
			Notification.show("Zaznacz nutê", WARNING_MESSAGE);
			return;
		}
		if (index >= notes.size()) {
			Notification.show("Zaznacz nutê", WARNING_MESSAGE);
			return;
		}
		notes.get(index).markToRemove();
		notes.remove(index);
		updateOrderNumbersFromIndex(index);
	}

	private void updateOrderNumbersFromIndex(int index) {
		index = index < 0 ? index = 0 : index;
		for (int i = index; i < notes.size(); i++) {
			notes.get(i).setNumber(i);
		}
	}

	public void addNote(int index, Note note) {
		notes.add(index, note);
		updateOrderNumbersFromIndex(index - 1);
	}

	public void addNote(Note note) {
		notes.add(note);
	}

	public boolean isMarkedToRemove() {
		return markToRemove;
	}

	public int getTempo() {
		return tempo;
	}

	public Metrum getMetrum() {
		return metrum;
	}

	public Tonation getTonation() {
		return tonation;
	}

	public int getNumber() {
		return number;
	}

	public Note getNote(int index) {
		if (notes.size() == 0 || index < 0 || notes.size() <= index) {
			return null;
		}
		return notes.get(index);
	}

	public long getVoiceId() {
		return voiceId;
	}

	public Clef getClef() {
		return clef;
	}

	public Key getKey() {
		return key;
	}

	public double getTactLength() {
		return getMetrum().getTactLength();
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setOrderNumber(int number) {
		this.number = number;
	}

	public void setClef(Clef clef) {
		this.clef = clef;
	}

	public void setTonation(Tonation tonation) {
		this.tonation = tonation;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public int getNoteCount() {
		return notes.size();
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public boolean isRepeatStart() {
		return repeatStart;
	}

	public boolean isRepeatEnd() {
		return repeatEnd;
	}

	public boolean isDoubleLine() {
		return doubleLine;
	}

	public void setDoubleLine(boolean doubleLine) {
		this.doubleLine = doubleLine;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public void setRepeatStart(boolean repeatStart) {
		this.repeatStart = repeatStart;
	}

	public void setRepeatEnd(boolean repeatEnd) {
		this.repeatEnd = repeatEnd;
	}
}
