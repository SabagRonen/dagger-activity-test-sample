package dagger.android

import android.app.Activity

var DaggerApplication.dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
  get() = this.activityInjector()
  set(value) {activityInjector = value}