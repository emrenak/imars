package com.imars.core.service;

import com.mongodb.client.MongoDatabase;

public interface ConnectionFactoryService {

	public MongoDatabase getConnection();
}
