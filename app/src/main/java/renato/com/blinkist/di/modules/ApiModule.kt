package renato.com.blinkist.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import renato.com.blinkist.repository.ApiClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

@Module
class ApiModule(var baseUrl: String = "http://localhost") {
  @Provides
  fun providesApi(retrofit: Retrofit.Builder): ApiClient = retrofit
      .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
      .baseUrl(baseUrl)
      .build()
      .create(ApiClient::class.java)
}
