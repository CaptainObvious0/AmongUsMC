package me.hulk.amongus.enums;

public enum PlayerColors {

    RED("&4"),
    BLACK("&0"),
    GREEN("&2"),
    CYAN("&b"),
    LIME("&a"),
    BLUE("&1"),
    WHITE("&f"),
    YELLOW("&e"),
    GRAY("&7"),
    ORANGE("&6"),
    PURPLE("&5");

    String title;

    PlayerColors(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
