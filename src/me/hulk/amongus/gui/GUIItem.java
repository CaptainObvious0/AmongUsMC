package me.hulk.amongus.gui;

import me.hulk.amongus.gui.ClickAction;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIItem {

    ItemStack item;
    ClickAction action;

    public GUIItem(ItemStack item, ClickAction action) {
        this.item = item;
        this.action = action;
    }

    public ItemStack getItem() {
        return item;
    }

    public static ItemStack createItem(Material type, String name) {
        ItemStack toReturn = new ItemStack(type);
        ItemMeta meta = toReturn.getItemMeta();
        meta.setDisplayName(color(name));
        toReturn.setItemMeta(meta);
        return toReturn;
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
