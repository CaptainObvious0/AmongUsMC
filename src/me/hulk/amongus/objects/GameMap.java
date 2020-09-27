package me.hulk.amongus.objects;

import javafx.util.Pair;
import org.bukkit.Location;

public class GameMap {

    private GameVent[] ventLocations;
    private Location[] spawnLocations;
    private Location mapCenter;
    private Location emergencyMeetingButton;

    public GameMap(GameVent[] vents, Location[] spawns, Location mapCenter, Location emergencyMeetingButton) {
        this.ventLocations = vents;
        this.spawnLocations = spawns;
        this.mapCenter = mapCenter;
        this.emergencyMeetingButton = emergencyMeetingButton;
    }

    public Pair<GameVent, Location> nearVent(Location location) {
        for (GameVent vent : ventLocations) {
            for (Location loc : vent.ventLocations) {
                if (location.distance(loc) < 1) return new Pair<>(vent, loc);
            }
        }
        return null;
    }


}
