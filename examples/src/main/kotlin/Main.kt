package io.serv.examples

import java.net.http.HttpClient
import java.net.http.HttpRequest

fun main() {
    val req = HttpRequest.newBuilder().GET().build()
    req.headers()
}
