package me.rabrg.skywars.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface CommandPermissions {

	public abstract String[] value();
}
