package com.example.ronensabag.daggeractivitytestsample

import android.app.Activity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
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

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class MainViewTests {
  val mockUserAction: MainContract.UserAction = mock(MainContract.UserAction::class.java)

  @get:Rule
  val activityTestRule = object : ActivityTestRule<MainActivity>(MainActivity::class.java, true, true) {
    override fun beforeActivityLaunched() {
      super.beforeActivityLaunched()
      val myApp = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyApp
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
    : DispatchingAndroidInjector<Any> {
  val injector = AndroidInjector<Activity> { instance ->
    if (instance is T) {
      instance.block()
    }
  }
  val factory = Factory<Activity> { injector }
  val map = mapOf(Pair<Class <*>, Provider<Factory<*>>>(T::class.java, Provider { factory }))
  return DispatchingAndroidInjector_Factory.newInstance(map, emptyMap())
}
