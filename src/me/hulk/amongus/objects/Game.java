package me.hulk.amongus.objects;

import me.hulk.amongus.AmongUs;
import me.hulk.amongus.gui.GameGUI;
import me.hulk.amongus.tasks.CooldownCounter;
import me.hulk.amongus.tasks.GameTimer;
import me.hulk.amongus.enums.GameStatus;
import me.hulk.amongus.enums.PlayerColors;
import me.hulk.amongus.enums.PlayerRole;
import me.hulk.amongus.events.GameEndEvent;
import me.hulk.amongus.gui.GUIItem;
import me.hulk.amongus.tasks.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Game {

    private HashMap<Player, GamePlayer> playersInGame;
    private final ArrayList<Player> playersInLobby;
    private final List<PlayerColors> colors;
    private final HashMap<GamePlayer, Location> deadPlayers = new HashMap<>();

    private int hideTask;
    private int meetingTask;

    private final GameSettings settings;
    private GameStatus status;
    private final GameMap map;
    private int alivePlayers;
    private int impostersAlive;
    private GameVote gameVote;

    // Voting
    int gameTimer;
    GameGUI votingGUI;
    public int discussionTimer;
    public int votingTimer;

    int meetingCooldown;

    public Game(GameSettings settings, GameMap map) {
        this.settings = settings;
        this.playersInLobby = new ArrayList<>();
        this.status = GameStatus.WAITING;
        this.map = map;
        colors = Arrays.asList(PlayerColors.values());
        meetingCooldown = settings.getMeetingCooldown();
    }

    public void gameStart() {

        status = GameStatus.PLAYING;
        this.hideTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(AmongUs.getInstance(), new GameTask(this), 100, 40);
        HashMap<Player, GamePlayer> gamePlayers = new HashMap<>();
        int imposters = settings.getImposters();

        // Randomly assign roles to players and create GamePlayer
        while (imposters > 0) {
            for (Player player : playersInLobby) {
                if (Math.random() > 0.8) {
                    if (gamePlayers.containsKey(player) && gamePlayers.get(player).getRole() == PlayerRole.CREWMATE) {
                        gamePlayers.get(player).setRole(PlayerRole.IMPOSTER);
                        imposters--;
                    } else {
                        if (!gamePlayers.containsKey(player)) {
                            gamePlayers.put(player, new GamePlayer(player, this, PlayerRole.IMPOSTER));
                            imposters--;
                        }
                    }
                } else {
                    if (!gamePlayers.containsKey(player)) gamePlayers.put(player, new GamePlayer(player, this, PlayerRole.CREWMATE));
                }
            }

        }

        for (GamePlayer gamePlayer : gamePlayers.values()) {
            gamePlayer.startGame();
            gamePlayer.getPlayer().setWalkSpeed(0);
            gamePlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
        }

        this.playersInGame = gamePlayers;
        this.alivePlayers = gamePlayers.size();

        this.meetingTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(AmongUs.getInstance(), new CooldownCounter(this), 20, 20);

        // Teleport player to game world/arena

    }

    public void resetGame() {
        this.deadPlayers.clear();
        gameVote = null;
        // Teleport players
        status = GameStatus.PLAYING;
        Bukkit.getScheduler().cancelTask(meetingTask);
        this.meetingTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(AmongUs.getInstance(), new CooldownCounter(this), 20, 20);

        for (Player player : playersInGame.keySet()) {
            player.getInventory().setItem(8, new ItemStack(Material.AIR));
        }

    }

    public void addGamePlayer(GamePlayer player) {
        playersInGame.put(player.getPlayer(), player);
    }

    public HashMap<Player, GamePlayer> getPlayersInGame() {
        return playersInGame;
    }

    public void gameEndEvent() {
        Bukkit.getScheduler().cancelTask(hideTask);
    }

    public GameStatus getStatus() {
        return status;
    }

    public void addPlayerInLobby(Player player) {
        playersInLobby.add(player);
    }

    public ArrayList<Player> getPlayersInLobby() {
        return playersInLobby;
    }

    public GameSettings getSettings() {
        return settings;
    }

    public void setGameStatus(GameStatus status) {
        this.status = status;
    }

    public GameMap getMap() {
        return map;
    }

    public GamePlayer getGamePlayer(Player player) {
        return playersInGame.getOrDefault(player, null);
    }

    public GameGUI getVotingGUI() {
        return votingGUI;
    }

    public GameVote getGameVote() { return this.gameVote; }

    public void updateCooldown() {
        if (meetingCooldown > 0) meetingCooldown--;
    }

    public void updateKillCooldown() {
        for (GamePlayer player : playersInGame.values()) {
            if (player.getRole() == PlayerRole.IMPOSTER && player.killCooldown > 0) player.killCooldown--;
        }
    }

    public List<GamePlayer> getAlivePlayers() {
        List<GamePlayer> toReturn = new ArrayList<>();

        for (GamePlayer player : playersInGame.values()) {
            toReturn.add(player);
        }

        return toReturn;

    }

    public void decreaseAlivePlayers(GamePlayer player) {
        alivePlayers--;
        playersInGame.remove(player);

        if (player.getRole() == PlayerRole.IMPOSTER) impostersAlive--;

        // TODO - add paramter to game end event to say who won
        if (alivePlayers <= impostersAlive) {
            status = GameStatus.END;
            Bukkit.getPluginManager().callEvent(new GameEndEvent(this, "Crew wins!"));
            // Imposter win
        } else if (impostersAlive == 0) {
            status = GameStatus.END;
            Bukkit.getPluginManager().callEvent(new GameEndEvent(this, settings.getImposters() > 1 ? "Imposters win!" : "Imposter wins!"));
        }
    }

    public PlayerColors getColor() {
        PlayerColors toReturn = colors.get(0);
        colors.remove(0);
        return toReturn;
    }

    public void addDeadPlayer(GamePlayer player, Location location) {
        deadPlayers.put(player, location);
    }

    public void onDeadBodyReport(GamePlayer reporter, GamePlayer deadPlayer) {
        status = GameStatus.DISCUSSION;
        votingGUI = GameGUI.createVotingGUI();
        // Teleport all players to discussion table
        if (deadPlayer == null) {
            Bukkit.broadcastMessage(GUIItem.color("&9AmongUs> " + reporter.getColor().getTitle() + reporter.getPlayer().getName() + " &7has called an emergency meeting!"));
        } else {
            Bukkit.broadcastMessage(GUIItem.color("&9AmongUs> " + reporter.getColor().name() + " &freported " + deadPlayer.getColor().name() + " &fas dead!"));
        }
        gameTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(AmongUs.getInstance(), new GameTimer(this), 40L, 20L);
        gameVote = new GameVote(playersInGame.size(), this);

        for (Player player : playersInGame.keySet()) {
            player.getInventory().setItem(8, GUIItem.createItem(Material.CHEST, "&bPlayer Voting"));
        }

    }

    public GamePlayer checkForNearDead(Location location) {
        for (Map.Entry<GamePlayer, Location> locations : deadPlayers.entrySet()) {
            if (locations.getValue().distance(location) < 1.5) {
                return locations.getKey();
            }
        }

        return null;

    }

    public void changeStatus() {
        if (status == GameStatus.DISCUSSION) {
            status = GameStatus.VOTING;
        } else if (status == GameStatus.VOTING) {
            Bukkit.getScheduler().cancelTask(gameTimer);
            GameVote gVotes = gameVote;
            gVotes.printVotingResults();
            if (gVotes.getPlayerVoted() != null) {
                gVotes.getPlayerVoted().ejectPlayer();
            } else {
                // No one was ejected
            }
            resetGame();
        }
    }

    public void hideAllPlayers(Player player) {
        for (Player gPlayer : playersInGame.keySet()) {
            gPlayer.hidePlayer(AmongUs.getInstance(), player);
        }
    }

    public GamePlayer getPlayerByColor(PlayerColors color) {
        for (GamePlayer player : playersInGame.values()) {
            if (player.getColor() == color) return player;
        }

        return null;
    }

    private String color(String color) {
        return ChatColor.translateAlternateColorCodes('&', color);
    }

}
