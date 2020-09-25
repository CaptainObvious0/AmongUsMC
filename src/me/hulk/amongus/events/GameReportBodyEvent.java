package me.hulk.amongus.events;

import me.hulk.amongus.objects.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameReportBodyEvent extends Event {

    private Player reporter;
    private Player deadPlayer;
    private Game game;

    public GameReportBodyEvent(Game game, Player reporter, Player deadPlayer) {
        this.reporter = reporter;
        this.deadPlayer = deadPlayer;
        this.game = game;
    }

    public Player getReporter() {
        return reporter;
    }

    public Player getDeadPlayer() {
        return deadPlayer;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
