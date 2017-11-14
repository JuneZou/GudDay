package com.june.gudday.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.june.gudday.db.base.DBBaseService
import java.io.File
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

/**
 * Created by June on 2017/11/14.
 * Email:upupupgoing@126.com
 */
class DBHelper(var path: String, var version: Int, var context: Context) {
    val TAG = "DBHelper"
    val baseUrl = "/data/data/" + context.packageName + "/databases/"
    var db: SQLiteDatabase? = null

    fun <T> create(service: Class<T>) : T {

        if (!File(baseUrl).exists()) {
            File(baseUrl).mkdirs()
        }

        db = SQLiteDatabase.openOrCreateDatabase(baseUrl + path + ".db", null)
        return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service),
                {
                    proxy, method, args ->
                    db?.execSQL(args[0].toString())
                }) as T
    }

    class Builder(var context: Context) {

        private var path = ""

        private var versionNumber = 0

        fun name(path: String) : Builder {
            this.path = path
            return this
        }

        fun versionCode(version: Int) : Builder {
            this.versionNumber = version
            return this
        }

        fun build() : DBHelper {
            return DBHelper(path, versionNumber, context)
        }
    }
}