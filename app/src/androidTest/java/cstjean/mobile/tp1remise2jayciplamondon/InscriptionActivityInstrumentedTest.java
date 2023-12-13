package cstjean.mobile.tp1remise2jayciplamondon;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cstjean.mobile.tp1remise2jayciplamondon.travail.DamierFragment;
import cstjean.mobile.tp1remise2jayciplamondon.travail.InscriptionActivity;
import cstjean.mobile.tp1remise2jayciplamondon.travail.SingletonDamier;

@RunWith(AndroidJUnit4.class)
public class InscriptionActivityInstrumentedTest {
    @Rule
    public ActivityScenarioRule<InscriptionActivity> rule = new ActivityScenarioRule<>(InscriptionActivity.class);
    @Test
    public void testResultat() {
        final String j1name = "joueur1name";
        final String j2name = "joueur2name";

        onView(withId(R.id.editTextPlayer1)).perform(typeText(j1name), closeSoftKeyboard());
        onView(withId(R.id.editTextPlayer2)).perform(typeText(j2name), closeSoftKeyboard());
        onView(withId(R.id.startGameButton)).perform(click());


        // Assuming you navigate to DamierActivity after clicking startGameButton
        // Now access DamierFragment to check player names
        ActivityScenario<InscriptionActivity> activityScenario = rule.getScenario();

        activityScenario.onActivity(activity -> {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.linearLayout); // Replace with your container ID

            if (fragment instanceof DamierFragment) {
                DamierFragment damierFragment = (DamierFragment) fragment;

                // Get player names from DamierFragment and perform assertions
                String displayedPlayer1Name = damierFragment.getPlayer1Name();
                String displayedPlayer2Name = damierFragment.getPlayer2Name();

                // Assert the player names against expected names
                assertEquals(j2name, displayedPlayer1Name);

                System.out.println(displayedPlayer1Name);
                System.out.println(displayedPlayer2Name);

                assertEquals(j2name, displayedPlayer2Name);

            }
        });
    }
}
