package fi.alandasilva.chat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fi.alandasilva.chat.model.Group
import fi.alandasilva.chat.model.Message

class ChatViewModel: ViewModel() {
    private val TAG = "ChatViewModel"

    val database = Firebase.database

    // GroupFragment
    val groupsRef = database.getReference("groups")
    private var _groups = GroupsLiveData(groupsRef)
    val groups: LiveData<ArrayList<Group>> get() = _groups

    // ChatFragment
    private var _messages = MutableLiveData<ArrayList<Message>>()
    val messages: LiveData<ArrayList<Message>> get() = _messages
    fun setMessagesRef(id: String){
        Log.d(TAG, "Will set message reference")
        val messagesRef = database.getReference("messages").child(id)
        _messages = MessagesLiveData(messagesRef)
    }
}