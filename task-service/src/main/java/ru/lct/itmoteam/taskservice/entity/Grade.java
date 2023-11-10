package ru.lct.itmoteam.taskservice.entity;

public enum Grade {
    JUNIOR, MIDDLE, SENIOR;
    public static boolean isCorrect(String grade) {
        try {
            Grade.valueOf(grade);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
