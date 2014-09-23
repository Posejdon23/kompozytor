package com.kamilu.kompozytor.mycomponent.client.drawingcanvas;

import org.vaadin.gwtgraphics.client.DrawingArea;


import com.google.gwt.user.client.ui.RootPanel;

public class DrawingCanvasWidget extends DrawingArea {

	public static final String CLASSNAME = "drawingcanvas";

	public DrawingCanvasWidget() {
		super(500, 500);
		setStyleName(CLASSNAME);
		RootPanel.get().add(this);
	}

}