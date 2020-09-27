package me.hulk.amongus.gui;

import me.hulk.amongus.AmongUs;
import me.hulk.amongus.objects.Game;
import me.hulk.amongus.objects.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

public class GameGUI {

    Inventory inventory;
    HashMap<Integer, ItemStack> invItems = new HashMap<>();

    public GameGUI(int size, String name, ItemStack[] items, int[] slots) {

        int slot = 0;

        inventory = Bukkit.createInventory(null, size, name);
        for (ItemStack item : items) {
            inventory.addItem(item);
            invItems.put(slots[slot], item);
            slot++;
        }

    }

    public Inventory getInventory() {
        return inventory;
    }

    public ItemStack getItem(int slot) {
        return invItems.get(slot);
    }


    public static GameGUI createVotingGUI() {

        Game game = AmongUs.getGame();
        int slot = 12;
        int i = 0;
        ItemStack[] items = new ItemStack[game.getAlivePlayers().size() + 1];
        int[] slots = new int[game.getAlivePlayers().size() + 1];

        for (GamePlayer player : game.getAlivePlayers()) {
            ItemStack head = GUIItem.createItem(Material.PLAYER_HEAD, player.getColor().getTitle() + player.getPlayer().getName());
            SkullMeta skull = (SkullMeta) head.getItemMeta();
            skull.setOwningPlayer(player.getPlayer());
            skull.setDisplayName("Vote for " + ChatColor.translateAlternateColorCodes('&', player.getColor().getTitle() + player.getPlayer().getName()));
            head.setItemMeta(skull);
            items[i] = head;
            slots[i] = slot;
            if (slot == 48) {
                slot = 14;
            } else {
                slot += 9;
            }
            i++;
        }

        ItemStack skip = new ItemStack(Material.ARROW);
        ItemMeta meta = skip.getItemMeta();
        meta.setDisplayName("Skip");
        skip.setItemMeta(meta);
        items[i] = skip;
        slots[i] = 45;

        return new GameGUI(54, "Voting", items, slots);
    }

}
