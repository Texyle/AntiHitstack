package me.texyle.antihitstacking;

import org.bukkit.plugin.java.JavaPlugin;

public class AntiHitstacking extends JavaPlugin {
    private static AntiHitstacking instance;

    public AntiHitstacking() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getCommand("hitstack").setExecutor(new MainCommand());
        getCommand("hitstack").setTabCompleter(new TabCompleter());
        getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
    }

    public static AntiHitstacking getInstance() {
        return instance;
    }
}
