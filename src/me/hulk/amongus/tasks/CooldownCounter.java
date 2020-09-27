package me.hulk.amongus.tasks;

import me.hulk.amongus.enums.GameStatus;
import me.hulk.amongus.objects.Game;

public class CooldownCounter implements Runnable {

    Game game;

    public CooldownCounter(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        if (game.getStatus() == GameStatus.PLAYING) {
            game.updateCooldown();
            game.updateKillCooldown();
        }
    }
}
