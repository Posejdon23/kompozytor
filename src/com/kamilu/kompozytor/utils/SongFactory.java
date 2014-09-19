package com.kamilu.kompozytor.utils;

import static com.kamilu.kompozytor.entities.MusicObject.*;
import static com.kamilu.kompozytor.propenums.Accidental.*;
import static com.kamilu.kompozytor.propenums.NoteValue.*;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.entities.*;
import com.kamilu.kompozytor.propenums.*;

public class SongFactory {

	public static Song createNewSong(String name, String autor) {
		Entity songEntity = new Entity(Song.KIND);
		songEntity.setProperty(Song.NAME, name);
		songEntity.setProperty(Song.AUTHOR, autor);
		songEntity.setUnindexedProperty(TO_REMOVE, false);
		Key songKey = DataStoreWrapper.save(songEntity);
		Song song = new Song(songEntity);

		Entity voiceEntity = new Entity(Voice.KIND);
		voiceEntity.setUnindexedProperty(Voice.INSTRUMENT,
				Instrument.Trabka.toString());
		voiceEntity.setProperty(Voice.SONG_ID, songKey.getId());
		voiceEntity.setUnindexedProperty(NUMBER, 1L);
		voiceEntity.setUnindexedProperty(TO_REMOVE, false);
		Key voiceKey = DataStoreWrapper.save(voiceEntity);
		Voice voice = new Voice(voiceEntity);

		Entity tactEntity = new Entity(Tact.KIND);
		tactEntity.setProperty(Tact.VOICE_ID, voiceKey.getId());
		tactEntity.setProperty(NUMBER, 0L);
		tactEntity.setUnindexedProperty(Tact.METRUM, Metrum.Common.name());
		tactEntity.setUnindexedProperty(Tact.TONATION, Tonation.C_dur.name());
		tactEntity.setUnindexedProperty(Tact.CLEF, Clef.Violin.name());
		tactEntity.setUnindexedProperty(Tact.TEMPO, 120L);
		tactEntity.setUnindexedProperty(Tact.REPEAT_START, false);
		tactEntity.setUnindexedProperty(Tact.REPEAT_END, false);
		tactEntity.setUnindexedProperty(Tact.DOUBLE_LINE, false);
		tactEntity.setUnindexedProperty(Tact.REPEAT_COUNT, 0L);
		tactEntity.setUnindexedProperty(TO_REMOVE, false);
		Key tactKey = DataStoreWrapper.save(tactEntity);
		Tact tact = new Tact(tactEntity);

		Entity noteEntity = new Entity(Note.KIND);
		prepareNote(noteEntity, tactKey, NotePitch.Pause, WholeNote, 0L,
				Natural);
		Note note = new Note(noteEntity);
		tact.addNote(note);
		voice.addTact(tact);
		song.addVoice(voice);
		return song;
	}

	private static Key prepareNote(Entity note, Key tactKey, NotePitch pitch,
			NoteValue noteValue, Long orderNumber, Accidental accid) {
		note.setProperty(Note.TACT_ID, tactKey.getId());
		note.setProperty(NUMBER, orderNumber);
		note.setUnindexedProperty(Note.VALUE, noteValue.name());
		note.setUnindexedProperty(Note.PITCH, pitch.name());
		note.setUnindexedProperty(Note.DYNAMIC, Dynamic.f.name());
		note.setUnindexedProperty(Note.ACCIDENTAL, accid.name());
		note.setUnindexedProperty(Note.TRIOLA, false);
		note.setUnindexedProperty(Note.WITH_DOT, false);
		note.setUnindexedProperty(Note.AKCENT, false);
		note.setUnindexedProperty(TO_REMOVE, false);
		return DataStoreWrapper.save(note);
	}
}
