package cz.boosik.boosCooldown;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.entity.Player;

/**
 * Tøída starající se o samotné èasovaèe warmupù pomocí TimerTask
 * 
 * @author Jakub Koláø
 *
 */
public class BoosWarmUpTimer extends TimerTask {

	private BoosCoolDown bCoolDown;
	private Player player;
	private String originalCommand;
	private String regexCommand;

	/**
	 * @param bCoolDown
	 *            instance tøídy BoosCoolDown
	 * @param timer
	 * @param player
	 *            specifický hráè
	 * @param regexCommand
	 *            pøíkaz z konfiguraèního souboru, který vyhovuje originálnímu
	 *            pøíkazu
	 * @param originalCommand
	 *            originální pøíkaz který hráè použil
	 */
	public BoosWarmUpTimer(BoosCoolDown bCoolDown, Timer timer, Player player,
			String regexCommand, String originalCommand) {
		this.bCoolDown = bCoolDown;
		this.player = player;
		this.regexCommand = regexCommand;
		this.originalCommand = originalCommand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		bCoolDown.getServer().getScheduler()
				.scheduleSyncDelayedTask(bCoolDown, new boosWarmUpRunnable());
	}

	public class boosWarmUpRunnable implements Runnable {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			if (player.isOnline() && !player.isDead()
					&& BoosWarmUpManager.hasWarmUps(player)) {
				BoosWarmUpManager.setWarmUpOK(player, regexCommand);
				BoosWarmUpManager.removeWarmUpProcess(player.getName() + "@"
						+ regexCommand);
				BoosWarmUpManager.clearLocWorld(player);
				player.chat(originalCommand);
			} else if (player.isOnline() && player.isDead()
					&& BoosWarmUpManager.hasWarmUps(player)) {
				BoosWarmUpManager.removeWarmUp(player, regexCommand);
				BoosWarmUpManager.removeWarmUpProcess(player.getName() + "@"
						+ regexCommand);
				BoosWarmUpManager.clearLocWorld(player);
			} else if (!player.isOnline()
					&& BoosWarmUpManager.hasWarmUps(player)) {
				BoosWarmUpManager.removeWarmUp(player, regexCommand);
				BoosWarmUpManager.removeWarmUpProcess(player.getName() + "@"
						+ regexCommand);
				BoosWarmUpManager.clearLocWorld(player);
			}
		}
	}
}
