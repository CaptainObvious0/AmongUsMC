package me.hulk.amongus.gui;

import me.hulk.amongus.AmongUs;
import me.hulk.amongus.objects.Game;
import me.hulk.amongus.objects.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GameGUI {

    Inventory inventory;
    HashMap<Integer, GUIItem> invItems = new HashMap<>();

    public GameGUI(int size, String name, GUIItem[] items, int[] slots) {

        int slot = 0;

        inventory = Bukkit.createInventory(null, size, name);
        for (GUIItem item : items) {
            inventory.addItem(item.getItem());
            invItems.put(slots[slot], item);
            slot++;
        }

    }

    public Inventory getInventory() {
        return inventory;
    }

    public static GameGUI createVotingGUI() {

        Game game = AmongUs.getGame();
        int slot = 12;

        for (GamePlayer player : game.getAlivePlayers()) {
            ItemStack head = GUIItem.createItem(Material.PLAYER_HEAD, player.getColor().getTitle() + player.getPlayer().getName());

        }

        return new GameGUI(0, "", null, null);
    }

}
