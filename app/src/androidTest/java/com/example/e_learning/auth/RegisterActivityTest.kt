package com.example.e_learning.auth


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.e_learning.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegisterActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun registerActivityTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.btnRegisterAkun), withText("Register Akun"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText = onView(
            allOf(
                withId(R.id.regisUsername),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutUsernameRegister),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("a"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btnRegisterAkun), withText("Register Akun"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.regisPass),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutPasswordRegister),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("a"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.btnRegisterAkun), withText("Register Akun"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.regisEmail),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutEmailRegister),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("arvellianputra@gmail.com"), closeSoftKeyboard())

        val materialButton4 = onView(
            allOf(
                withId(R.id.btnRegisterAkun), withText("Register Akun"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.regisTgl),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutTanggalLahir),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("12/12/2022"), closeSoftKeyboard())

        val materialButton5 = onView(
            allOf(
                withId(R.id.btnRegisterAkun), withText("Register Akun"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())
        onView(isRoot()).perform(waitFor(3000))

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.regisTelp),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.inputLayoutTelp),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("081223707004"), closeSoftKeyboard())

        val materialButton6 = onView(
            allOf(
                withId(R.id.btnRegisterAkun), withText("Register Akun"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())
        onView(isRoot()).perform(waitFor(3000))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " +delay+ "milliseconds"
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
