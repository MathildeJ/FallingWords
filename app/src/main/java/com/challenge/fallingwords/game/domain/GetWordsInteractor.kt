package com.challenge.fallingwords.game.domain

import com.challenge.fallingwords.game.data.WordsRepository
import com.challenge.fallingwords.game.domain.model.GameState
import com.challenge.fallingwords.game.domain.model.WordEngSpa
import com.challenge.fallingwords.infrastructure.base.Constants
import com.challenge.fallingwords.infrastructure.util.getRandomElement
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetWordsInteractor @Inject
constructor(private val wordsRepository: WordsRepository) : GetWords{

    private var wordCount = 0


    override fun execute(): Observable<GameState> {
        val words = wordsRepository.getWords()
        return Observable.create<GameState> { subscriber ->
            val disposable = Observable.interval(0, Constants.WORDS_UPDATE_INTERVAL, TimeUnit.MILLISECONDS)
                    .subscribe {
                        if(wordCount < Constants.NB_OF_WORDS_IN_GAME && words != null) {
                            val firstWord = words.getRandomElement()
                            val secondWord = words.getRandomElement()
                            val isCorrectTranslationOfWord = Math.random() < 0.5 || firstWord == secondWord
                            subscriber.onNext(GameState(
                                    WordEngSpa(firstWord.textEng, (if(isCorrectTranslationOfWord) firstWord else secondWord).textSpa),
                                    wordCount,
                                    isCorrectTranslationOfWord
                            ))
                            wordCount++
                        } else {
                            subscriber.onComplete()
                        }
                    }

            subscriber.setCancellable {
                disposable.dispose()
            }
        }
    }

}