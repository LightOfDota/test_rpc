package org.moran.api.impl;

import org.moran.api.OrderInterface;
import org.moran.vo.OrderVo;

public class OrderInterfaceImpl implements OrderInterface {
	@Override
	public OrderVo getOrderById(Long id) {
		return new OrderVo(111L,222L,"商品1",3);
	}

	@Override
	public int saveOrder(String name, Integer count) {
		return 1;
	}
}
