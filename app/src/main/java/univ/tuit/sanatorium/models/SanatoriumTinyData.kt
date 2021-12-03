package univ.tuit.sanatorium.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SanatoriumTinyData(
    @PrimaryKey
    var id: Int,
    var src: String,
    var name: String,
    var description: String
)