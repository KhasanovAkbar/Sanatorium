package univ.tuit.sanatorium.app

import android.app.Application
import univ.tuit.sanatorium.libs.Database
import univ.tuit.sanatorium.retrofit.ApiClient
import univ.tuit.sanatorium.room.AppDatabase

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
        Database.database!!.createDatabase().open()
//        Database.getDatabase().createDatabase().open()
        //////////////////////////////
        AppDatabase.init(this)
        ApiClient.refreshToken()
    }
}