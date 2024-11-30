package models;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    private Map<Task, Integer> scores;

    public Student(String name) {
        this.name = name;
        scores = new HashMap<>();
    }

    public void AddScore(Task task, int score) {
        scores.put(task, score);
    }

    public String getName() {
        return name;
    }

    public Map<Task, Integer> getScores() {
        return scores;
    }
}

