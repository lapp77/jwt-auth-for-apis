package com.manning.simplysend.util

import java.util.*

object Base64Url {
    private val encoder = Base64.getUrlEncoder().withoutPadding()
    private val decoder = Base64.getUrlDecoder()

    fun encode(data: ByteArray): String = encoder.encodeToString(data)

    fun decode(encoded: String): ByteArray = decoder.decode(encoded)
}