package hw2.create

import hw2.entity.UserStatus
import hw2.entity.User

class SignedUpWindowBuilderFactoryImpl : SignedUpWindowBuilderFactory {
    override fun createBuilder(user: User): WindowBuilder {
        return if (user.status == UserStatus.ADMINISTRATOR) {
            AdministratorWindowBuilder(user)
        } else {
            VisitorWindowBuilder(user)
        }
    }
}