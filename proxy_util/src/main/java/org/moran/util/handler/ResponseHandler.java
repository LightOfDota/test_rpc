package org.moran.util.handler;

import org.moran.util.entity.Packmsg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class ResponseHandler {
	private static ConcurrentHashMap<Long, CompletableFuture<Object>> map = new ConcurrentHashMap<>();

	public static void put(Long requestId,CompletableFuture<Object> completableFuture) {
		map.putIfAbsent(requestId,completableFuture);
	}

	public static void run(Packmsg packmsg) {
		CompletableFuture<Object> completableFuture = map.get(packmsg.getHeader().getRequestId());
		if (completableFuture != null) {
			completableFuture.complete(packmsg.getContent().getRes());
			map.remove(packmsg.getHeader().getRequestId());
		}
	}
}
