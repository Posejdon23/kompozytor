package com.kamilu.kompozytor.utils;

import static com.kamilu.kompozytor.entities.MusicObject.*;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.entities.MusicObject;
import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.propenums.Accidental;
import com.kamilu.kompozytor.propenums.Dynamic;
import com.kamilu.kompozytor.propenums.NotePitch;
import com.kamilu.kompozytor.propenums.NoteValue;

public class NoteFactory {

	public static Note createNewNote(Key tactKey, NoteValue noteValue,
			Long orderNumber, Accidental accid, NotePitch pitch) {
		Entity note = new Entity(Note.KIND);
		if (tactKey != null) {
			note.setProperty(Note.TACT_ID, tactKey.getId());
		}
		note.setProperty(NUMBER, orderNumber);
		note.setUnindexedProperty(Note.VALUE, noteValue.toString());
		note.setUnindexedProperty(Note.PITCH, pitch.toString());
		note.setUnindexedProperty(Note.DYNAMIC, Dynamic.f.toString());
		note.setUnindexedProperty(Note.ACCIDENTAL, accid.toString());
		note.setUnindexedProperty(Note.TRIOLA, false);
		note.setUnindexedProperty(Note.WITH_DOT, false);
		note.setUnindexedProperty(Note.AKCENT, false);
		note.setUnindexedProperty(TO_REMOVE, false);
		DataStoreWrapper.save(note);
		return new Note(note);
	}
}
