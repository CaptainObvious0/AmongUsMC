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

import java.util.Arrays;
import java.util.HashMap;

public class GameGUI {

    Inventory inventory;
    HashMap<Integer, ItemStack> invItems = new HashMap<>();

    public GameGUI(int size, String name, ItemStack[] items, int[] slots) {

        int slot = 0;

        inventory = Bukkit.createInventory(null, size, name);
        for (ItemStack item : items) {
            inventory.setItem(slots[slot], item);
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

    public static GameGUI createEmergencyMeetingGUI() {
        ItemStack button = new ItemStack(Material.RED_STAINED_GLASS);
        ItemMeta meta = button.getItemMeta();
        meta.setDisplayName(GUIItem.color("&cCall an emergency meeting"));
        button.setItemMeta(meta);

        ItemStack[] invItems = new ItemStack[0];
        int[] slots = new int[0];
        invItems[0] = button;
        slots[0] = 4;

        return new GameGUI(9, GUIItem.color("&4Emergency Meeting"), invItems, slots);

    }

    public static GameGUI createGameSettingsGUI() {

        ItemStack imposters = new ItemStack(Material.REDSTONE);
        ItemStack players = new ItemStack(Material.PLAYER_HEAD);
        ItemStack walkSpeed = new ItemStack(Material.DIAMOND_BOOTS);
        ItemStack crewVision = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemStack imposterVision = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemStack meetingCooldown = new ItemStack(Material.REDSTONE_TORCH);
        ItemStack discussionTime = new ItemStack(Material.PAPER);
        ItemStack votingTime = new ItemStack(Material.LEGACY_BOOK_AND_QUILL);
        ItemStack killCooldown = new ItemStack(Material.IRON_SWORD);
        ItemStack shortTasks = new ItemStack(Material.COAL_ORE);
        ItemStack commonTasks = new ItemStack(Material.IRON_ORE);
        ItemStack longTasks = new ItemStack(Material.DIAMOND_ORE);

        imposters = addName(imposters, "&cImposters");
        players = addName(players, "&2Players");
        walkSpeed = addName(walkSpeed, "&aWalk Speed");
        crewVision = addName(crewVision, "&7Crew Vision");
        imposterVision = addName(imposterVision, "&8Imposter Vision");
        meetingCooldown = addName(meetingCooldown, "&dMeeting Cooldown");
        discussionTime = addName(discussionTime, "&eDiscussion Time");
        votingTime = addName(votingTime, "&6Voting Time");
        killCooldown = addName(killCooldown, "&4Kill Cooldown");
        shortTasks = addName(shortTasks,"&1Short Tasks");
        commonTasks = addName(commonTasks, "&3Common Tasks");
        longTasks = addName(longTasks, "&dLong Tasks");

        ItemStack[] itemList = (ItemStack[]) Arrays.asList(imposters, players, walkSpeed, crewVision, imposterVision, meetingCooldown, discussionTime, votingTime, killCooldown, shortTasks, commonTasks, longTasks).toArray();
        int[] slots = Arrays.asList(11, 20, 29, 38, 13, 22, 31, 40, 15, 24, 33).stream().mapToInt(Integer::intValue).toArray();

        return new GameGUI(54, "Game Settings", itemList, slots);

    }

    private static ItemStack addName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(GUIItem.color(name));
        meta.setLore(Arrays.asList(GUIItem.color("&bUse &c&lRIGHT CLICK &bto increase"), GUIItem.color("&bUse &c&lLEFT CLICK &bto decrease")));
        item.setItemMeta(meta);
        return item;
    }


}
