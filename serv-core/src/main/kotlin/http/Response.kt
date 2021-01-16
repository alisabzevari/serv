package io.serv.http

import java.nio.Buffer

data class Response(
    val status: Status,
    val headers: Headers,
    val body: Buffer?
)
