package com.kamilu.kompozytor.utils;

import static com.kamilu.kompozytor.propenums.Metrum.*;
import static com.kamilu.kompozytor.propenums.NotePitch.*;
import static com.kamilu.kompozytor.propenums.NoteValue.*;
import static com.kamilu.kompozytor.propenums.Tonation.*;
import static com.kamilu.kompozytor.propenums.Accidental.*;
import static com.kamilu.kompozytor.propenums.Clef.*;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.entities.Voice;
import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.entities.Tact;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.propenums.*;

public class SongFactory {

	public static Song createNewUtwor(String name, String autor) {
		Entity songEntity = new Entity(Song.KIND);
		songEntity.setProperty(Song.NAME, name);
		songEntity.setProperty(Song.AUTHOR, autor);
		Key songKey = DataStoreWrapper.save(songEntity);
		Song song = new Song(songEntity);
		Entity voiceEntity = new Entity(Voice.KIND);
		voiceEntity.setUnindexedProperty(Voice.INSTRUMENT,
				Instrument.Trabka.toString());
		voiceEntity.setProperty(Voice.SONG_ID, songKey.getId());
		voiceEntity.setUnindexedProperty(Voice.NUMBER, 1L);
		Key voiceKey = DataStoreWrapper.save(voiceEntity);
		Voice voice = new Voice(voiceEntity);
		Entity tactEntity = new Entity(Tact.KIND);
		tactEntity.setProperty(Tact.VOICE_ID, voiceKey.getId());
		tactEntity.setProperty(Tact.ORDER_NUMBER, 0L);
		tactEntity.setUnindexedProperty(Tact.METRUM, Metrum.Common.toString());
		tactEntity.setUnindexedProperty(Tact.TONATION,
				Tonation.C_dur.toString());
		tactEntity.setUnindexedProperty(Tact.CLEF, Clef.Wiolinowy.toString());
		tactEntity.setUnindexedProperty(Tact.TEMPO, 120L);
		DataStoreWrapper.save(tactEntity);
		Tact tact = new Tact(tactEntity);
		voice.addTact(tact);
		song.addVoice(voice);
		return song;
	}

	public static Song createSampleUtwor() {
		Entity utworEntity = new Entity(Song.KIND);
		Key utworKey = prepareUtwor(utworEntity);
		Entity glosEntity = new Entity(Voice.KIND);
		Key glosKey = prepareTrack(glosEntity, utworKey);
		Voice glos = new Voice(glosEntity);

		Entity emptyTactEntity = new Entity(Tact.KIND);
		prepareTakt(emptyTactEntity, glosKey, 0L);
		Tact firstEmpyTact = new Tact(emptyTactEntity);
		Entity taktEntity = new Entity(Tact.KIND);
		Key taktKey = prepareTakt(taktEntity, glosKey, 0L);
		Tact takt = new Tact(taktEntity);
		Entity taktEntity2 = new Entity(Tact.KIND);
		Key taktKey2 = prepareTakt(taktEntity2, glosKey, 1L);
		Tact takt2 = new Tact(taktEntity2);
		Entity nutaEntity = new Entity(Note.KIND);
		prepareNote(nutaEntity, taktKey, c1, QuarterNote, 1L, Krzyzyk);
		Note nuta = new Note(nutaEntity);
		Entity nutaEntity2 = new Entity(Note.KIND);
		prepareNote(nutaEntity2, taktKey, c1, QuarterNote, 2L, Brak);
		Note nuta2 = new Note(nutaEntity2);
		Entity nutaEntity3 = new Entity(Note.KIND);
		prepareNote(nutaEntity3, taktKey, g1, QuarterNote, 3L, Bemol);
		Note nuta3 = new Note(nutaEntity3);
		Entity nutaEntity4 = new Entity(Note.KIND);
		prepareNote(nutaEntity4, taktKey, g1, QuarterNote, 4L, Brak);
		Note nuta4 = new Note(nutaEntity4);
		Entity nutaEntity5 = new Entity(Note.KIND);
		prepareNote(nutaEntity5, taktKey2, a1, QuarterNote, 5L, Brak);
		Note nuta5 = new Note(nutaEntity5);
		Entity nutaEntity6 = new Entity(Note.KIND);
		prepareNote(nutaEntity6, taktKey2, a1, QuarterNote, 6L, Brak);
		Note nuta6 = new Note(nutaEntity6);
		Entity nutaEntity7 = new Entity(Note.KIND);
		prepareNote(nutaEntity7, taktKey2, g1, HalfNote, 7L, Brak);
		Note nuta7 = new Note(nutaEntity7);
		Song utwor = new Song(utworEntity);
		utwor.addVoice(glos);
		glos.addTact(firstEmpyTact);
		glos.addTact(takt);
		glos.addTact(takt2);
		takt.addNote(nuta);
		takt.addNote(nuta2);
		takt.addNote(nuta3);
		takt.addNote(nuta4);
		takt2.addNote(nuta5);
		takt2.addNote(nuta6);
		takt2.addNote(nuta7);
		return utwor;
	}

	private static Key prepareUtwor(Entity utwor) {
		utwor.setProperty(Song.NAME, "Oda do Agaty");
		utwor.setProperty(Song.AUTHOR, "Kamil");
		return DataStoreWrapper.save(utwor);
	}

	private static Key prepareTrack(Entity track, Key utworKey) {
		track.setUnindexedProperty(Voice.INSTRUMENT,
				Instrument.Trabka.toString());
		track.setProperty(Voice.SONG_ID, utworKey.getId());
		return DataStoreWrapper.save(track);
	}

	private static Key prepareTakt(Entity takt, Key glosKey, Long orderNumber) {
		takt.setProperty(Tact.VOICE_ID, glosKey.getId());
		takt.setProperty(Tact.ORDER_NUMBER, orderNumber);
		takt.setUnindexedProperty(Tact.METRUM, Common.toString());
		takt.setUnindexedProperty(Tact.TONATION, C_dur.toString());
		takt.setUnindexedProperty(Tact.CLEF, Wiolinowy.toString());
		takt.setUnindexedProperty(Tact.TEMPO, 120L);
		return DataStoreWrapper.save(takt);
	}

	private static Key prepareNote(Entity note, Key tactKey, NotePitch pitch,
			NoteValue noteValue, Long orderNumber, Accidental accid) {
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
