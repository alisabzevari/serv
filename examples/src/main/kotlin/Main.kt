package io.serv.examples

import io.serv.http.Response
import io.serv.http.Status
import io.serv.netty.server.NettyHttpServer
import kotlinx.coroutines.delay
import java.net.http.HttpClient
import java.net.http.HttpRequest

fun main() {
    val server = NettyHttpServer(9090) {
        println("$it")
        println(Thread.currentThread().name)
        delay(2000)
        Response(Status.OK, mapOf("Auth" to "some auth"), null)
    }
}
