package org.moran.util.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.moran.util.entity.Content;
import org.moran.util.entity.Header;
import org.moran.util.entity.Packmsg;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		if (buf.readableBytes() >= 98) {
			byte[] bytes = new byte[98];
			buf.readBytes(bytes);
			ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
			ObjectInputStream oin = new ObjectInputStream(bin);
			Packmsg packmsg = new Packmsg();
			Header o = (Header) oin.readObject();
			if(buf.readableBytes() >= o.getDataLen()){
				bytes = new byte[(int) o.getDataLen()];
				buf.readBytes(bytes);
				bin = new ByteArrayInputStream(bytes);
				oin = new ObjectInputStream(bin);
				Content content = (Content) oin.readObject();
				packmsg.setContent(content);
			}
			packmsg.setHeader(o);
			ResponseHandler.run(packmsg);
		}
	}
}
