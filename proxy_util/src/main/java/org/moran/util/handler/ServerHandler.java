package org.moran.util.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.moran.util.decoder.SerDerUtil;
import org.moran.util.entity.Content;
import org.moran.util.entity.Header;
import org.moran.util.entity.Packmsg;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	Dispatcher dispatcher;

	public ServerHandler(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Packmsg packmsg = (Packmsg) msg;
		Content content = packmsg.getContent();
		String name = content.getName();
		Class<?> clazz = content.getClazz();
		String method = content.getMethodName();
		Object o = dispatcher.get(clazz);

		ctx.executor().parent().execute(()->{
			Object res = null;
			try {
				Method m = clazz.getMethod(method, packmsg.getContent().getArgTypes());
				res = m.invoke(o, packmsg.getContent().getArgs());
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			Content resContent = new Content(res);
//                String s = "io thread: " + ioThreadName + " exec thread: " + execThreadName + " from args:" + requestPkg.content.getArgs()[0];
			byte[] contentByte = SerDerUtil.ser(resContent);

			Header resHeader = new Header();
			resHeader.setRequestId(packmsg.getHeader().getRequestId());
			resHeader.setFlag(0x14141424);
			resHeader.setDataLen(contentByte.length);
			byte[] headerByte = SerDerUtil.ser(resHeader);
			ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerByte.length + contentByte.length);

			byteBuf.writeBytes(headerByte);
			byteBuf.writeBytes(contentByte);
			ctx.writeAndFlush(byteBuf);
		});
	}
}
