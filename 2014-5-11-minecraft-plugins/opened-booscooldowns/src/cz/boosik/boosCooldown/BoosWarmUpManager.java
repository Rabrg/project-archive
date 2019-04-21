package cz.boosik.boosCooldown;

import java.util.Iterator;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import util.boosChat;

/**
 * Tøída obsahuje veškeré metody potøebné k øízení warmup èasovaèù. Spouštìní,
 * ukonèování a zjišování zda je warmup èasovaè ji aktivní.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosWarmUpManager {

	private static ConcurrentHashMap<String, BoosWarmUpTimer> playercommands = new ConcurrentHashMap<String, BoosWarmUpTimer>();
	private static ConcurrentHashMap<Player, Location> playerloc = new ConcurrentHashMap<Player, Location>();
	private static ConcurrentHashMap<Player, String> playerworld = new ConcurrentHashMap<Player, String>();

	private static Timer scheduler;

	/**
	 * Metoda aplikuje na hráèe magickı efekt na dobu urèenou parametrem
	 * warmUpSeconds.
	 * 
	 * @param player
	 *            specifickı hráè
	 * @param regexCommand
	 *            pøíkaz z konfiguraèního souboru, kterı vyhovuje originálnímu
	 *            pøíkazu
	 * @param warmUpSeconds
	 *            doba warmup v sekundách, nastavená pro regexCommand v
	 *            konfiguraci
	 */
	static void applyPotionEffect(Player player, String regexCommand,
			int warmUpSeconds) {
		String potion = BoosConfigManager.getPotionEffect(regexCommand, player);
		if (potion.equals("")) {
			return;
		}
		int potionStrength = BoosConfigManager.getPotionEffectStrength(
				regexCommand, player);
		if (potionStrength == 0) {
			return;
		}
		PotionEffectType effect = PotionEffectType.getByName(potion);
		player.addPotionEffect(
				effect.createEffect(warmUpSeconds * 40, potionStrength - 1),
				true);
	}

	/**
	 * Metoda stornuje veškeré probíhající warmup èasovaèe specifického hráèe.
	 * 
	 * @param player
	 *            specifickı hráè
	 */
	public static void cancelWarmUps(Player player) {
		Iterator<String> iter = playercommands.keySet().iterator();
		while (iter.hasNext()) {
			if (iter.next().startsWith(player.getName() + "@")) {
				killTimer(player);
				iter.remove();
			}
		}
	}

	/**
	 * Metoda pro specifického hráèe vymae uloenou pozici a svìt.
	 * 
	 * @param player
	 *            specifickı hráè
	 */
	public static void clearLocWorld(Player player) {
		BoosWarmUpManager.playerloc.remove(player);
		BoosWarmUpManager.playerworld.remove(player);
	}

	/**
	 * Metoda vrací boolean hodnotu v závislosti na tom jestli specifikovanı
	 * hráè má aktivní warmup èasovaèe nebo ne.
	 * 
	 * @param player
	 *            specifickı hráè
	 * @return true pokud hráè má aktivní warmup èasovaèe, jinak false
	 */
	public static boolean hasWarmUps(Player player) {
		for (String key : playercommands.keySet()) {
			if (key.startsWith(player.getName() + "@")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda zjišuje, jestli je danı warmup èasovaè oznaèenı jako ji probìhlı
	 * nebo ne.
	 * 
	 * @param player
	 *            specifickı hráè
	 * @param regexCommand
	 *            pøíkaz z konfiguraèního souboru, kterı vyhovuje originálnímu
	 *            pøíkazu
	 * @return true pokud je warmup èasovaè oznaèen jako ji probìhlı, jinak
	 *         false
	 */
	static boolean checkWarmUpOK(Player player, String regexCommand) {
		int pre2 = regexCommand.toLowerCase().hashCode();
		int ok = 0;
		ok = BoosConfigManager.getConfusers().getInt(
				"users." + player.getName().toLowerCase().hashCode()
						+ ".warmup." + pre2, ok);
		if (ok == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda vrací boolean hodnotu na základì toho jestli je pro specifikovanı
	 * pøíkaz specifikovaného hráèe aktivní warmup èasovaè.
	 * 
	 * @param player
	 *            specifickı hráè
	 * @param regexCommand
	 *            pøíkaz z konfiguraèního souboru, kterı vyhovuje originálnímu
	 *            pøíkazu
	 * @return true pokud je warmup èasovaè aktivní, jinak false
	 */
	static boolean isWarmUpProcess(Player player, String regexCommand) {
		regexCommand = regexCommand.toLowerCase();
		if (playercommands.containsKey(player.getName() + "@" + regexCommand)) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda odstraní všechny èasovaèe specifikovaného hráèe
	 * 
	 * @param player
	 *            specifickı hráè
	 */
	static void killTimer(Player player) {
		for (String key : playercommands.keySet()) {
			if (key.startsWith(player.getName() + "@")) {
				playercommands.get(key).cancel();
			}
		}
	}

	/**
	 * Metoda odstraní èasovaèe na specifikovaném pøíkazu specifikovaného hráèe
	 * 
	 * @param player
	 *            specifickı hráè
	 * @param regexCommand
	 *            pøíkaz z konfiguraèního souboru, kterı vyhovuje originálnímu
	 *            pøíkazu
	 */
	static void removeWarmUp(Player player, String regexCommand) {
		int pre2 = regexCommand.toLowerCase().hashCode();
		BoosConfigManager.getConfusers().set(
				"users." + player.getName().toLowerCase().hashCode()
						+ ".warmup." + pre2, null);
	}

	/**
	 * Metoda odstraní ukonèené èasovaèe na specifikovaném pøíkazu
	 * specifikovaného hráèe
	 * 
	 * @param player
	 *            specifickı hráè
	 * @param regexCommand
	 *            pøíkaz z konfiguraèního souboru, kterı vyhovuje originálnímu
	 *            pøíkazu
	 */
	static void removeWarmUpOK(Player player, String regexCommand) {
		int pre2 = regexCommand.toLowerCase().hashCode();
		BoosConfigManager.getConfusers().set(
				"users." + player.getName().toLowerCase().hashCode()
						+ ".warmup." + pre2, null);
	}

	/**
	 * Metoda odstraòuje danı øetìzec z Hashmapy
	 * 
	 * @param tag
	 *            øetìzec, kterı se má odstranit z Hasmapy
	 */
	static void removeWarmUpProcess(String tag) {
		BoosWarmUpManager.playercommands.remove(tag);
	}

	/**
	 * Metoda oznaèuje warmup èasovaè specifikovaného pøíkazu specifikovaného
	 * hráèe jako ji ukonèenı.
	 * 
	 * @param player
	 *            specifickı hráè
	 * @param regexCommand
	 *            pøíkaz z konfiguraèního souboru, kterı vyhovuje originálnímu
	 *            pøíkazu
	 */
	static void setWarmUpOK(Player player, String regexCommand) {
		int pre2 = regexCommand.toLowerCase().hashCode();
		BoosConfigManager.getConfusers().set(
				"users." + player.getName().toLowerCase().hashCode()
						+ ".warmup." + pre2, 1);
	}

	/**
	 * Metoda spouští warmup èasovaè na základì parametrù pomocí Timer(). Pokud
	 * je ji warmup aktivní, odešle hráèi zprávu která ho o tom informuje.
	 * 
	 * @param bCoolDown
	 *            instance tøídy BoosCooldown
	 * @param player
	 *            specifickı hráè
	 * @param regexCommand
	 *            pøíkaz z konfiguraèního souboru, kterı vyhovuje originálnímu
	 *            pøíkazu
	 * @param originalCommand
	 *            originální pøíkaz pouitı hráèem
	 * @param warmUpSeconds
	 *            warmup doba nastavená pro pøíkaz v sekundách
	 */
	static void startWarmUp(BoosCoolDown bCoolDown, Player player,
			String regexCommand, String originalCommand, int warmUpSeconds) {
		regexCommand = regexCommand.toLowerCase();
		long warmUpMinutes = Math.round(warmUpSeconds / 60);
		long warmUpHours = Math.round(warmUpMinutes / 60);
		if (!isWarmUpProcess(player, regexCommand)) {
			BoosWarmUpManager.removeWarmUpOK(player, regexCommand);
			String msg = BoosConfigManager.getWarmUpMessage();
			msg = msg.replaceAll("&command&", originalCommand);
			if (warmUpSeconds >= 60 && 3600 >= warmUpSeconds) {
				msg = msg.replaceAll("&seconds&", Long.toString(warmUpMinutes));
				msg = msg.replaceAll("&unit&",
						BoosConfigManager.getUnitMinutesMessage());
			} else if (warmUpMinutes >= 60) {
				msg = msg.replaceAll("&seconds&", Long.toString(warmUpHours));
				msg = msg.replaceAll("&unit&",
						BoosConfigManager.getUnitHoursMessage());
			} else {
				msg = msg.replaceAll("&seconds&", Long.toString(warmUpSeconds));
				msg = msg.replaceAll("&unit&",
						BoosConfigManager.getUnitSecondsMessage());
			}
			boosChat.sendMessageToPlayer(player, msg);

			scheduler = new Timer();
			BoosWarmUpTimer scheduleMe = new BoosWarmUpTimer(bCoolDown,
					scheduler, player, regexCommand, originalCommand);
			playercommands.put(player.getName() + "@" + regexCommand,
					scheduleMe);
			scheduler.schedule(scheduleMe, warmUpSeconds * 1000);
			applyPotionEffect(player, regexCommand, warmUpSeconds);
		} else {
			String msg = BoosConfigManager.getWarmUpAlreadyStartedMessage();
			msg = msg.replaceAll("&command&", originalCommand);
			boosChat.sendMessageToPlayer(player, msg);
		}
	}

	/**
	 * @return
	 */
	public static ConcurrentHashMap<Player, String> getPlayerworld() {
		return playerworld;
	}

	/**
	 * @return
	 */
	public static ConcurrentHashMap<Player, Location> getPlayerloc() {
		return playerloc;
	}
}
