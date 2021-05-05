package fi.alandasilva.chat.model

data class Group(val id: String,val name: String , val photoUrl: String, val category: String) {
    companion object {
        fun from(map: HashMap<String, String>) = object {
            val id by map
            val name by map
            val photoUrl by map
            val category by map

            val data = Group(id, name, photoUrl, category)
        }.data
    }
}