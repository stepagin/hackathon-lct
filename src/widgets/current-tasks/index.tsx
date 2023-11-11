import { useEvent, useStore } from "effector-react";
import { tasksModel } from "features/tasks";
import React, { useEffect } from "react";

import { Card } from "shared/ui";
import { ModelTable } from "shared/ui/table";

export const CurrentTasksWidget: React.FC = () => {
    const page = useStore(tasksModel.$pageData);

    const setPage = useEvent(tasksModel.setPage);
    const pendingPage = useStore(tasksModel.getPageFx.pending);
    const pendingTasks = useStore(tasksModel.getFx.pending);

    useEffect(() => {
        tasksModel.getFx();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    return (
        <Card loading={pendingPage || pendingTasks}>
            <h1>Сейчас выполняются</h1>
            <ModelTable
                data={page}
                displayColumns={{
                    address: "Адрес",
                    name: "Наименование",
                    employeeId: "Сотрудник",
                }}
            />
        </Card>
    );
};
