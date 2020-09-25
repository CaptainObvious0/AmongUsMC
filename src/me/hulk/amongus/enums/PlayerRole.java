package me.hulk.amongus.enums;

public enum  PlayerRole {

    CREWMATE("&b&lCrewmate"),
    DEAD("&8&lDead"),
    IMPOSTER("&4&lImposter"),
    SPECTATOR("&7&lSpectator");

    String title;

    PlayerRole(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
