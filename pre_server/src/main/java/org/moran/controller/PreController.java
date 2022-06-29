package org.moran.controller;

import org.moran.api.OrderInterface;
import org.moran.util.ProxyUtil;
import org.moran.vo.OrderVo;

public class PreController {

	public final static OrderInterface orderInterface;
	static {
		orderInterface = ProxyUtil.proxy(OrderInterface.class);
	}

	public OrderVo selectById(Long id) {
		OrderVo orderById = orderInterface.getOrderById(id);
		return orderById;
	}

	public static void main(String[] args) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				PreController preController = new PreController();
				OrderVo orderVo = preController.selectById(111L);
				System.out.println("123123123123" + orderVo);
			}
		};
		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();


	}
}
