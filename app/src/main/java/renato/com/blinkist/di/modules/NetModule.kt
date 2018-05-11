package renato.com.blinkist.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import renato.com.blinkist.repository.BookDao
import renato.com.blinkist.repository.BookDatabase
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

/**
 * Creates services based on Retrofit interfaces.
 */
@Module
class NetModule {
  companion object {
    private val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")
  }

  @Provides
  fun providesMoshi(): Moshi = Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
      .build()

  @Provides
  fun providesOkHttpClient(application: Application): OkHttpClient = OkHttpClient.Builder()
      .addInterceptor(mockResponseInterceptor(application))
      .build()

  @Provides
  fun providesRetrofit(
      moshi: Moshi,
      okHttpClient: OkHttpClient
  ): Retrofit.Builder = Retrofit.Builder()
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .client(okHttpClient)

  @Provides
  fun provideDatabase(application: Application): BookDatabase {
    return Room.databaseBuilder(application, BookDatabase::class.java, "book.db").build()
  }

  @Provides
  fun provideBookDao(bookDatabase: BookDatabase): BookDao {
    return bookDatabase.bookDao()
  }

  /**
   * Short-Circuit response to handle us mock data
   */
  private fun mockResponseInterceptor(application: Application): Interceptor {
    return Interceptor {
      var request = it.request()
      val jsonString = application.assets.open("books_response.json").bufferedReader().use {
        it.readText()
      }

      val body = ResponseBody.create(MEDIA_TYPE, jsonString)
      var response = Response.Builder()
          .code(200)
          .request(request)
          .message("")
          .protocol(Protocol.HTTP_1_0)
          .body(body)
          .addHeader("content-type", "application/json")
          .build()

      response
    }
  }
}
