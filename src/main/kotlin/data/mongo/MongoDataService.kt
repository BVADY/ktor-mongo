package data.mongo

import com.mongodb.client.MongoCollection
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

open class MongoDataService(dbName: String) {
    private val database = KMongo.createClient().getDatabase(dbName)
    internal inline fun <reified T: Any> getCollection(): MongoCollection<T> {
        return database.getCollection()
    }
}