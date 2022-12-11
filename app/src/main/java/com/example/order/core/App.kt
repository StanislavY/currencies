package com.example.order.core

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.order.datasource.room.DataBaseFrom1C.DatabaseFrom1C
import com.example.order.datasource.room.DataBaseFrom1C.DatabaseFrom1CDAO
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this


    }

    companion object {




       lateinit var appInstance: App
        fun getContext(): Context? {
            return appInstance.applicationContext
        }

        private var db1C: DatabaseFrom1C? = null

        private val DB1C_NAME = GlobalConstAndVars.DATABASE1C_NAME



        fun get1CDAO(): DatabaseFrom1CDAO {
            if (db1C == null) {
                synchronized(DatabaseFrom1C::class.java) {
                    if (db1C == null) {


                        db1C = Room.databaseBuilder(
                            appInstance.applicationContext,
                            DatabaseFrom1C::class.java, DB1C_NAME
                        )
                            .allowMainThreadQueries()

                            .build()


                    }
                }
            }
            return db1C!!.databaseFrom1CDAO()


        }





    }
}