package io.serv.http

import io.netty.handler.codec.http.HttpHeaders

fun HttpHeaders.toHeaders() = this.associate { it.key to it.value }
