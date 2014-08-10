package com.kamilu.kompozytor.utils;

import java.util.Map.Entry;

import com.google.appengine.api.datastore.Entity;
import com.google.common.collect.ImmutableMap;
import com.kamilu.kompozytor.DataStoreWrapper;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;

public class TableDataLoader {

	@SuppressWarnings("unchecked")
	public static void loadData(Table table) {
		table.removeAllItems();
		String entityKind = table.getDescription();
		for (Entity entity : DataStoreWrapper.getAll(entityKind)) {
			ImmutableMap<String, Class<?>> propertiesDefinitions = EntityPropertiesMap
					.getPropertiesMap(entityKind);
			Item item = table.addItem(entity.getKey());
			for (Entry<String, Class<?>> entry : propertiesDefinitions
					.entrySet()) {
				String propertyName = entry.getKey();
				Class<?> columnType = entry.getValue();
				if (!propertyName.equals("Key")) {
					item.getItemProperty(propertyName).setValue(
							columnType.cast(entity.getProperty(propertyName)));
				}
			}
			item.getItemProperty("Key").setValue(entity.getKey());
		}
		table.setColumnWidth("Key", 0);
	}
}
