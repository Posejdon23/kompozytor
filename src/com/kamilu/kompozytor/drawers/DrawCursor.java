package com.kamilu.kompozytor.drawers;

import static com.vaadin.server.Sizeable.Unit.*;

import com.kamilu.kompozytor.components.CompositorEditor;
import com.kamilu.kompozytor.mycomponent.DrawingCanvas;
import com.vaadin.server.Sizeable.Unit;

@SuppressWarnings("serial")
public class DrawCursor implements Drawer {
	public static final float ROW_WIDTH = 830;
	public static final float ROWS_SPACE = 130;
	private static final int X_START = 10;
	private static final int Y_START = 130;
	private DrawingCanvas canvas;
	private int xPos, yPos;
	private TactField currTactField;
	private NoteField currNoteField;

	public DrawCursor(DrawingCanvas canvas) {
		this.xPos = X_START;
		this.yPos = Y_START;
		this.canvas = canvas;
	}

	public DrawingCanvas getCanvas() {
		return canvas;
	}

	public float getYPos() {
		return yPos;
	}

	public float getXPos() {
		return xPos;
	}

	public void moveRight(float widthToAdd) {
		xPos += widthToAdd;
		checkBounds();
	}

	private void checkBounds() {
		if (xPos > 830) {
			moveToNextLine();
		}
	}

	private void moveToNextLine() {
		addYPos(ROWS_SPACE);
		setXPos(X_START);
		drawStave();
		canvas.setHeight(canvas.getHeight() + 200, Unit.PIXELS);
	}

	private void addYPos(float heightToAdd) {
		yPos += heightToAdd;
	}

	private void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public void drawStave() {

		// canvas.fillRect(getXPos(), getYPos() - 20, ROW_WIDTH, 1);
		// canvas.fillRect(getXPos(), getYPos() - 10, ROW_WIDTH, 1);
		// canvas.fillRect(getXPos(), getYPos(), ROW_WIDTH, 1);
		// canvas.fillRect(getXPos(), getYPos() + 10, ROW_WIDTH, 1);
		// canvas.fillRect(getXPos(), getYPos() + 20, ROW_WIDTH, 1);
	}

	public void reset() {
		xPos = X_START;
		yPos = Y_START;
		canvas.setHeight(CompositorEditor.HEIGHT, PIXELS);
	}

	public void drawTactSelectBox(TactField tactField, int mouseX, int mouseY) {
		currTactField = tactField;
		// canvas.setFillStyle("#0000ee");
		float start = tactField.getStart();
		float end = tactField.getEnd();
		float yCenter = tactField.getYCenter();
		float yUp = (float) (yCenter - (TactDrawer.TAKT_HEIGHT / 2));
		float yDown = (float) (yCenter + (TactDrawer.TAKT_HEIGHT / 2));
		if (start < end) {
			// canvas.fillRect(start, yUp, LINE_WIDTH, TactDrawer.TAKT_HEIGHT);
			// canvas.fillRect(end, yUp, LINE_WIDTH, TAKT_HEIGHT);
			// canvas.fillRect(start, yDown, end - start, LINE_WIDTH);
			// canvas.fillRect(start, yUp, end - start, LINE_WIDTH);
		} else {
			if (mouseX > start) {
				yUp += ROWS_SPACE;
				yDown += ROWS_SPACE;
			} else {
				yUp -= ROWS_SPACE;
				yDown -= ROWS_SPACE;
			}
			// canvas.fillRect(start, yUp, LINE_WIDTH, TAKT_HEIGHT);
			// canvas.fillRect(end, yUp + ROWS_SPACE, LINE_WIDTH, TAKT_HEIGHT);
			//
			// canvas.fillRect(start, yUp, ROW_WIDTH - start, LINE_WIDTH);
			// canvas.fillRect(start, yDown, ROW_WIDTH - start, LINE_WIDTH);
			// canvas.fillRect(X_START, yUp + ROWS_SPACE, end, LINE_WIDTH);
			// canvas.fillRect(X_START, yDown + ROWS_SPACE, end, LINE_WIDTH);
		}
		// canvas.setFillStyle("#000000");
	}

	public void drawNoteSelectBox(NoteField noteField, int mouseX, int mouseY) {
		if (noteField == null) {
			currNoteField = null;
			return;
		}
		currNoteField = noteField;
		// canvas.setFillStyle("#ff0000");
		float start = noteField.getStart();
		float end = noteField.getEnd();
		float yCenter = noteField.getYCenter();
		float yUp = (float) (yCenter - (TactDrawer.TAKT_HEIGHT / 2));
		float yDown = (float) (yCenter + (TactDrawer.TAKT_HEIGHT / 2));
		if (start < end) {
			// canvas.fillRect(start, yUp, LINE_WIDTH, TactDrawer.TAKT_HEIGHT);
			// canvas.fillRect(end, yUp, LINE_WIDTH, TAKT_HEIGHT);
			// canvas.fillRect(start, yDown, end - start, LINE_WIDTH);
			// canvas.fillRect(start, yUp, end - start, LINE_WIDTH);
		} else {
			if (mouseX > start) {
				yUp += ROWS_SPACE;
				yDown += ROWS_SPACE;
			} else {
				yUp -= ROWS_SPACE;
				yDown -= ROWS_SPACE;
			}
			// canvas.fillRect(start, yUp, LINE_WIDTH, TAKT_HEIGHT);
			// canvas.fillRect(end, yUp + ROWS_SPACE, LINE_WIDTH, TAKT_HEIGHT);
			// canvas.fillRect(start, yUp, ROW_WIDTH - start, LINE_WIDTH);
			// canvas.fillRect(start, yDown, ROW_WIDTH - start, LINE_WIDTH);
			// canvas.fillRect(X_START, yUp + ROWS_SPACE, end, LINE_WIDTH);
			// canvas.fillRect(X_START, yDown + ROWS_SPACE, end, LINE_WIDTH);
		}
		// canvas.setFillStyle("#000000");
	}

	public TactField getCurrTactField() {
		return currTactField;
	}

	public NoteField getCurrNoteField() {
		return currNoteField;
	}

	public void setCurrTactField(TactField currTactField) {
		this.currTactField = currTactField;
	}

	public void setCurrNoteField(NoteField currNoteField) {
		this.currNoteField = currNoteField;
	}

}
