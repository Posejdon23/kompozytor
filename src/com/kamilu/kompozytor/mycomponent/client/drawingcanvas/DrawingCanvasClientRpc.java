package com.kamilu.kompozytor.mycomponent.client.drawingcanvas;

import com.vaadin.shared.communication.ClientRpc;

public interface DrawingCanvasClientRpc extends ClientRpc {

	// public void alert(String message);

	public void add(String vo);

	public void remove(String vo);

}