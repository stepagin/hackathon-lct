import React from "react";

import { Page } from "shared/ui";
import { NavigationBar } from "widgets/navigation";

import styles from "./style.module.css";

export const ManagerPage: React.FC<React.ComponentProps<typeof Page>> = ({
    children,
    className: classes,
}) => {
    const className = styles.page + (classes ? ` ${classes}` : "");

    return (
        <Page className={className} header={<NavigationBar />}>
            {children}
        </Page>
    );
};
