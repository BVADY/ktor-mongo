package service.attendee.interfaces

import domain.Attendee

interface IAttendeeService {

    fun getAllAttendees() : List<Attendee>?
    fun getAttendeeByEmail(email: String) : Attendee?
    fun checkInAttendeeByEmail(email: String): Attendee?
}