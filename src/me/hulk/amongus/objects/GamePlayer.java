package me.hulk.amongus.objects;

import me.hulk.amongus.AmongUs;
import me.hulk.amongus.GameTasks.Task;
import me.hulk.amongus.enums.PlayerColors;
import me.hulk.amongus.enums.PlayerRole;
import me.hulk.amongus.gui.GUIItem;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class GamePlayer {

    private Player player;
    private Task[] tasks;
    private Game game;
    private PlayerRole role;
    private PlayerColors color;
    public GamePlayer(Player player, Game game, PlayerRole role) {
        this.player = player;
        this.game = game;
        this.role = role;
        color = game.getColor();
    }

    public void generateTasks() {
        // TODO - Add tasks to players scoreboard
    }

    public void startGame() {
        String role = getRole().getTitle();
        generateTasks();
        player.sendTitle(ChatColor.translateAlternateColorCodes('&', role), ChatColor.translateAlternateColorCodes('&', "There are &c" + game.getSettings().getImposters() + " &famong us"), 30, 50, 15);
        Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), () -> {
            player.setWalkSpeed(game.getSettings().getWalkSpeed());
        }, 100);

        player.getInventory().setItem(0, GUIItem.createItem(Material.BLAZE_ROD, "&eReport Tool"));
        if (this.role == PlayerRole.CREWMATE) {
            player.getInventory().setItem(2, GUIItem.createItem(Material.IRON_SWORD, "&cKill"));
            player.getInventory().setItem(4, GUIItem.createItem(Material.STRING, "&bVent Tool"));
        }

    }

    public void killPlayer() {
        role = PlayerRole.DEAD;
        player.setGameMode(GameMode.SPECTATOR);
        game.decreaseAlivePlayers(this);
        game.addDeadPlayer(this, player.getLocation());
        // create dead body at players location
    }

    public void ejectPlayer() {
        role = PlayerRole.DEAD;
        player.setGameMode(GameMode.SPECTATOR);
        game.decreaseAlivePlayers(this);
    }

    public void imposterVent() {
        // imposter vent
    }

    public void nextVent() {

    }

    public Player getPlayer() {
        return this.player;
    }

    public void setRole(PlayerRole role) {
        this.role = role;
    }

    public PlayerRole getRole() {
        return role;
    }

    public double getPlayerVision() {
        if (role == PlayerRole.CREWMATE) return game.getSettings().getCrewVision();
        return game.getSettings().getImposterVision();
    }

    public PlayerColors getColor() {
        return color;
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
