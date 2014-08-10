package com.kamilu.kompozytor.utils;

import static com.kamilu.kompozytor.propenums.NoteValue.*;

import com.google.appengine.api.datastore.Entity;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.entities.Tact;

public class TactFactory {
	public static Tact createNewTact(Tact lastTact) {
		Entity tactEntity = new Entity(Tact.KIND);
		tactEntity.setProperty(Tact.VOICE_ID, lastTact.getIdGlosu());
		tactEntity.setProperty(Tact.ORDER_NUMBER,
				(long) lastTact.getOrderNumber() + 1);
		tactEntity.setUnindexedProperty(Tact.METRUM, lastTact.getMetrum()
				.toString());
		tactEntity.setUnindexedProperty(Tact.TONATION, lastTact.getTonation()
				.toString());
		tactEntity.setUnindexedProperty(Tact.CLEF, lastTact.getClef()
				.toString());
		tactEntity.setUnindexedProperty(Tact.TEMPO, (long) lastTact.getTempo());
		DataStoreWrapper.save(tactEntity);
		Note wholePause = NoteFactory.createPause(tactEntity.getKey(),
				WholeNote);
		Tact tact = new Tact(tactEntity);
		// tact.addNuta(wholePause);
		return tact;
	}
}
