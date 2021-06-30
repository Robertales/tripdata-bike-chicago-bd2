package unisa.progettobd.service;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public abstract class DatabaseManager {

	public static MongoCollection<Document> getWeekday() {
		return getDB().getCollection(weekday);
	}
	public static MongoCollection<Document> getFull() {
		return getDB().getCollection(full);
	}

	public static MongoCollection<Document> getWeekend() {
		return getDB().getCollection(weekend);

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
	private static final String dbName = "databaseBD";
	private static final String weekday = "weekday";
	private static final String full = "full";
	private static final String weekend = "weekend";
}

