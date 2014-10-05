package com.kamilu.kompozytor.propenums;

import static com.kamilu.kompozytor.propenums.Accidental.Flat;
import static com.kamilu.kompozytor.propenums.Accidental.Natural;
import static com.kamilu.kompozytor.propenums.Accidental.Sharp;

public enum NotePitch {
	Pause(0, Natural), //
	fes0(50, Flat), f0(50, Natural), fis0(50, Sharp), //
	ges0(45, Flat), g0(45, Natural), gis0(45, Sharp), //
	as0(40, Flat), a0(40, Natural), ais0(40, Sharp), //
	b0(35, Flat), h0(35, Natural), his0(35, Sharp), //
	ces1(30, Flat), c1(30, Natural), cis1(30, Sharp), //
	des1(25, Flat), d1(25, Natural), dis1(25, Sharp), //
	es1(20, Flat), e1(20, Natural), eis1(20, Sharp), //
	fes1(15, Flat), f1(15, Natural), fis1(15, Sharp), //
	ges1(10, Flat), g1(10, Natural), gis1(10, Sharp), //
	as1(5, Flat), a1(5, Natural), ais1(5, Sharp), //
	b1(0, Flat), h1(0, Natural), his1(0, Sharp), //
	ces2(-5, Flat), c2(-5, Natural), cis2(-5, Sharp), //
	des2(-10, Flat), d2(-10, Natural), dis2(-10, Sharp), //
	es2(-15, Flat), e2(-15, Natural), eis2(-15, Sharp), //
	fes2(-20, Flat), f2(-20, Natural), fis2(-20, Sharp), //
	ges2(-25, Flat), g2(-25, Natural), gis2(-25, Sharp), //
	as2(-30, Flat), a2(-30, Natural), ais2(-30, Sharp), //
	b2(-35, Flat), h2(-35, Natural), his2(-35, Sharp), //
	ces3(-40, Flat), c3(-40, Natural), cis3(-40, Sharp), //
	des3(-45, Flat), d3(-45, Natural), dis3(-45, Sharp), //
	es3(-50, Flat), e3(-50, Natural), eis3(-50, Sharp); //

	private final int height;
	private final Accidental accid;

	private NotePitch(int height, Accidental accid) {
		this.height = height;
		this.accid = accid;
	}

	public int getHeight() {
		return height;
	}

	public Accidental getAccid() {
		return accid;
	}
}
