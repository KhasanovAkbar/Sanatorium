package univ.tuit.sanatorium.libs

import android.annotation.SuppressLint
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import univ.tuit.sanatorium.models.SanatoriumData
import univ.tuit.sanatorium.models.SanatoriumFullData
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.*

class Database(private val mContext: Context?) {
    private var mDb: SQLiteDatabase? = null
    private val mDbHelper: DataBaseHelper
    fun createDatabase(): Database {
        try {
            mDbHelper.createDataBase()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return this
    }

    @Throws(SQLException::class)
    fun open(): Database {
        mDb = try {
            mDbHelper.openDataBase()
            mDbHelper.close()
            mDbHelper.getReadableDatabase()
        } catch (mSQLException: SQLException) {
            Log.e(TAG, "open >>$mSQLException")
            throw mSQLException
        }
        return this
    }

    fun close() {
        mDbHelper.close()
    }

    @SuppressLint("Range")
    fun getImageById(id: Int): Bitmap {
        val c = mDb!!.rawQuery("select * from files_x where id=$id", null)
        c.moveToFirst()
        val blob = c.getBlob(c.getColumnIndex("image"))
        val inputStream = ByteArrayInputStream(blob)
        return BitmapFactory.decodeStream(inputStream)
    }

    val sanatoriesImages: HashMap<String, ByteArray>
        @SuppressLint("Range")
        get() {
            val hashMap = HashMap<String, ByteArray>()
            val cursor = mDb!!.rawQuery(
                """
                   select files_x.image,all_inf.name from all_inf,files_x
                   where all_inf.id=files_x.all_id;
                   """.trimIndent(), null
            )
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                hashMap[cursor.getString(cursor.getColumnIndex("name"))] =
                    cursor.getBlob(cursor.getColumnIndex("image"))
                cursor.moveToNext()
            }
            cursor.close()
            return hashMap
        }

    @SuppressLint("Range")
    fun getFullDataById(id: Int): SanatoriumFullData {
        val cursor =
            mDb!!.rawQuery("select * from all_inf where all_inf.id=$id", null)
        cursor.moveToFirst()
        return SanatoriumFullData(
            cursor.getInt(cursor.getColumnIndex("id")),
            cursor.getString(cursor.getColumnIndex("name")),
            cursor.getString(cursor.getColumnIndex("content")),
            cursor.getString(cursor.getColumnIndex("location")),
            cursor.getString(cursor.getColumnIndex("tel_number")),
            cursor.getString(cursor.getColumnIndex("fax")),
            cursor.getString(cursor.getColumnIndex("email_number")),
            cursor.getDouble(cursor.getColumnIndex("cost")),
            cursor.getDouble(cursor.getColumnIndex("lat")),
            cursor.getDouble(cursor.getColumnIndex("lon")),
            0
        )
    }

    val sanatoriesFullInf: ArrayList<SanatoriumFullData>
        @SuppressLint("Range")
        get() {
            val data: ArrayList<SanatoriumFullData> = ArrayList<SanatoriumFullData>()
            val cursor = mDb!!.rawQuery("select * from all_inf", null)
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                data.add(
                    SanatoriumFullData(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("content")),
                        cursor.getString(cursor.getColumnIndex("location")),
                        cursor.getString(cursor.getColumnIndex("tel_number")),
                        cursor.getString(cursor.getColumnIndex("fax")),
                        cursor.getString(cursor.getColumnIndex("email_number")),
                        cursor.getDouble(cursor.getColumnIndex("cost")),
                        cursor.getDouble(cursor.getColumnIndex("lat")),
                        cursor.getDouble(cursor.getColumnIndex("lon")),
                        0
                    )
                )
                cursor.moveToNext()
            }
            cursor.close()
            return data
        }

    //    public ArrayList<HamkorData> getHamkor() {
    val sanatories: ArrayList<SanatoriumData>
        @SuppressLint("Range")
        get() {
            val data: ArrayList<SanatoriumData> = ArrayList<SanatoriumData>()
            val cursor = mDb!!.rawQuery(
                "select all_inf.id,files_x.image,all_inf.name,all_inf.content from all_inf,files_x where all_inf.id=files_x.all_id;",
                null
            )
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                data.add(
                    SanatoriumData(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getBlob(cursor.getColumnIndex("image")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("content"))
                    )
                )
                cursor.moveToNext()
            }
            cursor.close()
            return data
        }

    companion object {
        private const val TAG = "TAG"
        var database: Database? = null
            private set

        fun init(context: Context?): Database? {
            if (database == null) {
                database = Database(context)
            }
            return database
        }
    }

    init {
        mDbHelper = DataBaseHelper(mContext!!)
    }
}