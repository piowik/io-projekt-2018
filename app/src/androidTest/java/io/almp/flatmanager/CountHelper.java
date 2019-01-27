package io.almp.flatmanager;

import android.support.annotation.IdRes;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class CountHelper {

    private static int count;

    public static int getCountFromListUsingTypeSafeMatcher(@IdRes int listViewId) {
        count = 0;

        Matcher matcher = new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                count = ((ListView) item).getCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        };

        onView(withId(listViewId)).check(matches(matcher));

        int result = count;
        count = 0;
        return result;
    }

    public static int getCountFromListUsingBoundedMatcher(@IdRes int listViewId) {
        count = 0;

        Matcher<Object> matcher = new BoundedMatcher<Object, String>(String.class) {
            @Override
            protected boolean matchesSafely(String item) {
                count += 1;
                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        };

        try {
            onData(matcher).inAdapterView(withId(listViewId)).perform(typeText(""));
        } catch (Exception e) {
        }

        int result = count;
        count = 0;
        return result;
    }

}