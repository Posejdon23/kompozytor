package com.kamilu.kompozytor.views;

import com.kamilu.kompozytor.components.UserSongsTable;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings({ "serial" })
@Widgetset("com.kamilu.kompozytor.widgetset.KompozytorWidgetset.gwt.xml")
public class MainView extends VerticalLayout implements View {

	public static final String VIEW = "main";
	private UserSongsTable songs;
	private DrawingCanvas canvas;

	public MainView() {
		songs = new UserSongsTable(-1L);
		canvas = new DrawingCanvas();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		songs.reloadData();
		addComponent(songs);
		setComponentAlignment(songs, Alignment.TOP_CENTER);
		addComponent(canvas);
	}
}
