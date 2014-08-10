package com.kamilu.kompozytor.utils;

import com.google.appengine.api.datastore.Text;

public class ClassToDefault {

	public static Object convertClassToDefaultValue(Class<?> classType) {
		if (classType.equals(String.class)) {
			return "";
		}
		if (classType.equals(Text.class)) {
			return new Text("");
		}
		return null;
	}
}
