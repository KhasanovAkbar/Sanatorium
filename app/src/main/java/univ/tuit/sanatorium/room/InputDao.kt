package univ.tuit.sanatorium.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import univ.tuit.sanatorium.models.SanatoriumFullData
import univ.tuit.sanatorium.models.SanatoriumTinyData

@Dao
interface InputDao {

    @Query("SELECT * FROM SanatoriumFullData")
    fun get(): LiveData<List<SanatoriumFullData>>

    @Insert
        (onConflict = OnConflictStrategy.REPLACE)
    fun save(inputData: SanatoriumFullData)

    @Query("SELECT * FROM SanatoriumTinyData")
    fun getTiny(): LiveData<List<SanatoriumTinyData>>

    @Insert
        (onConflict = OnConflictStrategy.REPLACE)
    fun saveTiny(inputData: SanatoriumTinyData)

    @Query("SELECT * FROM SanatoriumTinyData")
    fun getTinySanatoryOffline(): List<SanatoriumTinyData>

    @Query("SELECT * FROM SanatoriumFullData")
    fun getSanatoryOffline(): List<SanatoriumFullData>

    @Query("SELECT * FROM SanatoriumFullData where id=:id")
    fun getSanatoryOfflineById(id: Int): SanatoriumFullData

}