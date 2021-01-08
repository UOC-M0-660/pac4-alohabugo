package edu.uoc.pac4.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.data.network.UnauthorizedException
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.user.User
import edu.uoc.pac4.data.user.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
        private val authenticationRepository: AuthenticationRepository,
        private val userRepository: UserRepository
) : ViewModel() {
    private val TAG = "ProfileViewModel"

    // Live Data
    val isLoading = MutableLiveData<Boolean>()
    val user = MutableLiveData<User>()
    val userError = MutableLiveData<Boolean>()
    val isUserLoggedOut = MutableLiveData<Boolean>()

    fun getUserProfile() {
        // obtener los datos del usuario de la API Twitch
        // Show Loading
        isLoading.postValue(true)
        viewModelScope.launch {
            try {
                userRepository.getUser()?.let {
                    // success :) post user
                    user.postValue(it)
                } ?: run {
                    // error :( post error message
                    userError.postValue(true)
                }
                // Hide Loading
                isLoading.postValue(false)
            } catch (t: UnauthorizedException) {
                Log.w(TAG, "Unauthorized Error getting user profile", t)
                // Clear local access token
                viewModelScope.launch {
                    authenticationRepository.logout()
                }
                // post user logged out
                isUserLoggedOut.postValue(true)
            }
        }
    }

    fun updateUserDescription(description: String) {
        // actualizar la descripción del usuario usando la API Twitch
        // Show Loading
        isLoading.postValue(true)
        viewModelScope.launch {
            try {
                userRepository.updateUser(description)?.let {
                    // success :) post user
                    user.postValue(it)
                } ?: run {
                    // error :( post error message
                    userError.postValue(true)
                }
                // Hide Loading
                isLoading.postValue(false)
            } catch (t: UnauthorizedException) {
                Log.w(TAG, "Unauthorized Error updating user description", t)
                // Clear local access
                logout()
                // post user logged out
                isUserLoggedOut.postValue(true)
            }
        }
    }

    fun logout() {
        // Clear local access
        viewModelScope.launch {
            authenticationRepository.logout()
        }
    }
}