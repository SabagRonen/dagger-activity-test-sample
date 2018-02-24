package com.example.ronensabag.daggeractivitytestsample

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
  @ContributesAndroidInjector(modules = [MainActivityModule::class])
  internal abstract fun bindMainFragment(): MainFragment
}