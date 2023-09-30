package com.example.unlimittaskapp.ui.home.presentations.fragment

import android.content.Context
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.factory.RealmConfigurationFactory
import com.example.unlimittaskapp.launchFragmentInHiltContainer
import com.example.unlimittaskapp.ui.home.presentations.adapter.JokesAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.realm.Realm
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named
import com.example.unlimittaskapp.R


@HiltAndroidTest
class JokesFragmentTest {
   /**
    * HiltRule initiate with application context.
    * */
   @get:Rule
   var hiltRule = HiltAndroidRule(this)

   /**
    * Application context inject
    * */
   @Inject
   @ApplicationContext
   lateinit var context: Context

   /**
    * CompletedTaskFragment object instance
    * */
   private lateinit var jokesFragment: JokesFragment

   /**
    * Realm database injection
    * */
   @Inject
   @Named("test_realm")
   lateinit var mRealm: Realm

   lateinit var jokesAdapter: JokesAdapter


   /**
    * Inject and Start hilt rule.
    * */
   @Before
   fun setUp() {
    hiltRule.inject()
    val query = mRealm.where(Jokes::class.java)
    jokesAdapter = JokesAdapter(query.findAll(),context)

   }

   /**
    * Initialize and Launch Fragment.
    * */
   @Before
   fun init(){
    launchFragmentInHiltContainer<JokesFragment> {
     jokesFragment = this
     jokesFragment.realm =
      Realm.getInstance(RealmConfigurationFactory.createRealmConfiguration())

    }
   }

   /**
    * Finish and Destroy each execution which start in @Before method.
    * */
   @After
   @Throws(Exception::class)
   fun endOfTest() {
    Realm.getInstance(RealmConfigurationFactory.createRealmConfiguration()).close()
   }
    @Test
    fun check_all_default_view_is_visible() {
        Espresso.onView(ViewMatchers.withId(R.id.rvJokesList))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}