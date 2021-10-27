package netty.server

import http.toHeaders
import http.toStatus
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.serv.http.Method
import io.serv.http.Request
import io.serv.http.Response
import io.serv.http.Status
import io.serv.netty.server.NettyHttpServer
import kotlinx.coroutines.future.await
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class NettyHttpServerTest : StringSpec({
    "Should start the server" {
        val port = 9090
        val expectedResponse = Response(Status.OK, mapOf("header" to "value"), null)
        val server = NettyHttpServer(port) { expectedResponse }
        val response = send(Request(Method.GET, URI("http://localhost:$port"), emptyMap(), null))

        response shouldBe expectedResponse

        server.shutdown()
    }

})

suspend fun send(req: Request): Response {
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(req.uri)
        .method(req.method.name, HttpRequest.BodyPublishers.noBody())
        .build()
    val resp = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
    return Response(
        resp.statusCode().toStatus(),
        resp.headers().toHeaders(),
        null
    )
}
