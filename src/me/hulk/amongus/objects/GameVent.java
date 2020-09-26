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
    public Location getNextVent(int vent) {

        if (vent == ventLocations.size()) {
            return ventLocations.get(0);
        }

        return ventLocations.get(vent + 1);

    }


}
