package me.hulk.amongus.objects;

public class GameSettings {

    private int imposters;
    private int players;
    private float walkSpeed;
    private double crewVision;
    private double imposterVision;
    private int discussionTime;
    private int votingTime;

    public GameSettings(int imposters, int size, float walkSpeed, double crewVision, double imposterVision, int discussionTime, int votingTime) {
        this.imposters = imposters;
        this.players = size;
        this.walkSpeed = walkSpeed;
        this.crewVision = crewVision;
        this.imposterVision = imposterVision;
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
}
