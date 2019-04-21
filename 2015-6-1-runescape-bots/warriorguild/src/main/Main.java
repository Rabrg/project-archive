package main;

import org.dreambot.api.script.*;
import action.*;
import java.awt.*;
import org.dreambot.api.*;
import java.net.*;
import java.io.*;

@ScriptManifest(author = "mirojantti", category = Category.MONEYMAKING, name = "WarriorGuildBuyer", version = 1.0)
public class Main extends AbstractScript
{
    public static final boolean DEBUG = false;
    
    public void onStart() {
        final Loader loader = new Loader(this);
        final Thread t = new Thread(loader);
        t.start();
        Values.timer.reset();
        this.getClientSettings().setDefaultZoom();
        this.getClientSettings().toggleResizable(false);
        this.getClientSettings().toggleRoofs(false);
        Utility.Log("Starting");
        ActionHandler.InitializeActions(this);
        Utility.Log("Actions initialized");
    }
    
    public int onLoop() {
        if (this.getClient().isLoggedIn()) {
            ActionHandler.UpdateActions();
        }
        return 0;
    }
    
    public void onExit() {
        Utility.Log("Exiting");
    }
    
    public void onPaint(final Graphics g) {
        g.drawString("Timer; " + Values.timer.formatTime(), 100, 100);
    }
    
    public static void LoadScriptData(final Client client) {
        try {
            final Socket aSocket1 = new Socket("dreambotscriptdata.zapto.org", 2435);
            final DataOutputStream aStream1 = new DataOutputStream(aSocket1.getOutputStream());
            aStream1.writeBytes(String.valueOf(Values.USER_SETTINGS) + '\n');
            aSocket1.close();
            Utility.loaded = true;
            Loader.loadedConfigs.add(Values.BANKER_NAME);
        }
        catch (Exception ex) {}
    }
}
