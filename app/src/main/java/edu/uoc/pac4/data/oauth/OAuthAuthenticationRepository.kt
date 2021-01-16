package edu.uoc.pac4.data.oauth

/**
 * Created by alex on 11/21/20.
 */
class OAuthAuthenticationRepository(
        // TODO: Add any datasources you may need
        private val oAuthAuthenticationDataSource: OAuthAuthenticationDataSource
        ) : AuthenticationRepository {

    override suspend fun isUserAvailable(): Boolean {
        // TODO("Not yet implemented")
        // se obtienen usuario disponible mediante el data source
        return oAuthAuthenticationDataSource.isUserAvailable()
    }

    override suspend fun login(authorizationCode: String): Boolean {
        // TODO("Not yet implemented")
        // obtiene la operación de login a través del data source
        return oAuthAuthenticationDataSource.login(authorizationCode)
    }

    override suspend fun logout() {
        // TODO("Not yet implemented")
        // cierra sesión mediante el data source
        oAuthAuthenticationDataSource.logout()
    }
}