package renato.com.blinkist.ui

import android.graphics.Color
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_book.*
import renato.com.androidsampleproject.R
import renato.com.blinkist.model.Book
import java.util.*

class BookItem constructor(private val book: Book) : Item() {

  override fun bind(viewHolder: ViewHolder, position: Int) {
    val rnd = Random()
    val color = Color.argb(255, rnd.nextInt(256),
        rnd.nextInt(256), rnd.nextInt(256))
    viewHolder.background.setBackgroundColor(color)
    viewHolder.title.text = book.title
    //viewHolder.genre.text = book.genre
  }

  override fun getLayout() = R.layout.item_book

  override fun getSpanSize(spanCount: Int, position: Int) = spanCount / 3
}