package renato.com.blinkist.ui

import android.annotation.SuppressLint
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.section_item.*
import renato.com.androidsampleproject.R
import java.text.SimpleDateFormat
import java.util.*

class SectionItem constructor(private val date: Date) : Item() {

  override fun bind(viewHolder: ViewHolder, position: Int) {

    viewHolder.title.text = getDayOfWeek(date)
  }

  override fun getLayout() = R.layout.section_item

  @SuppressLint("SimpleDateFormat")
//TODO could be extension function - handled better
  fun getDayOfWeek(date: Date): String {
    val simpleDateformat = SimpleDateFormat("EEEE")
    return simpleDateformat.format(date)
  }
}