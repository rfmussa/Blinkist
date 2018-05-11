package renato.com.blinkist.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import renato.com.blinkist.model.Book

@Database(entities = [(Book::class)], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class BookDatabase : RoomDatabase() {
  abstract fun bookDao(): BookDao
}