package com.imars.core.service.impl;

import org.springframework.stereotype.Service;

import com.imars.core.service.ConnectionFactoryService;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Service
public class ConnectionFactoryServiceImpl implements ConnectionFactoryService {

	/*
	 * mongo server on docker is running on 
	 * docker run -p 27017:27017 -h mongoserver --net imarsnet --ip 172.18.0.3 -t mongo:latest
	 */
	MongoClient mongoClient = null;
	MongoDatabase database = null;
	public synchronized MongoDatabase getConnection() {
		if(mongoClient == null){
			 mongoClient = new MongoClient( "172.18.0.3" , 27017 ); // define localhost for local development without docker 
			 database = mongoClient.getDatabase("imarsdb");
		}
		return database;
	}

}
