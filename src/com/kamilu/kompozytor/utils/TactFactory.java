package com.kamilu.kompozytor.utils;

import com.google.appengine.api.datastore.Entity;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.entities.MusicObject;
import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.entities.Tact;
import com.kamilu.kompozytor.propenums.Accidental;
import com.kamilu.kompozytor.propenums.NotePitch;
import com.kamilu.kompozytor.propenums.NoteValue;

public class TactFactory {
	public static Tact createNewTact(Tact lastTact) {
		Entity tactEntity = new Entity(Tact.KIND);
		tactEntity.setProperty(Tact.VOICE_ID, lastTact.getVoiceId());
		tactEntity.setProperty(Tact.NUMBER, (long) lastTact.getNumber() + 1);
		tactEntity.setUnindexedProperty(Tact.METRUM, lastTact.getMetrum()
				.name());
		tactEntity.setUnindexedProperty(Tact.TONATION, lastTact.getTonation()
				.name());
		tactEntity.setUnindexedProperty(Tact.CLEF, lastTact.getClef().name());
		tactEntity.setUnindexedProperty(Tact.TEMPO, (long) lastTact.getTempo());
		tactEntity.setUnindexedProperty(Tact.REPEAT_START, false);
		tactEntity.setUnindexedProperty(Tact.REPEAT_END, false);
		tactEntity.setUnindexedProperty(Tact.DOUBLE_LINE, false);
		tactEntity.setUnindexedProperty(Tact.REPEAT_COUNT, 0L);
		tactEntity.setUnindexedProperty(MusicObject.TO_REMOVE, false);

		Note wholePause = NoteFactory.createNewNote(tactEntity.getKey(),
				NoteValue.WholeNote, 0L, Accidental.Natural, NotePitch.Pause);
		DataStoreWrapper.save(tactEntity);
		Tact tact = new Tact(tactEntity);
		tact.addNote(wholePause);
		return tact;
	}
}
