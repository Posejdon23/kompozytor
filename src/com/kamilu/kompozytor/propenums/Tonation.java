package com.kamilu.kompozytor.propenums;

public enum Tonation {
	C_dur("../notes/cdur.png", 0, 20), //
	G_dur("../notes/krzyzyk.png", 20, 20), //
	F_dur("../notes/bemol.png", 5, 20);

	private final String url;
	private final int yAdjust, xAdjust;

	Tonation(String url, int yAdjust, int xAdjust) {
		this.url = url;
		this.yAdjust = yAdjust;
		this.xAdjust = xAdjust;
	}

	public String getUrl() {
		return url;
	}

	public int getYAdjust() {
		return yAdjust;
	}

	public float getXAdjust() {
		return xAdjust;
	}

}
