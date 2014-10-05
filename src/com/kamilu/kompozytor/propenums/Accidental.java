package com.kamilu.kompozytor.propenums;

public enum Accidental {
	Sharp(9,
			31,
			//
			"https://dl-web.dropbox.com/get/sharpKey.svg?_subject_uid=338188398&w=AAAMAXn0hIAlfoFa7f15nGFhkGhrSCOqnWuMW2HSU-v-1A"), //
	Flat(8,
			27,
			//
			"https://dl-web.dropbox.com/get/flatKey.svg?_subject_uid=338188398&w=AABJNgeMh_dCxCIgHGAkd6Xg3hHNjhAaqQgpfjpcHH-FEQ"), //
	Natural(7,
			29,//
			"https://dl-web.dropbox.com/get/natural.svg?_subject_uid=338188398&w=AABzoMNfBrGaUVnvXwfItFvdM73_acofEYzSOfjzYcBp6Q");

	private final String imageUrl;
	private final int width, height;

	Accidental(int width, int height, String imageUrl) {
		this.imageUrl = imageUrl;
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
		return imageUrl;
	}

}
