package me.hulk.amongus;

import me.hulk.amongus.events.GameEndEvent;
import me.hulk.amongus.events.GameReportBodyEvent;
import me.hulk.amongus.events.GameStartEvent;
import me.hulk.amongus.objects.Game;
import me.hulk.amongus.objects.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;

public class GameListeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Teleport player to lobby
        // Check if lobby is full, if so start the countdown and create a new game
        Game game = AmongUs.getGame();
        Player player = event.getPlayer();
        if (game.getStatus() != GameStatus.WAITING) {
            // message player that they are spectator and set invisible & in spectator mode
            player.setGameMode(GameMode.SPECTATOR);
            game.addGamePlayer(new GamePlayer(player, game, PlayerRole.SPECTATOR));
        } else {
            game.addPlayerInLobby(player);

            if (game.getSettings().getSize() <= game.getPlayersInLobby().size()) {
                // announce game is starting and start countdown
                game.setGameStatus(GameStatus.STARTING);

                Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), () -> {
                    game.gameStart();
                }, 100);

            }

        }
    }

    @EventHandler
    public void onKillPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || event.getEntity() instanceof Player) return;

        Player player = (Player) event.getDamager();
        Player damaged = (Player) event.getEntity();

        GamePlayer gamePlayer = AmongUs.getGame().getGamePlayer(player);
        GamePlayer damagedPlayer = AmongUs.getGame().getGamePlayer(damaged);
        if (AmongUs.getGame().getStatus() == GameStatus.PLAYING && gamePlayer != null && damagedPlayer != null && gamePlayer.getRole() == PlayerRole.IMPOSTER &&
                player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD) {
            if (damagedPlayer.getRole() == PlayerRole.CREWMATE) damagedPlayer.killPlayer();
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        if (event.isSneaking()) {
            GamePlayer player = AmongUs.getGame().getGamePlayer(event.getPlayer());
            if (player != null && player.getRole() == PlayerRole.IMPOSTER && AmongUs.getGame().getMap().nearVent(event.getPlayer().getLocation())) {
                player.imposterVent();
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Game game = AmongUs.getGame();
        GamePlayer gamePlayer = game.getGamePlayer(event.getPlayer());

        if (game.getStatus() == GameStatus.PLAYING && gamePlayer != null && (gamePlayer.getRole() == PlayerRole.CREWMATE || gamePlayer.getRole() == PlayerRole.IMPOSTER)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {

    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        event.getGame().gameEndEvent();
    }

    @EventHandler
    public void onReportBody(GameReportBodyEvent event) {
        event.getGame().onReportBody(event.getReporter(), event.getDeadPlayer());
    }

}
