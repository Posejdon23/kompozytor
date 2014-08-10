package com.kamilu.kompozytor.propenums;

public enum Accidental {
	Brak(""), Krzyzyk("http://s8.tinypic.com/aau0p3_th.jpg"), Bemol(
			"http://s8.tinypic.com/11ay70l_th.jpg"), PodwojnyKrzyzyk(
			"../notes/krzyzyk.png"), PodwojnyBemol("../notes/bemol.png");

	private final String url;

	Accidental(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
