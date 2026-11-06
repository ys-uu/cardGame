package cn.edu.sicnu.cs.ys.cardgame

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 主界面UI测试
 */
@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @Test
    fun test_mainActivity_displayCorrectly() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.tv_name)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_edit_info)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_start_game)).check(matches(isDisplayed()))
    }

    @Test
    fun test_clickEditInfo_jumpToEditActivity() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.btn_edit_info)).perform(click())
        onView(withId(R.id.et_name)).check(matches(isDisplayed()))
    }

    @Test
    fun test_clickStartGame_jumpToGameActivity() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.btn_start_game)).perform(click())
        onView(withId(R.id.rv_cards)).check(matches(isDisplayed()))
    }
}
