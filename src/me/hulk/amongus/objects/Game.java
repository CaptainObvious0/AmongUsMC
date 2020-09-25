package me.hulk.amongus.objects;

import me.hulk.amongus.AmongUs;
import me.hulk.amongus.GameTasks.GameTimer;
import me.hulk.amongus.enums.GameStatus;
import me.hulk.amongus.enums.PlayerColors;
import me.hulk.amongus.enums.PlayerRole;
import me.hulk.amongus.events.GameEndEvent;
import me.hulk.amongus.tasks.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Game {

    private HashMap<Player, GamePlayer> playersInGame;
    private List<PlayerColors> colors;
    private HashMap<GamePlayer, Location> deadPlayers = new HashMap<>();
    private int hideTask;
    private GameSettings settings;
    private ArrayList<Player> playersInLobby;
    private GameStatus status;
    private GameMap map;
    private int alivePlayers;
    private int impostersAlive;
    private GameVote gameVote;

    // Voting
    private HashMap<GamePlayer, PlayerColors> playerVotes = new HashMap<>();
    int gameTimer;

    public int discussionTimer;
    public int votingTimer;

    public Game(GameSettings settings, GameMap map) {
        this.settings = settings;
        this.playersInLobby = new ArrayList<>();
        this.status = GameStatus.WAITING;
        this.map = map;
        colors = Arrays.asList(PlayerColors.values());
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

        // Teleport player to game world/arena

    }

    public void onReportBody(Player reporter, Player deadPlayer) {
        status = GameStatus.DISCUSSION;
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

    public void decreaseAlivePlayers() {
        alivePlayers--;
        if (alivePlayers <= impostersAlive) {
            status = GameStatus.END;
            Bukkit.getPluginManager().callEvent(new GameEndEvent(this));
            // Imposter win
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
        // Teleport all players to discussion table
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&9AmongUs> " + reporter.getColor().name() + " &freported " + deadPlayer.getColor().name() + " &fas dead!"));
        gameTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(AmongUs.getInstance(), new GameTimer(this), 40L, 20L);
        gameVote = new GameVote();
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
            // Tally votes
            HashMap<PlayerColors, String> votes = new HashMap<>();
            for (Map.Entry<GamePlayer, PlayerColors> vote : playerVotes.entrySet()) {
                if (votes.get(vote.getValue()) == null) {
                    votes.put(vote.getValue(), color(vote.getKey().getColor().getTitle() + vote.getKey().getPlayer().getName()));
                } else {
                    String oldString = votes.get(vote.getValue());
                    votes.put(vote.getValue(), oldString + ", " + color(vote.getKey().getColor().getTitle() + vote.getKey().getPlayer().getName()));
                }
            }

            Bukkit.broadcastMessage("TOTAL VOTES:");
            GamePlayer playerEjected;
            int numVotes = 0;
            for (Map.Entry<PlayerColors, String> playerVote : votes.entrySet()) {
                Bukkit.broadcastMessage(color(playerVote.getKey().getTitle() + getPlayerByColor(playerVote.getKey()).getPlayer().getName() + " &f[" + playerVote.getValue() + " &f]"));
                if (playerVote.getValue().split(",").length + 1 > numVotes) {
                    playerEjected = getPlayerByColor(playerVote.getKey());
                }
            }

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
