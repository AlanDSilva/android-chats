package fi.alandasilva.chat.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fi.alandasilva.chat.livedata.FirebaseUserLiveData

class LoginViewModel: ViewModel() {
    private val TAG = "LoginViewModel"

    private var _user = FirebaseUserLiveData()
    val user: LiveData<FirebaseUser?> get() = _user

    private val auth = Firebase.auth
    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
    }
    fun signup(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
    }

}