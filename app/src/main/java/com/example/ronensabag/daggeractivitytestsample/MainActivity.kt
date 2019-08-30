package com.example.ronensabag.daggeractivitytestsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*

import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasAndroidInjector {
  override fun androidInjector(): AndroidInjector<Any>  = fragmentInjector

  @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
  }
}
