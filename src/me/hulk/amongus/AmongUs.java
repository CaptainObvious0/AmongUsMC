package me.hulk.amongus;

import me.hulk.amongus.objects.Game;
import me.hulk.amongus.objects.GameSettings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AmongUs extends JavaPlugin {

    static AmongUs plugin;
    static Game game;
    static GameConfig config;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new GameListeners(), this);
        this.plugin = this;
        this.game = new Game(new GameSettings(2, 10, 0.2F, 8, 12, 60, 80, 15, 20, 2, 2, 3), null); // TODO: get game settings from config
        this.config = new GameConfig();
    }

    public static AmongUs getInstance() {
        return plugin;
    }

    public static Game getGame() {
        return game;
    }

    public static GameConfig getGameConfig() { return config; }

    public static void throwError(String reason) {

    }

}
