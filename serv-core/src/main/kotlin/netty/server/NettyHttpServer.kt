package io.serv.netty.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.codec.http.HttpResponseEncoder

import io.netty.handler.codec.http.HttpRequestDecoder
import io.serv.Handler


class NettyHttpServer(port: Int, handler: Handler) {
    val bootstrap = ServerBootstrap()
    val bossGroup = NioEventLoopGroup(1)
    val workerGroup = NioEventLoopGroup()
    lateinit var channelFuture: ChannelFuture

    init {
        try {
            bootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .handler(LoggingHandler(LogLevel.INFO))
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel) {
                        val p = ch.pipeline()
                        p.addLast(HttpRequestDecoder())
                        p.addLast(HttpResponseEncoder())
                        p.addLast(NettyHttpServerHandler(handler))
                    }
                })

            channelFuture = bootstrap.bind(port).sync()
        } catch(ex: Exception) {
            println(ex)
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }

    fun shutdown() {
//        channelFuture.channel().closeFuture().sync()
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }
}
