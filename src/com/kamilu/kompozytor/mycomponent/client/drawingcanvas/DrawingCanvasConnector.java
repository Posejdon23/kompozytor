package com.kamilu.kompozytor.mycomponent.client.drawingcanvas;

import org.vaadin.gwtgraphics.client.VectorObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.kamilu.kompozytor.utils.SpaceDists;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

@SuppressWarnings("serial")
@Connect(DrawingCanvas.class)
public class DrawingCanvasConnector extends AbstractComponentConnector {

	private VectorObject objectAdded;

	private final class DrawingCanvasClientRpcImplementation implements
			DrawingCanvasClientRpc {

		@Override
		public void drawObject(int y, int width, int height, String url) {
			int x = getState().getX();
			int yPos = getState().getY();
			getWidget().drawObject(x, yPos + y, width, height, url);
			getState().setX(x + width + SpaceDists.SPACE);
		}

		@Override
		public void remove(String object) {
			getWidget().remove(objectAdded);
		}

		@Override
		public void setHeight(int height) {
			getWidget().setHeight(height);
		}

		@Override
		public void drawStave() {
			getWidget().drawStave();
		}

		@Override
		public void setWidth(int width) {
			getWidget().setWidth(width);
		}

		@Override
		public void drawTactBar(int number) {
			int x = getState().getX();
			int y = getState().getY();
			getWidget().drawTactBar(x, y);
			getState().setX(x + SpaceDists.SPACE);
		}
	}

	DrawingCanvasServerRpc rpc = RpcProxy.create(DrawingCanvasServerRpc.class,
			this);

	public DrawingCanvasConnector() {
		DrawingCanvasClientRpcImplementation dccRPCImpl = new DrawingCanvasClientRpcImplementation();
		registerRpc(DrawingCanvasClientRpc.class, dccRPCImpl);

		// getWidget().addClickHandler(new ClickHandler() {
		// public void onClick(ClickEvent event) {
		// final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
		// .buildMouseEventDetails(event.getNativeEvent(),
		// getWidget().getElement());
		// rpc.clicked(mouseDetails);
		// }
		// });

	}

	@Override
	protected Widget createWidget() {
		return GWT.create(DrawingCanvasWidget.class);
	}

	@Override
	public DrawingCanvasWidget getWidget() {
		return (DrawingCanvasWidget) super.getWidget();
	}

	@Override
	public DrawingCanvasState getState() {
		return (DrawingCanvasState) super.getState();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);
		// final String text = getState().text;
		// getWidget().setText(text);
	}

}
