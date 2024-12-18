package models;

public class TaskInformFromDb {
    public static TaskInform map(TaskEntity task) {
        return new TaskInform(task.getNameTask(), task.getCourse(), task.getTape(), task.getScore(), task.getCountComments());
    }
}
