package domain.interfaces

interface IAttendee {
    val name: String
    val email: String
    var present: Boolean

    fun checkedIn()
}