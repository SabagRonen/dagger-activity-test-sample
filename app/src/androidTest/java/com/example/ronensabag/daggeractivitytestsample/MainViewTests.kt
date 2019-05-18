package com.example.ronensabag.daggeractivitytestsample

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import dagger.android.AndroidInjector
import dagger.android.AndroidInjector.Factory
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import dagger.android.dispatchingActivityInjector

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
    : DispatchingAndroidInjector<Activity> {
  val myApp = InstrumentationRegistry.getTargetContext().applicationContext as MyApp
  val originalDispatchingActivityInjector = myApp.dispatchingActivityInjector
  var originalFragmentInjector: AndroidInjector<Fragment>? = null
  val fragmentInjector = AndroidInjector<Fragment> { fragment ->
    originalFragmentInjector?.inject(fragment)
    if (fragment is F) {
      fragment.block()
    }
  }
  val fragmentFactory = AndroidInjector.Factory<Fragment> { fragmentInjector }
  val fragmentMap = mapOf(Pair<Class <*>, Provider<Factory<*>>>(F::class.java, Provider { fragmentFactory }))
  val activityInjector = AndroidInjector<Activity> { activity ->
    originalDispatchingActivityInjector.inject(activity)
    if (activity is MainActivity) {
      originalFragmentInjector = activity.fragmentInjector
      activity.fragmentInjector = DispatchingAndroidInjector_Factory.newInstance(fragmentMap, emptyMap())
    }
  }
  val activityFactory = AndroidInjector.Factory<Activity> { activityInjector }
  val activityMap = mapOf(Pair<Class <*>, Provider<Factory<*>>>(MainActivity::class.java, Provider { activityFactory }))
  return DispatchingAndroidInjector_Factory.newInstance(activityMap, emptyMap())
}
