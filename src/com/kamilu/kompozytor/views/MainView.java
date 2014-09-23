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
	private DrawingCanvas canvas;
	private Button add, remove;
	private String objectAdded;

	public MainView() {
		songsTable = new UserSongsTable(-1L);
		canvas = new DrawingCanvas();
		add = new Button("Dodaj");
		remove = new Button("Usuñ");
		add.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				canvas.add("8");
				objectAdded = "circle";
			}
		});
		remove.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (objectAdded != null) {
					canvas.remove(objectAdded);
				}
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		songsTable.reloadData();
		addComponent(songsTable);
		setComponentAlignment(songsTable, Alignment.TOP_CENTER);
		addComponent(canvas);
		addComponent(add);
		addComponent(remove);
	}
}
