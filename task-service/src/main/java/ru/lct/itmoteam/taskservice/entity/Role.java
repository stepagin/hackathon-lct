package ru.lct.itmoteam.taskservice.entity;

public enum Role {
    EMPLOYEE, MANAGER;
    public static boolean isCorrect(String role) {
        try {
            Role.valueOf(role);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
