package fi.alandasilva.chat.model

data class User(val uid: String, val email: String, val name: String) {
    companion object {
        fun from(map: HashMap<String, String>) = object {
            val uid by map
            val email by map
            val name by map
            val data = User(uid, email, name)
        }.data
    }
}