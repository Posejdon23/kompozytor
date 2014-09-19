package com.kamilu.kompozytor.mycomponent.client;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.VectorObject;

public class DrawingCanvasWidget extends DrawingArea {

	public DrawingCanvasWidget() {
		super(100, 100);
		setStyleName("drawingcanvas");
	}

	public DrawingCanvasWidget(int width, int height) {
		super(width, height);
		setStyleName("drawingcanvas");
	}

	public VectorObject add(VectorObject vo) {
		return add(vo);
	}

}
