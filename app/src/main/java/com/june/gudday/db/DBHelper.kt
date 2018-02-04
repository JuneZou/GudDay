package com.june.gudday.db

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.june.gudday.db.base.DBBaseService
import java.io.File
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy



/**
 * Created by June on 2017/11/14.
 * Email:upupupgoing@126.com
 */
class DBHelper(var name: String,
               var version: Int,
               var context: Context) {
    val TAG = "DBHelper"
    var db: SQLiteDatabase? = null

    init {
        db = Help(context, DBConfigure.DB_NAME, null, DBConfigure.VERSION).writableDatabase
    }

    private class Help(var context: Context,
                       name: String?,
                       factory: SQLiteDatabase.CursorFactory?,
                       version: Int) : SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase?) {

            val city = context.resources.assets.open(DBConfigure.TABLE_CITY)

            var citySQl: String = ""

            city.buffered().reader().use {
                citySQl = it.readText()
            }

            citySQl.split(";").iterator().forEach {
                db?.execSQL(it)
            }

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        }

    }

    class Builder(var context: Context) {

        private var path = ""

        private var versionNumber = 0

        fun name(path: String): Builder {
            this.path = path
            return this
        }

        fun versionCode(version: Int): Builder {
            this.versionNumber = version
            return this
        }

        fun build(): DBHelper {
            return DBHelper(path, versionNumber, context)
        }
    }
}