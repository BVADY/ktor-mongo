package data.repositories

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import data.mongo.MongoDataService
import domain.User
import org.bson.BsonDocument
import org.bson.BsonString
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

object UserRepository {

    private val  database = MongoClient().getDatabase("scanr")
    private val collection = database.getCollection("users", Document::class.java)

    fun getAllUsers(): List<User> {
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

    fun getUserByEmail(email: String) : User {

        val client = KMongo.createClient()
        val database = client.getDatabase("scanr")
        val col = database.getCollection("users")

        val result =  col.findOne { User::email eq email }
        return User(
                name = result?.getString("name").toString(),
                email = result?.getString("email").toString(),
                present = result?.getString("email").toString() == "true"
        )
    }


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