package renato.com.blinkist.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

data class BlinkistResponse(
    val books: List<Book>)

@Entity(tableName = "book")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    @ColumnInfo(name = "date")
    val date: Date,
    val image: String,
    val author: String,
    val genre: String
)
