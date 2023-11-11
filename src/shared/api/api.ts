import { http } from "./config";
import {
    AddTaskDto,
    AuthDto,
    TaskDto,
    UserEntity,
    UserResponse,
} from "./models";

const routesConfig = http.createRoutesConfig({
    login: http.createRoute<AuthDto, UserResponse>((data) => ({
        url: "/login",
        method: "post",
        data,
    })),
    register: http.createRoute<AuthDto, UserResponse>((data) => ({
        url: "/register",
        method: "post",
        data,
    })),
    addTask: http.createRoute<AddTaskDto, TaskDto>((data) => ({
        url: "/tasks/add",
        method: "post",
        data,
    })),
    getTasks: http.createRoute<void, TaskDto[]>(() => ({
        url: "/tasks",
        method: "get",
    })),
    getEmployeeById: http.createRoute<number, UserEntity>((id) => ({
        url: `/employees/${id}`,
        method: "get",
    })),
});

export const MOCK_USER: UserEntity = {
    id: 1,
    firstName: "Степан",
    lastName: "Тикунов",
    middleName: "Викторович",
    role: "MANAGER",
    grade: "JUNIOR",
    location: "Белорусская, 6",
};

export const api = routesConfig.build();
