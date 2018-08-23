package com.challenge.fallingwords.game.domain

import com.challenge.fallingwords.game.domain.model.GameState
import com.challenge.fallingwords.game.domain.model.WordEngSpa
import io.reactivex.Observable

interface GetWords{
    fun execute(): Observable<GameState>

    operator fun invoke() = this.execute()
}