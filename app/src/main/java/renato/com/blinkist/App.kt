package renato.com.blinkist

import android.annotation.SuppressLint
import dagger.android.DaggerApplication
import renato.com.blinkist.di.components.DaggerAppComponent

@SuppressLint("Registered")
open class App : DaggerApplication() {
  override fun applicationInjector() = DaggerAppComponent.builder()
      .application(this)
      .build()
}