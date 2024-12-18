package models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Tasks")
public class TaskEntity {
    public static final String TAPE_COLUMN = "tape";
    public static final String COURSE_COLUMN = "course";

    @DatabaseField(generatedId = true)
    private long taskId;

    @DatabaseField(canBeNull = false)
    private String nameTask;

    @DatabaseField(canBeNull = false)
    private String course;

    @DatabaseField()
    private String tape;

    @DatabaseField()
    private float score;

    @DatabaseField()
    private int countComments;

    public TaskEntity() {}

    public TaskEntity(String nameTask, String course, String tape, float score, int countComments) {
        this.nameTask = nameTask;
        this.course = course;
        this.tape = tape;
        this.score = score;
        this.countComments = countComments;
    }

    public String getNameTask() {
        return nameTask;
    }

    public String getCourse() {
        return course;
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
}
