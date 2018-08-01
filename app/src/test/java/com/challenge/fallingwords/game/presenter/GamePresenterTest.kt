package com.challenge.fallingwords.game.presenter

import com.challenge.fallingwords.RxImmediateSchedulerRule
import com.challenge.fallingwords.game.domain.GetWords
import com.challenge.fallingwords.game.domain.model.WordEngSpa
import com.challenge.fallingwords.infrastructure.base.Constants
import com.challenge.fallingwords.infrastructure.util.getRandomElement
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class GamePresenterTest {

    @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    companion object {
        private const val eng1 = "cat"
        private const val spa1 = "perro"
        private const val ANY_WORD_COUNT = 1
        private val ANY_WORDS = arrayOf(WordEngSpa("", ""))
    }

    private val view: GamePresenter.View = mock()
    private val getWords: GetWords = mock()

    lateinit var presenter: GamePresenter

    @Before
    fun setup(){
        whenever(getWords(ANY_WORDS)).thenReturn(Observable.never())
        presenter = GamePresenter(getWords)
    }

    @Test
    fun shouldShowInitialStateOnInit(){
        presenter.initialize(view, ANY_WORDS)

        verify(view).showNewWords(Pair("", ""))
        verify(view).clearScore()
        verify(view).hideButtons()
    }

    @Test
    fun shouldStartGettingWordsOnInit(){
        presenter.initialize(view, ANY_WORDS)

        verify(getWords).invoke(ANY_WORDS)
    }

    @Test
    fun shouldUpdateWordsOnNewWords(){
        whenever(getWords(ANY_WORDS)).thenReturn(Observable.just(Triple(Pair(eng1, spa1), ANY_WORD_COUNT, true)))
        presenter.initialize(view, ANY_WORDS)

        verify(view).showNewWords(Pair(eng1, spa1))
    }

    @Test
    fun shouldUpdateScoreTotalWordsCountOnNewWords(){
        whenever(getWords(ANY_WORDS)).thenReturn(Observable.just(Triple(Pair(eng1, spa1), ANY_WORD_COUNT, true)))
        presenter.initialize(view, ANY_WORDS)

        verify(view).showScore(anyInt(), eq(ANY_WORD_COUNT))
    }

    @Test
    fun shouldShowButtonsOnNewWords(){
        whenever(getWords(ANY_WORDS)).thenReturn(Observable.just(Triple(Pair(eng1, spa1), ANY_WORD_COUNT, true)))
        presenter.initialize(view, ANY_WORDS)

        verify(view).showButtons()
    }

    @Test
    fun shouldHideButtonsOnYesButtonClicked(){
        presenter.initialize(view, ANY_WORDS)

        presenter.onYesClicked()

        verify(view, times(2)).hideButtons()
    }

    @Test
    fun shouldHideButtonsOnNoButtonClicked(){
        presenter.initialize(view, ANY_WORDS)

        presenter.onNoClicked()

        verify(view, times(2)).hideButtons()
    }

    @Test
    fun shouldUpdateScoreCorrectlyOnCorrectAnswer(){
        whenever(getWords(ANY_WORDS)).thenReturn(
                Observable.create<Triple<Pair<String, String>, Int, Boolean>> { subscriber ->
                    Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                            .take(2)
                            .subscribe {
                                subscriber.onNext(Triple(Pair(eng1, spa1), ANY_WORD_COUNT, true))
                                presenter.onYesClicked()
                            }
                }
        )
        presenter.initialize(view, ANY_WORDS)

        verify(view).showScore(0, ANY_WORD_COUNT)
        verify(view).showScore(1, ANY_WORD_COUNT)
    }

    @Test
    fun shouldUpdateScoreCorrectlyOnWrongAnswer(){
        whenever(getWords(ANY_WORDS)).thenReturn(
                Observable.create<Triple<Pair<String, String>, Int, Boolean>> { subscriber ->
                    Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                            .take(2)
                            .subscribe {
                                subscriber.onNext(Triple(Pair(eng1, spa1), ANY_WORD_COUNT, false))
                                presenter.onYesClicked()
                            }
                }
        )
        presenter.initialize(view, ANY_WORDS)

        verify(view, times(2)).showScore(0, ANY_WORD_COUNT)
    }
}