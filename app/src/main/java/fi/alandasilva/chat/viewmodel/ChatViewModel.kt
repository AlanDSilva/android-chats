package fi.alandasilva.chat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import fi.alandasilva.chat.model.Group
import fi.alandasilva.chat.model.Message
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ChatViewModel: ViewModel() {
    private val TAG = "ChatViewModel"

    val database = Firebase.database
    val storage = Firebase.storage

    // GroupFragment
    val groupsRef = database.getReference("groups")
    private var _groups = GroupsLiveData(groupsRef)
    val groups: LiveData<ArrayList<Group>> get() = _groups

    fun addGroup(data: ByteArray, name: String){
        var str = name.replace("\\s".toRegex(), "")
        val storageRef = storage.reference.child("images/${str}")

        var uploadTask = storageRef.putBytes(data)
        uploadTask
            .addOnCompleteListener{ task->
                if(task.isSuccessful) {
                    Log.d(TAG, "Task succesful with url: ${storageRef.downloadUrl}")
                    storageRef.downloadUrl.addOnCompleteListener {task->
                        Log.d(TAG, "URI succesful with: ${task.result}")
                        val key = groupsRef.push().key!!
                        val newGroup = Group(key, name, task.result.toString())
                        groupsRef.child(key).setValue(newGroup)
                    }
                } else {
                    Log.w(TAG, "Task was not succesful")
                }
            }

    }



    // ChatFragment
    private var messagesRef = database.getReference("messages")
    private var _messages = MutableLiveData<ArrayList<Message>>()
    val messages: LiveData<ArrayList<Message>> get() = _messages
    fun setMessagesRef(id: String){
        Log.d(TAG, "Will set message reference")
        messagesRef = database.getReference("messages").child(id)
        _messages = MessagesLiveData(messagesRef)
    }
    fun addMessage(message: String) {
        // Get current time
        val formatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))

        // Get user if any
        val userEmail = if (auth.currentUser != null) auth.currentUser.email else "Unknown"
        messagesRef.push().setValue(Message(message, userEmail, formatted))
        Log.i("ChatViewModel", "add message function")
    }

    // FirebaseUser
    private var _user = FirebaseUserLiveData()
    val user: LiveData<FirebaseUser?> get() = _user
    private val auth = Firebase.auth
    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
    }
    fun signup(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
    }
    fun logout () {
        auth.signOut()
    }

}