export interface AuthDto {
    login: string;
    password: string;
}

export interface SuccessResponse {
    success: boolean;
}

export type UserRole = "EMPLOYEE" | "MANAGER";

export type EmployeeGrade = "JUNIOR" | "MIDDLE" | "SENIOR";

export interface UserEntity {
    id: number;
    firstName: string;
    lastName: string;
    middleName: string;
    role: UserRole;
    grade: EmployeeGrade;
    location: string;
}

export interface UserResponse extends SuccessResponse {
    user?: UserEntity;
}

export type TaskStatus = "DISTRIBUTED_BY_MANAGER" | "NOT_DISTRIBUTED";

export type TaskPriority = "LOW" | "MEDIUM" | "HIGH";

export interface TaskDto {
    id: number;
    name: string;
    priority: TaskPriority;
    requiredGrade: EmployeeGrade;
    assignmentDate: string;
    minutesToResolve: number;
    completed: false;
    completionDate: string;
    status: TaskStatus;
    employeeId: Optional<number>;
    address: string;
    pointId: number;
}

export interface AddTaskDto
    extends RequireFields<
        Pick<
            Partial<TaskDto>,
            "name" | "status" | "employeeId" | "pointId" | "address"
        >,
        "name"
    > {}

export interface TaskEntity
    extends Omit<TaskDto, "completionDate" | "assignmentDate"> {
    completionDate: Date;
    assignmentDate: Date;
}

export interface TaskWithEmployee extends TaskEntity {
    employee: Optional<UserEntity>;
}
