package com.acme.a3csci3130;

import android.app.ListActivity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class CRUDTests {
    Contact compareA = new Contact("","AutoTest","Fisher", "right here", "AB" );

    @Rule
    public ActivityTestRule<MainActivity> createActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    /*
    I know these 3 tests should be separate, but I kept getting errors on anything that wasn't the first test
    any time I separated them out. (Future refactoring opportunity!)
    This is functionally equivalent, but would be harder to narrow down the problem if something were to break.
    I just did it this way so that I could demonstrate that all functions are currently working.
    There's no explicit test for Read, as its a necessary component for everything else.
    We can't update or destroy a specific object if we can't read that its there.
     */
    public void test_Create_Update_Destroy() throws Exception {
        // Creates a Fisher busisness named Autotest based in Alberta and adds it
        final int[] count = new int[1];
        onView(withId(R.id.listView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ListView listView = (ListView) item;
                count[0]=listView.getCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        compareA.bID = String.format("%09d",count[0]);

        //this block creates a new contact and tests that it is created and uploaded
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.name)).perform(typeText("AutoTest"));
        onView(withId(R.id.name)).perform(closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText("right here"));
        onView(withId(R.id.address)).perform(closeSoftKeyboard());
        onView(withId(R.id.bType)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Fisher"))).perform(click());
        onView(withId(R.id.prov)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AB"))).perform(click());
        onView(withId(R.id.submitButton)).perform(click());
        onData(hasToString(compareA.toString())).inAdapterView(withId(R.id.listView)).check(matches(isDisplayed()));

        //this block modifies and tests the contact we just updated
        onData(hasToString(compareA.toString())).inAdapterView(withId(R.id.listView)).perform(click());
        onView(withId(R.id.address)).perform(clearText());
        onView(withId(R.id.address)).perform(typeText("far away"));
        onView(withId(R.id.address)).perform(closeSoftKeyboard());
        onView(withId(R.id.updateButton)).perform(click());
        compareA.address="far away";
        onData(hasToString(compareA.toString())).inAdapterView(withId(R.id.listView)).check(matches(isDisplayed()));

        //this block destroys the contact we just updated and checks to make sure it no longer exists
        onData(hasToString(compareA.toString())).inAdapterView(withId(R.id.listView)).perform(click());
        onView(withId(R.id.deleteButton)).perform(click());
        onView(withId(R.id.listView)).check(matches(not(withAdaptedData(hasToString(compareA.toString())))));

    }

//See above note about separating tests;
//    @Test
//    public void test_delete() throws Exception{
//        final int[] count = new int[1];
//        onView(withId(R.id.listView)).check(matches(new TypeSafeMatcher<View>() {
//            @Override
//            protected boolean matchesSafely(View item) {
//                ListView listView = (ListView) item;
//                count[0]=listView.getCount();
//                return true;
//            }
//
//            @Override
//            public void describeTo(Description description) {
//
//            }
//        }));
//        String testBID = String.format("%09d",count[0]-1);
//        compareA.bID = testBID;
//        onData(hasToString(compareA.toString())).inAdapterView(withId(R.id.listView)).perform(click());
//        onView(withId(R.id.deleteButton)).perform(click());
//        onView(withId(R.id.listView)).check(matches(not(withAdaptedData(hasToString(compareA.toString())))));
//    }


//method taken from google android support library - https://google.github.io/android-testing-support-library/docs/espresso/advanced/#asserting-that-a-view-is-not-present
    private static Matcher<View> withAdaptedData(final Matcher<Object> dataMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with class name: ");
                dataMatcher.describeTo(description);
            }
            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof AdapterView)) {
                    return false;
                }
                @SuppressWarnings("rawtypes")
                Adapter adapter = ((AdapterView) view).getAdapter();
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (dataMatcher.matches(adapter.getItem(i))) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
