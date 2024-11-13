package com.puj.proyectoensenarte;

import android.view.View;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.puj.proyectoensenarte.IniciarSesionActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class IniciarSesionActivityUITest {

    @Rule
    public ActivityScenarioRule<IniciarSesionActivity> activityRule = new ActivityScenarioRule<>(IniciarSesionActivity.class);

    private View decorView;

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(activity -> {
            decorView = activity.getWindow().getDecorView();
        });
    }

    @Test
    public void login_withValidCredentials_showsWelcomeMessage() {
        Intents.init();

        try {
            onView(withId(R.id.editTextEmail)).perform(typeText("test@example.com"), closeSoftKeyboard());
            onView(withId(R.id.editTextPassword)).perform(typeText("password123"), closeSoftKeyboard());
            onView(withId(R.id.botonIniciarSesion)).perform(click());

            Thread.sleep(1000);

            intended(hasComponent(BottomNavigationActivity.class.getName()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Intents.release();
        }
    }


    @Test
    public void login_withEmptyFields_showsErrorMessage() {
        onView(withId(R.id.botonIniciarSesion)).perform(click());
        onView(withId(R.id.textViewMessage))
                .check(matches(withText("Email and Password must not be empty")))
                .check(matches(isDisplayed()));
    }


    @Test
    public void login_withInvalidEmailFormat_showsErrorMessage() {
        onView(withId(R.id.editTextEmail)).perform(typeText("invalidemail"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("password123"), closeSoftKeyboard());
        onView(withId(R.id.botonIniciarSesion)).perform(click());

        onView(withId(R.id.textViewMessage))
                .check(matches(withText("Invalid email format")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void login_withIncorrectPassword_showsErrorMessage() throws InterruptedException {
        onView(withId(R.id.editTextEmail)).perform(typeText("test@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("wrongpassword"), closeSoftKeyboard());
        onView(withId(R.id.botonIniciarSesion)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.textViewMessage))
                .check(matches(withText("Incorrect password.")))
                .check(matches(isDisplayed()));
    }


}
