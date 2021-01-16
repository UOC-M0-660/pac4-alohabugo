package edu.uoc.pac4.data.streams

/**
 * Created by alex on 11/21/20.
 */

class TwitchStreamsRepository(
        // TODO: Add any datasources you may need
        private val twitchStreamsDataSource: TwitchStreamsDataSource
        ) : StreamsRepository {

    override suspend fun getStreams(cursor: String?): Pair<String?, List<Stream>> {
        // TODO("Not yet implemented")
        // se obtienen los streams mediante el data source
        return twitchStreamsDataSource.getStreams(cursor)
    }

}