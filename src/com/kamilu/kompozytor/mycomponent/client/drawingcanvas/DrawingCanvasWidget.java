package com.kamilu.kompozytor.mycomponent.client.drawingcanvas;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Image;
import org.vaadin.gwtgraphics.client.Line;
import org.vaadin.gwtgraphics.client.VectorObject;

import com.google.gwt.user.client.ui.RootPanel;

public class DrawingCanvasWidget extends DrawingArea {

	private static final int END_X = 650;
	private static final int START_X = 50;
	public static final String CLASSNAME = "drawingcanvas";
	private Map<String, VectorObject> notes;

	public DrawingCanvasWidget() {
		super(700, 400);
		notes = new HashMap<String, VectorObject>();
		setStyleName(CLASSNAME);
		RootPanel.get().add(this);
	}

	@Override
	public void setHeight(int height) {
		setHeight(height);
	}

	public void drawObject(int x, int y, int width, int height, String url) {
		Image note = new Image(x, y, width, height, url);
		notes.put(url, note);
		add(note);
	}

	public void drawStave() {
		int y = 100;
		Line line1 = new Line(START_X, y - 10, END_X, y - 10);
		Line line2 = new Line(START_X, y - 5, END_X, y - 5);
		Line line3 = new Line(START_X, y, END_X, y);
		Line line4 = new Line(START_X, y + 5, END_X, y + 5);
		Line line5 = new Line(START_X, y + 10, END_X, y + 10);
		line1.setStrokeColor("black");
		line2.setStrokeColor("black");
		line3.setStrokeColor("black");
		line4.setStrokeColor("black");
		line5.setStrokeColor("black");
		add(line1);
		add(line2);
		add(line3);
		add(line4);
		add(line5);
	}

	public void drawTactBar(int x, int y) {
		Line lineBar = new Line(x, y - 10, x, y + 10);
		add(lineBar);
	}
}