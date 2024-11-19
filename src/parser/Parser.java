package parser;

import models.Course;
import models.Student;
import models.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Parser {

    public static Course parseCSV(String path, String name) {
        Map<Integer, Task> tasks = new HashMap<>();
        Map<Integer, Integer> tasksScore = new HashMap<>();
        ArrayList<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // пропуск первой строки с названием тем
            String line;
            br.readLine();

            // запись заданий и их столбцов
            line = br.readLine();
            String[] tacksName = line.split(";");
            line = br.readLine();
            String[] tacksScore = line.split(";");

            for (int i = 0; i < tacksName.length; i++){
                if (tacksName[i].startsWith("Упр:")){
                    tasks.put(i, new Task(tacksName[i].substring(5), "Упр", checkScore(tacksScore[i])));
                }
                else if (tacksName[i].startsWith("ДЗ:")){
                    tasks.put(i, new Task(tacksName[i].substring(4), "ДЗ", checkScore(tacksScore[i])));
                }
                else if (tacksName[i].startsWith("КВ:")){
                    tasks.put(i, new Task(tacksName[i].substring(4), "КВ", checkScore(tacksScore[i])));
                }
            }

            // Запись студентов
            var count = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                Student student = new Student(values[0]);
                students.add(student);
                for (int i: tasks.keySet()) {
                    int score = 0;
                    try {
                        score= Integer.parseInt(values[i]);
                    } catch (NumberFormatException e) {
                        System.out.println("Неверные типы данных в csv файле" + i + "   " + values[i]);
                    }
                    student.AddScore(tasks.get(i), score);
                    if (tasksScore.containsKey(i)) {
                        tasksScore.put(i, tasksScore.get(i) + score);
                    } else {
                        tasksScore.put(i, score);
                    }
                }
                count++;
            }

            // Добавление средней оценки
            for (int i: tasksScore.keySet()) {
                tasks.get(i).addАverageScore((float) tasksScore.get(i) / count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Course(name, students, new ArrayList<>(tasks.values()));
    }

    protected static int checkScore(String value) {
        int score = 0;
        try {
            score= Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println("Неверные типы данных в csv файле" + value);
        }
        return score;
    }
}
