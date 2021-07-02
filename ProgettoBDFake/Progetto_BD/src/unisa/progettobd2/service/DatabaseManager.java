package unisa.progettobd2.service;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public abstract class DatabaseManager {

	public static MongoCollection<Document> getBike() {
		return getDB().getCollection(bike);
	}

	
	
	
	public static void close() {
		if (client != null) client.close();
		client = null;
	}
	
	private static MongoDatabase getDB() {
		if(client == null) client = new MongoClient("localhost", 27017);
		return client.getDatabase(dbName);
	}

	private static MongoClient client = null;
	private static final String dbName = "databaseBD2Bike";
	private static final String bike = "bike";


}

