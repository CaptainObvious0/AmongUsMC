package me.hulk.amongus.GameTasks;

import me.hulk.amongus.enums.GameStatus;
import me.hulk.amongus.objects.Game;

public class GameTimer implements Runnable {

    Game game;

    public GameTimer(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        if (game.getStatus() == GameStatus.DISCUSSION) {
            game.discussionTimer--;
            if (game.discussionTimer <= 0) game.changeStatus();
        }

        if (game.getStatus() == GameStatus.VOTING) {
            game.votingTimer--;
            if (game.votingTimer <= 0) game.changeStatus();
        }

    }

}
