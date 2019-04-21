package me.rabrg.merch;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import me.rabrg.merch.model.ItemHistory;
import me.rabrg.merch.model.ItemName;
import me.rabrg.merch.model.ItemPrice;
import me.rabrg.merch.model.ItemPriceDif;

public final class RabrgMerch {

	private static final Gson gson = new Gson();

	public static void main(final String[] args) throws Exception {
		// getAlchData();
		printAlchData(false);
	}

	public static void findBest() throws Exception {
		final List<ItemPriceDif> dif7s = new ArrayList<ItemPriceDif>();
		final List<ItemPriceDif> dif1s = new ArrayList<ItemPriceDif>();
		
		for (final Entry<Integer, ItemName> item : getItemNames().entrySet()) {
			try {
				final ItemHistory[] history = getItemHistory(item.getKey());
				if (history[1].getOverallCompleted() > 200) {
					final ItemPriceDif dif7 = new ItemPriceDif();
					dif7.days = 7;
					dif7.name = item.getValue().getName();
					if (history[history.length - 1 - 7].getOverallPrice() - history[history.length - 1].getOverallPrice() > 0) {
						dif7.difference = -1 * (history[history.length - 1 - 7].getOverallPrice() / history[history.length - 1].getOverallPrice());
					}
					dif7.difference = history[history.length - 1 - 7].getOverallPrice() / history[history.length - 1].getOverallPrice();
					dif7s.add(dif7);
					final ItemPriceDif dif1 = new ItemPriceDif();
					dif1.days = 1;
					dif1.name = item.getValue().getName();
					dif1.difference = history[history.length - 1 - 1].getOverallPrice() / history[history.length - 1].getOverallPrice();
					dif1s.add(dif1);
				}
			} catch (final Exception e) {
				System.out.println("FAILED: " + item.getValue().getName());
				continue;
			}
		}
		
		Collections.sort(dif7s);
		Collections.sort(dif1s);
		
		System.out.println("--------7 DAYS--------");
		for (final ItemPriceDif dif : dif7s) {
			System.out.println(dif);
		}
		System.out.println("--------1 DAYS--------");
		for (final ItemPriceDif dif : dif1s) {
			System.out.println(dif);
		}
	}

	public static void printAlchData(final boolean gloves) throws IOException {
		final List<Data> datas = getData(gloves);
		Collections.sort(datas);
		for (final Data data : datas)
			System.out.println(data.name + " " + data.buy);
	}

	public static void getAlchData() {
		try {
			for (final Entry<Integer, ItemName> entry : getItemNames().entrySet()) {
				System.out.println(entry.getValue().getName() + "_" + entry.getValue().getStore() * 0.6  + "_" + getItemPrice(entry.getKey()).buying);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	public static List<Data> getData(final boolean gloves) throws IOException {
		final List<String> rawData = Files.readAllLines(Paths.get("data.txt"), Charset.defaultCharset());
		List<Data> datas = new ArrayList<Data>();
		for (final String raw : rawData) {
			final Data data = new Data();
			final String[] rawSplit = raw.split("_");
			data.name = rawSplit[0];
			data.alch = gloves ? Double.parseDouble(rawSplit[1]) * 1.166666666666667 : Double.parseDouble(rawSplit[1]);
			data.buy = Integer.parseInt(rawSplit[2]);
			if (data.buy == 0 || data.alch == 0 || data.buy > 25000)
				continue;
			datas.add(data);
		};
		return datas;
	}

	public static ItemPrice getItemPrice(final int id) throws Exception {
		final URL url = new URL("http://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + id);
		return gson.fromJson(new InputStreamReader(url.openStream()), ItemPrice.class);
	}

	public static ItemHistory[] getItemHistory(final int id) throws Exception {
		final URL url = new URL("http://api.rsbuddy.com/grandExchange?a=graph&i=" + id);
		return gson.fromJson(new InputStreamReader(url.openStream()), ItemHistory[].class);
	}

	public static Map<Integer, ItemName> getItemNames() throws IOException {
		final URL url = new URL("https://rsbuddy.com/exchange/names.json");
		return gson.fromJson(new InputStreamReader(url.openStream()), new TypeToken<Map<Integer, ItemName>>(){}.getType());
	}
}
