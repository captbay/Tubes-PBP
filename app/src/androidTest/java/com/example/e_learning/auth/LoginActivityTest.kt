package com.example.e_learning.auth


import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import com.example.e_learning.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun loginActivityTest() {
        val textInputEditText = onView(
allOf(withId(R.id.loginUsername),
childAtPosition(
childAtPosition(
withId(R.id.inputLayoutUsername),
0),
0),
isDisplayed()))
        textInputEditText.perform(replaceText("alfons"), closeSoftKeyboard())
        
        val materialButton = onView(
allOf(withId(R.id.btnLogin), withText("Login"),
childAtPosition(
allOf(withId(R.id.linearLayout),
childAtPosition(
withId(R.id.loginLayout),
3)),
1),
isDisplayed()))
        materialButton.perform(click())
        
        val materialButton2 = onView(
allOf(withId(R.id.btnClear), withText("clear text"),
childAtPosition(
allOf(withId(R.id.linearLayout),
childAtPosition(
withId(R.id.loginLayout),
3)),
0),
isDisplayed()))
        materialButton2.perform(click())
        
        val materialButton3 = onView(
allOf(withId(R.id.btnLogin), withText("Login"),
childAtPosition(
allOf(withId(R.id.linearLayout),
childAtPosition(
withId(R.id.loginLayout),
3)),
1),
isDisplayed()))
        materialButton3.perform(click())
        
        val textInputEditText2 = onView(
allOf(withId(R.id.loginUsername),
childAtPosition(
childAtPosition(
withId(R.id.inputLayoutUsername),
0),
0),
isDisplayed()))
        textInputEditText2.perform(click())
        
        val textInputEditText3 = onView(
allOf(withId(R.id.loginUsername),
childAtPosition(
childAtPosition(
withId(R.id.inputLayoutUsername),
0),
0),
isDisplayed()))
        textInputEditText3.perform(replaceText("alfon"), closeSoftKeyboard())
        
        val textInputEditText4 = onView(
allOf(withId(R.id.loginPassword),
childAtPosition(
childAtPosition(
withId(R.id.inputLayoutPassword),
0),
0),
isDisplayed()))
        textInputEditText4.perform(replaceText("1q3"), closeSoftKeyboard())
        
        val materialButton4 = onView(
allOf(withId(R.id.btnLogin), withText("Login"),
childAtPosition(
allOf(withId(R.id.linearLayout),
childAtPosition(
withId(R.id.loginLayout),
3)),
1),
isDisplayed()))
        materialButton4.perform(click())
        }
    
    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

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
    }
