package renato.com.blinkist.repository

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import renato.com.blinkist.model.Book

@Dao
interface BookDao {
  @Query("SELECT * FROM Book")
  fun loadAllBooks(): Flowable<List<Book>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(products: List<Book>)

  @Query("SELECT * FROM book ORDER BY date DESC")
  fun sortBooksByAscendingOrder(): Flowable<List<Book>>

  @Query("SELECT * FROM book ORDER BY date ASC")
  fun sortBooksByDescendingOrder(): Flowable<List<Book>>
}