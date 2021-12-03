package univ.tuit.sanatorium.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import univ.tuit.sanatorium.models.SanatoriumFullData
import univ.tuit.sanatorium.models.SanatoriumTinyData

@Database(entities = [SanatoriumFullData::class, SanatoriumTinyData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getInputData(): InputDao

    companion object {
        var database: AppDatabase? = null
        fun init(context: Context) {
            database = Room.databaseBuilder(
                context.applicationContext, AppDatabase::
                class.java, "lesson"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}