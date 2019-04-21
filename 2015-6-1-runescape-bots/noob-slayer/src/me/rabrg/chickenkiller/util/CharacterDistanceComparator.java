package me.rabrg.chickenkiller.util;

import org.dreambot.api.wrappers.interactive.Character;

import java.util.Comparator;

public class CharacterDistanceComparator implements Comparator<Character> {

    @Override
    public int compare(Character o1, Character o2) {
        if (o1.distance() > o2.distance())
            return 1;
        else if (o1.distance() < o2.distance())
            return -1;
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == getClass();
    }
}
