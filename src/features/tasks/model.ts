import {
    attach,
    createEffect,
    createEvent,
    createStore,
    sample,
} from "effector";
import { employeesModel } from "features/employees";
import { TaskDto, TaskEntity, TaskWithEmployee, api } from "shared/api";
import { closure } from "shared/closure";

const taskDtoToEntity = ({
    assignmentDate,
    completionDate,
    ...rest
}: TaskDto): TaskEntity => {
    return {
        assignmentDate: new Date(assignmentDate),
        completionDate: new Date(completionDate),
        ...rest,
    };
};

const TASKS_PER_PAGE = 5;

interface TasksStore {
    pageNumber: number;
    tasks: TaskEntity[];
}

export const tasksModel = closure(() => {
    const $tasks = createStore<TasksStore>({
        pageNumber: 0,
        tasks: [],
    });
    const $pageCount = $tasks.map((state) =>
        Math.ceil(state.tasks.length / TASKS_PER_PAGE)
    );
    const $pageData = createStore<TaskWithEmployee[]>([]);

    const setPage = createEvent<number>();

    const getPageFx = createEffect(async (state: TasksStore) => {
        const start = state.pageNumber * TASKS_PER_PAGE;
        const slice = state.tasks.slice(start, start + TASKS_PER_PAGE);

        const result: TaskWithEmployee[] = [];

        for (const task of slice) {
            const employee = task.employeeId
                ? await employeesModel.getByIdFx(task.employeeId)
                : null;

            result.push({
                ...task,
                employee,
            });
        }

        return result;
    });

    const addFx = attach({ effect: api.addTask });
    const getFx = attach({ effect: api.getTasks });

    sample({
        clock: setPage,
        source: $tasks,
        fn: (state, pageNumber) => ({ ...state, pageNumber }),
        target: getPageFx,
    });

    sample({
        clock: getFx.doneData,
        source: getFx,
        fn: (_, tasks) => ({
            pageNumber: 0,
            tasks: tasks.map(taskDtoToEntity),
        }),
        target: getPageFx,
    });

    $tasks.on(addFx.doneData, (state, data) => ({
        pageNumber: state.pageNumber,
        tasks: [...state.tasks, taskDtoToEntity(data)],
    }));
    $tasks.on(getFx.doneData, (state, data) => ({
        pageNumber: state.pageNumber,
        tasks: data.map(taskDtoToEntity),
    }));

    $pageData.on(getPageFx.doneData, (state, data) => data);

    return {
        $tasks,
        $pageData,
        $pageCount,
        addFx,
        getFx,
        getPageFx,
        setPage,
    };
});
