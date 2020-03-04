package data.mongo

import com.mongodb.MongoClient


class MongoDataService(mongoClient: MongoClient, database: String) {
    private val database = mongoClient.getDatabase(database)
}