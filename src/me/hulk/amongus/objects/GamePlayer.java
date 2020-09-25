package me.hulk.amongus.objects;

import me.hulk.amongus.AmongUs;
import me.hulk.amongus.GameStatus;
import me.hulk.amongus.GameTasks.Task;
import me.hulk.amongus.PlayerRole;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GamePlayer {

    private Player player;
    private Task[] tasks;
    private Game game;
    private PlayerRole role;
    private boolean alive;

    public GamePlayer(Player player, Game game, PlayerRole role) {
        this.player = player;
        this.alive = game.getStatus() == GameStatus.WAITING ? true : false;
        this.game = game;
        this.role = role;

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
    }

    public void killPlayer() {
        role = PlayerRole.DEAD;
        player.setGameMode(GameMode.SPECTATOR);
        game.decreaseAlivePlayers();
        // create dead body at players location
    }

    public void giveItems() {
        player.getInventory().setItem(0, new ItemStack(Material.BLAZE_ROD));
        if (role == PlayerRole.CREWMATE) {
            player.getInventory().setItem(2, new ItemStack(Material.IRON_SWORD));
            player.getInventory().setItem(4, new ItemStack(Material.STRING));
        }
    }

    public void imposterVent() {
        // imposter vent
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

}
