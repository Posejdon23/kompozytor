package com.kamilu.kompozytor.mycomponent.client;

import org.vaadin.gwtgraphics.client.VectorObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@SuppressWarnings("serial")
@Connect(com.kamilu.kompozytor.mycomponent.DrawingCanvas.class)
public class DrawingCanvasConnector extends AbstractComponentConnector {
	@Override
	protected Widget createWidget() {
		return GWT.create(DrawingCanvasWidget.class);
	}

	@Override
	public DrawingCanvasWidget getWidget() {
		return (DrawingCanvasWidget) super.getWidget();
	}

	public VectorObject add(VectorObject vo) {
		return getWidget().add(vo);
	}

}
