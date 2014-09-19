package com.kamilu.kompozytor.propenums;

public enum Accidental {
	Sharp("http://s8.tinypic.com/aau0p3_th.jpg"), Flat(
			"http://s8.tinypic.com/11ay70l_th.jpg"), Natural(
			"http://oi57.tinypic.com/hwlbih.jpg");

	private final String imageUrl;

	Accidental(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUrl() {
		return imageUrl;
	}

}
