package com.example.customerapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.activities.customerapp.MainActivity;

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
public class QRCodeUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);
    @AfterClass
    public static void tearDownClass() {
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
    public void test2OpenQRCodeInforamtion() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.openQRCodeButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.backButtonToList)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
    }

    @Test
    public void test3GenerateQRCodeNullValues() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.fabAddQRCode)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.buttonGenerate)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.backButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));

    }

    @Test
    public void test4generateQRCodeEmptyValues() {
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
    public void test5DeleteQRCode() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.deleteButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
    }

    @Test
    public void test6BackButton() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.fabAddQRCode)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.backButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.settings)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.addAddressButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.backButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.code)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
    }

    @Test
    public void test7AddTwoQRCodesOnTwoFragments() {
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.fabAddQRCode)).perform(click());
        onView(withId(R.id.lastNameEditText)).perform(typeText("Mo"), closeSoftKeyboard());
        onView(withId(R.id.firstNameEditText)).perform(typeText("Rania"), closeSoftKeyboard());
        onView(withId(R.id.streetEditText)).perform(typeText("Teststr"), closeSoftKeyboard());
        onView(withId(R.id.streetNrEditText)).perform(typeText("10C"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.buttonGenerate)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.backButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.settings)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.addAddressButton)).perform(click());
        onView(isRoot()).perform(waitFor(500));
        onView(withId(R.id.lastNameEditText)).perform(typeText("Baj"), closeSoftKeyboard());
        onView(withId(R.id.firstNameEditText)).perform(typeText("Sadik"), closeSoftKeyboard());
        onView(withId(R.id.streetEditText)).perform(typeText("Teststr"), closeSoftKeyboard());
        onView(withId(R.id.streetNrEditText)).perform(typeText("10C"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.buttonGenerate)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.backButton)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
        onView(withId(R.id.code)).perform(click());
        onView(isRoot()).perform(waitFor(2000));
    }

    @Test
    public void test8DeleteAllQRCode() {
        onView(isRoot()).perform(waitFor(1000));
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Alle QR-Codes löschen")).perform(click());
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
