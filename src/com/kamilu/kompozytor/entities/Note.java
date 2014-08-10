package com.kamilu.kompozytor.entities;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.propenums.Dynamic;
import com.kamilu.kompozytor.propenums.NoteValue;
import com.kamilu.kompozytor.propenums.NotePitch;
import com.kamilu.kompozytor.propenums.Accidental;
import com.kamilu.kompozytor.utils.EntityUtils;

@SuppressWarnings("serial")
public class Note implements MusicObject {
	public static final String KIND = "Note";
	public static final String VALUE = "value";
	public static final String PITCH = "pitch";
	public static final String DYNAMIC = "dynamic";
	public static final String ACCIDENTAL = "accidental";
	public static final String ID_TAKTU = "id_taktu";
	public static final String TRIOLA = "triola";
	public static final String AKCENT = "akcent";
	public static final String WITH_DOT = "with_dot";
	public static final String ORDER_NUMBER = "order_number";
	private Entity note;

	public Note(Entity note) {
		this.note = note;
		// EntityUtils.printEntity(note);
	}

	public void save() {
		DataStoreWrapper.save(note);
	}

	public void delete() {
		DataStoreWrapper.delete(note);
	}

	public Key getKey() {
		return note.getKey();
	}

	public NoteValue getValue() {
		return NoteValue.valueOf((String) note.getProperty(VALUE));
	}

	public void setValue(NoteValue value) {
		note.setUnindexedProperty(VALUE, value.toString());
		save();
	}

	public void setOrderNumber(Long value) {
		note.setUnindexedProperty(ORDER_NUMBER, value);
		save();
	}

	public void setPitch(NotePitch pitch) {
		note.setUnindexedProperty(PITCH, pitch);
		save();
	}

	public int getOrderNumber() {
		return ((Long) note.getProperty(ORDER_NUMBER)).intValue();
	}

	public NotePitch getPitch() {
		return NotePitch.valueOf((String) note.getProperty(PITCH));
	}

	public boolean isDot() {
		return (boolean) note.getProperty(WITH_DOT);
	}

	public boolean isTriola() {
		return (boolean) note.getProperty(TRIOLA);
	}

	public boolean isAkcent() {
		return (boolean) note.getProperty(AKCENT);
	}

	public Dynamic getDynamic() {
		return Dynamic.valueOf((String) note.getProperty(DYNAMIC));
	}

	public Accidental getAccidental() {
		return Accidental.valueOf((String) note.getProperty(ACCIDENTAL));
	}
}