package fr.epf.min2.pjtmin2.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CountryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

}