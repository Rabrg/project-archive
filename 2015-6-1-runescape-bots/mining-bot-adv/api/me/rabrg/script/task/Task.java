package me.rabrg.script.task;

public interface Task {

	public boolean validate();
	public int execute() throws InterruptedException;
}
