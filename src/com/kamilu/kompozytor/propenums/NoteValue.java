package com.kamilu.kompozytor.propenums;

public enum NoteValue {
	WholeNote(
			100d,
			28,
			20,
			18,
			3,
			-5,//
			"https://dl-web.dropbox.com/get/wholeNote.svg?_subject_uid=338188398&w=AAAVflWd5y6zqxa_mj0h1euakSfzX3uOoyciHy6u_8C-8A",
			"https://dl-web.dropbox.com/get/WholePause.svg?_subject_uid=338188398&w=AADpfXUvP9Jbq4Et2s5o8Lz0NuRXqmHFVGhBO0Zne5ypvg"), //
	HalfNote(
			50d,
			12,
			43,
			18,
			3,
			-3,//
			"https://dl-web.dropbox.com/get/halfNote.svg?_subject_uid=338188398&w=AACGZ2aO7-1K7e9_ZIPIt_LS3c2oymc6XLa6UL1YThiP6A",
			"https://dl-web.dropbox.com/get/WholePause.svg?_subject_uid=338188398&w=AADpfXUvP9Jbq4Et2s5o8Lz0NuRXqmHFVGhBO0Zne5ypvg"), //
	QuarterNote(
			25d,
			44,
			143,
			0,
			0,
			0,//
			"https://dl-web.dropbox.com/get/crochet.svg?_subject_uid=338188398&w=AACiU79LiRmWi1TjtJh3hrq-Y-FU-UYzOWrRT2a3e2D1Xg",
			""), //
	EighthNote(
			12.5,
			23,
			44,
			0,
			0,
			0,//
			"https://dl-web.dropbox.com/get/eighthNote.svg?_subject_uid=338188398&w=AABzdHDeCBrZjpJheKmXadgwk507wSMr_5Of2ycWX3IQHA",
			""), //
	SixteenthNote(
			6.25d,
			24,
			44,
			0,
			0,
			0,//
			"https://dl-web.dropbox.com/get/sixteenNote.svg?_subject_uid=338188398&w=AAAhgBGSEW2eoViQzoF8o6Bw7TT5vfwX9SCqk_nrnPVUhQ",
			""), //
	ThirtySecondNote(3.125d, 28, 20, 0, 0, 0, //
			"",//
			""), //
	SixtyFourthNote(1.5625, 28, 20, 0, 0, 0,//
			"",//
			"");

	private double value;
	private final String noteUrl;
	private final String pauseUrl;
	private final int width, height;
	private final int pauseWidth;
	private final int pauseHeight;
	private final int yAdjust;

	private NoteValue(double value, int width, int height, int pauseWidth,
			int pauseHeight, int yAdjust, String noteUrl, String pauseUrl) {
		this.value = value;
		this.noteUrl = noteUrl;
		this.pauseUrl = pauseUrl;
		this.width = width;
		this.height = height;
		this.pauseWidth = pauseWidth;
		this.pauseHeight = pauseHeight;
		this.yAdjust = yAdjust;
	}

	public int getYAdjust() {
		return yAdjust;
	}

	public int getPauseWidth() {
		return pauseWidth;
	}

	public int getPauseHeight() {
		return pauseHeight;
	}

	public int getWidth() {
		return width;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getHeight() {
		return height;
	}

	public String getPauseUrl() {
		return pauseUrl;
	}

	public double getValue() {
		return value;
	}

	public String getNoteUrl() {
		return noteUrl;
	}
}
