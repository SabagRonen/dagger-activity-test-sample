package com.example.ronensabag.daggeractivitytestsample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*

import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

  @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun supportFragmentInjector() = fragmentInjector

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
  }
}
