package models;

public class TaskInform {
    private final String name;
    private String course;
    private final String tape;
    private final float score;
    private final int countComments;

    public TaskInform(String name, String course, String tape, float score, int countComments) {
        this.name = name;
        this.course = course;
        this.tape = tape;
        this.score = score;
        this.countComments = countComments;
    }

    public String getName() {
        return name;
    }

    public String getTape() {
        return tape;
    }

    public float getScore() {
        return score;
    }

    public int getCountComments() {
        return countComments;
    }

    public String getCourse() {
        return course;
    }

    public String toString(){
        return name + " - " + course + ", " + tape + ", " + score + ", " + countComments + ".";
    }
}
