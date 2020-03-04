package service.attendee

import data.repositories.AttendeeRepository
import domain.Attendee
import service.attendee.interfaces.IAttendeeService

class AttendeeService : IAttendeeService {

    private val repo =  AttendeeRepository

    override fun getAllAttendees(): List<Attendee>? {
        return repo.getAllAttendees();
    }

    override fun getAttendeeByEmail(email: String): Attendee? {
        return repo.getAttendeeByEmail(email)
    }

    override fun checkInAttendeeByEmail(email: String): Attendee? {
        return repo.checkInAttendeeByEmail(email)
    }

}