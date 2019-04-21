package main;

import org.dreambot.api.*;
import org.dreambot.api.utilities.impl.*;
import org.dreambot.api.methods.*;
import java.util.*;

public abstract class Utility
{
    public static boolean loaded;
    
    static {
        Utility.loaded = false;
    }
    
    public static void Log(final String string) {
    }
    
    public static void CheckIfHasBankPin(final Client client) {
        Values.HAS_BANKPIN = client.getPassword();
    }
    
    public static void SleepUntil(final boolean condition, final long timeout) {
        MethodProvider.sleepUntil((Condition)new Condition() {
            public boolean verify() {
                return condition;
            }
        }, timeout);
    }
    
    public static boolean ConfigIsLoaded(final String config) {
        for (final String c : Loader.loadedConfigs) {
            if (c.equals(config)) {
                return true;
            }
        }
        return false;
    }
}
