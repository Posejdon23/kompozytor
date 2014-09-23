package com.kamilu.kompozytor.mycomponent;

import com.kamilu.kompozytor.mycomponent.client.drawingcanvas.DrawingCanvasClientRpc;
import com.kamilu.kompozytor.mycomponent.client.drawingcanvas.DrawingCanvasServerRpc;
import com.kamilu.kompozytor.mycomponent.client.drawingcanvas.DrawingCanvasState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class DrawingCanvas extends AbstractComponent {

	private DrawingCanvasServerRpc rpc = new DrawingCanvasServerRpc() {
		// private int clickCount = 0;

		// public void clicked(MouseEventDetails mouseDetails) {
		// getRpcProxy(DrawingCanvasClientRpc.class).alert(
		// "Ok, that's enough!");
		// getState().text = "You have clicked " + clickCount + " times";
		// }

		@Override
		public void add(String vo) {
			getRpcProxy(DrawingCanvasClientRpc.class).add(vo);
		}

		@Override
		public void remove(String vo) {
			getRpcProxy(DrawingCanvasClientRpc.class).remove(vo);
		}
	};

	public DrawingCanvas() {
		registerRpc(rpc);
	}

	@Override
	public DrawingCanvasState getState() {
		return (DrawingCanvasState) super.getState();
	}

	public void add(String vo) {
		rpc.add(vo);
	}

	public void remove(String vo) {
		rpc.remove(vo);
	}
}
