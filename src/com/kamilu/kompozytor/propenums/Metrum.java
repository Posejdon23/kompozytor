package com.kamilu.kompozytor.propenums;

public enum Metrum {
	Common("http://oi62.tinypic.com/25qu9e9.jpg", 4, 4), Metrum4_4("", 4, 4), DoubleCommon(
			"", 2, 4), Metrum2_4("", 2, 4), Metrum3_4("", 3, 4), Metrum6_8("",
			6, 8);

	private final String imageUrl;
	private final int top, down;

	Metrum(String url, int top, int down) {
		this.imageUrl = url;
		this.top = top;
		this.down = down;
	}

	public double getTactLength() {
		return 100 * top / down;
	}

	public String getUrl() {
		return imageUrl;
	}

	public int getTop() {
		return top;
	}

	public int getDown() {
		return down;
	}
}