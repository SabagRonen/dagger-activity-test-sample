package com.example.ronensabag.daggeractivitytestsample

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
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

@RunWith(AndroidJUnit4::class)
class MainViewTests {
  val mockUserAction: MainContract.UserAction = mock(MainContract.UserAction::class.java)

  @get:Rule
  val activityTestRule = object : ActivityTestRule<MainActivity>(MainActivity::class.java, true, true) {
    override fun beforeActivityLaunched() {
      super.beforeActivityLaunched()
      val myApp = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyApp
      myApp.dispatchingActivityInjector = createFakeFragmentInjector<MainFragment> {
        userAction = mockUserAction
      }
    }
  }

  @Test
  fun clickOnFabCallToCreateTopic() {
    onView(withId(R.id.fab)).perform(click())

    verify(mockUserAction).createTopic(
      view = activityTestRule.activity.supportFragmentManager.findFragmentById(R.id.mainFragment) as MainContract.View
    )
  }
}

inline fun <reified F : Fragment> createFakeFragmentInjector(crossinline block : F.() -> Unit)
    : DispatchingAndroidInjector<Any> {
  val myApp = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MyApp
  val originalDispatchingActivityInjector = myApp.dispatchingActivityInjector
  var originalFragmentInjector: AndroidInjector<Any>? = null
  val fragmentInjector = AndroidInjector<Fragment> { fragment ->
    originalFragmentInjector?.inject(fragment)
    if (fragment is F) {
      fragment.block()
    }
  }
  val fragmentFactory = Factory<Fragment> { fragmentInjector }
  val fragmentMap = mapOf(Pair<Class <*>, Provider<Factory<*>>>(F::class.java, Provider { fragmentFactory }))
  val activityInjector = AndroidInjector<Activity> { activity ->
    originalDispatchingActivityInjector.inject(activity)
    if (activity is MainActivity) {
      originalFragmentInjector = activity.androidInjector()
      activity.fragmentInjector = DispatchingAndroidInjector_Factory.newInstance(fragmentMap, emptyMap())
    }
  }
  val activityFactory = Factory<Activity> { activityInjector }
  val activityMap = mapOf(Pair<Class <*>, Provider<Factory<*>>>(MainActivity::class.java, Provider { activityFactory }))
  return DispatchingAndroidInjector_Factory.newInstance(activityMap, emptyMap())
}
