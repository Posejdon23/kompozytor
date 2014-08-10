package com.kamilu.kompozytor.utils;

import static com.kamilu.kompozytor.propenums.Accidental.*;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.propenums.Accidental;
import com.kamilu.kompozytor.propenums.Dynamic;
import com.kamilu.kompozytor.propenums.NotePitch;
import com.kamilu.kompozytor.propenums.NoteValue;

public class NoteFactory {
	public static Note createPause(Key tactKey, NoteValue noteValue) {
		Entity pauseEntity = new Entity(Note.KIND);
		prepareNote(pauseEntity, tactKey, noteValue, 0L, Brak, NotePitch.Rest);
		DataStoreWrapper.save(pauseEntity);
		return new Note(pauseEntity);
	}

	public static Note createNewNote(Key tactKey, Note lastNote, NoteValue value) {
		Entity noteEntity = new Entity(Note.KIND);
		prepareNote(noteEntity, tactKey, value,
				(long) lastNote.getOrderNumber() + 1, Brak, NotePitch.c1);
		DataStoreWrapper.save(noteEntity);
		return new Note(noteEntity);
	}

	private static Key prepareNote(Entity note, Key tactKey,
			NoteValue noteValue, Long orderNumber, Accidental accid,
			NotePitch pitch) {
		note.setProperty(Note.ID_TAKTU, tactKey.getId());
		note.setProperty(Note.ORDER_NUMBER, orderNumber);
		note.setUnindexedProperty(Note.VALUE, noteValue.toString());
		note.setUnindexedProperty(Note.PITCH, pitch.toString());
		note.setUnindexedProperty(Note.DYNAMIC, Dynamic.f.toString());
		note.setUnindexedProperty(Note.ACCIDENTAL, accid.toString());
		note.setUnindexedProperty(Note.TRIOLA, false);
		note.setUnindexedProperty(Note.WITH_DOT, false);
		note.setUnindexedProperty(Note.AKCENT, false);
		return DataStoreWrapper.save(note);
	}
}
