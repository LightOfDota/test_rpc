package org.moran.util;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.moran.util.decoder.ContentDecoder;
import org.moran.util.handler.Dispatcher;
import org.moran.util.handler.ServerHandler;

import java.net.InetSocketAddress;

public class Server {
	public final Context context;

	public Server(Context context) {
		this.context = context;
	}

	public void runServer() {
		NioEventLoopGroup boss = new NioEventLoopGroup(1);
		NioEventLoopGroup worker = boss;
		ServerBootstrap serverBootstrap= new ServerBootstrap();
		ChannelFuture bind = serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new ContentDecoder());
						pipeline.addLast(new ServerHandler(context.getDispatcher()));
					}
				}).bind(new InetSocketAddress("localhost", context.getPort()));
		try {
			bind.sync().channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
