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
	public static final String ORDER_NUMBER = "order_number";
	private Entity tact;
	private List<Note> notes;

	public Tact(Entity tact) {
		this.tact = tact;
		// EntityUtils.printEntity(tact);
		loadNotes();
	}

	public void save() {
		DataStoreWrapper.save(tact);
		for (Note nuta : notes) {
			nuta.save();
		}
	}

	public void deleteWithChildren() {
		DataStoreWrapper.delete(tact);
		for (Note note : notes) {
			note.delete();
		}
	}

	public void loadNotes() {
		notes = new ArrayList<Note>();
		List<Entity> noteNtts = DataStoreWrapper.getChildrenWithSortColumn(
				Note.KIND, Note.ID_TAKTU, getKey(), Note.ORDER_NUMBER);
		for (Entity noteNtt : noteNtts) {
			Note note = new Note(noteNtt);
			notes.add(note);
		}
	}

	public void removeNote(int index) {
		if (index == -1) {
			Notification.show("Zaznacz nutê", WARNING_MESSAGE);
			return;
		}
		notes.get(index).delete();
		notes.remove(index);
		updateOrderNumbersFromIndex(index);
	}

	private void updateOrderNumbersFromIndex(int index) {
		for (int i = index; i < notes.size(); i++) {
			notes.get(i).setOrderNumber((long)i);
			;
		}
	}

	public int getTempo() {
		return ((Long) tact.getProperty(TEMPO)).intValue();
	}

	public Metrum getMetrum() {
		return Metrum.valueOf((String) tact.getProperty(METRUM));
	}

	public Tonation getTonation() {
		return Tonation.valueOf((String) tact.getProperty(TONATION));
	}

	public int getOrderNumber() {
		return ((Long) tact.getProperty(ORDER_NUMBER)).intValue();
	}

	public void setOrderNumber(Long orderNumber) {
		tact.setProperty(ORDER_NUMBER, orderNumber);
	}

	public List<Note> getNotes() {
		return notes;
	}

	public Note getNote(int index) {
		return notes.get(index);
	}

	public long getIdGlosu() {
		return (long) tact.getProperty(Tact.VOICE_ID);
	}

	public Clef getClef() {
		return Clef.valueOf((String) tact.getProperty(CLEF));
	}

	public Key getKey() {
		return tact.getKey();
	}

	public double getDlugoscTaktu() {
		return getMetrum().getDlugoscTaktu();
	}

	public void addNote(Note nuta) {
		notes.add(nuta);
	}

	public void setClef(String clef) {
		tact.setUnindexedProperty(Tact.CLEF, clef);
	}

	public void setTonation(String tonation) {
		tact.setUnindexedProperty(Tact.TONATION, tonation);
	}

	public void setTempo(int tempo) {
		tact.setUnindexedProperty(TEMPO, (long) tempo);
	}
}
