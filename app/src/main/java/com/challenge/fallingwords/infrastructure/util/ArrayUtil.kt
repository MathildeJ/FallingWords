package com.challenge.fallingwords.infrastructure.util

import java.util.*

fun <E> Array<E>.getRandomElement() = this[Random().nextInt(this.size)]