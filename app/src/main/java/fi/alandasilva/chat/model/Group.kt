package fi.alandasilva.chat.model

data class Group(val id: String,val name: String , val photoUrl: String) {
    companion object {
        fun from(map: HashMap<String, String>) = object {
            val id by map
            val name by map
            val photoUrl by map

            val data = Group(id, name, photoUrl)
        }.data
    }
}