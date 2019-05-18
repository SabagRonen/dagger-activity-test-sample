package com.example.ronensabag.daggeractivitytestsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ronensabag.daggeractivitytestsample.MainContract.View
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.textView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), View {

  @Inject lateinit var userAction: MainContract.UserAction

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    fab.setOnClickListener { view ->
      userAction.createTopic(this)
    }
  }

  override fun showClickText() {
    textView.setText("Fab was clicked")
  }
}
