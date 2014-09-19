package com.kamilu.kompozytor.propenums;

public enum Clef {
	Violin("http://oi58.tinypic.com/2vla0it.jpg", 60), Bass(
			"http://s8.tinypic.com/34ef57c_th.jpg", 18);

	private final String url;
	private final int height;

	private Clef(String url, int height) {
		this.url = url;
		this.height = height;
	}

	public String getUrl() {
		return url;
	}

	public int getHeight() {
		return height;
	}
}