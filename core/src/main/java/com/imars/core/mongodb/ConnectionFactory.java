package com.imars.core.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ConnectionFactory {

	private final static ConnectionFactory CONNECTION_FACTORY = new ConnectionFactory();
	
	MongoClient mongoClient = null;
	MongoDatabase database = null;
	private ConnectionFactory(){
		
	}
	
	public static ConnectionFactory getInstance(){
		return CONNECTION_FACTORY;
	}
	
	public synchronized MongoDatabase getConnection(){
		if(mongoClient == null){
			 mongoClient = new MongoClient( "localhost" , 27017 );
			 database = mongoClient.getDatabase("imarsdb");
		}
		return database;
	}
}
