package org.moran.vo;

import java.io.Serializable;

public class OrderVo implements Serializable {

	private Long id;
	private Long orderNo;
	private String name;
	private Integer count;



	@Override
	public String toString() {
		return "OrderVo{" +
				"id=" + id +
				", orderNo=" + orderNo +
				", name='" + name + '\'' +
				", count=" + count +
				'}';
	}

	public OrderVo() {
	}

	public OrderVo(Long id, Long orderNo, String name, Integer count) {
		this.id = id;
		this.orderNo = orderNo;
		this.name = name;
		this.count = count;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
