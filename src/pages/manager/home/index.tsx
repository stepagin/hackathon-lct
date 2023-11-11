import React from "react";

import { ManagerPage } from "../template";
import { CurrentTasksWidget } from "widgets/current-tasks";

const HomePage: React.FC = () => {
    return <ManagerPage>
        <CurrentTasksWidget />
    </ManagerPage>;
};

export default HomePage;
