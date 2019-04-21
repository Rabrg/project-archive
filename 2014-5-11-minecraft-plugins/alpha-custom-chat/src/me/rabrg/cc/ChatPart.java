package me.rabrg.cc;

import java.util.Arrays;
import java.util.List;

import me.spoony.JSONChatLib.JSONChatClickEventType;
import me.spoony.JSONChatLib.JSONChatColor;
import me.spoony.JSONChatLib.JSONChatFormat;
import me.spoony.JSONChatLib.JSONChatHoverEventType;

public class ChatPart {
	String text;
	JSONChatColor color;
	List<JSONChatFormat> format;
	JSONChatHoverEventType hovertype;
	String hoverdata;
	JSONChatClickEventType clicktype;
	String clickdata;

	public ChatPart(final String text) {
		this.text = text;
		this.color = JSONChatColor.WHITE;
		this.format = null;
		this.hovertype = null;
		this.clicktype = null;
	}

	public ChatPart(final String text, final JSONChatColor color) {
		this.text = text;
		this.color = color;
		this.format = null;
		this.hovertype = null;
		this.clicktype = null;
	}

	public ChatPart(final String text, final JSONChatFormat[] format) {
		this.text = text;
		this.color = JSONChatColor.WHITE;
		this.format = Arrays.asList(format);
		this.hovertype = null;
		this.clicktype = null;
	}

	public ChatPart(final String text, final JSONChatColor color,
			final JSONChatFormat[] format) {
		this.text = text;
		this.color = color;
		this.format = Arrays.asList(format);
		this.hovertype = null;
		this.clicktype = null;
	}

	public ChatPart setHoverEvent(final JSONChatHoverEventType type,
			final String data) {
		this.hovertype = type;
		this.hoverdata = data;
		return this;
	}

	public ChatPart setClickEvent(final JSONChatClickEventType type,
			final String data) {
		this.clicktype = type;
		this.clickdata = data;
		return this;
	}

	public String getText() {
		return this.text;
	}

	public JSONChatColor getColor() {
		return this.color;
	}

	public List<JSONChatFormat> getFormat() {
		return this.format;
	}

	public JSONChatHoverEventType getHovertype() {
		return this.hovertype;
	}

	public String getHoverdata() {
		return this.hoverdata;
	}

	public JSONChatClickEventType getClicktype() {
		return this.clicktype;
	}

	public String getClickdata() {
		return this.clickdata;
	}
}
