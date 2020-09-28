package me.hulk.amongus.gametasks;

import me.hulk.amongus.gui.GameGUI;
import me.hulk.amongus.objects.GamePlayer;
import org.bukkit.Location;

public class Task {

    String taskName;
    Location location;
    GameGUI taskGUI;
    TaskType type;
    boolean isCompleted;

    public Task(String taskName, Location location, TaskType type) {
        this.taskName = taskName;
        this.location = location;
        this.type = type;
        isCompleted = false;
    }

    public boolean hasGUI() { return taskGUI != null; }

    public void setTaskGUI(GameGUI gui) { this.taskGUI = gui; }

    public GameGUI getTaskGUI() { return taskGUI; }

    public Location getLocation() { return location; }

    public void runTaskActions(GamePlayer player) {

    }

}
