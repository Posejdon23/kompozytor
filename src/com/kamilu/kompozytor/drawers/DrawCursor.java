package com.kamilu.kompozytor.drawers;

import static com.kamilu.kompozytor.drawers.TactDrawer.*;

import org.vaadin.hezamu.canvas.Canvas;

@SuppressWarnings("serial")
public class DrawCursor implements Drawer {
	public static final float ROW_WIDTH = 830;
	public static final float ROWS_SPACE = 130;
	private static final float X_START = 10;
	private static final float Y_START = 130;
	private final Canvas canvas;
	private float xPos, yPos;
	private int mouseX = -1, mouseY = -1;

	public DrawCursor(Canvas canvas) {
		this.xPos = X_START;
		this.yPos = Y_START;
		this.canvas = canvas;
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
	}

	private void addYPos(float heightToAdd) {
		yPos += heightToAdd;
	}

	private void setXPos(float xPos) {
		this.xPos = xPos;
	}

	public void drawStave() {
		canvas.fillRect(getXPos(), getYPos() - 20, ROW_WIDTH, 1);
		canvas.fill();
		canvas.fillRect(getXPos(), getYPos() - 10, ROW_WIDTH, 1);
		canvas.fill();
		canvas.fillRect(getXPos(), getYPos(), ROW_WIDTH, 1);
		canvas.fill();
		canvas.fillRect(getXPos(), getYPos() + 10, ROW_WIDTH, 1);
		canvas.fill();
		canvas.fillRect(getXPos(), getYPos() + 20, ROW_WIDTH, 1);
		canvas.fill();
	}

	public void reset() {
		xPos = X_START;
		yPos = Y_START;
		mouseX = -1;
		mouseY = -1;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void drawTactSelectBox(TactField tactField, float mouseX,
			float mouseY) {
		this.mouseX = (int) mouseX;
		this.mouseY = (int) mouseY;
		drawTactBox(tactField, mouseX, mouseY);
	}

	private void drawTactBox(TactField tactField, float mouseX, float mouseY) {
		canvas.setFillStyle("#0000ee");
		float start = tactField.getStart();
		float end = tactField.getEnd();
		float yCenter = tactField.getYCenter();
		float yUp = (float) (yCenter - (TactDrawer.TAKT_HEIGHT / 2));
		float yDown = (float) (yCenter + (TactDrawer.TAKT_HEIGHT / 2));
		if (start < end) {
			canvas.fillRect(start, yUp, LINE_WIDTH, TactDrawer.TAKT_HEIGHT);
			canvas.fillRect(end, yUp, LINE_WIDTH, TAKT_HEIGHT);
			canvas.fillRect(start, yDown, end - start, LINE_WIDTH);
			canvas.fillRect(start, yUp, end - start, LINE_WIDTH);
		} else {
			if (mouseX > start) {
				yUp += ROWS_SPACE;
				yDown += ROWS_SPACE;
			} else {
				yUp -= ROWS_SPACE;
				yDown -= ROWS_SPACE;
			}
			canvas.fillRect(start, yUp, LINE_WIDTH, TAKT_HEIGHT);
			canvas.fillRect(end, yUp + ROWS_SPACE, LINE_WIDTH, TAKT_HEIGHT);

			canvas.fillRect(start, yUp, ROW_WIDTH - start, LINE_WIDTH);
			canvas.fillRect(start, yDown, ROW_WIDTH - start, LINE_WIDTH);
			canvas.fillRect(X_START, yUp + ROWS_SPACE, end, LINE_WIDTH);
			canvas.fillRect(X_START, yDown + ROWS_SPACE, end, LINE_WIDTH);
		}
		// canvas.fillRect(getMouseX(), cursorY - (TactDrawer.TACT_HEIGHT / 2),
		// TactDrawer.LINE_WIDTH, TactDrawer.TACT_HEIGHT);
		canvas.setFillStyle("#000000");
	}

	public void drawNoteSelectBox(NoteField noteField, int mouseX, int mouseY) {
		this.mouseX = (int) mouseX;
		this.mouseY = (int) mouseY;
		drawNoteBox(noteField, mouseX, mouseY);
	}

	private void drawNoteBox(NoteField noteField, int mouseX, int mouseY) {
		if (noteField == null) {
			return;
		}
		canvas.setFillStyle("#ff0000");
		float start = noteField.getStart();
		float end = noteField.getEnd();
		float yCenter = noteField.getYCenter();
		float yUp = (float) (yCenter - (TactDrawer.TAKT_HEIGHT / 2));
		float yDown = (float) (yCenter + (TactDrawer.TAKT_HEIGHT / 2));
		if (start < end) {
			canvas.fillRect(start, yUp, LINE_WIDTH, TactDrawer.TAKT_HEIGHT);
			canvas.fillRect(end, yUp, LINE_WIDTH, TAKT_HEIGHT);
			canvas.fillRect(start, yDown, end - start, LINE_WIDTH);
			canvas.fillRect(start, yUp, end - start, LINE_WIDTH);
		} else {
			if (mouseX > start) {
				yUp += ROWS_SPACE;
				yDown += ROWS_SPACE;
			} else {
				yUp -= ROWS_SPACE;
				yDown -= ROWS_SPACE;
			}
			canvas.fillRect(start, yUp, LINE_WIDTH, TAKT_HEIGHT);
			canvas.fillRect(end, yUp + ROWS_SPACE, LINE_WIDTH, TAKT_HEIGHT);

			canvas.fillRect(start, yUp, ROW_WIDTH - start, LINE_WIDTH);
			canvas.fillRect(start, yDown, ROW_WIDTH - start, LINE_WIDTH);
			canvas.fillRect(X_START, yUp + ROWS_SPACE, end, LINE_WIDTH);
			canvas.fillRect(X_START, yDown + ROWS_SPACE, end, LINE_WIDTH);
		}
		// canvas.fillRect(getMouseX(), cursorY - (TactDrawer.TACT_HEIGHT / 2),
		// TactDrawer.LINE_WIDTH, TactDrawer.TACT_HEIGHT);
		canvas.setFillStyle("#000000");
	}
}
