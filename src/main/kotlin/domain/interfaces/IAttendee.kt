package domain.interfaces

interface IAttendee {
    val name: String
    val email: String
    var checkedIn: Boolean
}