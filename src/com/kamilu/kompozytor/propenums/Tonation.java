package com.kamilu.kompozytor.propenums;

public enum Tonation {
	C_dur("", 0, 0), //
	G_dur("", 0, 20), //
	F_dur("", 0, 5);

	private final String url;
	private final int width, height;

	Tonation(String url, int width, int height) {
		this.url = url;
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getUrl() {
		return url;
	}
}
