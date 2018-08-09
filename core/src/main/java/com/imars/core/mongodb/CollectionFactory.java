package com.imars.core.mongodb;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CollectionFactory {
	
	private static final CollectionFactory COLLECTION_FACTORY = new CollectionFactory();
	
	private CollectionFactory(){
		
	}
	
	public static CollectionFactory getInstance(){
		return COLLECTION_FACTORY;
	}
	
	public MongoCollection<Document> getCollection(String collectionName){
		MongoDatabase database = ConnectionFactory.getInstance().getConnection();
		MongoCollection<Document> collection = database.getCollection(collectionName);
		return collection;
	}

}
