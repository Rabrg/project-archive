package me.rabrg.wyvern.node;

public abstract class Node {

	public abstract boolean validate();

	public abstract int execute();

	public abstract int priority();

	public abstract String getName();

}