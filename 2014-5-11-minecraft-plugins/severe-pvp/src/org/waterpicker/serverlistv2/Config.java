package org.waterpicker.serverlistv2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config
{
  private FileConfiguration customConfig = null;
  private File customConfigFile = null;
  private Plugin instance;
  
  public Config(Plugin plugin)
  {
    this.instance = plugin;
  }
  
  public void reloadConfig()
  {
    if (this.customConfigFile == null) {
      this.customConfigFile = new File(this.instance.getDataFolder(), "message.yml");
    }
    this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
    

    InputStream defConfigStream = this.instance.getResource("message.yml");
    if (defConfigStream != null)
    {
      YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
      this.customConfig.setDefaults(defConfig);
    }
  }
  
  public FileConfiguration getConfig()
  {
    if (this.customConfig == null) {
      reloadConfig();
    }
    return this.customConfig;
  }
  
  public void saveConfig()
  {
    if ((this.customConfig == null) || (this.customConfigFile == null)) {
      return;
    }
    try
    {
      getConfig().save(this.customConfigFile);
    }
    catch (IOException ex)
    {
      this.instance.getLogger().log(Level.SEVERE, "Could not save config to " + this.customConfigFile, ex);
    }
  }
  
  public void saveDefaultConfig()
  {
    if (this.customConfigFile == null) {
      this.customConfigFile = new File(this.instance.getDataFolder(), "message.yml");
    }
    if (!this.customConfigFile.exists()) {
      this.instance.saveResource("customConfig.yml", false);
    }
  }
}
