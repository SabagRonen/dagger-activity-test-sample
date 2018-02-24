package com.example.ronensabag.daggeractivitytestsample

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
  @ContributesAndroidInjector(modules = [FragmentBuilder::class])
  internal abstract fun bindMainActivity(): MainActivity
}