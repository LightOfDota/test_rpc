package org.moran.util.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.moran.util.entity.Content;
import org.moran.util.entity.Header;
import org.moran.util.entity.Packmsg;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class ContentDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

		System.out.println(buf);
		while (buf.readableBytes() >= 98) {
			byte[] bytes = new byte[98];
			buf.readBytes(bytes);
			ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
			ObjectInputStream oin = new ObjectInputStream(bin);
			Header o = (Header) oin.readObject();
			System.out.println(o);
			if(buf.readableBytes() >= o.getDataLen()){
				bytes = new byte[(int) o.getDataLen()];
				buf.readBytes(bytes);
				bin = new ByteArrayInputStream(bytes);
				oin = new ObjectInputStream(bin);
				Content content = (Content) oin.readObject();
				System.out.println(content);
				Packmsg packmsg = new Packmsg();
				packmsg.setHeader(o);
				packmsg.setContent(content);
				out.add(packmsg);
			} else {
				System.out.println("少了");
				break;
			}
//			ResponseHandler.run(o.getRequestId());
		}
	}
}
