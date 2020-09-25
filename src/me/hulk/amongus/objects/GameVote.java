package me.hulk.amongus.objects;

import java.util.HashMap;
import java.util.Map;

public class GameVote {

    // Player voting, Player that they voted
    private HashMap<GamePlayer, GamePlayer> playerVotes = new HashMap<>();
    int skips;

     public void addVote(GamePlayer playerVoting, GamePlayer playerVoted) {
         if (playerVoted == null) {
             skips++;
             return;
         }
         playerVotes.put(playerVoting, playerVoted);
     }

     public GamePlayer getPlayerVoted() {

         GamePlayer playerVoted;
         int votes = 0;
         boolean tie = false;

         for (Map.Entry<GamePlayer, GamePlayer> vote : playerVotes.entrySet()) {
             if (vote.)
         }

     }

}
