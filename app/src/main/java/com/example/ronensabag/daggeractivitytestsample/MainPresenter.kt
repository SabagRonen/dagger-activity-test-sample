package com.example.ronensabag.daggeractivitytestsample

class MainPresenter : MainContract.UserAction {
  override fun createTopic(view: MainContract.View) {
    view.showClickText()
  }
}