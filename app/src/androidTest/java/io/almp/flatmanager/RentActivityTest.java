package io.almp.flatmanager;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


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
//    public void testCheckPossibilityToWriteValue() {
//        onView(withId(R.id.rentValueEditText)).perform(typeText("255.50"), closeSoftKeyboard());
//        onView(withId(R.id.sendRentButton)).perform(click());
//    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}