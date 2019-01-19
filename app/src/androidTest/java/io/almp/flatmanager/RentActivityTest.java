package io.almp.flatmanager;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RentActivityTest {

    @Rule
    public final ActivityTestRule<RentActivity> main = new ActivityTestRule<>(RentActivity.class);

    private RentActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = main.getActivity();
    }

    @Test
    public void testCheckPossibilityToWriteValue() throws InterruptedException {
        onView(withId(R.id.button_rent_add)).perform(click());
        Thread.sleep(5000);
        for (int i = 0; i < CountHelper.getCountFromListUsingTypeSafeMatcher(R.id.listview_rent_per_user); i++) {
            onData(anything()).inAdapterView(withId(R.id.listview_rent_per_user)).atPosition(i).onChildView(withId(R.id.rentValueEditText)).perform(typeText(String.valueOf(111 * (i + 1))), closeSoftKeyboard());
        }
        onView(withId(R.id.extrasEditText)).perform(typeText("1000"), closeSoftKeyboard());
        onView(withId(R.id.button_rent_post)).perform(click());
    }

    @Test
    public void testCheckEmptyValues() throws InterruptedException {
        onView(withId(R.id.button_rent_add)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.extrasEditText)).perform(typeText("1000"), closeSoftKeyboard());
        onView(withId(R.id.button_rent_post)).perform(click());
        onView(withText(R.string.someone_has_empty_field)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}

