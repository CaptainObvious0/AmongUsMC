package me.hulk.amongus.events;

import me.hulk.amongus.objects.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends Event {

    Game game;
    String endReason;

    public GameEndEvent(Game game, String endReason) {
        this.endReason = endReason;
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

}
