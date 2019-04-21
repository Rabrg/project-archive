package me.rabrg.merch;

public final class Data implements Comparable<Data>{

	public String name;
	public double alch;
	public int buy;
	@Override
	public int compareTo(Data o) {
		if (buy - alch < o.buy - o.alch)
			return 1;
		else if (buy - alch == o.buy - o.alch)
			return 0;
		return -1;
	}
}
