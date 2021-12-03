package univ.tuit.sanatorium.libs

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Error
import java.lang.Exception

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, 1) {
    var dataBase: SQLiteDatabase? = null
        private set
    private val mContext: Context
    @Throws(IOException::class)
    fun createDataBase() {
        //If the database does not exist, copy it from the assets.
        val mDataBaseExist = checkDataBase()
        if (!mDataBaseExist) {
            this.readableDatabase
            close()
            try {
                //Copy the database from assests
                copyDataBase()
                Log.e(TAG, "createDatabase database created")
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists()
    }

    //Copy the database from assets
    @Throws(IOException::class)
    private fun copyDataBase(): Boolean {
        return try {
            val inputStream = mContext.assets.open(DB_NAME)
            val outFileName = DB_PATH + DB_NAME
            val outputStream: OutputStream = FileOutputStream(outFileName)
            val buff = ByteArray(1024)
            var length: Int
            while (inputStream.read(buff).also { length = it } > 0) {
                outputStream.write(buff, 0, length)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    //Open the database, so we can query it
    @Throws(SQLException::class)
    fun openDataBase(): Boolean {
        val mPath = DB_PATH + DB_NAME
        //Log.v("mPath", mPath);
        dataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY)
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return dataBase != null
    }

    @Synchronized
    override fun close() {
        if (dataBase != null) dataBase!!.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val TAG = "DataBaseHelper" // Tag just for the LogCat window

        //destination path (location) of our database on device
        private var DB_PATH = ""
        private const val DB_NAME = "sihatgox.db" // Database name
    }

    init {
        if (Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.applicationInfo.dataDir + "/databases/"
        } else {
            DB_PATH = "/data/data/" + context.packageName + "/databases/"
        }
        mContext = context
    }
}
