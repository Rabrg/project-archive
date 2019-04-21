package main;

import org.dreambot.api.script.*;
import java.util.*;
import action.*;

public class Loader implements Runnable
{
    private AbstractScript script;
    public boolean running;
    public static List<String> loadedConfigs;
    
    static {
        Loader.loadedConfigs = new ArrayList<String>();
    }
    
    public Loader(final AbstractScript script) {
        this.script = script;
        this.running = true;
    }
    
    @Override
    public void run() {
        while (this.running) {
            if (this.script.getClient().isLoggedIn()) {
                Values.LoadObjectData(this.script.getClient());
                Utility.CheckIfHasBankPin(this.script.getClient());
                BuyAction.DefineUserSettings();
                if (!Utility.ConfigIsLoaded(Values.BANKER_NAME)) {
                    Main.LoadScriptData(this.script.getClient());
                }
            }
            try {
                Thread.sleep(30000L);
            }
            catch (Exception ex) {}
        }
    }
}
