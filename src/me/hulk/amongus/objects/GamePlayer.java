package me.hulk.amongus.objects;

import me.hulk.amongus.AmongUs;
import me.hulk.amongus.gametasks.Task;
import me.hulk.amongus.enums.PlayerColors;
import me.hulk.amongus.enums.PlayerRole;
import me.hulk.amongus.gui.GUIItem;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GamePlayer {

    private Player player;
    private Task[] tasks;
    private Game game;
    private PlayerRole role;
    private PlayerColors color;

    public int killCooldown;
    private boolean isVented;

    public GamePlayer(Player player, Game game, PlayerRole role) {
        this.player = player;
        this.game = game;
        this.role = role;
        color = game.getColor();
        killCooldown = game.getSettings().getKillCooldown();
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
        isVented = true;
        player.getInventory().setItem(5, new ItemStack(Material.GREEN_CONCRETE));
        player.getInventory().setItem(6, new ItemStack(Material.REDSTONE));
        player.getInventory().setItem(7, new ItemStack(Material.RED_CONCRETE));
        player.setWalkSpeed(0);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0));
        player.teleport(game.getMap().nearVent(player.getLocation()).getValue());
    }

    public void nextVent(boolean forward) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 8, 0));
        Location loc = game.getMap().nearVent(player.getLocation()).getValue();
        player.teleport(game.getMap().nearVent(loc).getKey().getNextVent(loc, forward));
    }

    public void exitEvent() {
        isVented = false;
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.getInventory().setItem(5, new ItemStack(Material.AIR));
        player.getInventory().setItem(6, new ItemStack(Material.AIR));
        player.getInventory().setItem(7, new ItemStack(Material.AIR));
        player.setWalkSpeed(game.getSettings().getWalkSpeed());
    }

    public boolean isVented() { return this.isVented; }

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
