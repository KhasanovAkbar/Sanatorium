package univ.tuit.sanatorium.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SanatoriumFullData(
    @PrimaryKey
    //(autoGenerate = true)
    var id: Int,
    var name: String,
    var content: String,
    var location: String,
    var tel_number: String,
    var fax: String,
    var email_number: String,
    var cost: Double,
    var lat: Double,
    var lon: Double,
    var place_count: Int
)