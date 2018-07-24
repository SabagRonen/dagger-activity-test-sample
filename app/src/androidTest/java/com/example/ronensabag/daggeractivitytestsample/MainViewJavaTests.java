package com.example.ronensabag.daggeractivitytestsample;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.DispatchingAndroidInjector_Factory;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Provider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class MainViewJavaTests {
  @Rule public ActivityTestRule mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
    @Override protected void beforeActivityLaunched() {
      super.beforeActivityLaunched();
      MyApp myApp = (MyApp)InstrumentationRegistry.getTargetContext().getApplicationContext();
      myApp.setDispatchingActivityInjector(createFakeActivityInjector());
    }
  };

  private MainContract.UserAction mockUserAction = mock(MainContract.UserAction.class);

  @Test
  public void clickOnFabCallToCreateTopic() {
    onView(withId(R.id.fab)).perform(click());

    verify(mockUserAction).createTopic((MainActivity)mActivityTestRule.getActivity());
  }

  private DispatchingAndroidInjector<Activity> createFakeActivityInjector() {
    final AndroidInjector<Activity> injector = new AndroidInjector<Activity>() {
      @Override public void inject(Activity instance) {
        if (instance instanceof MainActivity) {
          MainActivity mainActivity = (MainActivity)instance;
          mainActivity.userAction = mockUserAction;
        }
      }
    };

    final AndroidInjector.Factory<Activity> factory = new AndroidInjector.Factory<Activity>() {

      @Override public AndroidInjector<Activity> create(Activity instance) {
        return injector;
      }
    };

    Map<Class<? extends Activity>, Provider<AndroidInjector.Factory<? extends Activity>>> map = new HashMap<>(1);
    Provider<AndroidInjector.Factory<? extends Activity>> provider = new Provider<AndroidInjector.Factory<? extends Activity>>() {

      @Override public AndroidInjector.Factory<? extends Activity> get() {
        return factory;
      }
    };
    map.put(MainActivity.class, provider);
    return DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(map);
  }
}
