import React from "react";

import styles from "./style.module.css";

import { ManagerPage } from "../template";
import { CurrentTasksWidget } from "widgets/current-tasks";
import { EmpoyeesListWidget } from "widgets/employees-list";
import { TodayTasksWidget } from "widgets/today-tasks";

const HomePage: React.FC = () => {
    return <ManagerPage>
        <div className={styles.widgetsContainer}>
            <div className={styles.currAndEmplContainer}>
                <CurrentTasksWidget />
                <EmpoyeesListWidget />
            </div>
            <TodayTasksWidget />
        </div>
    </ManagerPage>;
};

export default HomePage;
