package data.repositories

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import data.mongo.MongoDataService
import domain.User
import org.bson.Document
import org.bson.types.ObjectId

object UserRepository {

    val  database: MongoDatabase = MongoClient().getDatabase("scanr")

    fun getAllUsers(collection: String): List<User> {
        val collection = database.getCollection("users", Document::class.java)
        val result = mutableListOf<User>()
        collection.find()
                .forEach {
                    val asMap: Map<String, Any> = mongoDocumentToMap(it)
                    val user = User(
                            name = asMap["name"].toString(),
                            email = asMap["email"].toString(),
                            present = asMap["present"].toString() == "true"
                    )
                    result.add(user)
                }
        return result

    }
//    fun getAllUsers(collection: String): ArrayList<Map<String, Any>> {
//        val collection = database.getCollection("users", Document::class.java)
//        val result = ArrayList<Map<String, Any>>()
//        collection.find()
//                .forEach {
//                    val asMap: Map<String, Any> = mongoDocumentToMap(it)
//                    result.add(asMap)
//                }
//        return result
//
//    }

    private  fun mongoDocumentToMap(document: Document): Map<String, Any> {
        val asMap: MutableMap<String, Any> = document.toMutableMap()
        if (asMap.containsKey("_id")) {
            val id = asMap.getValue("_id")
            if (id is ObjectId) {
                asMap.set("_id", id.toHexString())
            }
        }
        return asMap
    }


}