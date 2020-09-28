package me.hulk.amongus.objects;

import javafx.util.Pair;
import me.hulk.amongus.AmongUs;
import me.hulk.amongus.enums.PlayerColors;
import org.bukkit.Location;

public class GameMap {

    private GameVent[] ventLocations;
    private SpawnLocation[] spawnLocations;
    private Location mapCenter;
    private Location emergencyMeetingButton;
    private Location lobbyLocation;

    public GameMap(GameVent[] vents, SpawnLocation[] spawns, Location mapCenter, Location emergencyMeetingButton, Location lobbyLocation) {
        this.ventLocations = vents;
        this.spawnLocations = spawns;
        this.mapCenter = mapCenter;
        this.emergencyMeetingButton = emergencyMeetingButton;
        this.lobbyLocation = lobbyLocation;
    }

    public Pair<GameVent, Location> nearVent(Location location) {
        for (GameVent vent : ventLocations) {
            for (Location loc : vent.ventLocations) {
                if (location.distance(loc) < 1) return new Pair<>(vent, loc);
            }
        }
        return null;
    }

    public Location getSpawnLocationForColor(PlayerColors colors) {
        Location lastLocation = null;
        for (SpawnLocation spawnLocation : spawnLocations) {
            lastLocation = spawnLocation.getLocation();
            if (spawnLocation.getColor() == colors) return spawnLocation.getLocation();
        }

        AmongUs.throwError("No spawn location found for " + colors.name());
        return lastLocation;
    }

    public SpawnLocation[] getSpawnLocations() {
        return spawnLocations;
    }

    public Location getEmergencyMeetingButton() { return emergencyMeetingButton; }


}
