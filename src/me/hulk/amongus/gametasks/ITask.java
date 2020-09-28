package me.hulk.amongus.gametasks;

import me.hulk.amongus.gui.GameGUI;
import me.hulk.amongus.objects.GamePlayer;

public interface ITask {

    GameGUI generateTaskGUI();

    void handleGUIClick(GamePlayer player, int slot);

    void runTaskActions(GamePlayer player);

    void completeTask();

}
