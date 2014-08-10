package com.kamilu.kompozytor.propenums;

import static com.kamilu.kompozytor.propenums.Accidental.Bemol;
import static com.kamilu.kompozytor.propenums.Accidental.Brak;
import static com.kamilu.kompozytor.propenums.Accidental.Krzyzyk;

public enum NotePitch {
	fes0(50, Bemol), f0(50, Brak), fis0(50, Krzyzyk), //
	ges0(45, Bemol), g0(45, Brak), gis0(45, Krzyzyk), //
	as0(40, Bemol), a0(40, Brak), ais0(40, Krzyzyk), //
	b0(35, Bemol), h0(35, Brak), his0(35, Krzyzyk), //
	ces1(30, Bemol), c1(30, Brak), cis1(30, Krzyzyk), //
	des1(25, Bemol), d1(25, Brak), dis1(25, Krzyzyk), //
	es1(20, Bemol), e1(20, Brak), eis1(20, Krzyzyk), //
	fes1(15, Bemol), f1(15, Brak), fis1(15, Krzyzyk), //
	ges1(10, Bemol), g1(10, Brak), gis1(10, Krzyzyk), //
	as1(5, Bemol), a1(5, Brak), ais1(5, Krzyzyk), //
	b1(0, Bemol), h1(0, Brak), his1(0, Krzyzyk), //
	ces2(-5, Bemol), c2(-5, Brak), cis2(-5, Krzyzyk), //
	des2(-10, Bemol), d2(-10, Brak), dis2(-10, Krzyzyk), //
	es2(-15, Bemol), e2(-15, Brak), eis2(-15, Krzyzyk), //
	fes2(-20, Bemol), f2(-20, Brak), fis2(-20, Krzyzyk), //
	ges2(-25, Bemol), g2(-25, Brak), gis2(-25, Krzyzyk), //
	as2(-30, Bemol), a2(-30, Brak), ais2(-30, Krzyzyk), //
	b2(-35, Bemol), h2(-35, Brak), his2(-35, Krzyzyk), //
	ces3(-40, Bemol), c3(-40, Brak), cis3(-40, Krzyzyk), //
	des3(-45, Bemol), d3(-45, Brak), dis3(-45, Krzyzyk), //
	es3(-50, Bemol), e3(-50, Brak), eis3(-50, Krzyzyk), //
//	WholeNotePause(0, Brak), HalfNotePause(0, Brak), //
	Rest(26, Brak);

	private int yPos;
	private Accidental znak;

	private NotePitch(int yPos, Accidental accid) {
		this.yPos = yPos;
		this.znak = accid;
	}

	public int getYPos() {
		return yPos;
	}

	public Accidental getAccid() {
		return znak;
	}
}
