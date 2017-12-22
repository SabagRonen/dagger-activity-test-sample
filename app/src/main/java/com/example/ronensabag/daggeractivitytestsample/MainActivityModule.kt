package com.example.ronensabag.daggeractivitytestsample

import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
  @Provides
  fun provideMainPresenter() : MainContract.UserAction {
    return MainPresenter()
  }
}