package com.kamilu.kompozytor.entities;

import java.io.Serializable;

public interface MusicObject extends Serializable {

	public static final String TO_REMOVE = "toRemove";
	public static final String NUMBER = "number";

	public void persist();

	public void markToRemove();
}
