package me.rabrg.skywars.utilities;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;

public class CraftBukkitUtil {

	public static final String BUKKIT_PACKAGE;
	public static final String MINECRAFT_PACKAGE;

	public static Object getHandle(@Nonnull final Object target) {
		return getMethod(target, "getHandle", new Class<?>[0], new Object[0]);
	}

	public static Object getMethod(@Nonnull final Object target, @Nonnull final String methodName) {
		return getMethod(target, methodName, new Class<?>[0], new Object[0]);
	}

	public static Object getMethod(@Nonnull final Object target, @Nonnull final String methodName, @Nonnull final Class<?>[] paramTypes, @Nonnull final Object[] params) {
		Preconditions.checkNotNull(target, "Target is null");
		Preconditions.checkNotNull(methodName, "Method name is null");

		Class<?> currentClazz = target.getClass();
		Object returnValue = null;

		do {
			try {
				final Method method = currentClazz.getDeclaredMethod(methodName, paramTypes);
				returnValue = method.invoke(target, params);

			} catch (final Exception exception) {
				currentClazz = currentClazz.getSuperclass();
			}

		} while (currentClazz != null && currentClazz.getSuperclass() != null && returnValue == null);

		return returnValue;
	}

	public static void forceRespawn(final Player player) {
		Preconditions.checkNotNull(player, "Player is null");

		if (!player.isDead()) {
			return;
		}

		final Object playerHandle = getHandle(player);
		if (playerHandle == null) {
			return;
		}

		Object serverHandle = getHandle(Bukkit.getServer());
		if (serverHandle == null) {
			return;
		}

		serverHandle = getMethod(serverHandle, "getServer", new Class<?>[0], new Object[0]);
		if (serverHandle == null) {
			return;
		}

		final Object playerListHandle = getMethod(serverHandle, "getPlayerList", new Class<?>[0], new Object[0]);
		if (playerListHandle == null) {
			return;
		}

		getMethod(playerListHandle, "moveToWorld", new Class<?>[] { playerHandle.getClass(), int.class, boolean.class }, new Object[] { playerHandle, 0, false });
	}

	public static boolean isRunning() {
		final Object minecraftServer = getMethod(Bukkit.getServer(), "getServer");
		if (minecraftServer == null) {
			return false;
		}

		final Object isRunning = getMethod(minecraftServer, "isRunning");
		return isRunning instanceof Boolean && (Boolean) isRunning;
	}

	static {
		final String packageName = Bukkit.getServer().getClass().getPackage().getName();
		final String bukkitVersion = packageName.substring(packageName.lastIndexOf('.') + 1);

		BUKKIT_PACKAGE = "org.bukkit.craftbukkit." + bukkitVersion;
		MINECRAFT_PACKAGE = "net.minecraft.server." + bukkitVersion;
	}
}
