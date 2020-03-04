package domain.interfaces

interface IUser {
    val name: String
    val email: String
    var present: Boolean

    fun checkedIn()
}