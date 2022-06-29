package org.moran.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.CompleteFuture;
import org.moran.util.entity.Content;
import org.moran.util.entity.Header;
import org.moran.util.handler.ResponseHandler;
import org.moran.util.io.ClientPoolFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

public class ProxyUtil {
	public static <T> T proxy(Class<T> clazz) {
		ClassLoader classLoader = clazz.getClassLoader();
		Class<?>[] methodInfo = {clazz};

		return (T) Proxy.newProxyInstance(classLoader, methodInfo, (proxy, method, args) -> {
			String className = clazz.getName();
			String methodName = method.getName();
			Class<?>[] parameterTypes = method.getParameterTypes();
			Class<?> returnType = method.getReturnType();
			Content content = new Content(clazz,className, methodName, parameterTypes, args,returnType);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oout = new ObjectOutputStream(out);
			oout.writeObject(content);
			byte[] msgBody = out.toByteArray();
			System.out.println("main msgBody:::"+ content);
			System.out.println("main msgBody length:::"+ msgBody.length);
			Header header = createHeader(msgBody);
			out.reset();
			oout = new ObjectOutputStream(out);
			oout.writeObject(header);
			byte[] msgHeader = out.toByteArray();
			System.out.println("main header:::"+ header);
			System.out.println("main header length:::"+ msgHeader.length);

			ClientPoolFactory factory = ClientPoolFactory.getFactory();
			NioSocketChannel channel = factory.getClient(new InetSocketAddress("localhost", 9090));
			ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgBody.length + msgHeader.length);
			byteBuf.writeBytes(msgHeader);
			byteBuf.writeBytes(msgBody);
			ChannelFuture channelFuture = channel.writeAndFlush(byteBuf);
			channelFuture.sync();

			CompletableFuture<Object> completeFuture = new CompletableFuture<>();
			ResponseHandler.put(header.getRequestId(), completeFuture);

			return completeFuture.get();
		});
	}


	public static Header createHeader(byte[] bytes){
		long requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
		System.out.println("requestId = " + requestId);
		return new Header(0x14141414, requestId, bytes.length);
	}


}
