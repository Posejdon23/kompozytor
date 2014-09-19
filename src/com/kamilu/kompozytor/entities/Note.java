package com.kamilu.kompozytor.entities;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.propenums.Accidental;
import com.kamilu.kompozytor.propenums.Dynamic;
import com.kamilu.kompozytor.propenums.NotePitch;
import com.kamilu.kompozytor.propenums.NoteValue;

@SuppressWarnings("serial")
public class Note implements MusicObject {

	public static final String KIND = "Note";
	public static final String VALUE = "value";
	public static final String PITCH = "pitch";
	public static final String DYNAMIC = "dynamic";
	public static final String ACCIDENTAL = "accidental";
	public static final String TACT_ID = "tactId";
	public static final String TRIOLA = "triola";
	public static final String AKCENT = "akcent";
	public static final String WITH_DOT = "with_dot";

	private final Key key;
	private NoteValue value;
	private NotePitch pitch;
	private Dynamic dynamic;
	private Accidental accid;
	private Long tactId;
	private int number;
	private boolean triola, akcent, withDot;
	private boolean markToRemove = false;

	public Note(Entity note) {
		key = note.getKey();
		tactId = (Long) note.getProperty(TACT_ID);
		number = ((Long) note.getProperty(NUMBER)).intValue();
		value = NoteValue.valueOf((String) note.getProperty(VALUE));
		pitch = NotePitch.valueOf((String) note.getProperty(PITCH));
		dynamic = Dynamic.valueOf((String) note.getProperty(DYNAMIC));
		accid = Accidental.valueOf((String) note.getProperty(ACCIDENTAL));
		withDot = (boolean) note.getProperty(WITH_DOT);
		triola = (boolean) note.getProperty(TRIOLA);
		akcent = (boolean) note.getProperty(AKCENT);
		markToRemove = (boolean) note.getProperty(TO_REMOVE);
	}

	public void persist() {
		if (!markToRemove) {
			Entity note = new Entity(key);
			note.setProperty(TACT_ID, tactId);
			note.setProperty(NUMBER, number);
			note.setUnindexedProperty(VALUE, value.name());
			note.setUnindexedProperty(PITCH, pitch.name());
			note.setUnindexedProperty(DYNAMIC, dynamic.name());
			note.setUnindexedProperty(ACCIDENTAL, accid.name());
			note.setUnindexedProperty(WITH_DOT, withDot);
			note.setUnindexedProperty(TRIOLA, triola);
			note.setUnindexedProperty(AKCENT, akcent);
			note.setUnindexedProperty(TO_REMOVE, markToRemove);
			DataStoreWrapper.save(note);
		} else {
			DataStoreWrapper.delete(key);
		}
	}

	public void markToRemove() {
		markToRemove = true;
		Entity note = new Entity(key);
		note.setUnindexedProperty(TO_REMOVE, true);
		DataStoreWrapper.save(note);
	}

	public boolean isMarkToRemove() {
		return markToRemove;
	}

	public Key getKey() {
		return key;
	}

	public NoteValue getValue() {
		return value;
	}

	public int getNumber() {
		return number;
	}

	public Long getTactId() {
		return tactId;
	}

	public NotePitch getPitch() {
		return pitch;
	}

	public Dynamic getDynamic() {
		return dynamic;
	}

	public Accidental getAccidental() {
		return accid;
	}

	public void setAccidental(Accidental accid) {
		this.accid = accid;
	}

	public void setValue(NoteValue value) {
		this.value = value;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setPitch(NotePitch pitch) {
		this.pitch = pitch;
	}

	public boolean isDot() {
		return withDot;
	}

	public boolean isTriola() {
		return triola;
	}

	public boolean isAkcent() {
		return akcent;
	}
}