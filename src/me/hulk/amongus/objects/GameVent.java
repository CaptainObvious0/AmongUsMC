package me.hulk.amongus.objects;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class GameVent {

    public List<Location> ventLocations;

    public GameVent(Location[] locations) {

        ventLocations = new ArrayList<>();

        for (Location location : locations) {
            ventLocations.add(location);
        }
    }
    public Location getNextVent(Location location, boolean forward) {

        int current = ventLocations.indexOf(location);

        if (forward) {
            if (ventLocations.size() == current) return ventLocations.get(0);
            return ventLocations.get(current + 1);
        } else {
            if (current == 0) return ventLocations.get(ventLocations.size());
            return ventLocations.get(current - 1);
        }

    }


}
