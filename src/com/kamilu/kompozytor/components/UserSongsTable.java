package com.kamilu.kompozytor.components;

import static com.kamilu.kompozytor.KompozytorUI.*;
import static com.vaadin.server.Sizeable.Unit.*;
import static com.vaadin.ui.Alignment.*;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.kamilu.kompozytor.KompozytorUI;
import com.kamilu.kompozytor.drawers.SongDrawer;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.utils.EntityTable;
import com.kamilu.kompozytor.utils.TableDataLoader;
import com.kamilu.kompozytor.views.EditView;
import com.kamilu.kompozytor.views.ShowView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;

@SuppressWarnings("serial")
public class UserSongsTable extends VerticalLayout {

	private final Table songs;
	private final Button newSong, editSong, deleteSong, showSong;

	public UserSongsTable(Long userId) {
		newSong = new Button("Nowy", newLis);
		editSong = new Button("Edytuj", editLis);
		showSong = new Button("Wyœwietl", showLis);
		deleteSong = new Button("Usuñ", deleteLis);
		newSong.setStyleName(MENU_CLEAR);
		editSong.setStyleName(MENU_CLEAR);
		showSong.setStyleName(MENU_CLEAR);
		deleteSong.setStyleName(MENU_CLEAR);
		HorizontalLayout horizTop = new HorizontalLayout(newSong, editSong,
				showSong, deleteSong);
		this.songs = new EntityTable.Builder()//
				.kind(Song.KIND)//
				.build();
		songs.setSizeFull();
		songs.setCaption("Lista utworów");
		addComponents(horizTop, songs);
		setComponentAlignment(songs, TOP_CENTER);
		setWidth(SongDrawer.WIDTH, PIXELS);
	}

	public void reloadData() {
		TableDataLoader.loadData(songs);
	}

	Button.ClickListener newLis = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			getUI().getNavigator().navigateTo(EditView.VIEW);
		}
	};

	Button.ClickListener showLis = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			Key key = (Key) songs.getValue();
			if (key == null) {
				Notification.show("Zaznacz utwór do wyœwietlenia",
						Type.WARNING_MESSAGE);
				return;
			}
			getUI().getNavigator()
					.navigateTo(ShowView.VIEW + "/" + key.getId());
		}
	};

	Button.ClickListener editLis = new ClickListener() {

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
	};

	Button.ClickListener deleteLis = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			Key key = (Key) songs.getValue();
			if (key == null) {
				Notification.show("Zaznacz utwór do usuniêcia",
						Type.WARNING_MESSAGE);
			} else {
				Entity entity = new Entity(key);
				Song utwor = new Song(entity);
				utwor.deleteWithChildren();
				songs.removeItem(key);
			}
		}
	};

}
