package org.moran.util.entity;

import java.io.Serializable;
import java.util.Arrays;

public class Content implements Serializable {
	private Class<?> clazz;
	private String name;
	private String methodName;
	private Class<?>[] argTypes;
	private Object[] args;
	private Class<?> returnType;
	private Object res;

	@Override
	public String toString() {
		return "Content{" +
				"clazz=" + clazz +
				", name='" + name + '\'' +
				", methodName='" + methodName + '\'' +
				", argTypes=" + Arrays.toString(argTypes) +
				", args=" + Arrays.toString(args) +
				", returnType=" + returnType +
				", res=" + res +
				'}';
	}

	public Content() {
	}

	public Content(Class<?> clazz, String name, String methodName, Class<?>[] argTypes, Object[] args, Class<?> returnType) {
		this.clazz = clazz;
		this.name = name;
		this.methodName = methodName;
		this.argTypes = argTypes;
		this.args = args;
		this.returnType = returnType;
	}

	public Content(Object res) {
		this.res = res;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getArgTypes() {
		return argTypes;
	}

	public void setArgTypes(Class<?>[] argTypes) {
		this.argTypes = argTypes;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

	public Object getRes() {
		return res;
	}

	public void setRes(Object res) {
		this.res = res;
	}
}
