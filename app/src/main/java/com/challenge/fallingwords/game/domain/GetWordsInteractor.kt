package com.challenge.fallingwords.game.domain

import com.challenge.fallingwords.game.domain.model.WordEngSpa
import com.challenge.fallingwords.infrastructure.base.Constants.Companion.NB_OF_WORDS_IN_GAME
import com.challenge.fallingwords.infrastructure.base.Constants.Companion.WORDS_UPDATE_INTERVAL
import com.challenge.fallingwords.infrastructure.util.getRandomElement
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetWordsInteractor @Inject
constructor() : GetWords{

    private var wordCount = 0

    override fun execute(words: Array<WordEngSpa>?): Observable<Triple<Pair<String, String>, Int, Boolean>> {
        return Observable.create<Triple<Pair<String, String>, Int, Boolean>> { subscriber ->
            val disposable = Observable.interval(0, WORDS_UPDATE_INTERVAL, TimeUnit.MILLISECONDS)
                    .subscribe {
                        if(wordCount < NB_OF_WORDS_IN_GAME && words != null) {
                            val firstWord = words.getRandomElement()
                            val isCorrectTranslationOfWord = Math.random() < 0.5
                            subscriber.onNext(Triple(
                                    Pair(firstWord.text_eng, (if(isCorrectTranslationOfWord) firstWord else words.getRandomElement()).text_spa),
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