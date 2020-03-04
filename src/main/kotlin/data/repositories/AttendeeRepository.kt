package data.repositories

import com.mongodb.MongoClient
import domain.Attendee
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.updateOne

object AttendeeRepository {

    private val  database = MongoClient().getDatabase("scanr")
    private val collection = database.getCollection("attendees", Document::class.java)

    fun getAllAttendees(): List<Attendee> {
        val result = mutableListOf<Attendee>()
        collection.find()
                .forEach {
                    val asMap: Map<String, Any> = mongoDocumentToMap(it)
                    val attendee = Attendee(
                            name = asMap["name"].toString(),
                            email = asMap["email"].toString(),
                            present = asMap["present"].toString() == "true"
                    )
                    result.add(attendee)
                }
        return result

    }

    fun getAttendeeByEmail(email: String) : Attendee {

        val client = KMongo.createClient()
        val database = client.getDatabase("scanr")
        val col = database.getCollection("attendees")

        val result =  col.findOne { Attendee::email eq email }
        return Attendee(
                name = result?.getString("name").toString(),
                email = result?.getString("email").toString(),
                present = result?.getString("email").toString() == "true"
        )
    }

    fun checkInAttendeeByEmail(email: String) : Attendee {
        val client = KMongo.createClient()
        val database = client.getDatabase("scanr")
        val col = database.getCollection("attendees")

        val result =  col.findOne { Attendee::email eq email }
        val attendee = Attendee(
                name = result?.getString("name").toString(),
                email = result?.getString("email").toString(),
                present = result?.getString("email").toString() == "true"
        )

        attendee.checkIn()
        col.updateOne(Attendee::email eq email, attendee.checkIn())

        return attendee;
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