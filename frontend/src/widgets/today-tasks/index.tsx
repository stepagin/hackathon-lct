import { useEvent, useStore } from "effector-react";
import { tasksModel } from "features/tasks";
import React, { useEffect } from "react";

import { Button, Card } from "shared/ui";
import { ModelTable } from "shared/ui/table";

import styles from "./style.module.css";

export const TodayTasksWidget: React.FC = () => {
    const page = useStore(tasksModel.$pageData);

    const setPage = useEvent(tasksModel.setPage);
    const pendingPage = useStore(tasksModel.getPageFx.pending);
    const pendingTasks = useStore(tasksModel.getFx.pending);

    useEffect(() => {
        tasksModel.getFx();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const testData = [
        {address: 'home1', name: 'name1', employeeId: '2'},
        {address: 'home2', name: 'name1', employeeId: '2'},
        {address: 'home3', name: 'name1', employeeId: '2'},
        {address: 'home4', name: 'name1', employeeId: '2'},
        {address: 'home5', name: 'name1', employeeId: '2'},
        {address: 'home6', name: 'name1', employeeId: '2'},
        {address: 'home7', name: 'name1', employeeId: '2'},
        {address: 'home8', name: 'name1', employeeId: '2'},
    ]
    const testDataColumns = {
        address: "Адрес",
        name: "Наименование",
        employeeId: "Сотрудник",
    }

    return (
        <Card className={styles.todayWidget} loading={pendingPage || pendingTasks}>
            <h1 className={styles.title}>Задачи на сегодня</h1>
            <Button color="secondary">Добавить задачу</Button>
            <ModelTable
                // data={page}
                data={testData}
                displayColumns={testDataColumns}
            />
        </Card>
    );
};
