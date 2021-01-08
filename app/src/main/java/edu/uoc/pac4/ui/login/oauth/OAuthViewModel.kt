package edu.uoc.pac4.ui.login.oauth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import kotlinx.coroutines.launch

class OAuthViewModel  (
        private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    // Live Data
    val isLoging = MutableLiveData<Boolean>()

    fun loging(authorizationCode: String) {
        // inicar sesion en Twitch
        viewModelScope.launch {
            // post login result
            isLoging.postValue(authenticationRepository.login(authorizationCode))
        }
    }

}