package com.kamilu.kompozytor.propenums;

public enum Clef {
	Violin(18,
			52, //
			"https://dl-web.dropbox.com/get/violinKey.svg?_subject_uid=338188398&w=AACIl7Zpy4r66EYKFhxveqkm7D2gh6BGS5ciELLksa7bVQ"), //
	Bass(0, 0, //
			"");

	private final String url;
	private final int width, height;

	private Clef(int width, int height, String url) {
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