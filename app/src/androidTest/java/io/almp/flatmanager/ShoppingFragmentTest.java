package io.almp.flatmanager;

import android.app.Fragment;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShoppingFragmentTest {

    @Rule
    public final ActivityTestRule<ShoppingActivity> main = new ActivityTestRule<>(ShoppingActivity.class);

    private ShoppingActivity mActivity = null;
    private String itemName;
    private String itemPrice;

    @Before
    public void setUp()throws Exception{
        mActivity = main.getActivity();
    }

    @Test
    public void testLaunchShoppingMainFragment(){
        LinearLayout linearLayout = mActivity.findViewById(R.id.mainShoppingFragmentLinearLayout);
        assertNotNull(linearLayout);
        ShoppingMainFragment fragment = new ShoppingMainFragment();
        mActivity.getSupportFragmentManager().beginTransaction().add(linearLayout.getId(), fragment).commitAllowingStateLoss();
    }

    @Test
    public void testNoItemName(){
        translateToAddShoppingItemFragment();
        itemName = "";
        itemPrice = "10.22";
        onView(withId(R.id.new_item_name_id)).perform(typeText(itemName), closeSoftKeyboard());
        onView(withId(R.id.new_item_price_id)).perform(typeText(itemPrice), closeSoftKeyboard());
        onView(withId(R.id.save_item)).perform(click());
        onView(withText(R.string.empty_item_name)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
    @Test
    public void testNoItemPrice(){
        translateToAddShoppingItemFragment();
        itemName = "Product";
        itemPrice = "";
        onView(withId(R.id.new_item_name_id)).perform(typeText(itemName), closeSoftKeyboard());
        onView(withId(R.id.new_item_price_id)).perform(typeText(itemPrice), closeSoftKeyboard());
        onView(withId(R.id.save_item)).perform(click());
        onView(withText(R.string.empty_price)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    private void translateToAddShoppingItemFragment(){
        AddShoppingItemFragment fragment = new AddShoppingItemFragment();
        AddShoppingItemFragment addShoppingItemFragment = new AddShoppingItemFragment();
        FragmentTransaction fragmentTransaction;
        if (mActivity.getFragmentManager() != null) {
            fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.shopping_fragment_container, addShoppingItemFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

}
