package me.hulk.amongus.enums;

public enum PlayerColors {

    RED("&4"),
    BLACK("&9"), // TODO - change color
    GREEN("&2"),
    CYAN("&b"),
    LIME("&a"),
    BLUE("&3"), // TODO
    WHITE("&f"),
    YELLOW("&e"),
    GRAY("&7"),
    ORANGE("&6"),
    BROWN("&"); // TODO - hmmm

    String title;

    PlayerColors(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
