package ru.lct.itmoteam.taskservice.entity;

public enum TaskPriority {
    LOW, MEDIUM, HIGH;

    public static boolean isCorrect(String priority) {
        try {
            TaskPriority.valueOf(priority);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
