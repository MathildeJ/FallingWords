package com.challenge.fallingwords.game.presenter

import com.challenge.fallingwords.game.domain.GetWords
import com.challenge.fallingwords.game.domain.model.WordEngSpa
import com.challenge.fallingwords.infrastructure.base.BasePresenter
import com.challenge.fallingwords.infrastructure.base.BaseView
import com.challenge.fallingwords.infrastructure.base.Constants
import com.challenge.fallingwords.infrastructure.base.Constants.Companion.NB_OF_WORDS_IN_GAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GamePresenter @Inject
constructor(private val getWords: GetWords): BasePresenter<GamePresenter.View>(){

    private var correctCount = 0
    private var isCorrectTranslation = false

    fun initialize(view: View){
        initializeView(view)
        showInitialState()
        subscribeToGetWords()
    }

    private fun showInitialState(){
        view?.run {
            showNewWords(Pair("", ""))
            clearScore()
            hideButtons()
        }
    }

    private fun subscribeToGetWords(){
        if(compositeDisposable.size() == 0) {
            compositeDisposable.add(
                    getWords()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnComplete {
                                view?.showScore(correctCount, Constants.NB_OF_WORDS_IN_GAME)
                                view?.showEndOfGame()
                                cleanUp()
                            }
                            .subscribe({ onNewWordsReady(it.words, it.wordCount, it.isCorrectTranslationOfWord) }, {})
            )
        }
    }

    private fun onNewWordsReady(words: WordEngSpa, totalWordCount: Int, isCorrectTranslation: Boolean){
        view?.run {
            showNewWords(Pair(words.textEng, words.textSpa))
            if(totalWordCount > 0) showScore(correctCount, totalWordCount)
            showButtons()
        }
        this.isCorrectTranslation = isCorrectTranslation
    }

    fun onYesClicked(){
        onButtonClicked(isCorrectTranslation)
    }

    fun onNoClicked(){
        onButtonClicked(!isCorrectTranslation)
    }

    fun onButtonClicked(condition: Boolean){
        if(condition) correctCount++
        view?.hideButtons()
    }

    interface View: BaseView{
        fun showNewWords(words: Pair<String, String>)
        fun showScore(correctCount: Int, wordCount: Int)
        fun clearScore()
        fun showButtons()
        fun hideButtons()
        fun showEndOfGame()
    }
}