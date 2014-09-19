package com.kamilu.kompozytor.views;

import static com.vaadin.ui.Alignment.*;

import com.google.appengine.api.datastore.Entity;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.kamilu.kompozytor.components.CompositorEditor;
import com.kamilu.kompozytor.entities.Song;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ShowView extends VerticalLayout implements View {
	public static final String VIEW = "show";

	public ShowView() {
	}

	@Override
	public void enter(ViewChangeEvent event) {
		String parameter = event.getParameters();
		long articleId = Long.parseLong(parameter);
		Entity songEntity = DataStoreWrapper.getById(Song.KIND, articleId);
		Song song = new Song(songEntity);
		CompositorEditor compositor = new CompositorEditor(song);
		removeAllComponents();
		addComponent(compositor);
		setComponentAlignment(compositor, TOP_CENTER);
		compositor.drawSong();
	}

}
