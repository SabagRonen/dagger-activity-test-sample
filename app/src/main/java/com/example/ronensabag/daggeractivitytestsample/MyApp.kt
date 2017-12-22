package com.example.ronensabag.daggeractivitytestsample

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApp : Application(), HasActivityInjector {
  @Inject lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
  override fun activityInjector() = dispatchingActivityInjector

  override fun onCreate() {
    super.onCreate()
    DaggerAppComponent.builder().application(this).build().inject(this)
  }
}