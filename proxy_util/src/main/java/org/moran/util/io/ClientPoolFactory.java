package org.moran.util.io;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.moran.util.handler.ClientHandler;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ClientPoolFactory {
	int size = 1;
	Random random = new Random();
	NioEventLoopGroup clientWorker = new NioEventLoopGroup(1);
	private ConcurrentHashMap<InetSocketAddress,ClientPool> box = new ConcurrentHashMap<>();
	private ClientPoolFactory(){

	}
	private final static ClientPoolFactory factory;
	static {
		factory = new ClientPoolFactory();
	}
	public static ClientPoolFactory getFactory(){
		return factory;
	}

	public NioSocketChannel getClient(InetSocketAddress address) throws Exception {
		ClientPool clientPool = box.get(address);
		if (clientPool == null) {
			clientPool = new ClientPool(size);
			box.putIfAbsent(address,clientPool);
		}
		int i = random.nextInt(size);
		NioSocketChannel client = clientPool.getClients()[i];
		if (client != null && client.isActive()) {
			return client;
		}

		synchronized (clientPool.getLock()[i]){
			clientPool.getClients()[i] = create(address);
			return clientPool.getClients()[i];
		}

	}

	private NioSocketChannel create(InetSocketAddress address) throws Exception {
		Bootstrap bs = new Bootstrap();
		ChannelFuture connect = bs.group(clientWorker).channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new ClientHandler());
					}
				}).connect(address);
		return (NioSocketChannel) connect.sync().channel();
	}
}
