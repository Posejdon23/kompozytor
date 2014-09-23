package com.kamilu.kompozytor.mycomponent.client.drawingcanvas;

import com.vaadin.shared.communication.ServerRpc;

public interface DrawingCanvasServerRpc extends ServerRpc {

	// public void clicked(MouseEventDetails mouseDetails);

	public void add(String vo);

	public void remove(String vo);

}
