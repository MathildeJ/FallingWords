package com.challenge.fallingwords.game.domain

import com.challenge.fallingwords.game.domain.model.WordEngSpa
import io.reactivex.Observable

interface GetWords{
    fun execute(words: Array<WordEngSpa>?): Observable<Triple<Pair<String, String>, Int, Boolean>>

    operator fun invoke(words: Array<WordEngSpa>?) = this.execute(words)
}