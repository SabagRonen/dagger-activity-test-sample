package com.example.ronensabag.daggeractivitytestsample

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@Component(modules = [AndroidInjectionModule::class, ActivityBuilder::class])
interface AppComponent {
  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent

  }

  fun inject(app: MyApp)
}