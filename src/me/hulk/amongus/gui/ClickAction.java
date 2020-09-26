package me.hulk.amongus.gui;

import me.hulk.amongus.objects.GamePlayer;

public class ClickAction {

    Runnable action;
    GamePlayer player;

    public ClickAction(Runnable action) {
        this.action = action;
    }

    public Runnable getAction() {
        return action;
    }

    public void runAction() {
        action.run();
    }

    public void setPlayer(GamePlayer player) {
        this.player = player;
    }

    public GamePlayer getPlayer() {
        return player;
    }

}
