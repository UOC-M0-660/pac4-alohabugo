package edu.uoc.pac4.data.user

/**
 * Created by alex on 11/21/20.
 */

class TwitchUserRepository(
        // TODO: Add any datasources you may need
        private val twitchUserDataSource: TwitchUserDataSource
        ) : UserRepository {

    override suspend fun getUser(): User? {
        // TODO("Not yet implemented")
        // se obtienen datos del usuario mediante el data source
        return twitchUserDataSource.getUser()
    }

    override suspend fun updateUser(description: String): User? {
        // TODO("Not yet implemented")
        //se actualiza la descripcion del usuario a traves del data source
        return twitchUserDataSource.updateUser(description)
    }
}