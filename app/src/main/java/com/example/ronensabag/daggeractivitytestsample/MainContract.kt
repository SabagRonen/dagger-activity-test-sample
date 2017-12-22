package com.example.ronensabag.daggeractivitytestsample

interface MainContract {
  interface View {
    fun showClickText()

  }

  interface UserAction {
    fun createTopic(view: View)
  }
}