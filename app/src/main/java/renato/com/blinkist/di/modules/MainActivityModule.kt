package renato.com.blinkist.di.modules

import dagger.Module

@Module(includes = [NetModule::class, ApiModule::class])
abstract class MainActivityModule