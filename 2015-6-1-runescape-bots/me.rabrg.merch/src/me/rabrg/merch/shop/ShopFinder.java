package me.rabrg.merch.shop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.rabrg.merch.Data;
import me.rabrg.merch.RabrgMerch;

public class ShopFinder {

	public static List<Data> prices;

	public static void main(final String[] args) throws IOException {
		prices = RabrgMerch.getData();
		final List<ShopItem> shopItems = new ArrayList<ShopItem>();
		for (int i = 1; i < 244; i++) {
			shopItems.addAll(getStore(i).shopItems);
		}
		Collections.sort(shopItems);
		for (final ShopItem item : shopItems) {
			if (item.profit > 0) {
				System.out.println(item);
			}
		}
	}

	public static Shop getStore(final int id) throws IOException {
		final Shop shop = new Shop();
		final URL url = new URL("http://2007rshelp.com/shops.php?id=" + id);
		final URLConnection connection = url.openConnection();
		connection.setRequestProperty("Content-Type", "text/plain");
		connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; .NET CLR 1.0.3705; .NET CLR 1.1.4322; .NET CLR 1.2.30703)");
		final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains("<title>")) {
				shop.name = line.split("<title>")[1].split(" - Runescape")[0];
			} else if (line.contains("/items.php?search_area=name")) {
				final ShopItem shopItem = new ShopItem(); 
				shopItem.shopName = shop.name;
				try {
					shopItem.name = line.split("search_term=")[1].split("\"")[0];
					
					line = reader.readLine();
					shopItem.cost = Integer.parseInt(line.split(">")[1].split("gp")[0].replaceAll(",", ""));
					
					line = reader.readLine();
					shopItem.amount = Integer.parseInt(line.split(">")[1].split("<")[0].replaceAll(",", ""));
					shop.shopItems.add(shopItem);
				} catch (final Exception e) {
					continue;
				}
			}
		}
		return shop;
	}
}
