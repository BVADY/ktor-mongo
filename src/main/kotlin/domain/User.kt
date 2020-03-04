package domain

import domain.interfaces.IUser

class User(
        override val name: String,
        override val email: String,
        override var present: Boolean = false

): IUser {
    override fun checkedIn() {
        this.present = true;
    }
}