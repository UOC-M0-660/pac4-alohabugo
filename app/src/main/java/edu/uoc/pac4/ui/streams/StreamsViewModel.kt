package edu.uoc.pac4.ui.streams

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.data.network.UnauthorizedException
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.streams.Stream
import edu.uoc.pac4.data.streams.StreamsRepository
import kotlinx.coroutines.launch

class StreamsViewModel(
        private val authenticationRepository: AuthenticationRepository,
        private val streamsRepository: StreamsRepository
) : ViewModel() {
    private val TAG = "StreamsViewModel"

    // Live Data
    val isRefreshing = MutableLiveData<Boolean>()
    val streamsList = MutableLiveData<List<Stream>>()
    val streamsError = MutableLiveData<Boolean>()
    val isUserLoggedOut = MutableLiveData<Boolean>()

    private var cursor: String? = null

    fun getStreams() {
        Log.d(TAG, "Requesting streams with cursor $cursor")
        // Show Loading
        isRefreshing.postValue(true)
        // Get streams from Repository
        viewModelScope.launch {
            try {
                val streamsPair = streamsRepository.getStreams(cursor)
                val streams = streamsPair.second
                if (streams.isNotEmpty()) {
                    // success :) post streams
                    streamsList.postValue(streams)
                    // save cursor for next request
                    cursor = streamsPair.first
                } else {
                    // error :( post error message
                    streamsError.postValue(true)
                }
                // Finish loading
                isRefreshing.postValue(false)
            } catch (t: UnauthorizedException) {
                Log.w(TAG, "Unauthorized Error getting streams", t)
                // Clear local access token
                viewModelScope.launch {
                    authenticationRepository.logout()
                }
                // post user logged out
                isUserLoggedOut.postValue(true)
            }
        }
    }

    fun isCursorNull(): Boolean {
        return cursor == null
    }
}