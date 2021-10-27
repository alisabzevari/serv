package io.serv.netty.server

import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.DefaultFullHttpResponse
import io.netty.handler.codec.http.HttpRequest
import io.netty.handler.codec.http.HttpResponseStatus
import io.netty.handler.codec.http.HttpVersion
import io.serv.Handler
import io.serv.http.Method
import io.serv.http.Request
import io.serv.http.Response
import io.serv.http.toHeaders
import kotlinx.coroutines.asCoroutineDispatcher
import java.net.URI
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine

class NettyHttpServerHandler(val handler: Handler) : SimpleChannelInboundHandler<HttpRequest>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: HttpRequest) {
        this.handler.startCoroutine(
            msg.toRequest(),
            NettyContinuation(ctx)
        )
    }
}

fun HttpRequest.toRequest() =
    Request(
        Method.valueOf(method().name()),
        URI.create(uri()),
        headers().toHeaders(),
        null
    )

fun Response.toHttpResponse(): DefaultFullHttpResponse {
    val response = DefaultFullHttpResponse(
        HttpVersion.HTTP_1_1,
        HttpResponseStatus.valueOf(status.code)
    )
    headers.forEach {
        response.headers().add(it.key, it.value)
    }
    return response
}


class NettyContinuation(val ctx: ChannelHandlerContext) : Continuation<Response> {
    override fun resumeWith(result: Result<Response>) {
        val response = if (result.isSuccess) {
            result.getOrNull()?.toHttpResponse()
        } else {
            DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR)
        }

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE)
    }

    override val context: CoroutineContext
        get() = ctx.executor().asCoroutineDispatcher()
}
