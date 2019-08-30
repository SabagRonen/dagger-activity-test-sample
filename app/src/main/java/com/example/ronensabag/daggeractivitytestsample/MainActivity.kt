package com.example.ronensabag.daggeractivitytestsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.toolbar

import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasAndroidInjector {
  @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

  override fun androidInjector() = fragmentInjector

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar as Toolbar)
  }
}
