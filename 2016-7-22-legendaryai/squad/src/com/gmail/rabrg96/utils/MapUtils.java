package com.gmail.rabrg96.utils;

import java.util.*;

public class MapUtils {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map) {
        final List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(final Map.Entry<K, V> o1, final Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue()) * -1;
            }
        });

        final Map<K, V> result = new LinkedHashMap<K, V>();
        for (final Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
