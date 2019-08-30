package com.example.ronensabag.daggeractivitytestsample

import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApp : Application(), HasAndroidInjector {
  @Inject lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Any>
  override fun androidInjector() = dispatchingActivityInjector

  override fun onCreate() {
    super.onCreate()
    DaggerAppComponent.builder().application(this).build().inject(this)
  }
}