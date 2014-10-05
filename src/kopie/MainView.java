package com.kamilu.kompozytor.views;

import com.kamilu.kompozytor.components.UserSongsTable;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings({ "serial" })
@Widgetset("com.kamilu.kompozytor.widgetset.KompozytorWidgetset.gwt.xml")
public class MainView extends VerticalLayout implements View {

	public static final String VIEW = "main";
	private UserSongsTable songsTable;

	public MainView() {
		songsTable = new UserSongsTable(-1L);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		songsTable.reloadData();
		addComponent(songsTable);
		setComponentAlignment(songsTable, Alignment.TOP_CENTER);
	}
}
