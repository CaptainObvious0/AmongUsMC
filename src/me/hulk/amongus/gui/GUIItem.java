package me.hulk.amongus.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIItem {

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
