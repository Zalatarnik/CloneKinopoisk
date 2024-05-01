package com.example.clonekinopoisk.ui.fragmentsOfRegistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonekinopoisk.domain.FilmsRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
const val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[-+_!@#$%^&*.,?]).{6,50}\$"


@HiltViewModel
class SignUpViewModel @Inject constructor(
) : ViewModel() {


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
            _emailErrorForRead.value = "Email is required"
            return
        }
        if (password.isBlank()) {
            _passwordErrorForRead.value = "Password is required"
            return
        }
        if (!email.matches(EMAIL_PATTERN.toRegex())) {
            _emailErrorForRead.value = "Invalid email format"
            return
        }
        if (!password.matches(PASSWORD_PATTERN.toRegex())) {
            _passwordErrorForRead.value = "he password must contain at least 6 characters," +
                    " a large letter,a small letter, a number and a sign"
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

                } else {
                    _registrationStateForRead.value = RegistrationState.Error("Что-то пошло не так")
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





