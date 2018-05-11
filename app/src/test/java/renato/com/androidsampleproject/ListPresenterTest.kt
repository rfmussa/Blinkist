package renato.com.androidsampleproject

import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import renato.com.blinkist.model.BlinkistResponse
import renato.com.blinkist.model.Book
import renato.com.blinkist.presenter.ListPresenter
import renato.com.blinkist.repository.BookRepository
import java.util.*

@RunWith(JUnit4::class)
class ListPresenterUTest {
  @Mock
  private lateinit var bookRepository: BookRepository

  @Mock
  private lateinit var mockView: ListPresenter.View
  private lateinit var listPresenter: ListPresenter
  private val sortClick: BehaviorSubject<ListPresenter.DiscoverUiAction> = BehaviorSubject.create()

  @Before
  @Throws
  fun setUp() {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler({ Schedulers.trampoline() })
    MockitoAnnotations.initMocks(this)

    listPresenter = ListPresenter(bookRepository)
    mockView.actions().mockReturn(sortClick)

    Mockito.`when`(bookRepository.getBooks()).thenReturn(Observable.just(mockBooksModel().books))
  }

  @Test
  fun `It renders the list when you bind`() {

    listPresenter.bind(mockView)

    mockView.assertCalled(1).render(any(ListPresenter.DiscoverUiModel.List::class.java))
  }

  @Test
  fun `It calls sorting ASC method when the user clicks`() {
    Mockito.`when`(bookRepository.getBooksAscending()).thenReturn(Observable.just(emptyList()))

    listPresenter.bind(mockView)
    sortClick.onNext(ListPresenter.DiscoverUiAction.SortAscending())

    bookRepository.assertCalled(1).getBooksAscending()
  }

  @Test
  fun `It calls sorting DESC method when the user clicks`() {
    Mockito.`when`(bookRepository.getBooksDescending()).thenReturn(Observable.just(emptyList()))

    listPresenter.bind(mockView)
    sortClick.onNext(ListPresenter.DiscoverUiAction.SortDescending())

    bookRepository.assertCalled(1).getBooksDescending()
  }

  private fun mockBooksModel(): BlinkistResponse =
      BlinkistResponse(books = listOf(Book(id = 12, title = "test book", genre = "fiction", author = "Renato Mussa", image = "", date = Date()),
          Book(id = 13, title = "sample book", genre = "fiction", author = "Renato Mussa", image = "", date = Date())))
}
