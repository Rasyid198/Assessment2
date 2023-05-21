package org.d3if4075.Persegiku.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PersegiEntity::class], version = 1, exportSchema = false)
abstract class PersegiDb : RoomDatabase() {
    abstract val dao: PersegiDao

    companion object{
        @Volatile

        private var INSTANCE: PersegiDb? = null

        fun getInstance(context: Context): PersegiDb {
            synchronized(this){
                var instances = INSTANCE

                if(instances==null){
                    instances = Room.databaseBuilder(
                        context.applicationContext,
                        PersegiDb::class.java,
                        "persegi.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instances
                }
                return instances
            }
        }
    }
}