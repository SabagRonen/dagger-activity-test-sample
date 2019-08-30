package dagger.android

var DaggerApplication.dispatchingActivityInjector: DispatchingAndroidInjector<Any>
  get() = this.androidInjector
  set(value) {androidInjector = value}