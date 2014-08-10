package com.kamilu.kompozytor.propenums;

public enum Metrum {
	Common("http://oi62.tinypic.com/25qu9e9.jpg"), DoubleCommon("");

	private final String url;

	Metrum(String url) {
		this.url = url;
	}

	public double getDlugoscTaktu() {
		// FIX
		return 100d;
	}

	public String getUrl() {
		return url;
	}

}