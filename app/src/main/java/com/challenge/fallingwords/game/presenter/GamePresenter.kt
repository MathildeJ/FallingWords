package com.challenge.fallingwords.game.presenter

import com.challenge.fallingwords.game.domain.GetWords
import com.challenge.fallingwords.game.domain.WordEngSpa
import com.challenge.fallingwords.infrastructure.base.BaseView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GamePresenter @Inject
constructor(private val getWords: GetWords): LifecyclePresenter<GamePresenter.View>(){

    private var words: Array<WordEngSpa>? = null
    private var correctCount = 0
    private var isCorrectTranslation = false

    fun initialize(view: View, words: Array<WordEngSpa>?){
        this.words = words
        initializeView(view)
        showInitialState()
    }

    private fun showInitialState(){
        view?.run {
            showNewWords(Pair("", ""))
            clearScore()
            hideButtons()
        }
    }

    override fun onStart() {
        subscribeToGetWords()
    }

    override fun onPause() {
        stopReceivingUpdates()
    }

    override fun onResume() {
        subscribeToGetWords()
    }

    override fun onFinish() {
        cleanUp()
    }

    private fun subscribeToGetWords(){
        if(compositeDisposable.size() == 0) {
            compositeDisposable.add(
                    getWords(words)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnComplete {
                                stopReceivingUpdates()
                                view?.showEndOfGame()
                            }
                            .subscribe({ onNewWordsReady(it.first, it.second, it.third) }, {})
            )
        }
    }

    private fun onNewWordsReady(words: Pair<String, String>, totalWordCount: Int, isCorrectTranslation: Boolean){
        view?.run {
            showNewWords(words)
            showScore(correctCount, totalWordCount)
            showButtons()
        }
        this.isCorrectTranslation = isCorrectTranslation
    }

    private fun stopReceivingUpdates(){
        compositeDisposable.clear()
    }

    fun onYesClicked(){
        if(isCorrectTranslation) correctCount++
        view?.hideButtons()
    }

    fun onNoClicked(){
        if(!isCorrectTranslation) correctCount++
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