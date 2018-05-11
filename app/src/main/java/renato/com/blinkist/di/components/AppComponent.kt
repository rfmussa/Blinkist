package renato.com.blinkist.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import renato.com.blinkist.App
import renato.com.blinkist.di.modules.ActivityBuilderModule
import renato.com.blinkist.di.modules.AppModule
import javax.inject.Singleton

@Singleton @Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilderModule::class])
interface AppComponent : AndroidInjector<App> {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder
    fun build(): AppComponent
  }
}