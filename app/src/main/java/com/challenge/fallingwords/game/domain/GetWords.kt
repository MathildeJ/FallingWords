package com.challenge.fallingwords.game.domain

import io.reactivex.Observable

interface GetWords{
    fun execute(): Observable<Pair<String, String>>

    operator fun invoke() = this.execute()
}