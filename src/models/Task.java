package models;

public class Task {
    private String name;
    private String tape;
    private int maxScore;
    private float averageScore;
    private int countComments;

    public Task(String name, String tape, int maxScore) {
        this.name = name;
        this.tape = tape;
        this.maxScore = maxScore;
    }

    public void addАverageScore(float averageScore) {
        this.averageScore = averageScore;
    }

    public void addCountComments(int countComments) {
        this.countComments = countComments;
    }

    public String getName() {
        return name;
    }

    public String getTape() {
        return tape;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public float getAverageScore() {
        return averageScore;
    }

    public int getCountComments() {
        return countComments;
    }
}
