package cz.boosik.boosCooldown;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bukkit.entity.Player;

import util.boosChat;

/**
 * Tøída obsahuje veškeré metody potøebné k øízení cooldown èasovaèù. Spouštìní,
 * ukonèování, zjišování zda je cooldown èasovaè ji aktivní.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosCoolDownManager {
	/**
	 * Metoda ukonèuje specifikovanı cooldown èasovaè pro specifikovaného hráèe.
	 * 
	 * @param player
	 *            specifickı hráè
	 * @param regexCommand
	 *            pøíkaz z konfigurace vyhovující originálnímu pøíkazu
	 */
	static void cancelCooldown(Player player, String regexCommand) {
		int pre2 = regexCommand.toLowerCase().hashCode();
		BoosConfigManager.getConfusers().set(
				"users." + player.getName().toLowerCase().hashCode()
						+ ".cooldown." + pre2, null);
	}

	/**
	 * Metoda vrací hodnotu boolean na základì toho, jestli má specifikovanı
	 * pøíkaz aktivní cooldown èasovaè.
	 * 
	 * @param player
	 *            specifikovanı hráè
	 * @param regexCommand
	 *            pøíkaz z konfigurace vyhovující originálnímu pøíkazu
	 * @param originalCommand
	 *            originální pøíkaz pouitı hráèem
	 * @param coolDownSeconds
	 *            cooldown doba v sekundách, nastavená pro regexCommand v
	 *            konfiguraci
	 * @return true pokud je pøíkaz na cooldown èasovaèi, jinak false
	 */
	static boolean cd(Player player, String regexCommand,
			String originalCommand, int coolDownSeconds) {
		Date lastTime = getTime(player, regexCommand);
		String link = BoosConfigManager.getLink(regexCommand);
		if (lastTime == null) {
			if (link == null) {
				setTime(player, regexCommand);
			} else {
				List<String> linkGroup = BoosConfigManager.getLinkList(link);
				for (String a : linkGroup) {
					setTime(player, a);
				}
			}
			return false;
		} else {
			Calendar calcurrTime = Calendar.getInstance();
			calcurrTime.setTime(getCurrTime());
			Calendar callastTime = Calendar.getInstance();
			callastTime.setTime(lastTime);
			long secondsBetween = secondsBetween(callastTime, calcurrTime);
			long waitSeconds = coolDownSeconds - secondsBetween;
			long waitMinutes = Math.round(waitSeconds / 60) + 1;
			long waitHours = Math.round(waitMinutes / 60) + 1;
			if (secondsBetween > coolDownSeconds) {
				if (link == null) {
					setTime(player, regexCommand);
				} else {
					List<String> linkGroup = BoosConfigManager
							.getLinkList(link);
					for (String a : linkGroup) {
						setTime(player, a);
					}
				}
				return false;
			} else {
				String msg = BoosConfigManager.getCoolDownMessage();
				msg = msg.replaceAll("&command&", originalCommand);
				if (waitSeconds >= 60 && 3600 >= waitSeconds) {
					msg = msg.replaceAll("&seconds&",
							Long.toString(waitMinutes));
					msg = msg.replaceAll("&unit&",
							BoosConfigManager.getUnitMinutesMessage());
				} else if (waitMinutes >= 60) {
					msg = msg.replaceAll("&seconds&", Long.toString(waitHours));
					msg = msg.replaceAll("&unit&",
							BoosConfigManager.getUnitHoursMessage());
				} else {
					String secs = Long.toString(waitSeconds);
					if (secs.equals("0")) {
						secs = "1";
					}
					msg = msg.replaceAll("&seconds&", secs);
					msg = msg.replaceAll("&unit&",
							BoosConfigManager.getUnitSecondsMessage());
				}
				boosChat.sendMessageToPlayer(player, msg);
				return true;
			}
		}
	}

	/**
	 * Metoda kontroluje, jestli hráè nedisponuje oprávnìními, která obcházejí
	 * cooldown èasovaèe. Pokud tìmito oprávnìními hráè disponuje, pak metoda
	 * vrací false. Pokud hráè nedisponuje tìmito oprávnìními, vrací hodnotu
	 * vrácenou metodou cd();.
	 * 
	 * @param player
	 *            specifikovanı hráè
	 * @param regexCommand
	 *            pøíkaz z konfigurace vyhovující originálnímu pøíkazu
	 * @param originalCommand
	 *            originální pøíkaz pouitı hráèem
	 * @param time
	 *            cooldown doba v sekundách, nastavená pro regexCommand v
	 *            konfiguraci
	 * @return false pokud hráè disponuje oprávnìními, jinak hodnotu vrácenou
	 *         metodou cd();.
	 */
	static boolean coolDown(Player player, String regexCommand,
			String originalCommand, int time) {
		regexCommand = regexCommand.toLowerCase();
		if (time > 0
				&& !player.hasPermission("booscooldowns.nocooldown")
				&& !player.hasPermission("booscooldowns.nocooldown."
						+ originalCommand)) {
			return cd(player, regexCommand, originalCommand, time);
		}
		return false;
	}

	/**
	 * Metoda vrací souèasnı pøesnı datum a èas.
	 * 
	 * @return souèasnı èas a datum
	 */
	static Date getCurrTime() {
		String currTime = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		currTime = sdf.format(cal.getTime());
		Date time = null;

		try {
			time = sdf.parse(currTime);
			return time;
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Metoda vrací datum a èas, kdy hráè naposledy pouil danı pøíkaz.
	 * 
	 * @param player
	 *            specifikovanı hráè
	 * @param regexCommand
	 *            pøíkaz z konfigurace vyhovující originálnímu pøíkazu
	 * @return datum a èas kdy hráè naposledy pouil danı pøíkaz
	 */
	static Date getTime(Player player, String regexCommand) {
		int pre2 = regexCommand.toLowerCase().hashCode();
		String confTime = "";
		confTime = BoosConfigManager.getConfusers().getString(
				"users." + player.getName().toLowerCase().hashCode()
						+ ".cooldown." + pre2, null);

		if (confTime != null && !confTime.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			Date lastDate = null;

			try {
				lastDate = sdf.parse(confTime);
				return lastDate;
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Metoda vrací hodnotu boolean na základì toho, jestli má specifikovanı
	 * pøíkaz aktivní cooldown èasovaè.
	 * 
	 * @param player
	 *            specifikovanı hráè
	 * @param regexCommand
	 *            pøíkaz z konfigurace vyhovující originálnímu pøíkazu
	 * @param originalCommand
	 *            originální pøíkaz pouitı hráèem
	 * @param time
	 *            cooldown doba v sekundách, nastavená pro regexCommand v
	 *            konfiguraci
	 * @return false pokud má pøíkaz aktivní cooldown èasovaè, jinak false
	 */
	static boolean checkCoolDownOK(Player player, String regexCommand,
			String originalCommand, int time) {
		regexCommand = regexCommand.toLowerCase();
		if (time > 0) {
			Date lastTime = getTime(player, regexCommand);
			if (lastTime == null) {
				return true;
			} else {
				Calendar calcurrTime = Calendar.getInstance();
				calcurrTime.setTime(getCurrTime());
				Calendar callastTime = Calendar.getInstance();
				callastTime.setTime(lastTime);
				long secondsBetween = secondsBetween(callastTime, calcurrTime);
				long waitSeconds = time - secondsBetween;
				long waitMinutes = Math.round(waitSeconds / 60) + 1;
				long waitHours = Math.round(waitMinutes / 60) + 1;
				if (secondsBetween > time) {
					return true;
				} else {
					String msg = BoosConfigManager.getCoolDownMessage();
					msg = msg.replaceAll("&command&", originalCommand);
					if (waitSeconds >= 60 && 3600 >= waitSeconds) {
						msg = msg.replaceAll("&seconds&",
								Long.toString(waitMinutes));
						msg = msg.replaceAll("&unit&",
								BoosConfigManager.getUnitMinutesMessage());
					} else if (waitMinutes >= 60) {
						msg = msg.replaceAll("&seconds&",
								Long.toString(waitHours));
						msg = msg.replaceAll("&unit&",
								BoosConfigManager.getUnitHoursMessage());
					} else {
						msg = msg.replaceAll("&seconds&",
								Long.toString(waitSeconds));
						msg = msg.replaceAll("&unit&",
								BoosConfigManager.getUnitSecondsMessage());
					}
					boosChat.sendMessageToPlayer(player, msg);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Metoda vrací hodnotu rozdílu v sekundách mezi dvìmi hodnotami datumu a
	 * èasu.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return rozdíl v sekundách mezi startDate a endDate
	 */
	static long secondsBetween(Calendar startDate, Calendar endDate) {
		long secondsBetween = 0;

		while (startDate.before(endDate)) {
			startDate.add(Calendar.SECOND, 1);
			secondsBetween++;
		}
		return secondsBetween;
	}

	/**
	 * Metoda ukládá do databáze datum a èas kdy hráè naposledy pouil danı
	 * pøíkaz.
	 * 
	 * @param player
	 *            specifickı hráè
	 * @param regexCommand
	 *            pøíkaz z konfigurace vyhovující originálnímu pøíkazu
	 */
	static void setTime(Player player, String regexCommand) {
		int pre2 = regexCommand.toLowerCase().hashCode();
		String currTime = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		currTime = sdf.format(cal.getTime());
		BoosConfigManager.getConfusers().set(
				"users." + player.getName().toLowerCase().hashCode()
						+ ".cooldown." + pre2, currTime);
	}

	/**
	 * Metoda spouští veškeré cooldown èasovaèe pro specifického hráèe.
	 * 
	 * @param player
	 *            specifickı hráè
	 * @param message
	 */
	public static void startAllCooldowns(Player player, String message) {
		for (String a : BoosConfigManager.getCooldowns(player)) {
			int cooldownTime = BoosConfigManager.getCoolDown(a, player);
			coolDown(player, a, message, cooldownTime);
		}

	}

}
