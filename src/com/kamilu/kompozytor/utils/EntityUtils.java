package com.kamilu.kompozytor.utils;

import java.util.Map;

import com.google.appengine.api.datastore.Entity;

public class EntityUtils {
	public static void printEntity(Entity entity) {
		if (entity != null) {
			System.out.println(entity.getKind() + ": \n");
			Map<String, Object> propsy = entity.getProperties();
			for (String prop : propsy.keySet()) {
				System.out.println(prop + " - " + propsy.get(prop));
			}
			System.out.println();
		}
	}
}
