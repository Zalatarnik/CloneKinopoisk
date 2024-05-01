package com.example.clonekinopoisk.ui.fragmentsOfRegistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonekinopoisk.domain.FilmsRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel@Inject constructor(
) : ViewModel() {


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
            _emailErrorForRead.value = "Email is required"
            return
        }
        if (password.isBlank()) {
            _passwordErrorForRead.value = "Password is required"
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

                } else {
                    _registrationStateForRead.value = RegistrationState.Error("Что-то пошло не так")
                }
            }
    }
}


