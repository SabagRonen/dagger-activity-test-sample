package com.example.ronensabag.daggeractivitytestsample

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class,
  ActivityBuilder::class])
interface AppComponent : AndroidInjector<MyApp> {
  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent

  }
}