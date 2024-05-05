package com.example.clonekinopoisk.ui.fragmentsOfRegistration

import android.content.SharedPreferences
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
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


@HiltViewModel
class LoginViewModel@Inject constructor(
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

    fun login(email: String, password: String) {
        if (email.isBlank()) {
            _emailErrorForRead.value = "@string/email_is_required"
            return
        }
        if (password.isBlank()) {
            _passwordErrorForRead.value = "@string/password_is_required"
            return
        }

        _emailErrorForRead.value = null
        _passwordErrorForRead.value = null
        _registrationStateForRead.value = RegistrationState.Loading

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _registrationStateForRead.value = RegistrationState.Success
                    _navigationEventForRead.value = NavigationEvent.NavigateToMainFragment
                    sharedPreferencesRepository.setUserEmail(email)
                } else {
                    _registrationStateForRead.value = RegistrationState.Error( "@string/something_went_wrong")
                }
            }
    }
}


