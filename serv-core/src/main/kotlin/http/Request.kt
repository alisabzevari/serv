package io.serv.http

import java.net.URI
import java.nio.Buffer

data class Request(
    val method: Method,
    val uri: URI,
    val headers: Headers,
    val body: Buffer?
)
