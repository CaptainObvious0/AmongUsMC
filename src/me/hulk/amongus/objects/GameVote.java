package me.hulk.amongus.objects;

import me.hulk.amongus.AmongUs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameVote {

    // Player voting, Player that they voted
    private HashMap<GamePlayer, GamePlayer> playerVotes = new HashMap<>();
    private ArrayList<GamePlayer> skippedVoting = new ArrayList<>();
    int skips;

     public void addVote(GamePlayer playerVoting, GamePlayer playerVoted) {
         if (playerVoted == null) {
             skips++;
             skippedVoting.add(playerVoting);
             return;
         }
         playerVotes.put(playerVoting, playerVoted);
     }

     public GamePlayer getPlayerVoted() {

         HashMap<GamePlayer, Integer> votes = new HashMap<>();

         for (GamePlayer player : playerVotes.values()) {
             if (votes.get(player) != null) {
                 int beforeVotes = votes.get(player);
                 votes.put(player, beforeVotes + 1);
             } else {
                 votes.put(player, 1);
             }
         }

         GamePlayer playerVoted = null;
         int numVotes = 0;

         for (Map.Entry<GamePlayer, Integer> vote : votes.entrySet()) {
             if (vote.getValue() > numVotes) {
                 playerVoted = vote.getKey();
             } else if (vote.getValue() == numVotes) {
                 playerVoted = null;
             }
         }

         if (playerVoted != null && numVotes > skips) return playerVoted;
         return null;

     }

     public void printVotingResults() {
         Bukkit.broadcastMessage("VOTING RESULTS: ");
         Bukkit.broadcastMessage(" ");

         HashMap<GamePlayer, String> totalVotes = new HashMap<>();

         for (Map.Entry<GamePlayer, GamePlayer> vote : playerVotes.entrySet()) {
             if (totalVotes.get(vote.getValue()) != null) {
                 String stringToEdit = totalVotes.get(vote.getValue());
                 stringToEdit += ", " + vote.getKey().getPlayer().getName();
                 totalVotes.put(vote.getValue(), stringToEdit);
             } else {
                 totalVotes.put(vote.getValue(), vote.getKey().getPlayer().getName());
             }
         }

         for (Map.Entry<GamePlayer, String> voting : totalVotes.entrySet()) {
             Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), () -> {
                 Bukkit.broadcastMessage(voting.getKey().getColor().getTitle() + voting.getKey().getPlayer().getName() + " = " + voting.getValue());
             }, 15);

         }

         Bukkit.broadcastMessage("Skipped = " + skippedVoting.toString());


     }

     public String color(String msg) {
         return ChatColor.translateAlternateColorCodes('&', msg);
     }

}
