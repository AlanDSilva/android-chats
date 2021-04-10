package fi.alandasilva.chat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import fi.alandasilva.chat.model.Group

class ChatViewModel: ViewModel() {
    private val TAG = "ChatViewModel"

    val database = Firebase.database

    val groupsRef = database.getReference("groups")
    private var _groups = GroupsLiveData(groupsRef)
    val groups: LiveData<ArrayList<Group>> get() = _groups

}