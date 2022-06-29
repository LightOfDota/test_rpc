package org.moran.api;

import org.moran.vo.OrderVo;

/**
 *
 */
public interface OrderInterface {
	OrderVo getOrderById(Long id);

	int saveOrder(String name,Integer count);
}
