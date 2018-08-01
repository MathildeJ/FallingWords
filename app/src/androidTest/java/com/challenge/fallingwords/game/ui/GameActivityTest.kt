package com.challenge.fallingwords.game.ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.challenge.fallingwords.R
import com.challenge.fallingwords.game.domain.GetWords
import com.challenge.fallingwords.rules.LogicMock
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit


class GameActivityTest{

    @get:Rule var game: ActivityTestRule<GameActivity> = ActivityTestRule(GameActivity::class.java, true, false)
    @get:Rule var logicMock = LogicMock.create()

    private val getWords: GetWords = mock()

    companion object {
        private const val eng1 = "cat"
        private const val spa1 = "perro"
        private const val ANY_WORD_COUNT = 1
    }

    @Before
    fun setup(){
        whenever(getWords(any())).thenReturn(Observable.never())
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun yesClicked_shouldHideButtons() {
        givenSomeWords()
        startActivity()

        onView(withId(R.id.yes_button)).perform(click())

        onView(withId(R.id.yes_button)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_button)).check(matches(not(isDisplayed())))
    }

    @Test
    fun yesClicked_shouldUpdateScoreOnNextWord() {
        whenever(getWords(any())).thenReturn(Observable.create<Triple<Pair<String, String>, Int, Boolean>> { subscriber ->
            Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                    .take(2)
                    .subscribe {
                        subscriber.onNext(Triple(Pair(eng1, spa1), ANY_WORD_COUNT, false))
                    }
        }.doOnComplete{ onView(withId(R.id.score_text)).check(matches(withText("0/1"))) })
        startActivity()

        Thread.sleep(50)
        onView(withId(R.id.yes_button)).perform(click())
    }

    @Test
    fun noClicked_shouldHideButtons() {
        givenSomeWords()
        startActivity()

        onView(withId(R.id.no_button)).perform(click())

        onView(withId(R.id.yes_button)).check(matches(not(isDisplayed())))
        onView(withId(R.id.no_button)).check(matches(not(isDisplayed())))
    }

    @Test
    fun noClicked_shouldUpdateScoreOnNextWord() {
        var count = 0
        whenever(getWords(any())).thenReturn(Observable.create<Triple<Pair<String, String>, Int, Boolean>> { subscriber ->
            Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                    .take(2)
                    .subscribe {
                        if(count == 0){
                            subscriber.onNext(Triple(Pair(eng1, spa1), count, false))
                            count++
                        } else {
                            subscriber.onNext(Triple(Pair("other", spa1), count, false))
                        }
                    }
        }.doOnComplete {onView(withId(R.id.score_text)).check(matches(withText("1/1")))})
        startActivity()

        onView(withId(R.id.no_button)).perform(click())
    }

    @Test
    fun shouldShowButtonsOnNextWords() {
        givenSomeWords()
        startActivity()

        onView(withId(R.id.yes_button)).check(matches(isDisplayed()))
        onView(withId(R.id.no_button)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowWordsOnNextWords() {
        givenSomeWords()
        startActivity()

        onView(withId(R.id.fixed_text_view)).check(matches(withText(eng1)))
        onView(withId(R.id.falling_text_view)).check(matches(withText(spa1)))
    }

    @Test
    fun shouldFinishActivityOnNoMoreWords(){
        givenOneWord()
        startActivity()

        assertThat(game.activity.isFinishing, `is`(true))
    }

    private fun givenOneWord(){
        whenever(getWords(any())).thenReturn(Observable.just(Triple(Pair(eng1, spa1), ANY_WORD_COUNT, false)))
    }

    private fun givenSomeWords() {
        var count = 0
        whenever(getWords(any())).thenReturn(
                Observable.create<Triple<Pair<String, String>, Int, Boolean>> { subscriber ->
                    Observable.interval(0, 500, TimeUnit.MILLISECONDS)
                            .take(2)
                            .subscribe {
                                if (count == 0) {
                                    subscriber.onNext(Triple(Pair(eng1, spa1), count, false))
                                    count++
                                } else {
                                    subscriber.onComplete()
                                }
                            }
                }
        )
    }

    private fun startActivity(){
        game.launchActivity(null)
    }
}