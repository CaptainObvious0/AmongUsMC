package me.hulk.amongus.gametasks.tasks;

import me.hulk.amongus.AmongUs;
import me.hulk.amongus.gametasks.ITask;
import me.hulk.amongus.gametasks.Task;
import me.hulk.amongus.gametasks.TaskType;
import me.hulk.amongus.gui.GameGUI;
import me.hulk.amongus.objects.GamePlayer;

public class AdminSwipe extends Task implements ITask {

    public AdminSwipe() {
        super("Admin Swipe", AmongUs.getGameConfig().getTaskLocation("Admin Swipe"), TaskType.COMMON);
        this.setTaskGUI(generateTaskGUI());
    }

    @Override
    public GameGUI generateTaskGUI() {
        return null;
    }

    @Override
    public void handleGUIClick(GamePlayer player, int slot) {

    }

    @Override
    public void runTaskActions(GamePlayer player) {

    }

    @Override
    public void completeTask() {

    }

}
