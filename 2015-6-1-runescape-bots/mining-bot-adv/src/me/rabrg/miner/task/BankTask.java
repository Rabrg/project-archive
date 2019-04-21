package me.rabrg.miner.task;

import me.rabrg.miner.MinerTaskScript;
import me.rabrg.script.task.Task;

public class BankTask implements Task {

	private final MinerTaskScript script;

	public BankTask(final MinerTaskScript script) {
		this.script = script;
	}

	@Override
	public boolean validate() {
		return !script.inventory.isEmpty() && script.bankArea.contains(script.myPlayer());
	}

	@Override
	public int execute() throws InterruptedException {
		script.bank.open();
		script.bank.depositAll();
		script.bank.close();
		return 0;
	}

}
