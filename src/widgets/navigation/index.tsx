import React from "react";

import { UserCard } from "entities/user";
import { MOCK_USER } from "shared/api";
import { Link, useLocation } from "react-router-dom";
import { Icon } from "shared/ui";

import styles from "./style.module.css";

const ROUTE_NAMES = {
    "/employees": "Сотрудники",
    "/tasks": "Задачи",
    "/points": "Точки",
    "/history": "История",
};

export const NavigationBar: React.FC = () => {
    const location = useLocation();

    const homeIcon =
        location.pathname === "/" ? "home-active" : "home-inactive";

    return (
        <div className={styles.navbar}>
            <nav className={styles.navigation}>
                <Link to="/">
                    <Icon type={homeIcon} />
                </Link>

                {Object.entries(ROUTE_NAMES).map(([key, value], index) => {
                    const className =
                        styles.link +
                        (key === location.pathname ? ` ${styles.active}` : "");

                    return (
                        <Link className={className} key={index} to={key}>
                            {value}
                        </Link>
                    );
                })}
            </nav>

            <UserCard user={MOCK_USER} />
        </div>
    );
};
