package com.example.customerapp;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;

import android.view.View;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QRCodeUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
    }

    @Test
    public void test1GenerateQRCodeSuccessfully() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.fabAddQRCode)).perform(click());
        onView(withId(R.id.lastNameEditText)).perform(typeText("Mo"), closeSoftKeyboard());
        onView(withId(R.id.firstNameEditText)).perform(typeText("R"), closeSoftKeyboard());
        onView(withId(R.id.streetEditText)).perform(typeText("Teststr"), closeSoftKeyboard());
        onView(withId(R.id.streetNrEditText)).perform(typeText("10C"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.buttonGenerate)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.backButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));

    }

    @Test
    public void test2OpenQRCodeInforamtionTest() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.buttonOpenQRCode)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.backButtonToList)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
    }

    @Test
    public void test3GenerateQRCodeNullValuesTest() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.fabAddQRCode)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.buttonGenerate)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.backButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));

    }

    @Test
    public void test4generateQRCodeEmptyValuesTest() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.fabAddQRCode)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.lastNameEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.firstNameEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.streetEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.streetNrEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.buttonGenerate)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.backButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
    }

    @Test
    public void test5DeleteQRCodeTest() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.deleteButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
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
