package renato.com.blinkist.ui

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_grid.*
import renato.com.androidsampleproject.R
import renato.com.blinkist.presenter.ListPresenter
import renato.com.blinkist.presenter.ListPresenter.DiscoverUiAction
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), ListPresenter.View {

  private var sortAction: BehaviorSubject<ListPresenter.DiscoverUiAction> = BehaviorSubject.create<ListPresenter.DiscoverUiAction>()

  @Inject
  lateinit var presenter: ListPresenter

  private lateinit var groupAdapter: GroupAdapter<ViewHolder>

  private var ascendingOrder: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    AndroidInjection.inject(this)

    setContentView(R.layout.fragment_grid)
    setupRecyclerView()

    presenter.bind(this)

    //TODO use RXViews to have Observable of clicks
    sortFab.setOnClickListener {
      ascendingOrder = !ascendingOrder
      if (ascendingOrder) {
        sortAction.onNext(DiscoverUiAction.SortAscending())
      } else {
        sortAction.onNext(DiscoverUiAction.SortDescending())
      }
    }
  }

  private fun setupRecyclerView() {

    groupAdapter = GroupAdapter<ViewHolder>().apply {
      spanCount = 3
    }
    recyclerView.apply {
      layoutManager = GridLayoutManager(this.context, groupAdapter.spanCount).apply {
        spanSizeLookup = groupAdapter.spanSizeLookup
      }
      adapter = groupAdapter
    }
  }

  override fun actions(): Observable<ListPresenter.DiscoverUiAction> {
    return sortAction
  }

  override fun render(model: ListPresenter.DiscoverUiModel) {
    when (model) {
      is ListPresenter.DiscoverUiModel.List -> {
        Log.d("GroupAdapter", "ItemCount" + groupAdapter.itemCount)
        // TODO could be improved, current implementation will not play nicely with pagination
        // In case of a sort, invalidate whole adapter
        if (groupAdapter.itemCount > 0) {
          groupAdapter.clear()
        }
        // render a section for each key(date)
        model.bookList.keys.forEach {
          Section(SectionItem(it)).apply {
            // add items from given key below the section
            model.bookList[it]!!.forEach { book ->
              add(BookItem(book))
            }
            groupAdapter.add(this)
          }
        }
      }
    }
  }
}
