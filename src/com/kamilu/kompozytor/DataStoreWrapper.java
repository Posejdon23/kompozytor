package com.kamilu.kompozytor;

import static com.google.appengine.api.datastore.FetchOptions.Builder.*;

import java.util.List;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.kamilu.kompozytor.entities.Song;

public class DataStoreWrapper {

	public static final String ID = "id";

	public static Entity getById(String kind, long id) {
		try {
			return DatastoreServiceFactory.getDatastoreService().get(
					KeyFactory.createKey(kind, id));
		} catch (EntityNotFoundException e) {
			System.err.println("EntityNotFoundException"
					+ e.getLocalizedMessage());
			return null;
		}
	}

	public static Entity getSong(String songName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query(Song.KIND);
		FilterPredicate filterPredicate = new FilterPredicate(Song.NAME,
				FilterOperator.EQUAL, songName);
		query.setFilter(filterPredicate);
		PreparedQuery preparedQuery = datastore.prepare(query);
		return preparedQuery.asSingleEntity();
	}

	public static Key save(Entity entity) {
		return DatastoreServiceFactory.getDatastoreService().put(entity);
	}

	public static void remove(Entity entity) {
		DatastoreServiceFactory.getDatastoreService().delete(entity.getKey());
	}

	public static void delete(Entity entity) {
		DatastoreServiceFactory.getDatastoreService().delete(entity.getKey());
	}

	public static List<Entity> getChildren(String kind, String columnIdName,
			Key parentKey) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query(kind);
		FilterPredicate filterPredicate = new FilterPredicate(columnIdName,
				FilterOperator.EQUAL, parentKey.getId());
		query.setFilter(filterPredicate);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> asList = preparedQuery.asList(withDefaults());
		return asList;
	}

	public static List<Entity> getAll(String kind) {
		return DatastoreServiceFactory.getDatastoreService()
				.prepare(new Query(kind)).asList(withDefaults());
	}

	public static List<Entity> getChildrenWithSortColumn(String kind,
			String columnIdName, Key parentKey, String orderColumnName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query(kind);
		query.addSort(orderColumnName);
		FilterPredicate filterPredicate = new FilterPredicate(columnIdName,
				FilterOperator.EQUAL, parentKey.getId());
		query.setFilter(filterPredicate);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> asList = preparedQuery.asList(withDefaults());
		return asList;
	}
}
