package me.rabrg.beg;

import org.tbot.internal.AbstractScript;
import org.tbot.methods.Random;

public final class RabrgBeg extends AbstractScript {

	@Override
	public int loop() {
		
		return Random.nextInt(25, 50);
	}

}
