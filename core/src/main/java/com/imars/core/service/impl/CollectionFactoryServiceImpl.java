package com.imars.core.service.impl;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imars.core.service.CollectionFactoryService;
import com.imars.core.service.ConnectionFactoryService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Service
public class CollectionFactoryServiceImpl implements CollectionFactoryService {

	@Autowired
	ConnectionFactoryService connectionFactoryService;
	
	public MongoCollection<Document> getCollection(String collectionName) {
		MongoDatabase database = connectionFactoryService.getConnection();
		MongoCollection<Document> collection = database.getCollection(collectionName);
		return collection;
	}

}
