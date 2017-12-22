package com.example.ronensabag.daggeractivitytestsample

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import dagger.android.AndroidInjector
import dagger.android.AndroidInjector.Factory
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Provider

@RunWith(AndroidJUnit4::class)
class MainViewTests {
  val mockUserAction = mock(MainContract.UserAction::class.java)

  @get:Rule
  val activityTestRule = object : ActivityTestRule<MainActivity>(MainActivity::class.java, true, true) {
    override fun beforeActivityLaunched() {
      super.beforeActivityLaunched()
      val myApp = InstrumentationRegistry.getTargetContext().applicationContext as MyApp
      myApp.dispatchingActivityInjector = createFakeActivityInjector<MainActivity> {
        userAction = mockUserAction
      }
    }
  }

  @Test
  fun clickOnFabCallToCreateTopic() {
    onView(withId(R.id.fab)).perform(click())

    verify(mockUserAction).createTopic(view = activityTestRule.activity)
  }
}

inline fun <reified T : Activity> createFakeActivityInjector(crossinline block : T.() -> Unit)
    : DispatchingAndroidInjector<Activity> {
  val injector = AndroidInjector<Activity> { instance ->
    if (instance is T) {
      instance.block()
    }
  }
  val factory = AndroidInjector.Factory<Activity> { injector }
  val map = mapOf(Pair<Class <out Activity>, Provider<Factory<out Activity>>>(T::class.java, Provider { factory }))
  return DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(map)
}
