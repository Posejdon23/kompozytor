package com.kamilu.kompozytor.mycomponent.client.drawingcanvas;

import com.vaadin.shared.communication.ServerRpc;

public interface DrawingCanvasServerRpc extends ServerRpc {

	// public void clicked(MouseEventDetails mouseDetails);

	void drawObject(int y, int width, int height, String url);

	void remove(String vo);

	void setHeight(int height);

	void setWidth(int width);

	void drawStave();

}
