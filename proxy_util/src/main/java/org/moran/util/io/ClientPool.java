package org.moran.util.io;

import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientPool {
	private NioSocketChannel[] clients;
	private Object[] lock;

	public ClientPool(int size) {
		this.clients = new NioSocketChannel[size];
		this.lock = new Object[size];
		for (int i = 0; i < lock.length; i++) {
			lock[i] = new Object();
		}
	}

	public NioSocketChannel[] getClients() {
		return clients;
	}

	public void setClients(NioSocketChannel[] clients) {
		this.clients = clients;
	}

	public Object[] getLock() {
		return lock;
	}

	public void setLock(Object[] lock) {
		this.lock = lock;
	}
}
