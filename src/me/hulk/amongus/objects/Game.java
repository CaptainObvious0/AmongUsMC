package me.hulk.amongus.objects;

import me.hulk.amongus.AmongUs;
import me.hulk.amongus.GameStatus;
import me.hulk.amongus.PlayerRole;
import me.hulk.amongus.events.GameEndEvent;
import me.hulk.amongus.tasks.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private HashMap<Player, GamePlayer> playersInGame;
    private int hideTask;
    private GameSettings settings;
    private ArrayList<Player> playersInLobby;
    private GameStatus status;
    private GameMap map;
    private int alivePlayers;
    private int impostersAlive;

    public Game(GameSettings settings, GameMap map) {
        this.settings = settings;
        this.playersInLobby = new ArrayList<>();
        this.status = GameStatus.WAITING;
        this.map = map;
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

}
