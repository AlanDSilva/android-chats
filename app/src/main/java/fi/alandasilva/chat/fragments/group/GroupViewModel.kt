package fi.alandasilva.chat.fragments.group

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import fi.alandasilva.chat.livedata.GroupsLiveData
import fi.alandasilva.chat.model.Group
import kotlin.collections.ArrayList

class GroupViewModel: ViewModel() {
    private val TAG = "GroupViewModel"

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


}