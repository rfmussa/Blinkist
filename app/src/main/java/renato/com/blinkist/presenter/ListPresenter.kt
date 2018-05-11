package renato.com.blinkist.presenter

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import renato.com.blinkist.model.Book
import renato.com.blinkist.repository.BookRepository
import renato.com.blinkist.ui.MviView
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class ListPresenter @Inject internal constructor(
    private val bookRepository: BookRepository
) : MviPresenter<ListPresenter.View>() {

  override fun onBind(view: View, disposable: CompositeDisposable): Disposable {
    disposable += subscribeToBooks(view)
    disposable += subscribeToViewActions(view)

    return disposable
  }

  /**
   * Subscribe to data from repository
   */
  open fun subscribeToBooks(view: View): Disposable {
    return bookRepository.getBooks()
        .debounce(400, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(IoScheduler()).map {
          mapResponseToList(it)
        }
        .subscribeBy(
            onNext = {
              view.render(model = DiscoverUiModel.List(it))
            },
            onError = {
              Log.d("ErrorMessage", it.localizedMessage)
            })
  }

  /**
   * Subscribe to user actions, in this case sort button click
   */
  open fun subscribeToViewActions(view: View): Disposable {
    return view.actions()
        .subscribeBy(
            onNext = { action ->
              when (action) {
                is DiscoverUiAction.SortAscending -> {
                  bookRepository.getBooksAscending().observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                      onNext = {
                        view.render(model = DiscoverUiModel.List(mapResponseToList(it)))
                      })
                }
                is DiscoverUiAction.SortDescending -> {
                  bookRepository.getBooksDescending().observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                      onNext = {
                        view.render(model = DiscoverUiModel.List(mapResponseToList(it)))
                      })
                }

              }
            }
        )
  }

  interface View : MviView<Unit> {
    fun actions(): Observable<DiscoverUiAction>
    fun render(model: DiscoverUiModel)
  }

  sealed class DiscoverUiModel {
    data class List(val bookList: HashMap<Date, MutableList<Book>>) : DiscoverUiModel()
  }

  sealed class DiscoverUiAction {
    class SortAscending : DiscoverUiAction()
    class SortDescending : DiscoverUiAction()
  }

  /**
   * Maps the response from the repository into a HashMap RecyclerView friendly
   */
  private fun mapResponseToList(bookList: List<Book>): HashMap<Date, MutableList<Book>> {
    val bookHashMap = HashMap<Date, MutableList<Book>>()
    for (book in bookList) {
      // only add to final structure if key is not already present
      if (!bookHashMap.containsKey(book.date)) {
        val bookArray = ArrayList<Book>()
        // filter the response for items with the same date and add it to the array list
        bookList.filter { it.date == book.date }.forEach {
          bookArray.add(it)
        }
        // add date as key, and books as value
        bookHashMap[book.date] = bookArray
      }
    }
    return bookHashMap
  }
}
