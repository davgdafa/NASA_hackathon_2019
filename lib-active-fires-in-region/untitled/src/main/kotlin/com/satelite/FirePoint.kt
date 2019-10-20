package com.satelite

internal data class FirePoint(
    val lat: Double,
    val long: Double,
    val confidence: Int,
    val acquiredDateTime: Long
)