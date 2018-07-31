package com.challenge.fallingwords.game.presenter

interface Lifecycle {
    fun start()
    fun pause()
    fun resume()
    fun finish()
}