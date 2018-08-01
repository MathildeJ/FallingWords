package com.challenge.fallingwords.game.domain

import com.challenge.fallingwords.game.domain.model.WordEngSpa
import com.challenge.fallingwords.infrastructure.base.Constants.Companion.WORDS_UPDATE_INTERVAL
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.TimeUnit

class GetWordsInteractorTest{

    companion object {
        private const val eng1 = "cat"
        private const val eng2 = "dog"
        private const val spa1 = "gato"
        private const val spa2 = "perro"
        private val word1 = WordEngSpa(eng1, spa1)
        private val word2 = WordEngSpa(eng2, spa2)
    }
    private val getWords: GetWords
    private val anyWords = arrayOf(word1, word2)
    private val testScheduler = TestScheduler()

    init {
        getWords = GetWordsInteractor()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
    }

    @Test
    fun shouldNotifyWordImmediately() {
        val observable = getWords.execute(anyWords).test()
        testScheduler.advanceTimeBy(0, TimeUnit.MILLISECONDS)

        observable.assertValueCount(1)
    }

    @Test
    fun shouldNotifyWordsFromSourceEveryInterval() {
        val observable = getWords.execute(anyWords).test()
        testScheduler.advanceTimeBy(WORDS_UPDATE_INTERVAL, TimeUnit.MILLISECONDS)

        observable.assertValueCount(2)
        val value = observable.values()[0]
        assertTrue(value.first.first == eng1 || value.first.first == eng2)
        assertTrue(value.first.second == spa1 || value.first.second == spa2)
    }

    @Test
    fun shouldNotifyCorrectWordCount() {
        val observable = getWords.execute(anyWords).test()
        testScheduler.advanceTimeBy(WORDS_UPDATE_INTERVAL * 2, TimeUnit.MILLISECONDS)
        val expectedCount = 3

        observable.assertValueCount(expectedCount)
        assertTrue(observable.values()[0].second == 1)
        assertTrue(observable.values()[1].second == 2)
        assertTrue(observable.values()[2].second == 3)
    }

    @Test
    fun shouldNotifyCorrectTranslationState() {
        val observable = getWords.execute(anyWords).test()
        testScheduler.advanceTimeBy(WORDS_UPDATE_INTERVAL, TimeUnit.MILLISECONDS)

        val value = observable.values()[0]
        if((value.first.first == eng1 && value.first.second == spa1)
                || (value.first.first == eng2 && value.first.second == spa2)){
            assertTrue(value.third)
        } else {
            assertFalse(value.third)
        }
    }
}