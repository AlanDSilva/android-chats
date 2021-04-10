package fi.alandasilva.chat.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import fi.alandasilva.chat.model.Group

class GroupsLiveData(private val groupsRef: DatabaseReference): MutableLiveData<ArrayList<Group>>() {
    private val TAG = "GroupsLiveData"

    private var listenerRegistration = DataEventListener()
    private var groups = arrayListOf<Group>()

    private inner class DataEventListener: ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            Log.d(TAG, "Error: ${error.toException()}")
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            groups.clear()
            snapshot.children.forEach{child ->
                groups.add(Group.from(child.value as HashMap<String, String>))
            }
            value = groups
            Log.d(TAG, "Got some groups")

        }
    }

    override fun onActive() {
        super.onActive()
        groupsRef.addValueEventListener(listenerRegistration)
    }

    override fun onInactive() {
        super.onInactive()
        groupsRef.removeEventListener(listenerRegistration)
    }
}