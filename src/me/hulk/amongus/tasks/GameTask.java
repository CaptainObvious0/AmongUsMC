package me.hulk.amongus.tasks;

import me.hulk.amongus.AmongUs;
import me.hulk.amongus.objects.Game;
import me.hulk.amongus.objects.GamePlayer;

public class GameTask implements Runnable {

    Game game;

    public GameTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        for (GamePlayer player : game.getPlayersInGame().values()) {
            for (GamePlayer player1 : game.getPlayersInGame().values()) {
                if (player.getPlayer().getLocation().distance(player1.getPlayer().getLocation()) > player.getPlayerVision()) {
                    player.getPlayer().hidePlayer(AmongUs.getInstance(), player1.getPlayer());
                } else {
                    if (!player1.isVented()) player.getPlayer().showPlayer(AmongUs.getInstance(), player1.getPlayer());
                }
            }
        }
    }


}
