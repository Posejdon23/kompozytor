package com.kamilu.kompozytor.utils;

import com.google.common.collect.ImmutableMap;
import com.kamilu.kompozytor.entities.Song;
import com.kamilu.kompozytor.entities.Voice;

public class EntityPropertiesMap {

	static final ImmutableMap<String, ImmutableMap<String, Class<?>>> ENTITY_MAP = new ImmutableMap.Builder<String, ImmutableMap<String, Class<?>>>()
			.put(Song.KIND, Song.PROPERTIES)//
			.put(Voice.KIND, Voice.PROPERTIES)//
			.build();

	public static ImmutableMap<String, Class<?>> getPropertiesMap(String kind) {
		return ENTITY_MAP.get(kind);
	}

}
