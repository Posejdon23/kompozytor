package com.kamilu.kompozytor.mycomponent;

import com.kamilu.kompozytor.mycomponent.client.drawingcanvas.DrawingCanvasClientRpc;
import com.kamilu.kompozytor.mycomponent.client.drawingcanvas.DrawingCanvasServerRpc;
import com.kamilu.kompozytor.mycomponent.client.drawingcanvas.DrawingCanvasState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class DrawingCanvas extends AbstractComponent {

	public DrawingCanvas() {
		registerRpc(rpc);
	}

	@Override
	public DrawingCanvasState getState() {
		return (DrawingCanvasState) super.getState();
	}

	public void drawObject(int y, int width, int height, String url) {
		rpc.drawObject(y, width, height, url);
	}

	public void remove(String vo) {
		rpc.remove(vo);
	}

	public void drawStave() {
		rpc.drawStave();
	}

	public void setHeight(int height) {
		getRpcProxy(DrawingCanvasClientRpc.class).setHeight(height);
	}

	public float getHeight() {
		return getState().wys;
	}

	private DrawingCanvasServerRpc rpc = new DrawingCanvasServerRpc() {
		// private int clickCount = 0;

		// public void clicked(MouseEventDetails mouseDetails) {
		// getRpcProxy(DrawingCanvasClientRpc.class).alert(
		// "Ok, that's enough!");
		// getState().text = "You have clicked " + clickCount + " times";
		// }

		@Override
		public void drawObject(int y, int width, int height, String url) {
			getRpcProxy(DrawingCanvasClientRpc.class).drawObject(y, width, height,
					url);
		}

		@Override
		public void remove(String vo) {
			getRpcProxy(DrawingCanvasClientRpc.class).remove(vo);
		}

		@Override
		public void setHeight(int height) {
			getRpcProxy(DrawingCanvasClientRpc.class).setHeight(height);
		}

		@Override
		public void setWidth(int width) {
			getRpcProxy(DrawingCanvasClientRpc.class).setWidth(width);
		}

		@Override
		public void drawStave() {
			getRpcProxy(DrawingCanvasClientRpc.class).drawStave();
		}
	};

	public void drawTactBar(int number) {
		getRpcProxy(DrawingCanvasClientRpc.class).drawTactBar(number);
	}

	public void moveRight(int distance) {
		getState().setX(getState().getX() + distance);
	}

}
