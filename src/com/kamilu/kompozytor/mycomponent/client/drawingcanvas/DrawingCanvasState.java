package com.kamilu.kompozytor.mycomponent.client.drawingcanvas;

import com.vaadin.shared.AbstractComponentState;

@SuppressWarnings("serial")
public class DrawingCanvasState extends AbstractComponentState {

	private int x = 50, y = 100;
	public int szer = 700, wys = 400;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}