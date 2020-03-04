package data.repositories

import data.mongo.MongoDataService
import domain.Attendee
import org.litote.kmongo.*
import kotlin.collections.toList

object AttendeeRepository : MongoDataService(dbName= "scanr") {

    fun getAllAttendees(): List<Attendee>? {
        val result =  getCollection<Attendee>().find()
        return result.toList()
    }

    fun getAttendeeByEmail(email: String): Attendee? {
        return getCollection<Attendee>().findOne { Attendee::email eq email }
    }

    fun checkInAttendeeByEmail(email: String): Attendee? {
        getCollection<Attendee>().updateOne(Attendee::email eq email, Attendee::checkedIn setTo true )
        return getCollection<Attendee>().findOne { Attendee::email eq email }
    }



}