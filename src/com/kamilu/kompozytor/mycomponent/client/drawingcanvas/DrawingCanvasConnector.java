package com.kamilu.kompozytor.mycomponent.client.drawingcanvas;

import org.vaadin.gwtgraphics.client.Image;
import org.vaadin.gwtgraphics.client.VectorObject;
import org.vaadin.gwtgraphics.client.shape.Circle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
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
		public void add(String vo) {
			if (vo.equals("circle")) {
				Circle circle = new Circle(200, 200, 200);
				circle.setFillColor("red");
				getWidget().add(circle);
				objectAdded = circle;
			} else if (vo.equals("8")) {
				Image eighthNote = new Image(
						100,
						100,
						64,
						64,
						"https://dl-web.dropbox.com/get/eighthNote.svg?_subject_uid=338188398&w=AABzdHDeCBrZjpJheKmXadgwk507wSMr_5Of2ycWX3IQHA");
				getWidget().add(eighthNote);
				objectAdded = eighthNote;
			}
		}

		@Override
		public void remove(String vo) {
			getWidget().remove(objectAdded);

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
