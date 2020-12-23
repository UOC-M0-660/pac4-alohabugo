package edu.uoc.pac4.data.di

import edu.uoc.pac4.data.SessionManager
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.oauth.OAuthAuthenticationDataSource
import edu.uoc.pac4.data.oauth.OAuthAuthenticationRepository
import edu.uoc.pac4.data.streams.StreamsRepository
import edu.uoc.pac4.data.streams.TwitchStreamsDataSource
import edu.uoc.pac4.data.streams.TwitchStreamsRepository
import edu.uoc.pac4.data.user.TwitchUserDataSource
import edu.uoc.pac4.data.user.TwitchUserRepository
import edu.uoc.pac4.data.user.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by alex on 11/21/20.
 */

val dataModule = module {
    // TODO: Init your Data Dependencies

    // Streams example
    // single<StreamsRepository> { TwitchStreamsRepository() }

    // Data Sources
    single { OAuthAuthenticationDataSource(SessionManager(androidContext()), get()) }
    single { TwitchStreamsDataSource(get()) }
    single { TwitchUserDataSource(get()) }

    // Repositories
    single<AuthenticationRepository> { OAuthAuthenticationRepository(get()) }
    single<StreamsRepository> { TwitchStreamsRepository(get()) }
    single<UserRepository> { TwitchUserRepository(get()) }


}