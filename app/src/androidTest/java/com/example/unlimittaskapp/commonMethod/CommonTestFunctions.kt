package com.example.unlimittaskapp.commonMethod

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import com.example.unlimittaskapp.base.NoRecordRecyclerView
import com.google.android.material.tabs.TabLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf

object CommonTestFunctions {
    /**
     * Custom Function for Set Text on Edit Text View
     * @param value
     * @return ViewAction as required perform action
     * */
    fun setTextInput(value: String?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return AllOf.allOf(
                    ViewMatchers.isDisplayed(),
                    ViewMatchers.isAssignableFrom(EditText::class.java)
                )
            }

            override fun getDescription(): String {
                return "replace text"
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as EditText).setText(value ?: return)
            }
        }
    }

    /**
     * Custom Function for Set value in Edit Text
     * @param value
     * @return ViewAction as required perform action
     * */
    fun setEditTextInput(value: String?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return AllOf.allOf(
                    ViewMatchers.isDisplayed(),
                    ViewMatchers.isAssignableFrom(EditText::class.java)
                )
            }

            override fun getDescription(): String {
                return "replace text"
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as EditText).setText(value ?: return)
            }
        }
    }


    /**
     * Get Text from ViewInteraction
     * @return String from viewInteraction.
     * */
    fun getText(matcher: ViewInteraction): String {
        var text = ""
        try {
            matcher.perform(object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return ViewMatchers.isAssignableFrom(TextView::class.java)
                }

                override fun getDescription(): String {
                    return "Text of the view"
                }

                override fun perform(uiController: UiController, view: View) {
                    val tv = view as TextView
                    text = tv.text.toString()
                }
            })
        } catch (e: Exception) {
        }
        return text
    }


    /**
     * Get TabLayout from ViewInteraction
     * @return Required from viewInteraction.
     * */
    fun <T> getRequiredView(matcher: ViewInteraction): T {
        var tabLayout: T? = null
        try {
            matcher.perform(object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return ViewMatchers.isAssignableFrom(TabLayout::class.java)
                }

                override fun getDescription(): String {
                    return "Text of the view"
                }

                override fun perform(uiController: UiController, view: View) {
                    tabLayout = view as T

                }
            })
        } catch (e: Exception) {
        }
        return tabLayout!!
    }

    /**
     * Get ViewAction for RecyclerView item.
     * @return ViewAction will return the selected item ViewAction.
     * */
    fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController?, view: View) {
                val v: View = view.findViewById(id)
                v.performClick()
            }
        }
    }


    /**
     * Custom Function for Set Text on Edit Text View
     * @param value
     * @return ViewAction as required perform action
     * */
    fun <VH : RecyclerView.ViewHolder> setAdapter(adapter: RecyclerView.Adapter<VH>): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return AllOf.allOf(
                    ViewMatchers.isDisplayed(),
                    ViewMatchers.isAssignableFrom(NoRecordRecyclerView::class.java)
                )
            }

            override fun getDescription(): String {
                return "replace text"
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as NoRecordRecyclerView).setAdapter(adapter)
            }
        }
    }

    /**
     * WithParent custom method for check hierarchyLevel and implement id view.
     * @return TypeSafeMatcher for hierarchy level view.
     * */
    fun withParent(parentMatcher: Matcher<View>, hierarchyLevel: Int): Matcher<View?>? {
        return WithParentMatcher(parentMatcher, hierarchyLevel)
    }

    class WithParentMatcher(
        parentMatcher: Matcher<View>,
        hierarchyLevel: Int
    ) :
        TypeSafeMatcher<View?>() {
        private val parentMatcher: Matcher<View>
        private val hierarchyLevel: Int
        override fun describeTo(description: Description) {
            description.appendText("has parent matching: ")
            parentMatcher.describeTo(description)
        }

        init {
            this.parentMatcher = parentMatcher
            this.hierarchyLevel = hierarchyLevel
        }

        override fun matchesSafely(item: View?): Boolean {
            var viewParent = item?.parent
            for (index in 1 until hierarchyLevel) {
                viewParent = viewParent?.parent
            }
            return parentMatcher.matches(viewParent)
        }
    }
}

