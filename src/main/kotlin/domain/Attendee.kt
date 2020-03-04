package domain

import domain.interfaces.IAttendee

class Attendee(
        override val name: String,
        override val email: String,
        override var present: Boolean = false

): IAttendee {
    override fun checkedIn() {
        this.present = true;
    }
}