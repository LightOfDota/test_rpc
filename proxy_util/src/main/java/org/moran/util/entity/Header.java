package org.moran.util.entity;

import java.io.Serializable;

public class Header implements Serializable {
	private int flag;//32位
	private long requestId;
	private long dataLen;

	@Override
	public String toString() {
		return "Header{" +
				"flag=" + flag +
				", requestId=" + requestId +
				", dataLen=" + dataLen +
				'}';
	}

	public Header(int flag, long requestId, long dataLen) {
		this.flag = flag;
		this.requestId = requestId;
		this.dataLen = dataLen;
	}

	public Header() {
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public long getDataLen() {
		return dataLen;
	}

	public void setDataLen(long dataLen) {
		this.dataLen = dataLen;
	}
}
