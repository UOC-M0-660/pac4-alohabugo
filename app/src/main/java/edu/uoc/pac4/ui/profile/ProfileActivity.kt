package edu.uoc.pac4.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import edu.uoc.pac4.R
import edu.uoc.pac4.ui.login.LoginActivity
import edu.uoc.pac4.data.user.User
import kotlinx.android.synthetic.main.activity_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileActivity : AppCompatActivity() {

    private val TAG = "ProfileActivity"

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        // Get User Profile
        // obtenemos user profile haciendo uso de viewModel
        profileViewModel.getUserProfile()

        // Update Description Button Listener
        updateDescriptionButton.setOnClickListener {
            // Hide Keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
            // Update User Description
            profileViewModel.updateUserDescription(userDescriptionEditText.text?.toString() ?: "")
        }
        // Logout Button Listener
        logoutButton.setOnClickListener {
            // Logout
            logout()
        }

        // observar los datos del viewModel
        profileViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                // Show Loading
                progressBar.visibility = VISIBLE
            } else {
                // Hide Loading
                progressBar.visibility = GONE
            }
        })
        profileViewModel.user.observe(this, { user ->
            // Update the UI with the user data
            setUserInfo(user)
        })
        profileViewModel.userError.observe(this, { userError ->
            if (userError) {
                // error :( show error message
                showError(getString(R.string.error_profile))
            }
        })
        profileViewModel.isUserLoggedOut.observe(this, { isUserLoggedOut ->
            if (isUserLoggedOut) {
                // User was logged out, call fun onUnauthorized()
                onUnauthorized()
            }
        })
    }

    private fun setUserInfo(user: User) {
        // Set Texts
        userNameTextView.text = user.userName
        userDescriptionEditText.setText(user.description ?: "")
        // Avatar Image
        user.profileImageUrl?.let {
            Glide.with(this)
                .load(user.getSizedImage(it, 128, 128))
                .centerCrop()
                .transform(CircleCrop())
                .into(imageView)
        }
        // Views
        viewsText.text = getString(R.string.views_text, user.viewCount)
    }

    private fun logout() {
        // Clear local session data
        profileViewModel.logout()
        // Close this and all parent activities
        finishAffinity()
        // Open Login
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun onUnauthorized() {
        // User was logged out, close screen and all parent screens and open login
        finishAffinity()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun showError(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    // Override Action Bar Home button to just finish the Activity
    // not to re-launch the parent Activity (StreamsActivity)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            false
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}