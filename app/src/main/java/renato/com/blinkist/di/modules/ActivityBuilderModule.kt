package renato.com.blinkist.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import renato.com.blinkist.ui.MainActivity

@Module
abstract class ActivityBuilderModule {
  @ContributesAndroidInjector(modules = [MainActivityModule::class])
  abstract fun providesMainActivity(): MainActivity
}