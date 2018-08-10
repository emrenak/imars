package com.imars.core.service.impl;

import org.springframework.stereotype.Service;

import com.imars.core.service.ConnectionFactoryService;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Service
public class ConnectionFactoryServiceImpl implements ConnectionFactoryService {

	MongoClient mongoClient = null;
	MongoDatabase database = null;
	public synchronized MongoDatabase getConnection() {
		if(mongoClient == null){
			 mongoClient = new MongoClient( "localhost" , 27017 );
			 database = mongoClient.getDatabase("imarsdb");
		}
		return database;
	}

}
