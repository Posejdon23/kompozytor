package com.kamilu.kompozytor.utils;

import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;

public class EntityTable {

	private Table table;
	private final String entityKind;

	private void createColumnsFromEntityProperties() {

		ImmutableMap<String, Class<?>> propertiesDefinitions = EntityPropertiesMap
				.getPropertiesMap(entityKind);

		for (Entry<String, Class<?>> entry : propertiesDefinitions.entrySet()) {
			String columnName = entry.getKey();
			Class<?> columnType = entry.getValue();
			table.addContainerProperty(columnName, columnType,
					ClassToDefault.convertClassToDefaultValue((columnType)),
					columnName, null, Align.CENTER);
		}
		table.setDescription(entityKind);
		table.setSizeFull();
		table.setSelectable(true);
		table.setColumnReorderingAllowed(true);
		table.setColumnCollapsingAllowed(true);
	}

	private Table getTable() {
		return table;
	}

	public static class Builder {
		private String entityKind;

		public Builder kind(String entityKind) {
			this.entityKind = entityKind;
			return this;
		}

		public Table build() {
			return new EntityTable(this).getTable();
		}
	}

	private EntityTable(Builder builder) {
		this.entityKind = builder.entityKind;
		table = new Table();
		createColumnsFromEntityProperties();
	}
}
