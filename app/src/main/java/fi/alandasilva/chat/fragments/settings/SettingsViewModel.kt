package fi.alandasilva.chat.fragments.settings

import android.accounts.AuthenticatorException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import fi.alandasilva.chat.livedata.FirebaseUserLiveData
import fi.alandasilva.chat.model.Group
import fi.alandasilva.chat.model.Message
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class SettingsViewModel: ViewModel() {
    private val TAG = "SettingsViewModel"

    private var _user = FirebaseUserLiveData()
    val user: LiveData<FirebaseUser?> get() = _user
    private val auth = Firebase.auth

    fun logout () {
        auth.signOut()
    }

}