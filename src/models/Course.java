package models;

import java.util.ArrayList;
import java.util.Map;

public class Course {
    private String name;
    private ArrayList<Student> students;
    private ArrayList<Task> tasks;

    public Course(String name, ArrayList<Student> students, ArrayList<Task> tasks) {
        this.name = name;
        this.students = students;
        this.tasks = tasks;
    }

    public ArrayList<String> toListStudents() {
        var list = new ArrayList<String>();
        for (Student student: students) {
            StringBuilder string = new StringBuilder(student.getName() + " : ");
            for (Map.Entry<Task, Integer> task: student.getScores().entrySet()) {
                string.append(task.getKey().getName()).append(" - ").append(task.getValue()).append("; ");
            }
            list.add(string.toString());
        }
        return list;
    }

    public ArrayList<String> toListTasks() {
        var list = new ArrayList<String>();
        for (Task task: tasks) {
            StringBuilder string = new StringBuilder(task.getName());
            string.append(": Тип - ")
                    .append(task.getTape())
                    .append(",Максимальная оценка - ")
                    .append(task.getMaxScore())
                    .append(",Средняя оценка - ")
                    .append(task.getAverageScore())
                    .append("; ");
            list.add(string.toString());
        }
        return list;
    }
}
