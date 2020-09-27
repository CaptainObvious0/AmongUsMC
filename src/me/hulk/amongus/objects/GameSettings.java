package me.hulk.amongus.objects;

public class GameSettings {

    private int imposters;
    private int players;
    private float walkSpeed;
    private double crewVision;
    private double imposterVision;
    private int discussionTime;
    private int votingTime;
    private int meetingCooldown;
    private int killCooldown;

    public GameSettings(int imposters, int size, float walkSpeed, double crewVision, double imposterVision,
                        int discussionTime, int votingTime, int meetingCooldown, int killCooldown) {
        this.imposters = imposters;
        this.players = size;
        this.walkSpeed = walkSpeed;
        this.crewVision = crewVision;
        this.imposterVision = imposterVision;
        this.meetingCooldown = meetingCooldown;
        this.killCooldown = killCooldown;
        this.discussionTime = discussionTime;
        this.votingTime = votingTime;
    }

    public void setImposters(int imposters) {
        this.imposters = imposters;
    }

    public int getImposters() {
        return this.imposters;
    }

    public int getSize() {
        return players;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public double getCrewVision() {
        return crewVision;
    }

    public void setCrewVision(double crewVision) {
        this.crewVision = crewVision;
    }
    public double getImposterVision() {
        return imposterVision;
    }

    public void setImposterVision(double imposterVision) {
        this.imposterVision = imposterVision;
    }

    public int getMeetingCooldown() { return meetingCooldown; }

    public void setMeetingCooldown(int cooldown) { this.meetingCooldown = meetingCooldown; }

    public int getKillCooldown() { return this.killCooldown; }

    public void setKillCooldown(int cooldown) { this.killCooldown = cooldown; }


}
