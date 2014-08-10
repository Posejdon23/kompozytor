package com.kamilu.kompozytor.views;

import com.kamilu.kompozytor.components.UserSongsTable;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class MainView extends VerticalLayout implements View {
	public static final String VIEW = "main";
	private UserSongsTable songs;

	public MainView() {
		songs = new UserSongsTable(-1L);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		songs.reloadData();
		addComponent(songs);
		setComponentAlignment(songs, Alignment.TOP_CENTER);
	}

}
