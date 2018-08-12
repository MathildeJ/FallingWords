package com.challenge.fallingwords.game.domain

import com.challenge.fallingwords.game.domain.model.WordEngSpa
import io.reactivex.Observable

interface GetWords{
    fun execute(): Observable<Triple<Pair<String, String>, Int, Boolean>>

    operator fun invoke() = this.execute()
}