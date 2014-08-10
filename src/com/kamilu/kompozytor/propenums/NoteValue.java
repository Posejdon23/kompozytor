package com.kamilu.kompozytor.propenums;

public enum NoteValue {
	WholeNote(100d, "http://s8.tinypic.com/vpu4ug_th.jpg",
			"http://s8.tinypic.com/10ckl1h_th.jpg"), //
	HalfNote(50d, "http://s8.tinypic.com/2i27s6a_th.jpg",
			"http://s8.tinypic.com/ouoc52_th.jpg"), //
	QuarterNote(25d, "http://s8.tinypic.com/14j2k2v_th.jpg", ""), //
	EighthNote(12.5, "http://s8.tinypic.com/qs1uuv_th.jpg", ""), //
	SixteenthNote(6.25d, "", ""), //
	ThirtySecondNote(3.125d, "", ""), //
	SixtyFourthNote(1.5625, "", "");//

	private double value;
	private String noteUrl;
	private String pauseUrl;

	public String getPauseUrl() {
		return pauseUrl;
	}

	public double getValue() {
		return value;
	}

	public String getNoteUrl() {
		return noteUrl;
	}

	private NoteValue(double value, String noteUrl, String pauseUrl) {
		this.value = value;
		this.noteUrl = noteUrl;
		this.pauseUrl = pauseUrl;
	}
}
