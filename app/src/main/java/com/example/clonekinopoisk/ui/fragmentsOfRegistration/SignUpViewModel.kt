package com.example.clonekinopoisk.ui.fragmentsOfRegistration

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonekinopoisk.domain.FilmsRepository
import com.example.clonekinopoisk.domain.SharedPreferencesRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
const val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-+_!@#$%^&*.,?]).{6,50}\$"


@HiltViewModel
class SignUpViewModel @Inject constructor(
) : ViewModel() {

    @Inject
    lateinit var sharedPreferencesRepository: SharedPreferencesRepository


    private val _emailErrorForRead = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailErrorForRead

    private val _passwordErrorForRead = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordErrorForRead

    private val _registrationStateForRead = MutableLiveData<RegistrationState>()
    val registrationState: LiveData<RegistrationState> = _registrationStateForRead

    private val _navigationEventForRead = MutableLiveData<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> = _navigationEventForRead



    fun registerUser(email: String, password: String) {
        if (email.isBlank()) {
            _emailErrorForRead.value = "@string/email_is_required"
            return
        }
        if (password.isBlank()) {
            _passwordErrorForRead.value = "@string/password_is_required"
            return
        }
        if (!email.matches(EMAIL_PATTERN.toRegex())) {
            _emailErrorForRead.value = "@string/invalid_email_format"
            return
        }
        if (!password.matches(PASSWORD_PATTERN.toRegex())) {
            _passwordErrorForRead.value = "@string/invalid_password_format"
            return
        }

        _emailErrorForRead.value = null
        _passwordErrorForRead.value = null
        _registrationStateForRead.value = RegistrationState.Loading

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _registrationStateForRead.value = RegistrationState.Success
                    _navigationEventForRead.value = NavigationEvent.NavigateToMainFragment
                    sharedPreferencesRepository.setUserEmail(email)
                    Firebase.auth.currentUser!!.sendEmailVerification()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Log.d(TAG, "@string/Email_sent")
                            }
                        }

                } else {
                    _registrationStateForRead.value = RegistrationState.Error("@string/something_went_wrong")
                }
            }
    }
}

sealed class RegistrationState {
    object Loading : RegistrationState()
    object Success : RegistrationState()
    data class Error(val message: String?) : RegistrationState()
}

sealed class NavigationEvent {
    object NavigateToMainFragment : NavigationEvent()
}





