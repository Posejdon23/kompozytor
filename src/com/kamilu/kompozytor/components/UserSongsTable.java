package com.kamilu.kompozytor.components;

import static com.vaadin.server.Sizeable.Unit.*;
import static com.vaadin.ui.Alignment.*;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.entities.Note;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.entities.Tact;
import com.kamilu.kompozytor.entities.Voice;
import com.kamilu.kompozytor.utils.EntityTable;
import com.kamilu.kompozytor.utils.TableDataLoader;
import com.kamilu.kompozytor.views.EditView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

@SuppressWarnings("serial")
public class UserSongsTable extends VerticalLayout {

	private final Table songs;
	private final Button newSong, editSong, deleteSong;

	public UserSongsTable(Long userId) {
		newSong = new Button("Nowy", new NewSong());
		editSong = new Button("Edytuj", new EditSong());
		deleteSong = new Button("Usuñ", new DeleteSong());
		HorizontalLayout horizTop = new HorizontalLayout(newSong, editSong,
				deleteSong);
		this.songs = new EntityTable.Builder()//
				.kind(Song.KIND)//
				.build();
		songs.setSizeFull();
		songs.setCaption("Lista utworów");
		addComponents(horizTop, songs);
		setComponentAlignment(songs, TOP_CENTER);
		setWidth(CompositorEditor.WIDTH, PIXELS);
	}

	public void reloadData() {
		TableDataLoader.loadData(songs);
	}

	private final class NewSong implements ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			getUI().getNavigator().navigateTo(EditView.VIEW);
		}
	}

	private final class DeleteSong implements ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			Key key = (Key) songs.getValue();
			if (key == null) {
				Notification.show("Zaznacz utwór do usuniêcia",
						Type.WARNING_MESSAGE);
			} else {
				songs.removeItem(key);

				for (Entity voice : DataStoreWrapper.getChildren(Voice.KIND,
						Voice.SONG_ID, key)) {
					for (Entity tact : DataStoreWrapper.getChildren(Tact.KIND,
							Tact.VOICE_ID, voice.getKey())) {
						for (Entity note : DataStoreWrapper.getChildren(
								Note.KIND, Note.TACT_ID, tact.getKey())) {
							DataStoreWrapper.delete(note.getKey());
						}
						DataStoreWrapper.delete(tact.getKey());
					}
					DataStoreWrapper.delete(voice.getKey());
				}
				DataStoreWrapper.delete(key);
			}
		}
	}

	private final class EditSong implements ClickListener {
		@Override
		public void buttonClick(ClickEvent event) {
			Key key = (Key) songs.getValue();
			if (key == null) {
				Notification.show("Zaznacz utwór do edycji",
						Type.WARNING_MESSAGE);
				return;
			}
			getUI().getNavigator()
					.navigateTo(EditView.VIEW + "/" + key.getId());
		}
	}
}
