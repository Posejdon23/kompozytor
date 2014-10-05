package com.kamilu.kompozytor.mycomponent.client.drawingcanvas;

import com.vaadin.shared.communication.ClientRpc;

public interface DrawingCanvasClientRpc extends ClientRpc {

	// public void alert(String message);

	void drawObject(int y, int width, int height, String url);

	void remove(String object);

	void setHeight(int height);

	void drawStave();

	void setWidth(int width);

	void drawTactBar(int number);

}