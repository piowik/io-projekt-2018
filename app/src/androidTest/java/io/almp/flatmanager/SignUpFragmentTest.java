package io.almp.flatmanager;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;



import org.junit.After;
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




/**
 * Created by Mateusz Zaremba on 16.12.2018.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpFragmentTest {

    @Rule public final ActivityTestRule<LoginActivity> main = new ActivityTestRule<>(LoginActivity.class);

    private LoginActivity mActivity = null;
    private String mLogin;
    private String mPassword;
    private String mPasswordConfirmation;
    private String mEmail;
    private String mName;
    private String mSurname;

    private void writeValue(){

        onView(withId(R.id.sing_up_login_edittext)).perform(typeText(mLogin),closeSoftKeyboard());
        onView(withId(R.id.sing_up_password_edittext)).perform(typeText(mPassword),closeSoftKeyboard());
        onView(withId(R.id.sing_up_password_confirm_edittext)).perform(typeText(mPasswordConfirmation),closeSoftKeyboard());
        onView(withId(R.id.sing_up_email_textedit)).perform(typeText(mEmail),closeSoftKeyboard());
        onView(withId(R.id.sing_up_first_name_edittext)).perform(typeText(mName),closeSoftKeyboard());
        onView(withId(R.id.sing_up_last_name_edittex)).perform(typeText(mSurname),closeSoftKeyboard());

    }



    @Before
    public void setUp() throws Exception {
    mActivity =main.getActivity();
    }
    @Test
    public void testLaunchSingUpScreen(){
        FrameLayout frameLayout = mActivity.findViewById(R.id.fragment_container);
        assertNotNull(frameLayout);
        Fragment fragment = new SignUpFragment();
        mActivity.getSupportFragmentManager().beginTransaction().add(frameLayout.getId(),fragment).commitAllowingStateLoss();

    }
    @Test
    public void testCheckPosibilityToWriteValue(){
        mLogin = "login";
        mPassword ="haslo";
        mPasswordConfirmation="222222222";
        mEmail = "222222222";
        mName = "22";
        mSurname ="22";
        onView(withId(R.id.sign_up)).perform(click());
        writeValue();
       // onView(withId(R.id.sign_up_button)).perform(click());

        //onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(mSuccessString))).check(matches(isDisplayed()));


    }
    @Test
    public void testCheckEmptyLoginToast(){
        mLogin ="";
        mPassword ="itbptebk8";
        mPasswordConfirmation="itbptebk8";
        mEmail = "mail@onet.pl";
        mName = "Imie";
        mSurname ="Nazwisko";
        onView(withId(R.id.sign_up)).perform(click());
        writeValue();
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withText(R.string.empty_login)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
    @Test
    public void testCheckEmptyPasswordToast(){
        mLogin ="Login";
        mPassword ="";
        mPasswordConfirmation="";
        mEmail = "mail@onet.pl";
        mName = "Imie";
        mSurname ="Nazwisko";
        onView(withId(R.id.sign_up)).perform(click());
        writeValue();
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withText(R.string.empty_password)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));

    }
    @Test
    public void testCheckEmptyEmailToast(){
        mLogin ="Login";
        mPassword ="itbptebk8";
        mPasswordConfirmation="itbptebk8";
        mEmail = "";
        mName = "Imie";
        mSurname ="Nazwisko";
        onView(withId(R.id.sign_up)).perform(click());
        writeValue();
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withText(R.string.empty_email)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));

    }
    @Test
    public void testCheckEmptyPasswordConfirmationToast(){
        mLogin ="Login";
        mPassword ="itbptebk8";
        mPasswordConfirmation="";
        mEmail = "mail@onet.pl";
        mName = "Imie";
        mSurname ="Nazwisko";
        onView(withId(R.id.sign_up)).perform(click());
        writeValue();
        onView(withId(R.id.sign_up_button)).perform(click());
        onView(withText(R.string.empty_password)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));


    }
    @After
    public void tearDown() throws Exception {
        mActivity= null;
    }
}