package com.challenge.fallingwords.game.domain

import io.reactivex.Observable

interface GetWords{
    fun execute(words: Array<WordEngSpa>?): Observable<Pair<String, String>>

    operator fun invoke(words: Array<WordEngSpa>?) = this.execute(words)
}