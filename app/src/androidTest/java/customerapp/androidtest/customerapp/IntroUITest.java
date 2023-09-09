package customerapp.androidtest.customerapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import customerapp.activities.customerapp.MainActivity;
import com.example.customerapp.R;

import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntroUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void test1Intro() {
        onView(isRoot()).perform(waitFor(1000));
        onView(ViewMatchers.withId(R.id.nextButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
    }

    @Test
    public void test2Intro() {
        onView(isRoot()).perform(waitFor(1000));
        onView(ViewMatchers.withId(R.id.nextButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.introEndButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
    }

    @Test
    public void test3NoIntro() {
        onView(isRoot()).perform(waitFor(3000));
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }
}
