package me.rabrg.merch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * GrandExchange Price Class
 * 
 * @author Reid
 *
 */
public class GrandExchange {

	public static void main(final String[] args) throws IOException {
		final GrandExchange exchange = new GrandExchange();
		System.out.println(exchange.getOverallPrice(4151));
	}

	private static final String BASE = "https://api.rsbuddy.com/grandExchange?a=guidePrice&i=";

	/**
	 * Default Constructor
	 * 
	 */
	public GrandExchange() {

	}
	
	/**
	 * Gets the overall price of an item.
	 * 
	 * @param itemID
	 * @return itemPrice
	 * @throws IOException
	 */
	public int getOverallPrice(final int itemID) throws IOException {
		return parse(itemID,"overall");
	}

	
	/**
	 * Gets the buying price of an item.
	 * 
	 * @param itemID
	 * @return itemPrice
	 * @throws IOException
	 */
	public int getBuyingPrice(final int itemID) throws IOException {
		return parse(itemID,"buying");
	}
	
	/**
	 * Gets the selling price of an item.
	 * 
	 * @param itemID
	 * @return itemPrice
	 * @throws IOException
	 */
	public int getSellingPrice(final int itemID) throws IOException {
		return parse(itemID,"selling");
	}


	/**
	 * Retrieves the price of an item.
	 * 
	 * @param itemID
	 * @return itemPrice
	 * @throws IOException
	 */
	private int parse(final int itemID, String choice) throws IOException {
		final URL url = new URL(BASE + itemID);
		BufferedReader file = new BufferedReader(new InputStreamReader(url.openStream()));
		String line;
		String price = null;
		while ((line = file.readLine()) != null) {
			if (line.contains("{")) {
				price = (line).trim();
			}
		}
		if (choice.equals("buying")){
			price = price.substring(price.indexOf(",") + 10, nthOccurrence(price, ',', 1)).trim();
		} else if(choice.equals("selling")) {
			price = price.substring(nthOccurrence(price, ',', 2)  + 11 , price.indexOf("sellingQuantity") - 2).trim();
		} else {
			price = price.substring(price.indexOf(":") + 1, price.indexOf(",")).trim();
		}
		file.close();
		return Integer.parseInt(price);
	}

	private int nthOccurrence(String str, char c, int n) {
		int pos = str.indexOf(c, 0);
		while (n-- > 0 && pos != -1)
			pos = str.indexOf(c, pos + 1);
		return pos;
	}

}