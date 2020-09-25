package me.hulk.amongus.objects;

import org.bukkit.Location;

public class GameMap {

    private Location[] ventLocations;
    private Location[] spawnLocations;

    public boolean nearVent(Location location) {
        for (Location vent : ventLocations) {
            if (location.distance(vent) < 1) return true;
        }
        return false;
    }


}
