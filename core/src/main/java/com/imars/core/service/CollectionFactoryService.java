package com.imars.core.service;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public interface CollectionFactoryService {

	public MongoCollection<Document> getCollection(String collectionName);
}
