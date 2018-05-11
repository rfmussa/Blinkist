package renato.com.androidsampleproject

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import renato.com.blinkist.model.BlinkistResponse
import renato.com.blinkist.model.Book
import renato.com.blinkist.repository.ApiClient
import renato.com.blinkist.repository.BookDao
import renato.com.blinkist.repository.BookRepository
import java.util.*


@RunWith(JUnit4::class)


class BookRepositoryTest {
  @Rule
  @JvmField var testSchedulerRule = RxOverridingRule()

  @Mock
  private lateinit var bookRepository: BookRepository
  @Mock
  private lateinit var dao: BookDao

  @Mock
  private lateinit var apiClient: ApiClient

  @Before
  @Throws
  fun setUp() {
    MockitoAnnotations.initMocks(this)


    `when`(bookRepository.getBooks()).thenReturn(Observable.just(listOf(mockBook())))
  }

  @Test
  fun `It returns empty list when there is no data on Api`() {
    `when`(apiClient.getBooks()).thenReturn(Observable.just(BlinkistResponse(books= emptyList())))

    bookRepository.getBooks().test()
        .assertValue { it.isEmpty() }
  }

  @Test
  fun `Returns Api Data over DB`() {
    `when`(bookRepository.getBooks()).thenReturn(Observable.just(listOf(mockBook())))

    bookRepository.getBooks().test()
        .assertValueCount(1)
        .assertValue { it.size == 1 }
  }

  @Test
  fun `Both Api and Db return data`() {
    Mockito.`when`(bookRepository.getBooks()).thenReturn(Observable.just(listOf(mockBook())))

    val cachedData = listOf(mockBook())
    val apiData = listOf(mockBook(), mockBook())

    `when`(dao.loadAllBooks()).thenReturn(Flowable.just(cachedData))
    `when`(apiClient.getBooks()).thenReturn(Observable.just(mockBooksModel()))

    bookRepository.getBooks().test()
        //Both cached & API data delivered
        .assertValueCount(2)
        //First cache data delivered
        .assertValueAt(0, { it == cachedData })
        //Secondly api data delivered
        .assertValueAt(1, { it == apiData })
  }

  @Test
  fun `Updates DB from API`() {
    val apiData = mockBooksModel()
    val mockBook = listOf(mockBook())
    `when`(bookRepository.getBooksFromDb()).thenReturn(Observable.just(emptyList()))
    `when`(bookRepository.getBooksFromApi()).thenReturn(Observable.just(BlinkistResponse(books = mockBook).books))
    `when`(bookRepository.getBooks()).thenReturn(Observable.just(mockBook))

    val testObserver = TestObserver<List<Book>>()
    bookRepository.getBooks().subscribe(testObserver)
    testObserver.assertComplete()
    testObserver.assertNoErrors()
    testObserver.assertValue(mockBook)


    bookRepository.assertCalled(1).storeBooksInDb(mockBook)
  }

  private fun mockBook() = Book(33, "Do androids dream of electric sheep", genre = "fiction", author = "Philip K. Dick", image = "", date = Date())

  private fun mockBooksModel(): BlinkistResponse =
      BlinkistResponse(books = listOf(mockBook(), mockBook(), mockBook()))
}


