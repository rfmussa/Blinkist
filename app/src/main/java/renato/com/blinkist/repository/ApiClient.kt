package renato.com.blinkist.repository

import io.reactivex.Observable
import renato.com.blinkist.model.BlinkistResponse
import retrofit2.http.GET

interface ApiClient {
  @GET("user/books")
  fun getBooks(): Observable<BlinkistResponse>
}
