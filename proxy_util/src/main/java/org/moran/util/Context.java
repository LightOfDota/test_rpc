package org.moran.util;

import org.moran.util.handler.Dispatcher;

public class Context {

	public Context(Dispatcher dispatcher, int port) {
		this.dispatcher = dispatcher;
		this.port = port;
	}

	private final Dispatcher dispatcher;
	private final int port;

	public Dispatcher getDispatcher() {
		return dispatcher;
	}
	public int getPort() {
		return port;
	}
}
