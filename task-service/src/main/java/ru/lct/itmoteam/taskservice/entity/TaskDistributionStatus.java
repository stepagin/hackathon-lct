package ru.lct.itmoteam.taskservice.entity;

public enum TaskDistributionStatus {
    DISTRIBUTED_AUTOMATICALLY, DISTRIBUTED_BY_MANAGER, NOT_DISTRIBUTED;

    public static boolean isCorrect(String status) {
        try {
            TaskDistributionStatus.valueOf(status);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
