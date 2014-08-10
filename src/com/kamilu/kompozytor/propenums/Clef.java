package com.kamilu.kompozytor.propenums;

public enum Clef {
	Wiolinowy("http://oi58.tinypic.com/2vla0it.jpg", 60), Basowy(
			"http://s8.tinypic.com/34ef57c_th.jpg", 18);

	private final String url;
	private final int wysokosc;

	private Clef(String url, int wysokosc) {
		this.url = url;
		this.wysokosc = wysokosc;
	}

	public String getUrl() {
		return url;
	}

	public int getWysokosc() {
		return wysokosc;
	}
}