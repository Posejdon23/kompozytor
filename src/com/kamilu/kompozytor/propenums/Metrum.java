package com.kamilu.kompozytor.propenums;

public enum Metrum {
	Common("", 4, 4, 0, 0), Metrum4_4("", 4, 4, 0, 0), DoubleCommon("", 2, 4,
			0, 0), Metrum2_4("", 2, 4, 0, 0), Metrum3_4("", 3, 4, 0, 0), Metrum6_8(
			"", 6, 8, 0, 0);

	private final String url;
	private final int top, down, width, height;

	Metrum(String url, int top, int down, int width, int height) {
		this.url = url;
		this.top = top;
		this.down = down;
		this.width = width;
		this.height = height;
	}

	public double getTactLength() {
		return 100 * top / down;
	}

	public String getUrl() {
		return url;
	}

	public int getTop() {
		return top;
	}

	public int getDown() {
		return down;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}