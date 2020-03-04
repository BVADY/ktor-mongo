package domain

import domain.interfaces.IAttendee

class Attendee(
        override val name: String,
        override val email: String,
        override var checkedIn: Boolean = false

): IAttendee {
    override fun checkIn() {
        this.checkedIn = true;
    }
}