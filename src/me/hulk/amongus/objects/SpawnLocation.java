package me.hulk.amongus.objects;

import me.hulk.amongus.enums.PlayerColors;
import org.bukkit.Location;

public class SpawnLocation {

    PlayerColors color;
    Location location;

    public SpawnLocation(PlayerColors color, Location location) {
        this.color = color;
        this.location = location;
    }

    public PlayerColors getColor() {
        return color;
    }

    public Location getLocation() {
        return location;
    }

}
