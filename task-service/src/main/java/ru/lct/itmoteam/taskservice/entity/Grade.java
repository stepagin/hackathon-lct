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

    public boolean suitableForTask(Grade taskGrade) {

        if (this == Grade.JUNIOR && taskGrade != Grade.JUNIOR) {
            return false;
        }
        if (this == Grade.MIDDLE && taskGrade == Grade.SENIOR) {
            return false;
        }
        return true;
    }
}
