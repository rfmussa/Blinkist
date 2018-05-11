package renato.com.blinkist.repository

import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import renato.com.blinkist.model.Book
import javax.inject.Inject

class BookRepository @Inject internal constructor(private val userApi: ApiClient, private val bookDao: BookDao) {

  fun getBooks(): Observable<List<Book>> {
    return Observable.concatArray(
        getBooksFromDb(),
        getBooksFromApi())
  }

  fun getBooksFromDb(): Observable<List<Book>> {
    return bookDao.loadAllBooks()
        .filter { it.isNotEmpty() }
        .toObservable()
        .doOnNext {
          Log.d("BookDatabase", "Dispatching ${it.size} books from DB...")
        }.doOnError {
          Log.d("BookDatabase", "error RetrievingFrom DB" + it.localizedMessage)
        }
  }

  fun getBooksFromApi(): Observable<List<Book>> {
    return userApi.getBooks().map { it -> it.books }
        .doOnNext {
          Log.d("apiDatabase", "Dispatching ${it.size} books from API...")
          storeBooksInDb(it)
        }.doOnError {
          Log.d("BookDatabase", "error RetrievingFrom DB" + it.localizedMessage)
        }
  }

  fun getBooksAscending(): Observable<List<Book>> {
    return bookDao.sortBooksByAscendingOrder().toObservable()
  }

  fun getBooksDescending(): Observable<List<Book>> {
    return bookDao.sortBooksByDescendingOrder().toObservable()
  }

  fun storeBooksInDb(books: List<Book>) {
    Observable.fromCallable { bookDao.insertAll(books) }
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe {
          Log.d("BookDatabase", "Inserted ${books.size} books")
        }
  }
}